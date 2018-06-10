import Network.Genome;

public class SpieceFilter {

    private final double c1,c2,c3;

    public SpieceFilter(double c1,double c2, double c3){
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    public double getCompabilityDistance(Genome g1, Genome g2){
        return 0;
    }

    public int getNoOfDissjointGenes(Genome g1, Genome g2){
        return 0;
    }

    public int getNoOfExcessGenes(Genome g1, Genome g2){
        return 0;
    }

    public double getAvergaeWeightDiff(Genome g1, Genome g2){
        return 0;
    }

}
