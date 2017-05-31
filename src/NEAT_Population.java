/**
 * Created by elias on 2017-05-22.
 */
public interface NEAT_Population {

    int getCurrentGeneration();
    void mutatePopulation();
    void createNextGeneration();
    Iterable<Genome> getPopulation();
    Genome getFittestGenome();

}
