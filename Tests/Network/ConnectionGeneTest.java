package Network;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionGeneTest {

    @Test
    void equals1(){
        ConnectionGene c1 = new ConnectionGene(1,2,1);
        ConnectionGene c2 = new ConnectionGene(1,2,1);
        assertEquals(c1,c2);
    }

    @Test
    void equals2(){
        ConnectionGene c1 = new ConnectionGene(1,2,1);
        ConnectionGene c3 = new ConnectionGene(1,2,1);
        c3.weight = 0.5;
        assertEquals(c1,c3);
    }

    @Test
    void equals3(){
        ConnectionGene c1 = new ConnectionGene(1,2,1);
        ConnectionGene c4 = new ConnectionGene(1,2,5);
        assertNotEquals(c1,c4);
    }

}