package MutatingOfGenome;

public class Mutation {

        public int input;
        public int output;
        public int innovationNr;

        public Mutation(int input, int output,int innovationNr){
            this.input = input;
            this.output = output;
            this.innovationNr = innovationNr;
        }

        @Override
        public boolean equals(Object obj){
            if(!(obj instanceof Mutation))
                return false;
            Mutation m = (Mutation) obj;

            if(m.input != input)
                return false;
            if(m.output != output)
                return false;
            return true;
        }
    }

