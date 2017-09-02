import java.util.ArrayList;

/**
 * Created by elias on 2017-05-14.
 */
public class NEAT_Population {

    private Genome[] population;
    private GenomeMutator mutator;
    private int generation = 0;
    private int lastInnovationNumber = 0;

    public NEAT_Population(PopulationProperties popP) {

        if(!popP.isComplete()){
            throw new IllegalArgumentException("PopulationProperties object is not complete!");
        }

        createPopulation(popP.nrOfInputs,popP.nrOfOutputs,popP.populationSize);
    }

    public int getCurrentGeneration() {
        return generation;
    }


    public void mutatePopulation() {
        for (Genome genome : population) {
            mutator.mutateGenome(genome, lastInnovationNumber);
            lastInnovationNumber += mutator.getLastMutationInnovationIncrease();
        }
        mutator.clearMutationMemory();
    }


    public void createNextGeneration() {
        mutatePopulation();
        generation++;
    }


    public Genome[] getPopulation() {
        return population;
    }


    public Genome getFittestGenome() {
        Genome fittest = new Genome(1,1);
        fittest.setFitness(0);
        for(Genome g:population){
            if(g.getFitness() > fittest.getFitness()){
                fittest = g;
            }
        }
        return fittest;
    }

    private void createPopulation(int nrOfInputs, int nrOfOutputs, int populationSize) {
        population = new Genome[populationSize];
        for (int i = 0; i < populationSize; i++) {
            population[i] = new Genome(nrOfInputs, nrOfOutputs);
        }
        lastInnovationNumber = nrOfInputs + nrOfOutputs - 1;
    }

}