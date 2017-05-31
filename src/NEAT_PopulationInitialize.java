/**
 * Created by elias on 2017-05-22.
 */
public class NEAT_PopulationInitialize {

    public int nrOfInputs;
    public int nrOfOutputs;
    public int populationSize;
    public GenomeMutator mutator;

    /*
    public void setNrOfInputs(int nrOfInputs) {
        this.nrOfInputs = nrOfInputs;
    }

    public void setNrOfOutputs(int nrOfOutputs) {
        this.nrOfOutputs = nrOfOutputs;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setMutator(GenomeMutator mutator) {
        this.mutator = mutator;
    }
*/

    public boolean isComplete() {

        return nrOfInputs != 0 && nrOfOutputs != 0 &&
                populationSize != 0 && mutator != null;
    }
}
