import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elias on 2017-05-20.
 */
public class GenomeMutator {

    private static final double PROBABILITY_OF_NEW_NODE = 0.5;
    private static final double PROBABILITY_OF_NEW_CONNECTION = 0.5;
    private static final double PROBABILITY_OF_WEIGHT_MUTATION = 0.5;
    private static final double PROBABILITY_OF_NEW_RANDOM_WEIGHT_ON_CONNECTION = 0.5;
    private static final double PROBABILITY_OF_SLIGHT_CHANGE_OF_WEIGHT_ON_CONNECTION = 0.5;

    private enum MutationType{
        NEW_NODE,NEW_CONNECTION, MUTATE_WEIGHT;
    }

    private enum WeightMutation{
        RANDOM_WEIGHT, SLIGHT_CHANGE_OF_WEIGHT;
    }

    private int lastMutationIncreaseInInnovationNumber = 0;
    private Random random = new Random();
    private ArrayList<NetworkMutation> networkMutationMemory = new ArrayList<>();
    private ArrayList<Integer> innovationNumberMemory = new ArrayList<>();

    public void mutateGenome(Genome genome, int lastInovationNumber){
        lastMutationIncreaseInInnovationNumber = 0;
        int randomNr =  random.nextInt(100);

        NetworkMutation mutation;

        if(randomNr < PROBABILITY_OF_NEW_NODE*100){
            mutation = createPreMutation(genome, MutationType.NEW_NODE);
            if(existInMutationMemory(mutation)){
                int indexOfPrevMutation = findIndexOf(mutation);
                addNewNode(genome,innovationNumberMemory.get(indexOfPrevMutation));
            }else{
                lastInovationNumber++;
                addNewNode(genome,lastInovationNumber);
            }
            //TODO
        }

        randomNr =  random.nextInt(100);

        if(randomNr < PROBABILITY_OF_NEW_CONNECTION*100){
            mutation = createPreMutation(genome, MutationType.NEW_CONNECTION);
            if(existInMutationMemory(mutation)){

            }

        }
        randomNr =  random.nextInt(100);
        if(randomNr< PROBABILITY_OF_WEIGHT_MUTATION*100) {
            if (randomNr < PROBABILITY_OF_NEW_RANDOM_WEIGHT_ON_CONNECTION * 100) {
                mutateConnectinWeight(genome,WeightMutation.RANDOM_WEIGHT);
            }

            if (randomNr < PROBABILITY_OF_SLIGHT_CHANGE_OF_WEIGHT_ON_CONNECTION * 100) {
                mutateConnectinWeight(genome, WeightMutation.SLIGHT_CHANGE_OF_WEIGHT);
            }
        }
    }

    private void addNewNode(Genome genome, Integer integer) {




    }

    private int findIndexOf(NetworkMutation mutation) {
        int res = -1;
        for(int i = 0;i<networkMutationMemory.size();i++){
            if(networkMutationMemory.get(i).equals(mutation)){
                res = i;
            }
        }
        return res;
    }


    private NetworkMutation createPreMutation(Genome genome, MutationType type) {
        ArrayList<ConnectionGene> connections = genome.getConnectionGenes();
        int randomIndex = random.nextInt(connections.size());
        ConnectionGene connectionToSplit = connections.get(randomIndex);
        NetworkMutation newMutation = new NetworkMutation(type,connectionToSplit.inputNode,connectionToSplit.outputNode);

        return newMutation;
    }

    //Mutate a random connection in the genome
    private void mutateConnectinWeight(Genome g, WeightMutation typeOfMutation){


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
        return lastMutationIncreaseInInnovationNumber;
    }

    public void clearMutationMemory(){
        networkMutationMemory = new ArrayList<>();
        innovationNumberMemory = new ArrayList<>();
        lastMutationIncreaseInInnovationNumber = 0;
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
