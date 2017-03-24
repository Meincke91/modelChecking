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
        ArrayList<TransitionState> tsList = new ArrayList<TransitionState>();
        
        for (TransitionState ts : states) {
           for(int rs : ts.getRelatedStates()){
            //if
           }

        }


        return tsList;
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

    private TransitionState getTransitionStateFromId(int id){
        for(TransitionState ts : this.states){
            if (ts.getState() == id){
                return ts;
            }
        }
        return null;
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
