package linda.server;



import javax.security.auth.callback.Callback;

import linda.Tuple;

public class RemoteCallbackServeur implements Callback {
    private RemoteCallbackInterface callback;

    RemoteCallbackServeur(RemoteCallbackInterface cb) {
        this.callback = cb;
    }
    
    public void call(Tuple t) {
        try {
            callback.call(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
}
