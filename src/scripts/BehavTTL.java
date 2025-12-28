package scripts;

import core.eng.Behaviour;
import core.eng.IBehaviour;
import util.Invoker;

/**
 * Implements fixed time to live
 * @author hdaniel@ualg.pt
 * @version 202502081132
 */
public class BehavTTL extends Behaviour {

    protected int ttl;  //milisecs
    protected Invoker ttlInvoker;  //milisecs

    private void init(int ttl) { this.ttl = ttl;  }
    public BehavTTL(int ttl) { init(ttl); }
    public BehavTTL(BehavTTL b) {
        super(b);
        init(b.ttl);
    }

    @Override
    public void onInit() {
        //ttlInvoker = new Invoker(() -> { destroy(); }, 0, 5000, ttl);
        ttlInvoker = new Invoker(this::expire, 0, 5000, ttl);
    }

    /**
     * Removes the game object without explosion
     * models fuel exhausted for missiles, etc.
     */
    private void expire() {
        gameObject.engine().destroy(gameObject);
        ttlInvoker.cancel();
    }

    @Override
    public IBehaviour copy() {
        return new BehavTTL(this);
    }

}
