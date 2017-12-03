package Mutation;

import Network.Genome;

public class NewNode extends NetworkMutation {
    public NewNode(int inputNodeID, int outputNodeID) {
        super(inputNodeID, outputNodeID);
    }

    public NewNode(int inputNodeID, int outputNodeID, int innovationNumber) {
        super(inputNodeID, outputNodeID, innovationNumber);
    }

    @Override
    public void applyToGenome(Genome genome) {

    }
}
