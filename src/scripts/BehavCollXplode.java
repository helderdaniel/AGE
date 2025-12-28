package scripts;

import core.eng.Behaviour;
import core.eng.IBehaviour;
import core.eng.ICollider;
import core.eng.IGameObject;
import core.gui.SoundClip;
import geometric.Point;

import java.util.List;

/**
 * Implements simple explosion behaviour
 * @author hdaniel@ualg.pt
 * @version 202502081132
 */
public class BehavCollXplode extends Behaviour {

    protected IGameObject explosion;
    protected String target;
    protected SoundClip expSnd;

    private void init(IGameObject explosion, String target, SoundClip expSnd) {
        this.explosion = explosion;
        this.target = target;
        this.expSnd = expSnd;
    }

    public BehavCollXplode(IGameObject explosion, String target, SoundClip clip) {
        init(explosion, target, clip);
    }

    public BehavCollXplode(BehavCollXplode b) {
        super(b);
        init(b.explosion, b.target, b.expSnd);
    }

    @Override
    public void onCollision(List<ICollider> colliders) {
        for (ICollider c : colliders) {
            if (c.gameObject().name().equals(target)) {
                onExplosion();
                gameObject.engine().destroy(gameObject);
                break;
            }
        }
    }

    private void onExplosion() {
        Point p = gameObject.centroid();
        IGameObject exp = explosion.copy();
        exp.transform().move(p.x(), p.y());
        gameObject.engine().addEnabled(exp);
        expSnd.play();
    }

    @Override
    public IBehaviour copy() {
        return new BehavCollXplode(this);
    }
}
