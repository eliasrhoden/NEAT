import java.util.ArrayList;

/**
 * Created by elias on 2017-05-14.
 */
public class NEAT_PopulationImplementation implements NEAT_Population {

    private ArrayList<Genome> population;
    private GenomeMutator mutator;
    private int generation = 0;
    private int lastInnovationNumber = 0;

    public NEAT_PopulationImplementation(NEAT_PopulationInitialize neatInit) {

        if(!neatInit.isComplete()){
            throw new IllegalArgumentException("NEAT_PopulationInitialize object is not complete!");
        }

    }

    @Override
    public int getCurrentGeneration() {
        return generation;
    }

    @Override
    public void mutatePopulation() {
        for (Genome genome : population) {
            mutator.mutateGenome(genome, lastInnovationNumber);
            lastInnovationNumber += mutator.getLastMutationInnovationIncrease();
        }
        mutator.clearMutationMemory();
    }

    @Override
    public void createNextGeneration() {

    }

    @Override
    public Iterable<Genome> getPopulation() {
        return population;
    }

    @Override
    public Genome getFittestGenome() {
        return null;
    }

    private void createPopulation(int nrOfInputs, int nrOfOutputs, int populationSize) {
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Genome(nrOfInputs, nrOfOutputs));
        }
        lastInnovationNumber = nrOfInputs + nrOfOutputs - 1;
    }

}