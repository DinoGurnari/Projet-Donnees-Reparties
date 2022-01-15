package linda.crible;

import linda.Linda;

public class ThreadEratosthene implements Runnable {

    private Linda linda;
    private int n;
    private int i;

    public ThreadEratosthene(Linda l, int n, int i) {
        this.linda = l;
        this.n = n;
        this.i = i;
    }

    @Override
    public void run() {
        Eratosthene.retirerMultiple(this.linda, this.n, this.i);
    }
    
}
