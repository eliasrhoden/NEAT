import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by elias on 2017-05-22.
 */
class NEAT_PopulationImplementationTest {

    @Test
    void populationInitTest(){
        NEAT_PopulationInitialize init = new NEAT_PopulationInitialize();

        init.mutator = new GenomeMutator();
        assertFalse(init.isComplete());
        init.nrOfInputs = 6;
        init.nrOfOutputs = 3;
        assertFalse(init.isComplete());
        init.populationSize = 150;
        assertTrue(init.isComplete());
    }





}