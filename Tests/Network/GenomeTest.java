package Network;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by elias on 2017-05-19.
 */
class GenomeTest {

    @Test
    void equalsTest1(){
        Genome g1 = new Genome(4,2);
        Genome g2 = new Genome(4,2);
        assertEquals(g1,g2);
    }

    @Test
    void equalsTest2(){
        Genome g1 = new Genome(2,1);
        int nodeId = g1.addNode();
        g1.addConnectionGene(0,nodeId,5,1);
        g1.addConnectionGene(nodeId,2,6,1);

        Genome g2 = new Genome(2,1);
        nodeId = g2.addNode();
        g2.addConnectionGene(0,nodeId,5,1);
        g2.addConnectionGene(nodeId,2,6,1);

        assertEquals(g1,g2);
    }



    @Test
    void equalsTest3(){
        Genome g1 = new Genome(2,1);
        int nodeId = g1.addNode();
        g1.addConnectionGene(0,nodeId,5,2);
        g1.addConnectionGene(nodeId,2,6,1);

        Genome g2 = new Genome(2,1);
        nodeId = g2.addNode();
        g2.addConnectionGene(0,nodeId,5,1);
        g2.addConnectionGene(nodeId,2,6,1);

        assertEquals(g1,g2);
    }

    @Test
    void equalsTest4(){
        Genome g1 = new Genome(2,1);
        int nodeId = g1.addNode();
        g1.addConnectionGene(0,nodeId,5,1);
        g1.addConnectionGene(nodeId,2,7,1);

        Genome g2 = new Genome(2,1);
        nodeId = g2.addNode();
        g2.addConnectionGene(0,nodeId,5,1);
        g2.addConnectionGene(nodeId,2,6,1);

        assertNotEquals(g1,g2);
    }

    @Test
    void equals5(){
        Genome g1 = new Genome(1,1);
        Genome g2 = new Genome(1,1);
        g2.removeConnectionGene(0,1);
        assertNotEquals(g1,g2);
    }

    @Test
    void mutability(){
        Genome g1 = new Genome(2,1);
        g1.getConnectionGenes().remove(0);
        assertEquals(2,g1.getConnectionGenes().size());
    }

    @Test
    void removeGene(){
        Genome g1 = new Genome(2,1);
        g1.removeConnectionGene(0,2);
        g1.removeConnectionGene(1,2);
        assertEquals(0,g1.getConnectionGenes().size());
    }

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
    void innovationNumberTest(){
        Genome g = new Genome(3,1);
        assertEquals(2,g.getHighestInnovationNumber());

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

    @Test
    void nodeSupplyersSimple(){
        Genome g = new Genome(4,2);

        int[] expected = {0,1,2,3};

        int[] result = g.getSupplyingNodesToNode(4);
        assertArrayEquals(expected,result);
    }

    @Test
    void nodeSupplyersHard(){
        Genome g = new Genome(2,1);

        /**
         *
         * OI \
         *      \        OU
         * OI -> O -> O /
         * */


        int nID1 = g.addNode();
        int nID2 = g.addNode();

        g.disableConnectionGene(1,2);
        g.disableConnectionGene(0,2);

        g.addConnectionGene(0,nID1,10,0.7);
        g.addConnectionGene(1,nID1,10,0.7);

        g.addConnectionGene(nID1,nID2,12,0.1);

        g.addConnectionGene(nID2,2,13,0.5);

        int[] expected = {nID1,0,1};

        Set<Integer> result = setFromArray(g.getSupplyingNodesToNode(nID2));

        assertEquals(expected.length,result.size());
        assertTrue(result.containsAll(setFromArray(expected)));
    }

    @Test
    public void supplyFoundBuggTest() {

        /*
        [INPUT NODE: 0 OUTPUT NODE: 3 ENABLED: true]
        [INPUT NODE: 1 OUTPUT NODE: 3 ENABLED: false]
        [INPUT NODE: 2 OUTPUT NODE: 3 ENABLED: true]

        [INPUT NODE: 4 OUTPUT NODE: 3 ENABLED: true]
        [INPUT NODE: 0 OUTPUT NODE: 4 ENABLED: true]
        [INPUT NODE: 1 OUTPUT NODE: 4 ENABLED: false]
        [INPUT NODE: 1 OUTPUT NODE: 5 ENABLED: true]
        [INPUT NODE: 5 OUTPUT NODE: 4 ENABLED: true]
        [INPUT NODE: 1 OUTPUT NODE: 6 ENABLED: true]
        [INPUT NODE: 6 OUTPUT NODE: 3 ENABLED: true]
        [INPUT NODE: 4 OUTPUT NODE: 5 ENABLED: true]
        *
        * */

        Genome g = new Genome(3, 1);
        g.addNode();
        g.addNode();
        g.addNode();
        g.addConnectionGene(4, 3, 1, 1);
        g.addConnectionGene(0, 4, 1, 1);
        g.addConnectionGene(1, 4, 1, 1);
        g.addConnectionGene(1, 5, 1, 1);
        g.addConnectionGene(5, 4, 1, 1);
        g.addConnectionGene(1, 6, 1, 1);
        g.addConnectionGene(6, 3, 1, 1);

        g.disableConnectionGene(1, 3);
        g.disableConnectionGene(1, 4);

        int nodes[] = g.getSupplyingNodesToNode(4);
        int expexted[] = {0,1, 5};
        assertEquals(expexted.length, nodes.length);

        for (int ex : expexted) {

            if(!(arrayContains(nodes,ex))){
                String msg = "";
                for(int n:nodes)
                    msg += n + " ";
                msg += "\n";
                msg += "Did not contain: " + ex;
                fail(msg);
            }
        }

    }


    private boolean arrayContains(int[] arr, int val){
        for(int v :arr){
            if(v==val)
                return true;
        }
        return false;
    }

    private Set<Integer> setFromArray(int[] arr){
        Set<Integer> res = new HashSet<>();
        for(int i : arr)
            res.add(i);
        return res;
    }

    /**
     * Sometimes during crossover *or* mutation, loops occur within the networks.
     * Cant find the cause of why right now, but will write a functions that detects loops
     * so these networks can be caught.
     * */
    @Test
    void testGenomeCheck(){
        Genome g = new Genome(3,1);
        g.addNode();
        g.addNode();
        g.addNode();
        g.addConnectionGene(0,4,1,1);
        g.addConnectionGene(4,5,1,1);
        g.addConnectionGene(5,6,1,1);
        g.addConnectionGene(6,3,1,1);
        g.addConnectionGene(6,4,1,1); //Loop connection

        assertFalse(g.freeFromLoops());

    }
    @Test
    void testGenomeCheck2(){
        Genome g = new Genome(3,1);
        g.addNode();
        g.addNode();
        g.addNode();
        g.addConnectionGene(0,4,1,1);
        g.addConnectionGene(4,5,1,1);
        g.addConnectionGene(5,6,1,1);


        assertTrue(g.freeFromLoops());

    }
    @Test
    void testGenomeCheck3(){
        Genome g = new Genome(3,1);
        g.addNode();
        g.addNode();
        g.addConnectionGene(0,4,1,1);
        g.addConnectionGene(4,5,1,1);
        g.addConnectionGene(5,3,1,1);


        assertTrue(g.freeFromLoops());

    }

}