package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {

    private List<Tuple> donnees;
    private ReentrantLock mon_moniteur = new java.util.concurrent.locks.ReentrantLock();
	
    public CentralizedLinda() {
        donnees = new ArrayList<Tuple>();
    }

    @Override
    public void write(Tuple t) {
        try {
            mon_moniteur.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        donnees.add(t);     
        
        mon_moniteur.unlock();
        
    }

    @Override
    public Tuple take(Tuple template) {
        
        Tuple tupleTrouve = null;
        
            while (tupleTrouve == null) {
                try {
                    mon_moniteur.lock();
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
                mon_moniteur.unlock();

            }
            
        
        return tupleTrouve;
    }

    @Override
    public Tuple read(Tuple template) {
        Tuple tupleTrouve = null;
        
        while (tupleTrouve == null) {
            try {
                mon_moniteur.lock();
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
            mon_moniteur.unlock();
        }
        
        return tupleTrouve;
    }

    @Override
    public Tuple tryTake(Tuple template) {
        Tuple tupleTrouve = null;
        try {
            mon_moniteur.lock();
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
        mon_moniteur.unlock();
        return tupleTrouve;
    }

    @Override
    public Tuple tryRead(Tuple template) {
        Tuple tupleTrouve = null;
        try {
            mon_moniteur.lock();
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
        mon_moniteur.unlock();
        return tupleTrouve;
    }

    @Override
    public Collection<Tuple> takeAll(Tuple template) {
        ArrayList<Tuple> tuplesTrouves = new ArrayList<Tuple>();
        Tuple essaiTake;
        try {
            mon_moniteur.lock();
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
        mon_moniteur.unlock();
        return tuplesTrouves;
    }

    @Override
    public Collection<Tuple> readAll(Tuple template) {
        ArrayList<Tuple> tuplesTrouves = new ArrayList<Tuple>();
        try {
            mon_moniteur.lock();
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
        mon_moniteur.unlock();
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
