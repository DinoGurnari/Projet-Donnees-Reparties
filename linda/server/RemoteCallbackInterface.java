package linda.server;



import java.rmi.*;

import linda.Tuple;


public interface RemoteCallbackInterface extends java.rmi.Remote {
      /** Callback when a tuple appears. 
     * See Linda.eventRegister for details.
     * 
     * @param t the new tuple
     */
    void call(Tuple t) throws RemoteException;
}
