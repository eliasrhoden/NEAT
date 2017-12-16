package Mutation;

import Network.Genome;

public class MutateWeight extends NetworkMutation{

    public MutateWeight(int inputNodeID, int outputNodeID) {
        super(inputNodeID, outputNodeID);
    }

    public MutateWeight(int inputNodeID, int outputNodeID, int innovationNumber) {
        super(inputNodeID, outputNodeID, innovationNumber);
    }

    @Override
    public void applySpecificToGenome(Genome genome) {

    }
}
