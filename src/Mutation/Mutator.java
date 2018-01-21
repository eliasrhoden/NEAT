package Mutation;

import Network.ConnectionGene;
import Network.Genome;

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

    public Mutator(MutatorParams params){this.params = params;}

    public void mutateGenome(Genome g){
        innovationCounter = g.getHighestInnovationNumber();

        if(shouldAddNode()){
            addNode(g);
        }
        if(shouldAddConnection()){
            //TODO
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

    private void addNode(Genome g) {
        ConnectionGene connection = getRandomEnabledGene(g);
        int inp = connection.inputNode;
        int out = connection.outputNode;
        connection.enabled = false;

        int nodeId = g.addNode();

        int leadIn_innovationNr = getInnovationNrForNewConnection(inp,nodeId);
        int leadOut_innovationNr = getInnovationNrForNewConnection(nodeId,out);
        g.addConnectionGene(inp,nodeId,leadIn_innovationNr,1);
        g.addConnectionGene(nodeId,out,leadOut_innovationNr,connection.weight);
    }

    public boolean shouldAddNode(){
        //TODO

        return false;
    }

    public boolean shouldAddConnection(){
        //TODO
        return false;
    }

    public boolean shouldMutateWeights(){
        //TODO
        return false;
    }

    public boolean shouldDisableGene(){
        //TODO
        return false;
    }

    public boolean shouldEnableGene(){
        //TODO
        return false;
    }

    private int getInnovationNrForNewConnection(int input, int output){
        //TODO
        return 0;
    }

    private ConnectionGene getRandomEnabledGene(Genome g){
        List<ConnectionGene> enabledGenes = g. getEnabledGenes();
        int maxIndex = enabledGenes.size();
        Random r = new Random();
        int randomIndex = r.nextInt(maxIndex);
        return enabledGenes.get(randomIndex);
    }

    public int getInnovationCounter(){
        return innovationCounter;
    }

    private class Mutation{
        public ConnectionGene connectionGene;
        public int innovationNr;
        public Mutation(ConnectionGene connectionGene,int innovationNr){
            this.connectionGene = connectionGene;
            this.innovationNr = innovationNr;
        }
    }
}
