import MutatingOfGenome.Mutator;
import Network.Genome;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class XorTest {

    private double[][] inputs;
    private double[] facit;


    private PopulationParams createParams(){
        PopulationParams par = new PopulationParams();
        par.TOP_GENOMES_TO_CROSSOVER_TO_NEXT_GEN_PERCENTAGE = 0.3;
        par.TOP_GENOMES_TO_COPY_TO_NEXT_GEN_PERCENTAGE = 0.4;
        par.POPULATION_SIZE = 200;
        par.C1 = 1;
        par.C2 = 1;
        par.C3 = 0.4;
        par.DELTA_T = 4;
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

    /*
    private int fitness(double facit,double output){
        double dist = output - facit;
        dist = Math.abs(dist);
        int res = 0;
        if(dist < 4)
            res = (int) 4-dist;
    }
*/
    private int fitnessold2(double facit,double output){
        double diff = Math.abs(output-facit);
        int res = 0;

        if(diff<2){
            res = (int)((diff/2 * 20));
            res = 20 - res;
        }

        if(output < facit + 0.1 && output > facit - 0.1){
            res += 30;
        }
        return res;
    }

    private int fitnessOLD(double facit, double output){
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
        Genome best = null;
        generateInputsNFacit();
        loops:
        for(int i=0;i<80;i++){
            population.createNextGeneration();
            for(Genome g:population.getPopulation()){
                double totalErrorDist = 0;
                int fittnes = 0;
                for(int j = 0;j<4;j++){
                    double[] out = g.getOutput(inputs[j]);
                    //int outV = (int) Math.round(out[0]);
                    totalErrorDist += Math.abs(out[0] - facit[j]);
                }

                if(totalErrorDist < 4){
                    fittnes = (int) Math.pow((4-totalErrorDist),2);
                }

                g.setFitness(fittnes);
                if(fittnes>=7){
                    System.out.println("NÄMEEEENENENENENENEN");
                    System.out.println(fittnes);
                    best = g;
                    break loops;

                }
            }
        }
        if(best == null)
            best = population.getFittestGenome();
        System.out.println("BÄSTA NETWORK:");
        System.out.println(best.debugString());
        System.out.println(best.getFitness());
        for(int i = 0;i<4;i++){
            double[] out = best.getOutput(inputs[i]);
            int outputVal = (int) Math.round(out[0]);
            System.out.println("INPUT: " + inputs[i][0] + " : " + inputs[i][1]);
            System.out.println("OUTPUT: "+ outputVal);
            System.out.println("Facit: " + facit[i]);
            assertEquals(facit[i],outputVal);
        }

    }

    @Test
    public void testFIttnessEval(){
        double totalErrorDist = 0;
        int fittnes = 0;
        generateInputsNFacit();
        double[] perfOut = {0,1,1,0};
        for(int j = 0;j<4;j++){


            totalErrorDist += Math.abs(perfOut[j] - facit[j]);
        }

        if(totalErrorDist < 4){
            fittnes = (int) Math.pow((4-totalErrorDist),2);
        }


        if(fittnes>=12){
            System.out.println("NÄMEEEENENENENENENEN");
        }

        assertEquals(fittnes,16);
    }






}