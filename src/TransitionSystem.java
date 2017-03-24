import javafx.animation.Transition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by martinmeincke on 21/03/2017.
 */
public class TransitionSystem {
    ArrayList<TransitionState> states;

    public TransitionSystem(){
        this.states = new ArrayList<TransitionState>();
    }

    public void add(int state, boolean initalState, String[] atomicPropositions, int[] relatedStates){
        states.add(new TransitionState(state, initalState, atomicPropositions, relatedStates));
    }

    public ArrayList<TransitionState> getStates() {
        return states;
    }

    public void setStates(ArrayList<TransitionState> states) {
        this.states = states;
    }

    public void remove(int index){
        states.remove(index);
    }

    // function ctlAP produces the list of states where the proposition argument applies
    public ArrayList<TransitionState> ctlAP(String proposition){
        ArrayList<TransitionState> ts = new ArrayList<TransitionState>();

        for(TransitionState state : this.states){
            for(String prop : state.getAtomicPropositions() ) {
                if (prop.equals(proposition)) {
                    ts.add(state);
                }
            }
        }
        return ts;
    }

    public ArrayList<TransitionState> ctlAG(ArrayList<TransitionState> states){
        HashSet<Integer> allRelatedIds = new HashSet<Integer>();

        for (TransitionState ts : states) {
           for(int rs : ts.getRelatedStates()){
               allRelatedIds.add(rs);
           }
        }

        int[] allRelatedIdsArray =  allRelatedIds.stream().mapToInt(i -> i).toArray();

        if(compare(getAllTransitionStatesFromIds(allRelatedIdsArray), states)){
            return states;
        }

        return new ArrayList<TransitionState>();
    }



    public ArrayList<TransitionState> ctlEX(ArrayList<TransitionState> tsInputs){
        ArrayList<TransitionState> tsList = new ArrayList<TransitionState>();
        for(TransitionState ts : this.states){
            for(int rs : ts.getRelatedStates()){
                for(TransitionState tsInput : tsInputs){
                    if(tsInput.getState() == rs && !tsList.contains(ts) ){
                        tsList.add(ts);
                    }
                }
            }
        }
        return tsList;
    }

    public ArrayList<TransitionState> ctlAX(ArrayList<TransitionState> states){
        return not(ctlEX(not(states)));
    }

    private boolean compare(ArrayList<TransitionState> states1, ArrayList<TransitionState> states2){
        return states1.containsAll(states2);
    }

    private TransitionState getTransitionStateFromId(int id){
        for(TransitionState ts : this.states){
            if (ts.getState() == id){
                return ts;
            }
        }
        return null;
    }

    private ArrayList<TransitionState> getAllTransitionStatesFromIds(int[] ids){
        ArrayList<TransitionState> tsList = new ArrayList<TransitionState>();

        for(int id : ids){
            tsList.add(getTransitionStateFromId(id));
        }

        return tsList;
    }

    private ArrayList<TransitionState> and(ArrayList<TransitionState> states1, ArrayList<TransitionState> states2){
        HashSet<TransitionState> ts = new HashSet<TransitionState>();

        ts.addAll(states1);
        ts.addAll(states2);

        return new ArrayList<TransitionState>(ts);
    }

    public ArrayList<TransitionState> not(ArrayList<TransitionState> inputStates){

        HashSet<TransitionState> ts = new HashSet<TransitionState>();

        for(TransitionState state : this.states){
            if(!Arrays.stream(getStateIds(inputStates)).anyMatch(i -> i == state.getState())){
                ts.add(state);
            }
        }
        return new ArrayList<TransitionState>(ts);
    }


    public int[] getStateIds(ArrayList<TransitionState> states){
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for(TransitionState state : states){
            ids.add(state.getState());
        }

        return ids.stream().mapToInt(i -> i).toArray();
    }

    public String toString(){
        String toString = "";
        for (TransitionState s : states) {
            toString += "{ "+ s.toString() + " }\n";
        }
        return toString;
    }
}
