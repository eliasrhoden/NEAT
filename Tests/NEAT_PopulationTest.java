import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by elias on 2017-05-22.
 */
class NEAT_PopulationTest {

    @Test
    void populationInitTest(){
        PopulationProperties init = new PopulationProperties();

        init.mutator = new GenomeMutator();
        assertFalse(init.isComplete());
        init.nrOfInputs = 6;
        init.nrOfOutputs = 3;
        assertFalse(init.isComplete());
        init.populationSize = 150;
        assertTrue(init.isComplete());
    }

    @Test
    void XOR_Test(){

        final int generationLimit = 30;

        PopulationProperties properties = new PopulationProperties();

        properties.mutator = new GenomeMutator();
        properties.nrOfInputs = 2;
        properties.nrOfOutputs = 1;
        properties.populationSize = 150;

        NEAT_Population neat = new NEAT_Population(properties);
        nested:
        for(int i = 0;true;i++){

            for(Genome genome:neat.getPopulation()){
                genome.setFitness(getFitnessOfGenome(genome));  //Let each individual try to do the task and return a fitness
                if(getFitnessOfGenome(genome) > 39){
                    System.out.println("Succseded to do the task at generation: " + neat.getCurrentGeneration());
                    break nested;
                }
            }
            neat.createNextGeneration();
            if(i>generationLimit){
                fail("Didn't manage to do the task within generation " + generationLimit);
            }
        }
    }

    private int getFitnessOfGenome(Genome genome){
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

}