package linda.server;
import java.rmi.RemoteException;
import linda.Callback;
import linda.Tuple;

public class RemoteCallback implements RemoteCallbackInterface {
    private Callback cb;

    RemoteCallback (Callback cb) {
        this.cb = cb;
    }

    @Override
    public void call(Tuple t) throws RemoteException {
        cb.call(t);
    }
    
}
