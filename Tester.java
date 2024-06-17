// Matthew Lee
// Artificial Intelligence
// TSP #2
// 11/9/22

public class Tester {

    public enum Method {
        NN,
        GREEDY,
    }

    public static void main(String[] args) {
        TSP tsp = new TSP(false);
        
        Method algorithm = Method.NN;
        boolean canada = false;
        boolean twoOpt = false;
        String state = "";
        String option = "";
        int limit = 100;
        
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "-usa":
                    tsp = new TSP(false);
                    canada = false;
                    break;
                case "-canada":
                    tsp = new TSP(true);
                    canada = true;
                    break;
                case "-nn":
                    algorithm = Method.NN;
                    break;
                case "-greedy":
                    algorithm = Method.GREEDY;
                    break;
                case "-twoopt":
                    twoOpt = true;
                    break;
                case "-limit":
                    if (option.length() > 0) {
                        System.err.println("Missing value for option: " + option);
                    }
                    option = arg;
                    continue;
                default:
                    try {
                        limit = Integer.parseInt(arg);
                    } catch(NumberFormatException e) {
                        state = arg.toLowerCase();
                    }
                    break;
            }
            try {
                switch (option) {
                    case "-limit":
                        twoOpt = true;
                        limit = Integer.parseInt(arg);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid value for option " + option + ": " + arg);
            }
            option = "";
        }
                
        if(canada == true) {
            if(state == "") state = Canada.provinces[(int) (Math.random() * Canada.provinces.length)].code();
            switch(algorithm) {
                case NN:
                    System.out.println(Canada.find(state.toUpperCase()));
                    tsp.nearestNeighbors(Canada.find(state.toUpperCase()));
                    if(twoOpt == true) tsp.twoOpt(limit);
                case GREEDY:
                    
            }
        }
        else {
            if(state == "") state = States.states[(int) (Math.random() * States.states.length)].code();
            switch(algorithm) {
                case NN:
                    System.out.println(States.find(state.toUpperCase()));
                    tsp.nearestNeighbors(States.find(state.toUpperCase()));
                    if(twoOpt == true) tsp.twoOpt(limit);
                case GREEDY:
                    
            }
        }
        
        tsp.print();
        System.out.println("");
        System.out.println("Tour Length = " + String.format("%.1f", tsp.distance()) + "km.");
        System.out.println("OPT TSP LENGTH >= " + String.format("%.1f", tsp.mst()) + "km.");
        System.out.println("OPT TSP LENGTH <= " + String.format("%.1f", 1.5 * tsp.mst()) + "km.");
    }
}
