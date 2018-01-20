package Mutation;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutatorTest {

    private MutatorParams params;

    @Before
    void setUp(){
        params = new MutatorParams();
        assertFalse(params.isValid());
    }

    @Test
    void testNewNode(){



    }


}