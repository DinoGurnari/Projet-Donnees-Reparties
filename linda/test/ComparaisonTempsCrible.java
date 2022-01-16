package linda.test;

import java.time.Duration;
import java.time.LocalTime;

import linda.crible.Eratosthene;
import linda.crible.EratostheneConcurrent;
import linda.crible.EratostheneSequentiel;
import linda.crible.Eratosthene.mode;

public class ComparaisonTempsCrible {

    enum typeCrible {SEQ, CONC};
    
    public static Duration[] test1(typeCrible type, int[] liste) {
        LocalTime t1;
        LocalTime t2;
        Duration tps[] = new Duration[liste.length];
        String args[] = {"", mode.CENTRALIZED.toString(), "null", "10"};

        for (int i = 0 ; i < liste.length; i++) {
            int j = liste[i];
            args[0] = String.valueOf(j);

            if (type == typeCrible.SEQ) {
                t1 = LocalTime.now();
                Eratosthene crible = new EratostheneSequentiel(args);
                t2 = LocalTime.now();
            } else {
                t1 = LocalTime.now();
                Eratosthene crible = new EratostheneConcurrent(args);
                t2 = LocalTime.now();
            }
            tps[i] = Duration.between(t1, t2);
        }

        return tps;
    }

    public static void main(String[] args) {
        // Nous allons evaluer le temps d'exécution pour differentes valeurs de n
        int listeN[] = {10, 50, 100, 500, 1000, 5000, 10000};

        Duration tpsSeq[] = new Duration[listeN.length];
        Duration tpsConc[] = new Duration[listeN.length];

        tpsSeq = test1(typeCrible.SEQ, listeN);
        tpsConc = test1(typeCrible.CONC, listeN);

        for (int i = 0; i < listeN.length ; i++) {
            System.out.println("Pour n = " + listeN[i] + " :");
            System.out.println("    Temps d'exécution du crible Séquentiel : " + tpsSeq[i].toMillis() + " ms");
            System.out.println("    Temps d'exécution du crible Concurrent : " + tpsConc[i].toMillis() + " ms\n");
        }
    }

}
