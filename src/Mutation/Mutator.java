package Mutation;

import Network.ConnectionGene;
import Network.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Used to mutate genomes
 * */


/**
 * What nodes to add
 * What connections to add
 * What Weights to mutate
 * What Connections to disable
 * What Connections to re-enable
 *
 * */
public class Mutator {

    private List<Mutation> mutationMemory;
    private int innovationCounter;
    private MutatorParams params;
    private Random random;
    private final int NR_OF_TRIES_TO_FIND_NODES = 5;


    public Mutator(MutatorParams params){
        this.params = params;
        random = new Random();
        mutationMemory = new ArrayList<Mutation>();
    }

    public void mutateGenome(Genome g){

        if(shouldAddNode()){
            addNode(g);
        }
        if(shouldAddConnection()){
            AddConnection();
        }
        if(shouldMutateWeights()){
            //TODO
        }
        if(shouldDisableGene()){
            //TODO
        }
        if(shouldEnableGene()){
            //TODO
        }
    }

    private void AddConnection() {


    }

    private void addNode(Genome g) {
        ConnectionGene connection = getRandomEnabledConnection(g);
        int inp = connection.inputNode;
        int out = connection.outputNode;
        connection.enabled = false;

        int nodeId = g.addNode();

        int leadIn_innovationNr = getInnovationNrForNewConnection(inp,nodeId);
        int leadOut_innovationNr = getInnovationNrForNewConnection(nodeId,out);
        g.addConnectionGene(inp,nodeId,leadIn_innovationNr,1);
        Mutation intoNode = new Mutation(inp,nodeId,leadIn_innovationNr);
        addToMemoryIfNeeded(intoNode);
        g.addConnectionGene(nodeId,out,leadOut_innovationNr,connection.weight);
        Mutation outOfNode = new Mutation(nodeId,out,leadOut_innovationNr);
        addToMemoryIfNeeded(outOfNode);
    }

    public boolean shouldAddNode(){
        return random.nextDouble() <= params.PROBABILITY_OF_NEW_NODE;
    }

    public boolean shouldAddConnection(){
        return random.nextDouble() <= params.PROBABILITY_OF_NEW_CONNECTION;
    }

    public boolean shouldMutateWeights(){
        return random.nextDouble() <= params.PROBABILITY_OF_WEIGHT_MUTATION;
    }

    public boolean shouldDisableGene(){
        return random.nextDouble() <= params.PROBABILITY_OF_DISABLE_GENE;
    }

    public boolean shouldEnableGene(){
        return random.nextDouble() <= params.PROBABILITY_OF_RE_ENABLE_GENE;
    }

    private int getInnovationNrForNewConnection(int input, int output){
        int resultNr = -1;
        for(Mutation m:mutationMemory){
            if(m.input == input && m.output == output){
                resultNr = m.innovationNr;
                break;
            }
        }
        if(resultNr == -1){
            innovationCounter++;
            resultNr = innovationCounter;
        }
        return resultNr;
    }

    private ConnectionGene getRandomEnabledConnection(Genome g){
        List<ConnectionGene> enabledGenes = g. getEnabledGenes();
        int maxIndex = enabledGenes.size();
        Random r = new Random();
        int randomIndex = r.nextInt(maxIndex);
        return enabledGenes.get(randomIndex);
    }

    public int getInnovationCounter(){
        return innovationCounter;
    }

    private boolean addToMemoryIfNeeded(Mutation mutation){
        if(mutationMemory.contains(mutation))
            return false;
        else{
            mutationMemory.add(mutation);
            return true;
        }
    }

    private class Mutation{
        private int input;
        private int output;
        public int innovationNr;
        public Mutation(int input, int output,int innovationNr){
            this.input = input;
            this.output = output;
            this.innovationNr = innovationNr;
        }

        @Override
        public boolean equals(Object obj){
            if(!(obj instanceof Mutation))
                return false;
            Mutation m = (Mutation) obj;

            if(m.input != input)
                return false;
            if(m.output != output)
                return false;
            return true;
        }
    }
}
