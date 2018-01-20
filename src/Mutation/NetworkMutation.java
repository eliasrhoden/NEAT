package Mutation;

import Network.Genome;

import java.util.*;

public abstract class NetworkMutation {
    private int inputNodeID;
    private int outputNodeID;
    public int innovationNumber;

    public NetworkMutation(int inputNodeID, int outputNodeID){
        this.inputNodeID = inputNodeID;
        this.outputNodeID = outputNodeID;
    }

    public NetworkMutation(int inputNodeID, int outputNodeID, int innovationNumber){
        this.inputNodeID = inputNodeID;
        this.outputNodeID = outputNodeID;
        this.innovationNumber = innovationNumber;
    }


    public boolean equals(Object obj){

        boolean res = true;
        NetworkMutation otherNet = null;

        if(obj instanceof NetworkMutation){
            otherNet = (NetworkMutation) obj;
            boolean sameMutationSUbClas = this.getClass().equals(otherNet.getClass());
            res = res && sameMutationSUbClas;
            res = res && otherNet.inputNodeID == this.inputNodeID;
            res = res && otherNet.outputNodeID == this.outputNodeID;
        }else{
            res = false;
        }
        return res;
    }


    public void applyToGenome(Genome g) throws Exception{

        List<Integer> possibleInputs = new ArrayList<Integer>();
        List<Integer> possibleOutputs = new ArrayList<Integer>();
        List<Integer> inIds = arrayAsList(g.getInputNodeIDs());
        List<Integer> outIds = arrayAsList(g.getOutputNodeIDs());
        List<Integer> hiddenIds = arrayAsList(g.getHiddenNodeIDs());

        possibleInputs.addAll(inIds);
        possibleInputs.addAll(hiddenIds);

        possibleOutputs.addAll(hiddenIds);
        possibleOutputs.addAll(outIds);

        if(!possibleInputs.contains(this.inputNodeID))
            throw new IllegalArgumentException("Ivalid input node for mutation!");

        if(!possibleOutputs.contains(this.outputNodeID))
            throw new IllegalArgumentException("Invalid output node for mutation!");

        applySpecificToGenome(g);
    }

    private List<Integer> arrayAsList(int[] array){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i:array){
            res.add(i);
        }
        return res;
    }

    public abstract void applySpecificToGenome(Genome genome);
}