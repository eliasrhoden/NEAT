import Network.Genome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpieceFilterTest {

    SpieceFilter f;
    Genome gMaster;
    Genome gExtra;

    final double c1 = 3;
    final double c2 = 5;
    final double c3 = 2;

    @BeforeEach
    void setup(){
        f = new SpieceFilter(c1,c2,c3);
        gMaster = new Genome(2,1);
        gExtra = new Genome(2,1);
        int node = gExtra.addNode();
        gMaster.addNode();
        gExtra.addConnectionGene(0,node,2,1);
        gExtra.addConnectionGene(node,2,4,1);
        gExtra.addConnectionGene(1,node,5,1);

        gMaster.addConnectionGene(0,node,2,2);
        gMaster.addConnectionGene(node,2,3,1);
    }

    @Test
    void excessGenes(){
        int resutl_1 = f.getNoOfExcessGenes(gMaster,gExtra);
        int result_2 = f.getNoOfExcessGenes(gExtra,gMaster);
        assertEquals(result_2,resutl_1);
        assertEquals(2,resutl_1);
    }

    @Test
    void dissjointGenes(){
        int resutl_1 = f.getNoOfDissjointGenes(gMaster,gExtra);
        int result_2 = f.getNoOfDissjointGenes(gExtra,gMaster);
        assertEquals(result_2,resutl_1);
        assertEquals(1,resutl_1);
    }

    //Average weight diff on matching genes.
    @Test
    void averageWeightDiference(){
        double resutl_1 = f.getAvergaeWeightDiff(gMaster,gExtra);
        double result_2 = f.getAvergaeWeightDiff(gExtra,gMaster);
        assertEquals(result_2,resutl_1);
        double exp = (0+0+1)/3.0;
        assertEquals(exp,resutl_1);
    }

    @Test
    void compabilityDistance(){
        double resutl_1 = f.getCompabilityDistance(gMaster,gExtra);
        double result_2 = f.getCompabilityDistance(gExtra,gMaster);
        assertEquals(result_2,resutl_1);
        double exp = c1*2 + c2*1 + c3*(1/3.0);
        assertEquals(exp,resutl_1);
    }

}