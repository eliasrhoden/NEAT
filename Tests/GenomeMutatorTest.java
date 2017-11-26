
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

/**
 * Created by Elias on 2017-08-20.
 */
public class GenomeMutatorTest {

    private GenomeMutator mutator;

    @BeforeClass
    void setup(){

        MutatorParams params = new MutatorParams();

        mutator = new GenomeMutator(params);
    }

    @Test
    void addNodeTest(){

    }


}
