package Network;

/**
 * Created by elias on 2017-05-12.
 * A gene to be used in Genomes/Nets
 */
public class ConnectionGene {
    public int inputNode;
    public int outputNode;
    public double weight;
    public int innovationNumber;
    public boolean enabled;

    public ConnectionGene(int inputNode, int outputNode){
        this(inputNode,outputNode,-1);
    }

    public ConnectionGene(int inputNode, int outputNode,int innovationNumber){
        weight = 1;
        enabled = true;
        this.inputNode = inputNode;
        this.outputNode = outputNode;
        this.innovationNumber = innovationNumber;
    }

    public boolean equals(Object obj){
        if(obj instanceof ConnectionGene) {
            ConnectionGene gene = (ConnectionGene) obj;
            boolean res = true;
            res = res && this.outputNode == gene.outputNode;
            res = res && this.inputNode == gene.inputNode;
            return res;
        }
        return false;
    }

}
