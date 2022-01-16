package linda.search.improved;

import linda.*;

public class Main {

    public static void main(String args[]) {
    	if (args.length != 2) {
            System.err.println("linda.search.improved.Main search file.");
            return;
    	}
        Linda linda = new linda.shm.CentralizedLinda();
        Manager manager = new Manager(linda, args[1], args[0]);
        Searcher searcher = new Searcher(linda);
        (new Thread(manager)).start();

        int n = 7; // Number of threads
        for (int i = 0; i < n; i++) {
            new Thread(searcher).start();
        }
    }
}
