
import Mutation.*;
import Network.Genome;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Elias on 2017-08-20.
 */
public class GenomeMutatorTest {

    @Test
    void newNodeBasicEquals(){
        NetworkMutation netM = new NewNode(3,6,4);
        NetworkMutation netM2 = new NewNode(3,6,4);
        NetworkMutation netM3 = new NewConnection(3,6,4);
        assertEquals(netM,netM2);
        assertNotEquals(netM2,netM3);
    }

    @Test
    void newNodeBasicEqualsWithNoInnovNumber(){
        NetworkMutation netM = new NewNode(0,2);
        NetworkMutation netM2 = new NewNode(0,2,4);

        assertEquals(netM,netM2);
    }

    @Test
    void newNodeExceptionWhenNoInnovNR(){
        NetworkMutation netM = new NewNode(0,2);
        Genome g = new Genome(3,1);
        try {
            netM.applyToGenome(g);
            fail("Should not be able to apply mutation without innovation number!");
        }catch (Exception e){

        }

    }

    @Test
    void newNodeExceptionOnNonValidInput(){

        Genome g = new Genome(3,1);

        NetworkMutation netM3 = new NewNode(5,1,4);
        try {
            netM3.applyToGenome(g);
            fail("Should not be able to apply mutation when nodes don't exists!");
        }catch (Exception e){

        }
    }


}
