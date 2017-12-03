package Mutation;

import Network.Genome;

public abstract class NetworkMutation {
    private int inputNodeID;
    private int outputNodeID;
    public int innovationNumber;

    public NetworkMutation(int inputNodeID, int outputNodeID){
        this.inputNodeID = inputNodeID;
        this.outputNodeID = outputNodeID;
    }

    public NetworkMutation(int inputNodeID, int outputNodeID, int innovationNumber){
        this.inputNodeID = inputNodeID;
        this.outputNodeID = outputNodeID;
        this.innovationNumber = innovationNumber;
    }

    public boolean equals(NetworkMutation otherNet){
        boolean res = true;
        boolean sameMutationSUbClas = this.getClass().equals(otherNet.getClass());
        res = res && otherNet.inputNodeID == this.inputNodeID;
        res = res && otherNet.outputNodeID == this.outputNodeID;
        return res;
    }

    public abstract void applyToGenome(Genome genome);
}