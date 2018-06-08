import Network.ConnectionGene;
import Network.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by eliasr on 2017-05-15.
 */
public class GenomeCrossover {

    private final Random random = new Random();

    /**
     * How two Networks/Genomes produces an offspring
     * */
    public Genome crossOver(Genome mom, Genome dad){
        Genome fittestParent = dad.getFitness()>mom.getFitness() ? dad : mom;
        Genome secondfittestParent = fittestParent==mom ? dad : mom;
        int nrOfInputs = mom.getInputNodeIDs().length;
        int nrOfOutputs = mom.getOutputNodeIDs().length;

        List<ConnectionGene> genesForOffspring = new ArrayList<>();

        List<ConnectionGene> commonGenesFittest = getCommonGenes(secondfittestParent,fittestParent);
        List<ConnectionGene> commonGenesSecondFittest = getCommonGenes(fittestParent,secondfittestParent);

        List<ConnectionGene> extraGenesInFittest = getExtraGenesFromSecond(secondfittestParent,fittestParent);
        List<ConnectionGene> extraGenesInSecondFittest = getExtraGenesFromSecond(fittestParent,secondfittestParent);

        for(int i = 0;i<commonGenesFittest.size();i++){
            if(random.nextBoolean()){
                genesForOffspring.add(commonGenesFittest.get(i));
            }else{
                genesForOffspring.add(commonGenesSecondFittest.get(i));
            }
        }
        genesForOffspring.addAll(extraGenesInFittest);
        for(ConnectionGene g : extraGenesInSecondFittest){
            if(random.nextBoolean()){
                genesForOffspring.add(g);
            }
        }

        int maxNodeId = 0;
        Genome offspring = new Genome(nrOfInputs,nrOfOutputs);
        offspring.clearAllConnectionGenes();
        for(ConnectionGene g :genesForOffspring){
            int maxIdInConnection = Math.max(g.inputNode,g.outputNode);
            maxNodeId = Math.max(maxIdInConnection,maxNodeId);
        }

        for(int i = (nrOfInputs + nrOfOutputs - 1);i<maxNodeId;i++){
            offspring.addNode();
        }

        for(ConnectionGene g :genesForOffspring){
            offspring.addConnectionGene(g.inputNode,g.outputNode,g.innovationNumber,g.weight);
        }

        return offspring;
    }

    private List<ConnectionGene> getExtraGenesFromSecond(Genome master, Genome evalutated) {
        List<ConnectionGene> result = new ArrayList<>();
        List<ConnectionGene> masterGenes = master.getConnectionGenes();
        for(ConnectionGene cg : evalutated.getConnectionGenes()){
            for(ConnectionGene masterG : masterGenes){
                if(cg.innovationNumber == masterG.innovationNumber){
                    break;
                }
            }
            result.add(cg);
        }
        return result;
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
        sortByInnovationNumber(result);
        return result;
    }

    public static void sortByInnovationNumber(List<ConnectionGene> list){
        for(int i = 1;i<list.size();i++){
            for(int j = 1;j<list.size();j++){
                ConnectionGene prev = list.get(j-1);
                ConnectionGene current = list.get(j);
                if(current.innovationNumber < prev.innovationNumber){
                    list.set(j-1,current);
                    list.set(j,prev);
                }
            }
        }
    }

}
