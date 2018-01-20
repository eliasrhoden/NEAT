package Mutation;

import Network.Genome;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutatorTest {

    @Test
    void testNewNode(){
        Genome g = new Genome(1,1);
        MutatorParams params = new MutatorParams();
        params.PROBABILITY_OF_NEW_NODE = 1;
        Mutator m = new Mutator(params);
        m.muateGenome(g);
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
        Genome g = new Genome(1,1);
        MutatorParams params = new MutatorParams();
        params.PROBABILITY_OF_NEW_CONNECTION = 1;
        Mutator m = new Mutator(params);
        //TODO
    }


}