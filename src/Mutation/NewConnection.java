package Mutation;

import Network.Genome;

public class NewConnection extends NetworkMutation{
    public NewConnection(int inputNodeID, int outputNodeID) {
        super(inputNodeID, outputNodeID);
    }

    public NewConnection(int inputNodeID, int outputNodeID, int innovationNumber) {
        super(inputNodeID, outputNodeID, innovationNumber);
    }

    @Override
    public void applySpecificToGenome(Genome genome) {

    }
}
