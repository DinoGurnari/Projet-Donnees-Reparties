package linda.test;

import linda.*;
/* Test pour la version centralisée de Linda */
/* Write + TryTake */
public class TestTryTake {

    public static void main(String[] a) {
                
        final Linda linda = new linda.shm.CentralizedLinda();
        // final Linda linda = new linda.server.LindaClient("//localhost:4000/LindaServer");

        Tuple t11 = new Tuple(4, 6);
        System.out.println("(2) write: " + t11);
        linda.write(t11);

        Tuple t2 = new Tuple("hello", 15);
        System.out.println("(2) write: " + t2);
        linda.write(t2);

        Tuple t3 = new Tuple(4, "foo");
        System.out.println("(2) write: " + t3);
        linda.write(t3);
                        
        Tuple motif = new Tuple(Integer.class, Integer.class);
        Tuple res = linda.tryRead(motif);
        System.out.println("(1) Resultat:" + res);
    
       res = linda.tryRead(motif);
       System.out.println("(1) Resultat:" + res);

       Tuple motif2 = new Tuple(String.class, String.class);
        Tuple res2 = linda.tryRead(motif2);
        System.out.println("(1) Resultat:" + res2);


        

                
    }
}