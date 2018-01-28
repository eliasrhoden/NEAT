package Mutation;

import Network.ConnectionGene;
import Network.Genome;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

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

        assertEquals(expected,g);
    }

    @Test
    void testNewConnection(){
        Genome g1 = new Genome(1,1);
        Genome g2 = new Genome(1,1);

        g1.removeConnectionGene(0,1);

        MutatorParams params = new MutatorParams();
        params.PROBABILITY_OF_NEW_CONNECTION = 1;
        Mutator m = new Mutator(params);

        m.mutateGenome(g1);
        System.out.println(g1.getConnectionGenes());
        System.out.println(g2.getConnectionGenes());
        assertEquals(g2,g1);
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
        assertTrue(m.getInnovationCounter() == 2);
        for(int i = 0;i<g1Genes.size();i++){
            int inov1 = g1Genes.get(i).innovationNumber;
            int inov2 = g2Genes.get(i).innovationNumber;
            assertEquals(inov1,inov2);
        }
    }


}