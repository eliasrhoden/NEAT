import Network.ConnectionGene;
import Network.Genome;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliasr on 2017-05-15.
 */
public class GenomeCrossover {

    /**
     * How two Networks/Genomes produces an offspring
     * */
    public Genome crossOver(Genome mom, Genome dad){
        Genome fittestParent = dad.getFitness()>mom.getFitness() ? dad : mom;
        Genome secondfittestParent = fittestParent==mom ? dad : mom;

        List<ConnectionGene> commonGenesFittest = getCommonGenes(secondfittestParent,fittestParent);
        List<ConnectionGene> commonGenesSecondFittest = getCommonGenes(fittestParent,secondfittestParent);


        //TODO Hanky panky (IN MATH) here
        return null;
    }

    public static List<ConnectionGene> getCommonGenes(Genome master, Genome evalutated){
        List<ConnectionGene> result = new ArrayList<>();
        List<ConnectionGene> masterGenes = master.getConnectionGenes();
        for(ConnectionGene cg : evalutated.getConnectionGenes()){
            for(ConnectionGene masterG : masterGenes){
                if(cg.innovationNumber == masterG.innovationNumber){
                    result.add(cg);
                    break;
                }
            }
        }
        return result;
    }

}
