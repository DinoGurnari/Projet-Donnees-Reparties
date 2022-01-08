package linda.server;

import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;

public class LindaServer implements Linda {

    private Linda lindaCentralise;
    String uri; // Utiliser un type URI ?

    public LindaServer() {
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
    
}
