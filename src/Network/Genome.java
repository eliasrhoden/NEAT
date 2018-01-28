package Network;


import java.util.*;

/**
 * Created by elias on 2017-05-12.
 * A network/individual
 */
public class Genome {

    private ArrayList<ConnectionGene> connectionGenes = new ArrayList<>();
    private int nrOfInputs;
    private int nrOfOutputs;
    private ArrayList<Integer> hiddenNodeIDs = new ArrayList<Integer>();
    private int lastNodeID = 0;
    private int fitness = 0;

    public Genome(int nrOfInputs, int nrOfOutputs){
        if(nrOfInputs < 1 || nrOfOutputs < 1){
            throw new IllegalArgumentException("Must be at least one input and one output!");
        }
        this.nrOfInputs = nrOfInputs;
        this.nrOfOutputs = nrOfOutputs;
        initConnections();
    }

    private void initConnections() {
        int inovNumber = 0;
        for(int output = 0; output<nrOfOutputs; output++){
            for(int input = 0; input<nrOfInputs; input++){
                connectionGenes.add(new ConnectionGene(input,output+nrOfInputs,inovNumber));
                inovNumber++;
            }
        }
        lastNodeID = nrOfInputs + nrOfOutputs - 1;
    }

    public double[] getOutput(double[] inputValues){

        double[] result = new double[nrOfOutputs];
        int[] outputIDs = getOutputNodeIDs();
        for(int i = 0;i< nrOfOutputs;i++){
            result[i] = getNodeOutput(outputIDs[i],inputValues);
        }

        return result;
    }

    private double getNodeOutput(int nodeID, double[] inputVals){
        if(nodeID < nrOfInputs){
            return inputVals[nodeID];
        }
        double inputSum = 0;
        for(ConnectionGene connection:connectionGenes){
            if(connection.outputNode == nodeID && connection.enabled){
                inputSum += getNodeOutput(connection.inputNode,inputVals) * connection.weight;
            }
        }
        if (isOutputID(nodeID)) {
            return inputSum;
        }else {
            return transferFunction(inputSum);
        }
    }

    private boolean isOutputID(int nodeID) {
        int[] outpIDs = getOutputNodeIDs();
        int index = Arrays.binarySearch(outpIDs,nodeID);
        return index >= 0;
    }

    public double getConnectionWeight(int inputID, int outputID){
        ConnectionGene connectionGene = findConnection(inputID,outputID);
        return connectionGene.weight;
    }

    public void assignNewConnectionWeight(int inputNode, int outputNode,double newWeight){
        ConnectionGene geneToBeModified = findConnection(inputNode,outputNode);
        geneToBeModified.weight = newWeight;
    }

    public int addNode(){
        hiddenNodeIDs.add(++lastNodeID);
        return lastNodeID;
    }

    public void disableConnectionGene(int inputNode, int outputNode){
        ConnectionGene connection = findConnection(inputNode,outputNode);
        connection.enabled = false;
    }

    private ConnectionGene findConnection(int inputNode, int outputNode){
        for(ConnectionGene connection:connectionGenes){
            if(connection.inputNode == inputNode && connection.outputNode == outputNode){
                return connection;
            }
        }
        throw new IllegalArgumentException("Connection is not to be found in net!");
    }

    public void addConnectionGene(int inputNode, int outputNode, int innovationNumber, double weight){
        ConnectionGene toAdd = new ConnectionGene(inputNode,outputNode,innovationNumber);
        toAdd.weight = weight;
        for(ConnectionGene g:connectionGenes){
            if(g.equals(toAdd)){
                //Gene already in the list, maybe throw an Exception?
                return;
            }
        }
        connectionGenes.add(toAdd);
    }

    public boolean removeConnectionGene(int input,int output){
        boolean removed = false;
        for(ConnectionGene cg: new ArrayList<>(connectionGenes)){
            if(cg.inputNode == input && cg.outputNode == output) {
                connectionGenes.remove(cg);
                removed = true;
            }
        }
        return removed;
    }

    public int[] getInputNodeIDs(){
        int[] res = new int[nrOfInputs];
        for(int i = 0;i<nrOfInputs;i++){
            res[i] = i;
        }
        return res;
    }

    public int[] getOutputNodeIDs(){
        int[] res = new int[nrOfOutputs];
        for(int i = 0;i<nrOfOutputs;i++){
            res[i] = i + nrOfInputs;
        }
        return res;
    }

    public int[] getHiddenNodeIDs(){
        int[] res = new int[hiddenNodeIDs.size()];
        for(int i = 0;i<res.length;i++){
            res[i] = hiddenNodeIDs.get(i);
        }
        return res;
    }

    public int getHighestInnovationNumber(){
        int highestInnNumber = 0;
        for(ConnectionGene g:connectionGenes){
            if(g.innovationNumber>highestInnNumber){
                highestInnNumber = g.innovationNumber;
            }
        }
        return highestInnNumber;
    }

    public int[] getSuppyingNodesToNode(int nodeId){

        Set<Integer> set =  new HashSet<Integer>();

        supplyingNodes(nodeId,set);
        set.remove(nodeId);

        Integer[] res = set.toArray(new Integer[set.size()]);
        int[] result = new int[res.length];
        for(int i = 0;i<res.length;i++)
            result[i] = res[i];

        return result;
    }

    private void supplyingNodes(int nodeId, Set<Integer> nodesVisited){
        if(nodesVisited.contains(nodeId))
            return;
        else
            nodesVisited.add(nodeId);

        for(ConnectionGene connectionGene:getConnectionGenes()){
            if(connectionGene.outputNode == nodeId)
                supplyingNodes(connectionGene.inputNode,nodesVisited);
        }
    }

    public ArrayList<ConnectionGene> getConnectionGenes() {
        return new ArrayList<>(connectionGenes);
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString(){
        return "Genome, Nr or inputs: " + nrOfInputs + ", Nr of outputs: "+ nrOfOutputs + ", Nr of hidden nodes: "+ hiddenNodeIDs.size();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Genome)){
            return false;
        }
        Genome other = (Genome) obj;

        if( other == obj)
            return true;

        if(other.nrOfOutputs != nrOfOutputs ||
                other.nrOfInputs != nrOfInputs ||
                other.hiddenNodeIDs.size() != hiddenNodeIDs.size())
            return false;
        if(!sameNrOfNodes(this,other))
            return false;

        List<ConnectionGene> myEnabledGenes = this.getEnabledGenes();
        List<ConnectionGene> otherEnabledGenes = other.getEnabledGenes();

        if(myEnabledGenes.size() != otherEnabledGenes.size())
            return false;

        for(int i = 0;i<myEnabledGenes.size();i++){
            ConnectionGene my = myEnabledGenes.get(i);
            ConnectionGene oth = otherEnabledGenes.get(i);
            if(!my.equals(oth))
                return false;
        }

        return true;
    }

    public List<ConnectionGene> getEnabledGenes(){
        List<ConnectionGene> res = new ArrayList<>(connectionGenes);
        for(ConnectionGene g:res){
            if(!g.enabled)
                res.remove(g);
        }
        return res;
    }

    private static boolean sameNrOfNodes(Genome me, Genome other){
        return other.nrOfInputs == me.nrOfInputs &&
                other.nrOfOutputs == me.nrOfOutputs &&
                other.hiddenNodeIDs.size() == me.hiddenNodeIDs.size();
    }

    public static double transferFunction(double x){
        return 1/(1+Math.exp(-4.6 * x));
    }
}
