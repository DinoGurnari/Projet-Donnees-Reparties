package linda.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Client part of a client/server implementation of Linda.
 * It implements the Linda interface and propagates everything to the server it is connected to.
 * */
public class LindaClient implements Linda {
    
    private LindaRemote serveur;

    /** Initializes the Linda implementation.
     *  @param serverURI the URI of the server, e.g. "rmi://localhost:4000/LindaServer" or "//localhost:4000/LindaServer".
     */
    public LindaClient(String serverURI) {
        try {
            this.serveur = (LindaRemote) Naming.lookup(serverURI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Tuple t) {
        try {
            this.serveur.write(t);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    @Override
    public Tuple take(Tuple template) {
        Tuple resultat = null;
        try {
            resultat = this.serveur.take(template);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return resultat;
    }

    @Override
    public Tuple read(Tuple template) {
        Tuple resultat = null;
        try {
            resultat = this.serveur.read(template);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return resultat;
    }

    @Override
    public Tuple tryTake(Tuple template) {
        Tuple resultat = null;
        try {
            resultat = this.serveur.tryTake(template);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return resultat;
    }

    @Override
    public Tuple tryRead(Tuple template) {
        Tuple resultat = null;
        try {
            resultat = this.serveur.tryRead(template);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return resultat;
    }

    @Override
    public Collection<Tuple> takeAll(Tuple template) {
        Collection<Tuple> resultat = null;
        try {
            resultat = this.serveur.takeAll(template);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return resultat;
    }

    @Override
    public Collection<Tuple> readAll(Tuple template) {
        Collection<Tuple> resultat = null;
        try {
            resultat = this.serveur.readAll(template);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return resultat;
    }

    @Override
    public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
        try {
            RemoteCallbackInterface rci = new RemoteCallback(callback);
            this.serveur.eventRegister(mode, timing, template, rci);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void debug(String prefix) {
        try {
            this.serveur.debug(prefix);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
    
    // TO BE COMPLETED

}
