import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by elias on 2017-05-22.
 */
class NEAT_PopulationTest {

    /*

    Skall skriva fler sub-test innan, dessa får jöbba

    @Test
    void populationInitTest(){
        Population.PopulationParams init = new Population.PopulationParams();

        init.mutator = new Mutation.GenomeMutator();
        assertFalse(init.isComplete());
        init.NR_OF_INPUTS = 6;
        init.NR_OF_OUTPUTS = 3;
        assertFalse(init.isComplete());
        init.POPULATION_SIZE = 150;
        assertTrue(init.isComplete());
    }

    @Test
    void XOR_Test(){

        final int generationLimit = 30;

        Population.PopulationParams properties = new Population.PopulationParams();

        properties.mutator = new Mutation.GenomeMutator();
        properties.NR_OF_INPUTS = 2;
        properties.NR_OF_OUTPUTS = 1;
        properties.POPULATION_SIZE = 150;

        Population neat = new Population(properties);
        nested:
        for(int i = 0;true;i++){

            for(Network.Network genome:neat.getPopulation()){
                genome.setFitness(getFitnessOfGenome(genome));  //Let each individual try to do the task and return a fitness
                if(getFitnessOfGenome(genome) > 39){
                    System.out.println("Succeeded to do the task at generation: " + neat.getCurrentGeneration());
                    break nested;
                }
            }
            neat.createNextGeneration();
            if(i>generationLimit){
                fail("Didn't manage to do the task within generation " + generationLimit);
            }
        }
    }

    private int getFitnessOfGenome(Network.Network genome){
        final double marginal = 0.4;
        final double marginal2 = 0.4;
        double[][] input = {{1, 1}, {1,0},{0,1},{0,0}};
        int[] answer = {0,1,1,0};
        int fitness = 0;
        double[] slask;
        for(int i = 0;i< answer.length;i++){
            slask = genome.getOutput(input[i]);
            if( slask[i] < (answer[i] + marginal) && slask[i] > (answer[i] - marginal)){
                fitness += 10;
            }

            if( slask[i] < (answer[i] + marginal2) && slask[i] > (answer[i] - marginal2)){
                fitness += 2;
            }

        }

        return fitness;
    }
*/
}