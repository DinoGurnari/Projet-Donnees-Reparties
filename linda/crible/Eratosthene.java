package linda.crible;

import linda.Linda;
import linda.Tuple;

public interface Eratosthene {

    public enum mode {CENTRALIZED, REMOTE};

    /* Méthode qui ajoute les n premiers entiers au linda (0 et 1 exclus) */
    public static void initialisation(Linda linda, int n){
        for (int i = 2; i <= n; i++) {
            linda.write(new Tuple(i));
        }
    }

    /* Méthode qui retire tout les multiples de i du linda*/
    public static void retirerMultiple(Linda linda, int n, int i) {
        Tuple templateMultiple;

        for (int j = i*i; j <= n; j+=i) {
            templateMultiple = new Tuple(j);
            linda.tryTake(templateMultiple);
        }
    }

    /* Méthode pour convertir les Tuples en int */
    public static int tupleToInt(Tuple t) {
        String s = t.toString();
        s = s.substring(2, s.length()-2);

        return Integer.valueOf(s);
    }

}
