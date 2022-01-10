package linda.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import linda.Callback;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;

public class LindaServer extends java.rmi.server.UnicastRemoteObject implements LindaRemote {

    private Linda lindaCentralise;
    String uri = "//localhost:4000/LindaServer"; // Utiliser un type URI ?

    public LindaServer() throws RemoteException {
        this.lindaCentralise = new CentralizedLinda();
    }

    @Override
    public void write(Tuple t) {
        this.lindaCentralise.write(t);
        
    }

    @Override
    public Tuple take(Tuple template) {
        return this.lindaCentralise.take(template);
    }

    @Override
    public Tuple read(Tuple template) {
        return this.lindaCentralise.read(template);
    }

    @Override
    public Tuple tryTake(Tuple template) {
        return this.lindaCentralise.tryTake(template);
    }

    @Override
    public Tuple tryRead(Tuple template) {
        return this.lindaCentralise.tryRead(template);
    }

    @Override
    public Collection<Tuple> takeAll(Tuple template) {
        return this.lindaCentralise.takeAll(template);
    }

    @Override
    public Collection<Tuple> readAll(Tuple template) {
        return this.lindaCentralise.readAll(template);
    }

    @Override
    public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void debug(String prefix) {
        this.lindaCentralise.debug(prefix);
    }

    public enum actions {WRITE, READ, TAKE, TRYREAD, TRYTAKE, READALL, TAKEALL};

    @Override
    public List<Tuple> actionRemote(actions ac, Tuple t) throws RemoteException {
        ArrayList<Tuple> result = new ArrayList<Tuple>();
        switch (ac) {
            case WRITE:
                write(t);
                break;
        
            case READ:
                read(t);
                break;

            case TAKE:
                take(t);
                break;

            case TRYREAD:
                tryRead(t);
                break;

            case TRYTAKE:
                tryTake(t);
                break;

            case READALL:
                readAll(t);
                break;

            case TAKEALL:
                takeAll(t);
                break;

            default:
                break;
        }
        return result;
    }
    
}
