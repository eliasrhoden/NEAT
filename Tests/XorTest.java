import MutatingOfGenome.Mutator;
import Network.Genome;

import org.junit.jupiter.api.Test;

public class XorTest {

    private double[][] inputs;
    private double[] facit;


    private PopulationParams createParams(){
        PopulationParams par = new PopulationParams();
        par.TOP_GENOMES_TO_CROSSOVER_TO_NEXT_GEN_PERCENTAGE = 0.35;
        par.TOP_GENOMES_TO_COPY_TO_NEXT_GEN_PERCENTAGE = 0.35;
        par.POPULATION_SIZE = 150;
        par.C1 = 1;
        par.C2 = 1;
        par.C3 = 0.4;
        par.DELTA_T = 3;
        par.NR_OF_INPUTS = 3;
        par.NR_OF_OUTPUTS = 1;
        return par;
    }

    private void generateInputsNFacit(){
        double[][] inp = {
                {0,0,1},
                {0,1,1},
                {1,0,1},
                {1,1,1}
        };
        inputs = inp;
        double[] re = {0,1,1,0};
        facit = re;
    }

    private int fitness(double facit, double output){
        long res = 0;
        double diff = Math.abs(output - facit);
        res = Math.round((1/diff)*100);

        if(res > Integer.MAX_VALUE){
            res = Integer.MAX_VALUE - 1;
        }

        return (int) res;
    }

    @Test
    public void xorTest(){

        Population population = new Population(createParams());

        generateInputsNFacit();

        for(int i=0;i<50;i++){
            population.createNextGeneration();
            for(Genome g:population.getPopulation()){
                for(int j = 0;j<4;j++){
                    System.out.println("");
                    System.out.println("************************");
                    if(Mutator.debugStop(g)){
                        System.out.println("HEJFESHFSENJGFGksj");
                        g.setFitness(0);
                        continue;
                    }
                    System.out.println();
                    System.out.println("");
                    System.out.println(g.debugString());

                    double[] out = g.getOutput(inputs[j]);

                    g.setFitness(fitness(facit[j],out[0]));
                }
            }
        }

        Genome best = population.getFittestGenome();
        System.out.println("BÄSTA NETWORK:");
        for(int i = 0;i<4;i++){
            double[] out = best.getOutput(inputs[i]);
            int outputVal = (int) Math.round(out[0]);
            System.out.println("INPUT: " + inputs[i][0] + " : " + inputs[i][1]);
            System.out.println("OUTPUT: "+ outputVal);

            //assertEquals(outputVal,facit[i]);
        }

    }








}