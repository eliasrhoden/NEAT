import Network.ConnectionGene;
import Network.Genome;

public class SpieceFilter {

    private final double c1,c2,c3;

    public SpieceFilter(double c1,double c2, double c3){
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    public double getCompabilityDistance(Genome g1, Genome g2){
        return c1*getNoOfExcessGenes(g1,g2) +
                c2*getNoOfDissjointGenes(g1,g2) +
                c3*getAvergaeWeightDiff(g1,g2);
    }

    public int getNoOfDissjointGenes(Genome g1, Genome g2){
        int result = 0;
        int g1HighestInnovationNr = g1.getHighestInnovationNumber();
        int g2HighestInnovationNr = g2.getHighestInnovationNumber();

        int higestInnovInLowest = Math.min(g1HighestInnovationNr,g2HighestInnovationNr);

        for(ConnectionGene c :g1.getConnectionGenes()){
            if(c.innovationNumber <= higestInnovInLowest){
                if(!innovationNrExistsInGenome(c.innovationNumber,g2))
                    result++;
            }
        }

        for(ConnectionGene c :g2.getConnectionGenes()){
            if(c.innovationNumber <= higestInnovInLowest){
                if(!innovationNrExistsInGenome(c.innovationNumber,g1))
                    result++;
            }
        }

        return result;
    }

    private boolean innovationNrExistsInGenome(int innovNr, Genome g){
        for(ConnectionGene c:g.getConnectionGenes()){
            if(c.innovationNumber == innovNr)
                return true;
        }
        return false;
    }

    public int getNoOfExcessGenes(Genome g1, Genome g2){
        int result = 0;
        int g1HighestInnovationNr = g1.getHighestInnovationNumber();
        int g2HighestInnovationNr = g2.getHighestInnovationNumber();

        int higestInnovInLowest = Math.min(g1HighestInnovationNr,g2HighestInnovationNr);
        Genome genomeWithExcess = (higestInnovInLowest==g1HighestInnovationNr) ?
                g2 : g1;

        for(ConnectionGene c:genomeWithExcess.getConnectionGenes()){
            if(c.innovationNumber > higestInnovInLowest){
                result++;
            }
        }
        return result;
    }

    public double getAvergaeWeightDiff(Genome g1, Genome g2){
        double sumOfDiffs = 0;
        int nrOfMatchingGenes = 0;
        for(ConnectionGene c1:g1.getConnectionGenes()){
            for(ConnectionGene c2:g2.getConnectionGenes()){
                if(c1.innovationNumber == c2.innovationNumber){
                    double rawDiff = c1.weight - c2.weight;
                    sumOfDiffs += Math.abs(rawDiff);
                    nrOfMatchingGenes++;
                    break;
                }
            }
        }
        return sumOfDiffs/nrOfMatchingGenes;
    }

}
