
import MutatingOfGenome.Mutator;
import MutatingOfGenome.MutatorParams;
import Network.Genome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by elias on 2017-05-14.
 */
public class Population {

    private Genome[] population;
    private Mutator mutator;
    private SpieceFilter spieceFilter;
    private int generation = 0;
    private double dT;
    private PopulationParams params;
    private GenomeCrossover crossover;

    public Population(PopulationParams neatParams) {
        createPopulation(neatParams.NR_OF_INPUTS,neatParams.NR_OF_OUTPUTS,neatParams.POPULATION_SIZE);
        MutatorParams mutatorParams = createMutatorParams();
        mutator = new Mutator(mutatorParams);
        spieceFilter = new SpieceFilter(neatParams.C1,neatParams.C2,neatParams.C3);
        dT = neatParams.DELTA_T;    //Kan tas bort
        params = neatParams;
        crossover = new GenomeCrossover();
    }

    private MutatorParams createMutatorParams() {
        MutatorParams params = new MutatorParams();
        params.PROBABILITY_OF_WEIGHT_MUTATION = 0.8;
        params.PROBABILITY_OF_SLIGHT_CHANGE_OF_WEIGHT_ON_CONNECTION = 0.9;
        params.PROBABILITY_OF_DISABLE_GENE = 0.1;
        params.PROBABILITY_OF_NEW_NODE = 0.1;
        params.PROBABILITY_OF_NEW_CONNECTION = 0.1;
        params.PROBABILITY_OF_RE_ENABLE_GENE = 0.09;
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
        Collections.sort(sortedPopulation);

        List<Genome> nextGen = new ArrayList<>();

        for(int i=0;
            i<params.TOP_GENOMES_TO_COPY_TO_NEXT_GEN_PERCENTAGE*sortedPopulation.size();
            i++){
            Genome g = sortedPopulation.get(i);
            if(g.freeFromLoops()){
                nextGen.add(g);
            }
        }
        for(int i=0;
            i<params.TOP_GENOMES_TO_CROSSOVER_TO_NEXT_GEN_PERCENTAGE*sortedPopulation.size();
            i++){
            Genome parent1 = sortedPopulation.get(i);
            Genome parent2 = sortedPopulation.get(i+1);
            Genome offspring = crossover.crossOver(parent1,parent2);

            if(offspring.freeFromLoops()){
                nextGen.add(offspring);
            }
        }
        double topGenomesToMutateIntoNextGen = params.POPULATION_SIZE - nextGen.size();
        if(topGenomesToMutateIntoNextGen>=sortedPopulation.size())
            topGenomesToMutateIntoNextGen = sortedPopulation.size();

        for(int i=0; i<topGenomesToMutateIntoNextGen; i++){
            Genome g = sortedPopulation.get(i);
            mutator.mutateGenome(g);
            if(g.freeFromLoops()){
                nextGen.add(g);
            }
        }

        for(int i=0;i<nextGen.size();i++){
            Genome g = nextGen.get(i);
            if(!g.freeFromLoops()){
                nextGen.remove(g);
                i--;
            }
        }

        Genome[] newPop = new Genome[nextGen.size()];
        newPop = nextGen.toArray(newPop);
        population = newPop;

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
        Genome fittest = population[0];
        int bestFitness = 0;
        for(Genome g:population){
            if(g.getFitness() > bestFitness){
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