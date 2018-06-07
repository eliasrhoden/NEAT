package Tests;

import Network.ConnectionGene;
import Network.Genome;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils {

    public static void assertSameGenes(Genome g1, Genome g2){
        boolean g1ContainsG2 = g1_Contains_g2(g1,g2);
        boolean g2ContainsG1 = g1_Contains_g2(g2,g1);
        if(!(g1ContainsG2)){
            fail("G1 did not contain same genes as G2");
        }
        if(!(g2ContainsG1)){
            fail("G2 did not contain same genes as G1");
        }
    }

    public static boolean g1_Contains_g2(Genome g1, Genome g2){
        boolean res = false;
        for(ConnectionGene cg: g1.getConnectionGenes()){
            res = false;
            System.out.println(cg);
            for(ConnectionGene cg2: g2.getConnectionGenes()){
                if(cg.inputNode == cg2.inputNode &&
                        cg.outputNode == cg2.outputNode &&
                        cg.innovationNumber == cg2.innovationNumber){
                    res = true;
                    break;
                }

            }
        }
        return res;
    }

    public static void assertGeneListsEquals(List<ConnectionGene> list1, List<ConnectionGene> list2){
        if(list1 == null || list2 == null)
            fail("One of the lists were null!");
        assertEquals(list1.size(),list2.size());
        for(int i = 0;i<list1.size();i++){
            assertEquals(list1.get(i),list2.get(i));
        }
    }

}
