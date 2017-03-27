import java.util.ArrayList;

/**
 * Created by martinmeincke on 21/03/2017.
 */
public class Main {

    public static void main(String[] args){
        TransitionSystem ts = new TransitionSystem();

//        ts.add(1, true,  new String[] {"v"}, new int[] {2});
//        ts.add(2, false, new String[] {"v"}, new int[] {1, 4});
//        ts.add(3, false, new String[] {"c"}, new int[] {3});
//        ts.add(4, false, new String[] {"c"}, new int[] {4});

        //Towers of Hanoi w. 2 disks
        ts.add(11, true,   new String[] {"v"}, new int[] {21, 31});
        ts.add(21, false,  new String[] {"v"}, new int[] {11, 31, 23});
        ts.add(31, false,  new String[] {"v"}, new int[] {11, 21, 32});
        ts.add(23, false,  new String[] {"v"}, new int[] {21, 33, 13});
        ts.add(33, false,  new String[] {"v"}, new int[] {23, 13});
        ts.add(13, false,  new String[] {"v"}, new int[] {33, 23, 12});
        ts.add(12, false,  new String[] {"v"}, new int[] {13, 32, 22});
        ts.add(22, false,  new String[] {"v"}, new int[] {12, 32});
        ts.add(32, false,  new String[] {"v"}, new int[] {22, 12, 31});

        System.out.println(ts.ctlEX(ts.ctlAX(ts.ctlAP("v"))));
    }
}
