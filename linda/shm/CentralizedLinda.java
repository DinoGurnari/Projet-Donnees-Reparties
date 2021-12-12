package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {

    private List<Tuple> donnees;
	
    public CentralizedLinda() {
        donnees = new ArrayList<Tuple>();
    }

    @Override
    public void write(Tuple t) {
        donnees.add(t);       
    }

    @Override
    public Tuple take(Tuple template) {
        Tuple tupleTrouve = null;

        while (tupleTrouve == null) {
            if (!donnees.isEmpty()){
                for (Tuple tuple : donnees) {
                    if (tuple.matches(template)) {
                        tupleTrouve = tuple;
                        donnees.remove(tuple);
                        break;
                    }
                }
            }
        }
        return tupleTrouve;
    }

    @Override
    public Tuple read(Tuple template) {
        Tuple tupleTrouve = null;

        while (tupleTrouve == null) {
            if (!donnees.isEmpty()){
                for (Tuple tuple : donnees) {
                    if (tuple.matches(template)) {
                        tupleTrouve = tuple.deepclone();
                        break;
                    }
                }
            }
        }
        return tupleTrouve;
    }

    @Override
    public Tuple tryTake(Tuple template) {
        Tuple tupleTrouve = null;

        if (!donnees.isEmpty()) {
            for (Tuple tuple : donnees) {
                if (tuple.matches(template)) {
                    tupleTrouve = tuple;
                    donnees.remove(tuple);
                    break;
                }
            }        
        }
        return tupleTrouve;
    }

    @Override
    public Tuple tryRead(Tuple template) {
        Tuple tupleTrouve = null;

        if (!donnees.isEmpty()) {
            for (Tuple tuple : donnees) {
                if (tuple.matches(template)) {
                    tupleTrouve = tuple.deepclone();
                    break;
                }   
            }
        }
        return tupleTrouve;
    }

    @Override
    public Collection<Tuple> takeAll(Tuple template) {
        ArrayList<Tuple> tuplesTrouves = new ArrayList<Tuple>();
        Tuple essaiTake;

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
        return tuplesTrouves;
    }

    @Override
    public Collection<Tuple> readAll(Tuple template) {
        ArrayList<Tuple> tuplesTrouves = new ArrayList<Tuple>();

        if (!donnees.isEmpty()) {
            for (Tuple tuple : donnees) {
                if (tuple.matches(template)) {
                    tuplesTrouves.add(tuple.deepclone());
                }
            }
        }
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
