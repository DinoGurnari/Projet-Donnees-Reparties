package linda.shm;

import linda.Tuple;

/* Classe permettant de reveiller les take et read en attente */
public class Reveil {

    private Tuple motif;
    private Thread thread;

    public Reveil(Tuple motif, Thread thread) {
        this.motif = motif;
        this.thread = thread;
    }

    public Tuple getMotif() {
        return motif;
    }

    public Thread getThread() {
        return thread;
    }

    public void reveiller() {
        this.thread.notify();
    }

}
