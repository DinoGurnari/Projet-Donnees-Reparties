package linda.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.lang.Integer;

import linda.Callback;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;

public class LindaServer extends java.rmi.server.UnicastRemoteObject implements LindaRemote {

    private Linda lindaCentralise;

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
    public void  eventRegister(linda.Linda.eventMode mode, linda.Linda.eventTiming timing, Tuple template, RemoteCallbackInterface callback) throws RemoteException {
        RemoteCallbackServeur rcs = new RemoteCallbackServeur(callback);
        lindaCentralise.eventRegister(mode, timing, template, rcs);
        
    }

    @Override
    public void debug(String prefix) throws RemoteException {
        this.lindaCentralise.debug(prefix);
    }

    public static void main(String[] args) {
        String URI;
        int port;
        try {
            Integer I = Integer.valueOf(args[0]);
            port = I.intValue();
        }
        catch (Exception e) {
            System.out.println(" Please enter: java LindaServer <port>");
            return;
        }
        try {
            // Launching the naming service – rmiregistry – within the JVM
            Registry registry = LocateRegistry.createRegistry(port);
            // Create an instance of the server object
            LindaServer serveur = new LindaServer();
            // compute the URL of the server
            URI = "//localhost:" + port + "/LindaServer"; // Utiliser un type URI ?
            Naming.rebind(URI, serveur);
            System.out.println("Linda Server " + " bound in registry");
            System.out.println("URI = " + URI);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
