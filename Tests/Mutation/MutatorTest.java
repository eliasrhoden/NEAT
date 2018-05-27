package Mutation;

import Network.ConnectionGene;
import Network.Genome;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutatorTest {

    @Test
    void testNewNode(){
        Genome g = new Genome(1,1);
        MutatorParams params = new MutatorParams();
        params.PROBABILITY_OF_NEW_NODE = 1;
        Mutator m = new Mutator(params);
        m.mutateGenome(g);
        Genome expected = new Genome(1,1);
        int nId = expected.addNode();
        int outId = expected.getOutputNodeIDs()[0];
        int inpId = expected.getInputNodeIDs()[0];
        expected.disableConnectionGene(inpId,outId);
        expected.addConnectionGene(inpId,nId,1,1);
        expected.addConnectionGene(nId,outId,2,1);

        assertSameGenes(g,expected);
    }

    @Test
    void testNewConnection(){
        Genome g1 = new Genome(1,1);
        Genome g2 = new Genome(1,1);
        double[] input = {1};

        g1.removeConnectionGene(0,1);

        MutatorParams params = new MutatorParams();
        params.PROBABILITY_OF_NEW_CONNECTION = 1;
        Mutator m = new Mutator(params);

        m.mutateGenome(g1);
        System.out.println(g1.getConnectionGenes());
        System.out.println(g2.getConnectionGenes());
        assertArrayEquals(g2.getOutput(input),g1.getOutput(input));
    }

    /**
     * If a mutation have appeared in previous mutation, use the same innovation-number from
     * previously mutation.
     * */

    @Test
    void innovationNumber(){
        Genome g1 = new Genome(1,1);
        Genome g2 = new Genome(1,1);

        MutatorParams par = new MutatorParams();
        par.PROBABILITY_OF_NEW_NODE = 1;
        Mutator m = new Mutator(par);
        m.mutateGenome(g1);
        m.mutateGenome(g2);

        List<ConnectionGene> g1Genes = g1.getConnectionGenes();
        List<ConnectionGene> g2Genes = g2.getConnectionGenes();
        assertEquals(g2Genes.size(),g1Genes.size());
        assertEquals(2,m.getInnovationCounter());
        for(int i = 0;i<g1Genes.size();i++){
            int inov1 = g1Genes.get(i).innovationNumber;
            int inov2 = g2Genes.get(i).innovationNumber;
            assertEquals(inov1,inov2);
        }
    }


    @Test
    void mutateWeightNewRandom(){
        Genome g = new Genome(1,1);
        MutatorParams par = new MutatorParams();
        par.PROBABILITY_OF_WEIGHT_MUTATION = 1;
        par.PROBABILITY_OF_NEW_RANDOM_WEIGHT_ON_CONNECTION = 1;
        Mutator m = new Mutator(par);
        m.mutateGenome(g);
        double w =  g.getConnectionGenes().get(0).weight;
        assertNotEquals(1.0,w);
    }


    /**
     * Should for now only mutate +/- with (0-0.1)
     * */
    @Test
    void mutateWeightSlightChange(){
        Genome g = new Genome(1,1);
        MutatorParams par = new MutatorParams();
        par.PROBABILITY_OF_WEIGHT_MUTATION = 1;
        par.PROBABILITY_OF_SLIGHT_CHANGE_OF_WEIGHT_ON_CONNECTION = 1;
        Mutator m = new Mutator(par);
        m.mutateGenome(g);
        double w =  g.getConnectionGenes().get(0).weight;
        assertTrue(w < 1.1);
        assertTrue(w > 0.9);
        assertNotEquals(1.0,w);
    }

    @Test
    void disableGene(){
        Genome g = new Genome(1,1);
        MutatorParams par = new MutatorParams();
        par.PROBABILITY_OF_DISABLE_GENE = 1;
        Mutator m = new Mutator(par);
        m.mutateGenome(g);
        ConnectionGene gene = g.getConnectionGenes().get(0);
        assertEquals(false,gene.enabled);
    }

    @Test
    void reEnableGene(){
        Genome g = new Genome(1,1);
        g.getConnectionGenes().get(0).enabled = false;
        MutatorParams par = new MutatorParams();
        par.PROBABILITY_OF_RE_ENABLE_GENE = 1;
        Mutator m = new Mutator(par);
        m.mutateGenome(g);
        ConnectionGene gene = g.getConnectionGenes().get(0);
        assertEquals(true,gene.enabled);
    }

    private void assertSameGenes(Genome g1, Genome g2){
        boolean g1ContainsG2 = g1_Contains_g2(g1,g2);
        boolean g2ContainsG1 = g1_Contains_g2(g2,g1);
        if(!(g1ContainsG2)){
            fail("G1 did not contain same genes as G2");
        }
        if(!(g2ContainsG1)){
            fail("G2 did not contain same genes as G1");
        }
    }

    private boolean g1_Contains_g2(Genome g1, Genome g2){
        boolean res = false;
        System.out.println("G1 Contains G2");
        for(ConnectionGene cg: g1.getConnectionGenes()){
            res = false;
            System.out.println(cg);
            for(ConnectionGene cg2: g2.getConnectionGenes()){
                if(cg.inputNode == cg2.inputNode &&
                        cg.outputNode == cg2.outputNode &&
                        cg.innovationNumber == cg2.innovationNumber){
                    res = true;
                    System.out.println("FOUND!");
                }

            }
        }
        return res;
    }

}