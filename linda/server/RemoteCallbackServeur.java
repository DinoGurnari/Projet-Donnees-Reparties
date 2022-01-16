package linda.server;
import java.rmi.Naming;

import linda.Tuple;

public class RemoteCallbackServeur implements linda.Callback {
    private RemoteCallbackInterface callback;

    RemoteCallbackServeur(String cbURI) {
        try {
            this.callback = (RemoteCallbackInterface) Naming.lookup(cbURI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void call(Tuple t) {
        try {
            callback.call(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
}
