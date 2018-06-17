
import Mutation.Mutator;
import Mutation.MutatorParams;
import Network.Genome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by elias on 2017-05-14.
 */
public class Population {

    /**
     * To be considered as PSEUDO-code only so far, need to implemtnes lower level peices first
     * And also write TESTS for this...
     *
     * */

    private Genome[] population;
    private Mutator mutator;
    private SpieceFilter spieceFilter;
    private int generation = 0;
    private double dT;

    public Population(PopulationParams neatParams) {
        createPopulation(neatParams.NR_OF_INPUTS,neatParams.NR_OF_OUTPUTS,neatParams.POPULATION_SIZE);
        MutatorParams mutatorParams = createMutatorParams();
        mutator = new Mutator(mutatorParams);
        spieceFilter = new SpieceFilter(neatParams.C1,neatParams.C2,neatParams.C3);
        dT = neatParams.DELTA_T;
    }

    private MutatorParams createMutatorParams() {
        MutatorParams params = new MutatorParams();
        params.PROBABILITY_OF_WEIGHT_MUTATION = 0.8;
        params.PROBABILITY_OF_SLIGHT_CHANGE_OF_WEIGHT_ON_CONNECTION = 0.9;
        params.PROBABILITY_OF_DISABLE_GENE = 0.3;
        params.PROBABILITY_OF_NEW_NODE = 0.05;
        params.PROBABILITY_OF_NEW_CONNECTION = 0.1;
        params.PROBABILITY_OF_RE_ENABLE_GENE = 0.02;
        return params;
    }

    public int getCurrentGeneration() {
        return generation;
    }


    /**
     * Assumes that the user have set each genome's fitness before calling this function.
     * */
    public void createNextGeneration() {
        List<Genome> newPopulation = new ArrayList<>();
        List<Genome> sortedPopulation = new ArrayList<>(Arrays.asList(population));
        Collections.sort(sortedPopulation);
        List<Spiece> spieces = filterOutSpieces(population);

        adjustFitnessToSpecies(spieces);
        Collections.sort(spieces);

        //TODO

        generation++;
    }

    /**
     * Assigns each genome's 'adjusted fitness', with regard to its spices.
     */
    private void adjustFitnessToSpecies(List<Spiece> spieces) {
        for(Spiece spiece:spieces){
            int totalFitnessSpice = 0;
            int spiceSize = spiece.genomes.size();
            for(Genome g:spiece.genomes){
                int originalFitness = g.getFitness();
                g.setFitness(originalFitness/spiceSize);
                totalFitnessSpice += originalFitness;
            }
            spiece.averageFitness = totalFitnessSpice/spiceSize;
        }
    }

    private List<Spiece> filterOutSpieces(Genome[] population) {
        List<Spiece> result = new ArrayList<>();
        populationLoop:
        for(Genome g:population){
            for(Spiece s:result){
                if(isOfSameSpieces(g,s.master)){
                    s.genomes.add(g);
                    continue populationLoop;
                }
            }
            result.add(new Spiece(g));
        }

        return result;
    }

    private boolean isOfSameSpieces(Genome g1, Genome g2){
        return spieceFilter.getCompabilityDistance(g1,g2) <=  dT;
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
    }

}