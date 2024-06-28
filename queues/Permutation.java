/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randQueueu = new RandomizedQueue<>();

        int k = Integer.parseInt(args[0]);

        if (k == 0) {
            return;
        }

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randQueueu.enqueue(str);
        }

        Iterator<String> iter = randQueueu.iterator();

        for (int i = 0; i < k; i++) {
            StdOut.println(iter.next());
        }
    }
}
