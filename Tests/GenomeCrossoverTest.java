import Network.ConnectionGene;
import Network.Genome;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static Tests.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class GenomeCrossoverTest {


    /**
     * Tests the example from the AI-article Figure 4.
     * In the Genome class, innovation number and Node ids start from 0.
     * */

    @Test
    void exampleFromText(){
        GenomeCrossover crossover = new GenomeCrossover();

        Genome g1 = new Genome(3,1);
        Genome g2 = new Genome(3,1);
        Genome expeted = new Genome(3,1);

        //Parent 1
        g1.disableConnectionGene(1,3);
        g1.addNode();
        g1.addConnectionGene(1,4,3,1);
        g1.addConnectionGene(4,3,4,1);
        g1.addConnectionGene(0,4,7,1);

        //Parent 2
        g2.disableConnectionGene(1,3);
        g2.addNode();
        g2.addNode();
        g2.addConnectionGene(1,4,3,1);
        g2.addConnectionGene(4,3,4,1);
        g2.addConnectionGene(4,5,5,1);
        g2.addConnectionGene(5,3,6,1);
        g2.addConnectionGene(2,4,8,1);
        g2.addConnectionGene(0,5,9,1);
        g2.disableConnectionGene(4,3);

        //Expected offspring
        expeted.addNode();
        expeted.addNode();
        expeted.addConnectionGene(1,4,3,1);
        expeted.addConnectionGene(4,3,4,1);
        expeted.addConnectionGene(4,5,5,1);
        expeted.addConnectionGene(5,3,6,1);
        expeted.addConnectionGene(0,4,7,1);
        expeted.addConnectionGene(2,4,8,1);
        expeted.addConnectionGene(0,5,9,1);

        Genome crossed = crossover.crossOver(g1,g2);

        assertSameGenes(expeted,crossed);
    }




    /**
     * Little test to test the helper function,'getCommonGenes'
     * The first argument is the master-genome, the second argument is the genome to be compared, if the
     * second genome contains matching genes to the master, the second genomes genes are added to a list.
     * */

    @Test
    void getCommonGenesTest(){

        Genome g1 = new Genome(2,1);
        g1.assignNewConnectionWeight(0,2,2);
        Genome g2 = new Genome(2,1);
        g2.addNode();
        g2.addConnectionGene(0,3,2,1);

        List<ConnectionGene> res1 = GenomeCrossover.getCommonGenes(g2,g1);

        List<ConnectionGene> exp = new LinkedList<>();
        exp.add(new ConnectionGene(0,2,0));
        exp.add(new ConnectionGene(1,2,1));
        exp.get(0).weight = 2;

        List<ConnectionGene> result = GenomeCrossover.getCommonGenes(g2,g1);

        asssertGeneListsEquals(exp,result);
    }


}