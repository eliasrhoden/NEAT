package Mutation;

import Network.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by elias on 2017-05-20.
 */
public class GenomeMutator {
    private enum MutationType{
        NEW_NODE,NEW_CONNECTION, MUTATE_WEIGHT;
    }

    private MutatorParams params;
    private ArrayList<NetworkMutation> networkMutationMemory = new ArrayList<>();

    public GenomeMutator(MutatorParams params){
        this.params = params;
    }

    public void mutateGenome(Genome genome) {
        for(MutationType mType:randomMutations()){
            NetworkMutation mutation = null;
            switch (mType){
                case NEW_NODE:

                    break;
                case NEW_CONNECTION:

                    break;
                case MUTATE_WEIGHT:

                    break;
            }
            int prevOccurredInnovationNr = findMutationInMemory(mutation);
            if(prevOccurredInnovationNr>=0){
                mutation.innovationNumber = prevOccurredInnovationNr;
            }else{
                mutation.innovationNumber = genome.getHighestInnovationNumber() + 1;
            }
            mutation.applyToGenome(genome);
        }
    }

    private int findMutationInMemory(NetworkMutation mutation) {
        return 0;
    }

    public List<MutationType> randomMutations() {
        Random random = new Random();
        ArrayList<MutationType> res = new ArrayList<>();

        if(random.nextInt(100)<params.PROBABILITY_OF_NEW_NODE*100){
            res.add(MutationType.NEW_NODE);
        }

        if(random.nextInt(100)<params.PROBABILITY_OF_NEW_CONNECTION*100){
            res.add(MutationType.NEW_CONNECTION);
        }

        if(random.nextInt(100)<params.PROBABILITY_OF_WEIGHT_MUTATION*100){
            res.add(MutationType.MUTATE_WEIGHT);
        }

        return res;
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
        //TODO...
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


    public void clearMutationMemory(){
        networkMutationMemory = new ArrayList<>();
    }


}
