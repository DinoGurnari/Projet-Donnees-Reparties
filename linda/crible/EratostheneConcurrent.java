package linda.crible;

import java.util.ArrayList;
import java.util.List;

import linda.Linda;
import linda.Tuple;
import linda.server.LindaClient;
import linda.shm.CentralizedLinda;

public class EratostheneConcurrent implements Eratosthene {
    
    public EratostheneConcurrent() {};

    public static void main(String[] args) {
        String URI = null;
        mode m;
        int n;
        int i = 2;
        Linda linda;
        List<Integer> resultat = new ArrayList<Integer>();

        /* On récupère l'entier n le mode d'utilisation et l'URI si besoin*/
        try {
            Integer I = Integer.valueOf(args[0]);
            n = I.intValue();
            m = mode.valueOf(args[1]);
            URI = args[2];
        }
        catch (Exception e) {
            System.out.println(" Please enter: java EratostheneConcurrent <n> <CENTRALIZED|REMOTE> <URI>");
            return;
        }
        /* On instantie un Linda suivant le mode choisi */
        if (m == mode.REMOTE){
            linda = new LindaClient(URI);
        } else {
            linda = new CentralizedLinda();
        }
        /* On crée les entiers nécessaires sous forme de tuples */
        Eratosthene.initialisation(linda, n);

        /* On retire les nombres non-premiers (multiple d'un autre)
        Puis on ajout i au resultat et on passe au prochain premier */
        while (i <= n) {
            Eratosthene.retirerMultiple(linda, n, i);
            resultat.add(Eratosthene.tupleToInt(linda.take(new Tuple(Integer.class))));

            Tuple prochain = linda.tryRead(new Tuple(Integer.class));

            if (prochain != null) {
                i = Eratosthene.tupleToInt(prochain);
            } else {
                break;
            }
        }

        /* On les affiches */
        System.out.println("Les nombres premiers jusqu'à " + n + " sont :");
        System.out.print(resultat.toString());

    }

}
