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
    private int innovationCounter;

    public GenomeMutator(MutatorParams params){
        this.params = params;
        innovationCounter = 0;
    }

    public void mutateGenome(Genome genome) {
        for(MutationType mType:randomMutations()){
            NetworkMutation mutation = null;
            switch (mType){
                case NEW_NODE:
                    mutation = newNodeMutation(genome);
                    break;
                case NEW_CONNECTION:
                    mutation = newConnectionMutation(genome);
                    break;
                case MUTATE_WEIGHT:
                    //Not to be added to memory, thereby skip all code regarding innovation number
                    weightMutation(genome);
                    continue;
            }

            NetworkMutation prevMutation = findMutationInMemory(mutation);
            if(prevMutation == null){
                mutation.innovationNumber = innovationCounter;
                innovationCounter++;
            }else{
                mutation.innovationNumber = prevMutation.innovationNumber;
            }

            try {
                mutation.applyToGenome(genome);
                networkMutationMemory.add(mutation);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error while applying mutation to genome!");
            }
        }
    }

    private NetworkMutation findMutationInMemory(NetworkMutation mutation) {
        //TODO, return null if not found.
        return null;
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

    private NewNode newNodeMutation(Genome genome) {
        //TODO...
        return null;
    }

    private NewConnection newConnectionMutation(Genome genome) {
        //TODO...
        return null;
    }

    private void weightMutation(Genome g){
        //TODO...
    }

    private boolean existInMutationMemory(NetworkMutation mutation){
        return networkMutationMemory.contains(mutation);
    }


    public void clearMutationMemory(){
        networkMutationMemory = new ArrayList<>();
        innovationCounter = 0;
    }


}
