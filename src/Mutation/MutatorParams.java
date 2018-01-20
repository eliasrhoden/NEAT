package Mutation;

import java.lang.reflect.Field;

/**
 * Created by elias on 2017-05-22.
 */
public class MutatorParams {
    public double PROBABILITY_OF_NEW_NODE;
    public double PROBABILITY_OF_NEW_CONNECTION;
    public double PROBABILITY_OF_WEIGHT_MUTATION;
    public double PROBABILITY_OF_NEW_RANDOM_WEIGHT_ON_CONNECTION;
    public double PROBABILITY_OF_SLIGHT_CHANGE_OF_WEIGHT_ON_CONNECTION;

    public boolean isValid(){
        Field[] params = this.getClass().getDeclaredFields();
        boolean valid = true;
        for(Field f:params){
            try {
                valid = valid && f.getDouble(this) != 0;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return valid;
    }
}
