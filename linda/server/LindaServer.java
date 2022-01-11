package linda.server;

import java.rmi.RemoteException;
import java.util.Collection;

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
    public void write(Tuple t) throws RemoteException {
        this.lindaCentralise.write(t);
        
    }

    @Override
    public Tuple take(Tuple template) throws RemoteException {
        return this.lindaCentralise.take(template);
    }

    @Override
    public Tuple read(Tuple template) throws RemoteException {
        return this.lindaCentralise.read(template);
    }

    @Override
    public Tuple tryTake(Tuple template) throws RemoteException {
        return this.lindaCentralise.tryTake(template);
    }

    @Override
    public Tuple tryRead(Tuple template) throws RemoteException {
        return this.lindaCentralise.tryRead(template);
    }

    @Override
    public Collection<Tuple> takeAll(Tuple template) throws RemoteException {
        return this.lindaCentralise.takeAll(template);
    }

    @Override
    public Collection<Tuple> readAll(Tuple template) throws RemoteException {
        return this.lindaCentralise.readAll(template);
    }

    @Override
    public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void debug(String prefix) throws RemoteException {
        this.lindaCentralise.debug(prefix);
    }
    
}
