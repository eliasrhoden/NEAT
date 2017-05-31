import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by elias on 2017-05-19.
 */
class GenomeTest {

    @Test
    void creationTest(){
        Genome g = new Genome(3,1);
        int[] inp = g.getInputNodeIDs();
        int[] outp = g.getOutputNodeIDs();
        assertEquals(inp.length,3);
        assertEquals(outp.length,1);

        for(int i = 0;i<inp.length;i++){
            assertEquals(inp[i],i);
        }
        for(int i = 0;i<outp.length;i++){
            assertEquals(outp[i],inp.length+i);
        }
    }

    @Test
    void invalidArgumentsTest(){
        try{
            new Genome(5,0);
            fail("No exception thrown...");
        } catch (Exception e){
            assertEquals(e.getClass(),IllegalArgumentException.class);
        }

        try{
            new Genome(0,8);
            fail("No exception thrown...");
        } catch (Exception e){
            assertEquals(e.getClass(),IllegalArgumentException.class);
        }
    }

    @Test
    void addNodeTest(){
        Genome g = new Genome(5,3);
        g.addNode();
        g.addNode();
        g.addNode();
        int[] swag = g.getHiddenNodeIDs();
        assertEquals(8,swag[0]);
        assertEquals(9,swag[1]);
        assertEquals(10,swag[2]);
    }

    @Test
    void getOutputSimpleTest(){
        Genome g = new Genome(3,1);
        double[] inputVals = {1,5,15};
        double excpected = (1+5+15);
        double[] result = g.getOutput(inputVals);
        assertEquals(excpected,result[0]);
    }

    @Test
    void getOutputNormalTest(){

        Genome g = new Genome(3,1);
        double[] inputVals = {1,5,15};
        double excpected = (1+5+15);
        int nID = g.addNode();

        g.disableConnectionGene(0,3);
        g.addConnectionGene(0,nID,5,1);
        g.addConnectionGene(nID,3,6,1);

        excpected = 20 + 0.99;
        double[] res = g.getOutput(inputVals);
        assertEquals(excpected * 100, (int)(res[0]*100));

    }

    @Test
    void getOutputHardTest(){

        Genome g = new Genome(4,2);
        double[] inputVals = {2,3.2,1.7,17};
        double[] excpected = {6.64,3};
        int nID1 = g.addNode();
        int nID2 = g.addNode();
        double errorRange = 0.01;

        g.assignNewConnectionWeight(0,4,0.5);
        g.assignNewConnectionWeight(1,4,0.7);
        g.assignNewConnectionWeight(2,4,1);
        g.assignNewConnectionWeight(3,4,0.1);

        g.disableConnectionGene(2,5);
        g.disableConnectionGene(1,5);
        g.disableConnectionGene(3,5);

        g.addConnectionGene(1,nID1,10,0.7);
        g.addConnectionGene(2,nID1,11,1);

        g.addConnectionGene(3,nID2,12,0.1);
        g.addConnectionGene(nID1,nID2,13,0.5);

        g.addConnectionGene(nID2,5,14,2);
        g.assignNewConnectionWeight(0,5,0.5);

        double[] outP = g.getOutput(inputVals);

        for(int i = 0;i<2;i++){
            assertTrue(outP[i] < excpected[i] + errorRange/2);
            assertTrue(outP[i] > excpected[i] - errorRange/2);
        }
    }

    @Test
    void transferFunctionTest(){
        assertEquals((int)(Genome.transferFunction(4) * 100.0),99);
        assertEquals((int)(Genome.transferFunction(0.5) * 100),90);
        assertEquals((int)(Genome.transferFunction(0.2) * 100),71);
        assertEquals((int)(Genome.transferFunction(-0.2) * 100),28);
    }
}