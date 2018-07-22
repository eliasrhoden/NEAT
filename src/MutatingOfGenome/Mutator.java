package MutatingOfGenome;

import Network.ConnectionGene;
import Network.Genome;

import java.util.*;

/**
 * Used to mutate genomes
 * */

/**
 * What nodes to add
 * What connections to add
 * What Weights to mutate
 * What Connections to disable
 * What Connections to re-enable
 * */
public class Mutator {

    private List<Mutation> mutationMemory;
    private int innovationCounter;
    private MutatorParams params;
    private Random random;
    private final int NR_OF_TRIES_TO_FIND_NODES = 5;


    public Mutator(MutatorParams params){
        this.params = params;
        random = new Random();
        mutationMemory = new ArrayList<Mutation>();
        innovationCounter = -1;
    }

    public void mutateGenome(Genome g){

        boolean node,conn,mut,enabl;
        node = false;
        conn = false;
        mut = false;
        enabl = false;

        if(innovationCounter == -1){
            innovationCounter = g.getHighestInnovationNumber()+1;
        }


        if(shouldAddNode()){
            addNode(g);
            node = true;
        }
        if(shouldAddConnection()){
            addConnection(g);
            conn = true;
        }
        if(shouldMutateWeights()){
            mutateWeights(g);
            mut = true;
        }
        if(shouldEnableGene()){
            reenableGene(g);
            enabl = true;
        }
        if(shouldDisableGene()){
            disableGene(g);
        }
        String sss = "ff" + node + conn + mut + enabl;
        if(debugStop(g)){
            System.out.println("CHEAHH");
        }

    }


    public static boolean debugStop(Genome g){
        ArrayList<Integer> inputs = new ArrayList<>();
        ArrayList<Integer> outputs = new ArrayList<>();

        for(ConnectionGene cg :g.getConnectionGenes()){
            inputs.add(cg.inputNode);
            outputs.add(cg.outputNode);
        }

        for(int i=0;i<inputs.size();i++){
            int inVal = inputs.get(i);
            if(outputs.contains(inVal)){
                int index = outputs.indexOf(inVal);
                if(inputs.get(index) == outputs.get(i)){
                    return true;
                }
            }
        }

        return false;
    }

    private void reenableGene(Genome g) {
        List<ConnectionGene> disabledGenes = new LinkedList<>();
        for(ConnectionGene cg:g.getConnectionGenes()){
            if(cg.enabled == false){
                disabledGenes.add(cg);
            }
        }
        if(disabledGenes.size() > 0){
            ConnectionGene gene = disabledGenes.get(random.nextInt(disabledGenes.size()));
            gene.enabled = true;
        }
    }

    private void disableGene(Genome g) {
        if(g.getEnabledGenes().size()==0)
            return;
        ConnectionGene geneToMutate = getRandomEnabledConnection(g);
        geneToMutate.enabled = false;
    }


    private void mutateWeights(Genome g) {
        for(ConnectionGene gene:g.getEnabledGenes()){
            double w = gene.weight;
            if(shouldSlightlyMutateWeight()){
                gene.weight = w + (random.nextInt(20)-10)/1000.0;
            }else{
                gene.weight = (random.nextInt(40)-20)/10.0;
            }
        }
    }

    /**
     * Chooses two nodes at random and connects them, also checks that the new connection don't creates an 'loop'.
     * */
    private void addConnection(Genome g) {
        int inputNode = 0;
        int outputNode = 0;

        for(int i = 0;i<NR_OF_TRIES_TO_FIND_NODES;i++){
            inputNode = getRandomHiddenNodeFromGenome(g,g.getInputNodeIDs());
            outputNode =getRandomHiddenNodeFromGenome(g,g.getOutputNodeIDs());
            if(validInputOutputNodes(outputNode,inputNode,g)){
                int innovationNr = getInnovationNrForNewConnection(inputNode,outputNode);
                g.addConnectionGene(inputNode,outputNode,innovationNr,1);
                Mutation intoNode = new Mutation(inputNode,outputNode,innovationNr);
                addToMemoryIfNeeded(intoNode);
                return;
            }
        }
        System.out.println("NO ABLE TO FIND NODE PAIRS TO ADD CONNECTION BETWEEN!");
        //Maybe add some error message here
    }


    public boolean validInputOutputNodes(int input, int output, Genome g){


        try {
            g.getConnectionWeight(input,output);
            return false;
        }catch (IllegalArgumentException e){

        }

        if(input==output)
            return false;

        int[] suplyers = g.getSupplyingNodesToNode(input);

        for(int i:suplyers){
            if(i == output)
                return false;
        }
        return true;
    }




    private int getRandomHiddenNodeFromGenome(Genome g, int[] extraSelection) {
        int[] hiddens = g.getHiddenNodeIDs();
        int[] selection = new int[hiddens.length + extraSelection.length];
        for(int i = 0;i<hiddens.length;i++){
            selection[i] = hiddens[i];
        }
        for(int i = 0;i<extraSelection.length;i++){
            selection[i + hiddens.length] = extraSelection[i];
        }
        int randomIndex = random.nextInt(selection.length);
        return selection[randomIndex];
    }

    /**
     * Chooses a connection at random and splits it into two with a new node in the split
     * */
    private void addNode(Genome g) {
        if(g.getEnabledGenes().size()==0)
            return;
        ConnectionGene connection = getRandomEnabledConnection(g);
        int inp = connection.inputNode;
        int out = connection.outputNode;
        connection.enabled = false;

        int nodeId = g.addNode();

        int leadIn_innovationNr = getInnovationNrForNewConnection(inp,nodeId);
        int leadOut_innovationNr = getInnovationNrForNewConnection(nodeId,out);
        g.addConnectionGene(inp,nodeId,leadIn_innovationNr,1);
        Mutation intoNode = new Mutation(inp,nodeId,leadIn_innovationNr);
        addToMemoryIfNeeded(intoNode);
        g.addConnectionGene(nodeId,out,leadOut_innovationNr,connection.weight);
        Mutation outOfNode = new Mutation(nodeId,out,leadOut_innovationNr);
        addToMemoryIfNeeded(outOfNode);
    }

    public boolean shouldAddNode(){
        return random.nextDouble() <= params.PROBABILITY_OF_NEW_NODE;
    }

    public boolean shouldAddConnection(){
        return random.nextDouble() <= params.PROBABILITY_OF_NEW_CONNECTION;
    }

    public boolean shouldMutateWeights(){
        return random.nextDouble() <= params.PROBABILITY_OF_WEIGHT_MUTATION;
    }

    public boolean shouldDisableGene(){
        return random.nextDouble() <= params.PROBABILITY_OF_DISABLE_GENE;
    }

    public boolean shouldSlightlyMutateWeight(){
        return random.nextDouble() <= params.PROBABILITY_OF_SLIGHT_CHANGE_OF_WEIGHT_ON_CONNECTION;
    }
    private boolean shouldEnableGene() {
        return random.nextDouble() <= params.PROBABILITY_OF_RE_ENABLE_GENE;
    }

    private int getInnovationNrForNewConnection(int input, int output){
        int resultNr = -1;
        for(Mutation m:mutationMemory){
            if(m.input == input && m.output == output){
                resultNr = m.innovationNr;
                break;
            }
        }
        if(resultNr == -1){
            innovationCounter++;
            resultNr = innovationCounter;
        }
        return resultNr;
    }

    private ConnectionGene getRandomEnabledConnection(Genome g){
        List<ConnectionGene> enabledGenes = g. getEnabledGenes();
        int maxIndex = enabledGenes.size();
        Random r = new Random();
        int randomIndex = r.nextInt(maxIndex);
        return enabledGenes.get(randomIndex);
    }

    public int getInnovationCounter(){
        return innovationCounter;
    }

    private boolean addToMemoryIfNeeded(Mutation mutation){
        if(mutationMemory.contains(mutation))
            return false;
        else{
            mutationMemory.add(mutation);
            return true;
        }
    }

}
