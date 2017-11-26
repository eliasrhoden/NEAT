
/**
 * Created by elias on 2017-05-22.
 */
public class PopulationParams {
    //Network properties
    public int NR_OF_INPUTS;
    public int NR_OF_OUTPUTS;

    //Population limits
    public int POPULATION_SIZE;

    //Constants fro measuring capability
    public double C1;
    public double C2;
    public double C3;

    public double DELTA_T;

    //Cross-over properties
    public double TOP_GENOMES_TO_COPY_TO_NEXT_GEN_PERCENTAGE;       //Percentage of the fittest networks that just get copied into next generation
    public double TOP_GENOMES_TO_CROSSOVER_TO_NEXT_GEN_PERCENTAGE;  //Percentage of the fittest networks that are allowed to crossover, to from the next generation
}
