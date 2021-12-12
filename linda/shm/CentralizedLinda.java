package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {

    private List<Tuple> donnees;
    private Boolean ecriture_en_cours;
    private int nbl;
    private int nbe;
    private ReentrantLock mon_moniteur = new java.util.concurrent.locks.ReentrantLock();
    private Condition PL = mon_moniteur.newCondition();
    private Condition PE = mon_moniteur.newCondition();


	
    public CentralizedLinda() {
        donnees = new ArrayList<Tuple>();
        nbl = 0;
        nbe = 0;
        ecriture_en_cours = false;
    }

    void demanderEcriture() throws InterruptedException {
        mon_moniteur.lock();
        while (!(!ecriture_en_cours && nbl == 0)){
            nbe ++;
            PE.await();
            nbe --;
        }
        ecriture_en_cours = true;
        mon_moniteur.unlock();
    }

    void finEcriture() {
        mon_moniteur.lock();
        ecriture_en_cours = false;
        if (nbe != 0){
            PE.signal();
        } else {
            PL.signalAll();
        }
        mon_moniteur.unlock();
    }
    
    void demanderLecture() throws InterruptedException {
        mon_moniteur.lock();
        while (!(!ecriture_en_cours && nbe == 0)) {
            PL.await();
        }
        nbl ++;
        mon_moniteur.unlock();
    }

    void finLecture() {
        mon_moniteur.lock();
        nbl--;
        if (nbl == 0 && nbe > 0) {
            PE.signal();
        }
        mon_moniteur.unlock();
    }
    @Override
    public void write(Tuple t) {
        try {
        demanderEcriture();
        } catch (Exception e) {
            e.printStackTrace();
        }
        donnees.add(t);     
        
        finEcriture();
        
    }

    @Override
    public Tuple take(Tuple template) {
        
        Tuple tupleTrouve = null;
        
            while (tupleTrouve == null) {
                try {  demanderLecture();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!donnees.isEmpty()){
                    for (Tuple tuple : donnees) {
                        if (tuple.matches(template)) {
                            tupleTrouve = tuple;
                            donnees.remove(tuple);
                            break;
                        }
                    }
                }
                finLecture();

            }
            
        
        return tupleTrouve;
    }

    @Override
    public Tuple read(Tuple template) {
        Tuple tupleTrouve = null;
        
        while (tupleTrouve == null) {
            try {
                demanderLecture();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!donnees.isEmpty()){
                for (Tuple tuple : donnees) {
                    if (tuple.matches(template)) {
                        tupleTrouve = tuple.deepclone();
                        break;
                    }
                }
            }
            finLecture();
        }
        
        return tupleTrouve;
    }

    @Override
    public Tuple tryTake(Tuple template) {
        Tuple tupleTrouve = null;
        try {
            demanderEcriture();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!donnees.isEmpty()) {
            for (Tuple tuple : donnees) {
                if (tuple.matches(template)) {
                    tupleTrouve = tuple;
                    donnees.remove(tuple);
                    break;
                }
            }        
        }
        finEcriture();
        return tupleTrouve;
    }

    @Override
    public Tuple tryRead(Tuple template) {
        Tuple tupleTrouve = null;
        try {
            demanderLecture();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!donnees.isEmpty()) {
            for (Tuple tuple : donnees) {
                if (tuple.matches(template)) {
                    tupleTrouve = tuple.deepclone();
                    break;
                }   
            }
        }
        finLecture();
        return tupleTrouve;
    }

    @Override
    public Collection<Tuple> takeAll(Tuple template) {
        ArrayList<Tuple> tuplesTrouves = new ArrayList<Tuple>();
        Tuple essaiTake;
        try {
            demanderEcriture();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!donnees.isEmpty()) {
            /* On regarde si on peut Take */
            essaiTake = tryTake(template);
            if (essaiTake != null) {
                tuplesTrouves.add(essaiTake);
            }
            while (essaiTake != null) {
                /* On est oblige de passer par tryTake pour
                ne pas modifer la liste sur laquelle on itère */
                essaiTake = tryTake(template);
                if (essaiTake != null) {
                    tuplesTrouves.add(essaiTake);
                }
            }
        }
        finEcriture();
        return tuplesTrouves;
    }

    @Override
    public Collection<Tuple> readAll(Tuple template) {
        ArrayList<Tuple> tuplesTrouves = new ArrayList<Tuple>();
        try {
            demanderLecture();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!donnees.isEmpty()) {
            for (Tuple tuple : donnees) {
                if (tuple.matches(template)) {
                    tuplesTrouves.add(tuple.deepclone());
                }
            }
        }
        finLecture();
        return tuplesTrouves;
    }

    @Override
    public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void debug(String prefix) {
        System.out.println(prefix);
    }

    // TO BE COMPLETED

}
