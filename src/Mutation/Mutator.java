package Mutation;

import Network.ConnectionGene;
import Network.Genome;

import java.util.List;

/**
 * Used to mutate genomes
 * */

public class Mutator {

    private List<Mutation> mutationMemory;
    private int innovationCounter;
    private MutatorParams params;

    public Mutator(MutatorParams params){this.params = params;}

    public void muateGenome(Genome g){
        //TODO
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
