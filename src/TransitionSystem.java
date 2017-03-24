import java.util.ArrayList;

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
        ArrayList<TransitionState> ts = new ArrayList<TransitionState>();

        return ts;
    }

    public ArrayList<TransitionState> ctlEX(ArrayList<TransitionState> states){
        ArrayList<TransitionState> ts = new ArrayList<TransitionState>();

        return ts;
    }

    public String toString(){
        String toString = "";
        for (TransitionState s : states) {
            toString += "{ "+ s.toString() + " }\n";
        }
        return toString;
    }
}
