package linda.server;
import linda.Tuple;

public class RemoteCallbackServeur implements linda.Callback {
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
