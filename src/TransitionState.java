import java.util.ArrayList;

/**
 * Created by martinmeincke on 21/03/2017.
 */
public class TransitionState {

    private int state;
    private boolean initalState;
    private String[] atomicPropositions;
    private int[] relatedStates;

    public TransitionState() {
        super();
    }

    public TransitionState(int state, boolean initalState, String[] atomicPropositions, int[] relatedStates) {
        this.state = state;
        this.initalState = initalState;
        this.atomicPropositions = atomicPropositions;
        this.relatedStates = relatedStates;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isInitalState() {
        return initalState;
    }

    public void setInitalState(boolean initalState) {
        this.initalState = initalState;
    }

    public String[] getAtomicPropositions() {
        return atomicPropositions;
    }

    public void setAtomicPropositions(String[] atomicPropositions) {
        this.atomicPropositions = atomicPropositions;
    }

    public int[] getRelatedStates() {
        return relatedStates;
    }

    public void setRelatedStates(int[] relatedStates) {
        this.relatedStates = relatedStates;
    }


    public String toString(){
        return "state: " + state + ", isInitialState: " + initalState + ", atomic propositions: " + atomicPropositions.toString() + ", relatedStates: " + relatedStates.toString();
    }

}
