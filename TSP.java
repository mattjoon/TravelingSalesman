// Matthew Lee
// Artificial Intelligence
// TSP #2
// 11/9/22

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

public class TSP {
    private State[] states;
    private LinkedList<State> finalTour;
    private Set<State> visited;
    
    //Default US
    public TSP() {   
        this.states = States.continentalStates;
        this.finalTour = new LinkedList<State>();
        this.visited = new HashSet<State>();
    }
    
    public TSP(boolean canada) {
        if(canada == true) this.states = Canada.provinces;
        else this.states = States.continentalStates;
        this.finalTour = new LinkedList<State>();
        this.visited = new HashSet<State>();
    }
    
    public double mst() {
        double tourDistance = 0;
        visited = new HashSet<State>();
        State[] spanningTree = new State[states.length];
        spanningTree[0] = randomState();
        visited.add(spanningTree[0]);
        
        while(visited.size() < states.length) {
            State currentState = null;
            double shortestDistance = Double.MAX_VALUE;
            for (int i = 0; i < visited.size(); i++) {
                State closest = closest(spanningTree[i]);
                double distance = spanningTree[i].capital().distance(closest.capital());
                if(distance < shortestDistance) {
                    shortestDistance = distance;
                    currentState = closest;
                }
            }
            spanningTree[visited.size()] = currentState;
            visited.add(currentState);
            tourDistance += shortestDistance;
        } 
        return tourDistance;  
    }
    
    public void twoOpt(int timesUntilEnd) {
        int counter = 0;
        double bestDistance = distance();
        while(counter < timesUntilEnd) {
            for(int i = 1; i < finalTour.size() - 1; i++) {
                for(int j = i + 1; j < finalTour.size(); j++) {
                    LinkedList<State> possibleNewTour = twoOptSwap(i, j);
                    double newDistance = distance(possibleNewTour);
                    if(newDistance < bestDistance) {
                        counter = 0;
                        bestDistance = newDistance;
                        finalTour = possibleNewTour;
                    }
                }
            }
            counter++;
        }
    }
    
    public LinkedList<State> twoOptSwap(int v1, int v2) {
        LinkedList<State> newTour = new LinkedList<State>();
        deepCopy(newTour, finalTour);
        int start = v2;
        for(int i = v1 + 1; i <= v2; i++) {
            newTour.add(i, finalTour.get(start));
            newTour.remove(i + 1);
            start--;
        }
        return newTour;
    }
    
    //Both ends
    public void nearestNeighbors(State start) {
        State rover = start;
        this.finalTour.add(this.visited.size(), rover);
        this.visited.add(rover);
        while(visited.size() < this.states.length) {
            rover = this.closest(rover);
            //rover = this.closest(start); for fastest 2opt
            this.finalTour.add(this.visited.size(), rover);
            this.visited.add(rover);
        }
    }
    
    public void deepCopy(LinkedList<State> toGetCopiedTo, LinkedList<State> toCopy) {
        for(int i = 0; i < toCopy.size(); i++) {
            State currentState = toCopy.get(i);
            toGetCopiedTo.add(currentState);
        }
    }
    
    //Helper for mst and nearestNeighbors
    public State closest(State state) {
        State rover = state;
        double closestDistance = Double.MAX_VALUE;
        for(int i = 0; i < states.length; i++) {
            double currentDistance = state.capital().distance(states[i].capital());
            //Shortest distance found
            if(currentDistance < closestDistance && visited.contains(states[i]) == false) {
                closestDistance = currentDistance;
                rover = states[i];
            }
        }
        return rover;
    }
    
    public void tour(State start, Tester.Method method) {
        switch(method) {
            case NN:
                nearestNeighbors(start);
                break;
            case GREEDY:
                break;
            //Will implement others later
            default:
                break;
        }
    }
    
    public double distance() {
        double totalDistance = 0;
        State state = this.finalTour.get(0);
        for (int i = 1; i < this.finalTour.size(); i++) {
            totalDistance += state.capital().distance(this.finalTour.get(i).capital());
            state = this.finalTour.get(i);
        }
        totalDistance += this.finalTour.get(0).capital().distance(this.finalTour.get(this.finalTour.size() - 1).capital());
        return totalDistance;
    }
    
    public double distance(LinkedList<State> possibleNewTour) {
        double totalDistance = 0;
        State state = possibleNewTour.get(0);
        for (int i = 1; i < possibleNewTour.size(); i++) {
            totalDistance += state.capital().distance(possibleNewTour.get(i).capital());
            state = possibleNewTour.get(i);
        }
        totalDistance += possibleNewTour.get(0).capital().distance(possibleNewTour.get(possibleNewTour.size() - 1).capital());
        return totalDistance;
    }
    
    public void print() {
        for(State state : this.finalTour) {
            System.out.print(state.code() + " ");
        }
    }
    
    public State randomState() {
        int random = (int) (Math.random() * states.length);
        while(visited.contains(states[random])) {
            //Continually randomize until not visited
            random = (int) (Math.random() * states.length);
        }
        return states[(random)];
    }

}