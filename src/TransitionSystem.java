import com.sun.tools.javac.util.ArrayUtils;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sun.tools.doclint.Entity.copy;
import static com.sun.tools.doclint.Entity.ge;
import static javafx.scene.input.KeyCode.M;

/**
 * Created by martinmeincke on 21/03/2017.
 */
public class TransitionSystem{
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

    public ArrayList<TransitionState> ctlEF(ArrayList<TransitionState> inputStates){
        ArrayList<TransitionState> returnStates = new ArrayList<TransitionState>();
        if(inputStates.isEmpty()){
            return returnStates;
        }
        ArrayList<TransitionState> flippedSystem = getFlippedStates();

        int[] inputIds = getStateIds(inputStates);


        ArrayList<Integer> visitedStates = new ArrayList<Integer>();

        Stack<TransitionState> stack = new Stack<>();
        // Push corresponding input states in flipped system onto the starting stack. The DFS will start from these
        for(TransitionState state : inputStates){

            stack.push(getTransitionStateFromId(state.getState(),flippedSystem));
        }
        //Fetch top element in the stack(without pop);
        TransitionState node = stack.peek();
        while(!stack.isEmpty()){
            if(!visitedStates.contains(node.getState())) {
                visitedStates.add(node.getState());
            }
            // Get child of node
            Integer nextId = getNextUnvisitedID(node,visitedStates);
            // Push child or pop if no unvisted child is found.
            if(nextId != null){
                node = getTransitionStateFromId(nextId,flippedSystem);
                stack.push(node);
            } else {
                stack.pop();
                if(!stack.empty()){
                    node = stack.peek();
                }
            }

        }

        return returnStates;
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


    // ------------------------------------------------- Helper methods --------------------------------------------- //


    private boolean compare(ArrayList<TransitionState> states1, ArrayList<TransitionState> states2){
        return states1.containsAll(states2);
    }

    private Integer getNextUnvisitedID(TransitionState state,ArrayList<Integer> vistedStates ){
        for(int id : state.getRelatedStates()) {
            if(!vistedStates.contains(id)){
                return id;
            }
        }
        return null;
    }

    public ArrayList<TransitionState> getFlippedStates(){
        // Clone states into new object to avoid modifying original
        ArrayList<TransitionState> ts = new ArrayList<>();
        for (TransitionState state : this.getStates()){
            ts.add((TransitionState)state.clone());
        }

        // Add all states to return list, with no
        for (TransitionState state : ts){
            state.setRelatedStates(new int[] {});
        }

        // Add flipped related states.
        for (TransitionState state : this.getStates()) {
            for (int id : state.getRelatedStates()) {
                TransitionState curState = getTransitionStateFromId(id, ts);
                //Check if int array already contains value
                if(!IntStream.of(curState.getRelatedStates()).anyMatch(x -> x == state.getState())){
                    curState.setRelatedStates(concat(curState.getRelatedStates(),new int[] {state.getState()}));
                }

            }
        }
        return ts;
    }

    public int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] c= new int[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }


    private TransitionState getTransitionStateFromId(int id, ArrayList<TransitionState> stateList){
        for(TransitionState ts : stateList){
            if (ts.getState() == id){
                return ts;
            }
        }
        return null;
    }

    private ArrayList<TransitionState> getAllTransitionStatesFromIds(int[] ids){
        ArrayList<TransitionState> tsList = new ArrayList<TransitionState>();

        for(int id : ids){
            tsList.add(getTransitionStateFromId(id, this.getStates()));
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
