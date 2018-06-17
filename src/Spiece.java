import Network.Genome;

import java.util.ArrayList;
import java.util.List;

public class Spiece implements Comparable{

    public final Genome master;
    public final List<Genome> genomes;
    public int averageFitness;

    public Spiece(Genome master){
        this.master = master;
        genomes = new ArrayList<>();
        genomes.add(master);
    }

    @Override
    public int compareTo(Object o) {
        if(o == null || !(o instanceof Spiece)){
            return 0;
        }
        int otherFitness = ((Spiece) o).averageFitness;
        return otherFitness-averageFitness;
    }
}
