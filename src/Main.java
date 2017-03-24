import java.util.ArrayList;

/**
 * Created by martinmeincke on 21/03/2017.
 */
public class Main {

    public static void main(String[] args){
        TransitionSystem ts = new TransitionSystem();

        ts.add(1, true,  new String[] {"v"}, new int[] {2});
        ts.add(2, false, new String[] {"v"}, new int[] {1, 4});
        ts.add(3, false, new String[] {"c"}, new int[] {3});
        ts.add(4, false, new String[] {"c"}, new int[] {4});


        System.out.println(ts.ctlEX(ts.ctlAG(ts.ctlAP("c"))));
    }
}
