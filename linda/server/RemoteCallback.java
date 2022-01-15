package linda.server;
import java.rmi.RemoteException;
import linda.Callback;
import linda.Tuple;

public class RemoteCallback extends java.rmi.server.UnicastRemoteObject implements RemoteCallbackInterface {
    private Callback cb;

    RemoteCallback (Callback cb) throws RemoteException {
        this.cb = cb;
    }

    @Override
    public void call(Tuple t) throws RemoteException {
        cb.call(t);
    }
    
}
