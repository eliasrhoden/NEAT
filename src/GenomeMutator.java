package Network;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elias on 2017-05-20.
 */
public class GenomeMutator {
    private enum MutationType{
        NEW_NODE,NEW_CONNECTION, MUTATE_WEIGHT;
    }

    private int lastIncreaseInnovationNR = 0;
    private Params params;
    private ArrayList<NetworkMutation> networkMutationMemory = new ArrayList<>();
    private ArrayList<Integer> innovationNumberMemory = new ArrayList<>();

    public GenomeMutator(Params params){
        this.params = params;
    }

    public void mutateGenome(Genome genome, int lastInnovationNumber) {
        int innovationIncrease = 0;
        switch(randomMutation()){
            case NEW_NODE:
                innovationIncrease = newNodeMutation(genome);
                break;
            case NEW_CONNECTION:
                innovationIncrease = newConnectionMutation(genome);
                break;
            case MUTATE_WEIGHT:
                weightMutation(genome);
                innovationIncrease = 0;
                break;
            default:
                //No mutation
                innovationIncrease = 0;
        }
        lastIncreaseInnovationNR = innovationIncrease;
    }

    private MutationType randomMutation() {
        Random random = new Random();
        MutationType[] possibleMutations = MutationType.values();




        //TODO Return a random mutation...
        return null;
    }

    private int newNodeMutation(Genome genome) {
        //TODO...
        return 0;
    }

    private int newConnectionMutation(Genome genome) {
        int innovationIncrease = 0;
        NetworkMutation mutation = createNewConnection(genome);
        try{
            int index = findIndexInMemory(mutation);
        }catch(Exception e){
            innovationIncrease = 1;
        }
        return innovationIncrease;
    }

    private NetworkMutation createNewConnection(Genome genome){
        Random r = new Random();
        int nrofInputs, nrOfHidden, nrOfOutput;
        nrofInputs = genome.getInputNodeIDs().length;
        nrOfHidden = genome.getHiddenNodeIDs().length;
        nrOfOutput = genome.getOutputNodeIDs().length;

        int[] possibleInputs = new int[nrofInputs + nrOfHidden];
        int[] possibleOutputs = new int[nrOfHidden + nrOfOutput];

        //TODO...

        return null;
    }

    private void weightMutation(Genome g){

    }

    private int findIndexInMemory(NetworkMutation mutation) throws Exception {
        int res = -1;
        for(int i = 0;i<networkMutationMemory.size();i++){
            if(networkMutationMemory.get(i).equals(mutation)){
                res = i;
                break;
            }
        }
        if(res == -1){
            throw new Exception("Mutation not found in memory!");
        }
        return res;
    }

    private boolean existInMutationMemory(NetworkMutation mutation){
        boolean res = false;
        for(NetworkMutation nM:networkMutationMemory){
            if(nM.equals(mutation))
                res = true;
        }
        return res;
    }

    public int getLastMutationInnovationIncrease(){
        return lastIncreaseInnovationNR;
    }

    public void clearMutationMemory(){
        networkMutationMemory = new ArrayList<>();
        innovationNumberMemory = new ArrayList<>();
        lastIncreaseInnovationNR = 0;
    }

    class NetworkMutation {
        private MutationType mutationType;
        private int inputNodeID;
        private int outputNodeID;

        public NetworkMutation(MutationType mutationType, int inputNodeID, int outputNodeID){
            this.mutationType = mutationType;
            this.inputNodeID = inputNodeID;
            this.outputNodeID = outputNodeID;
        }

        public boolean equals(NetworkMutation otherNet){
            boolean res = true;
            res = res && otherNet.mutationType == this.mutationType;
            res = res && otherNet.inputNodeID == this.inputNodeID;
            res = res && otherNet.outputNodeID == this.outputNodeID;
            return res;
        }
    }


}
