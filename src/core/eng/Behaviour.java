package core.eng;

import java.util.List;

/**
 * Implements elementary behaviour of a GameObject
 * @author hdaniel@ualg.pt
 * @version 202502151530
 */
public class Behaviour implements IBehaviour {

    protected IGameObject gameObject;

    public Behaviour() { }
    public Behaviour(Behaviour b) { this.gameObject = b.gameObject; }

    @Override
    public IGameObject gameObject() { return gameObject; }

    @Override
    public void gameObject(IGameObject go) { gameObject = go; }

    /*
        Default empty event methods implementation
        This allows the creation of GameObjects without implementing all the methods
     */
    @Override
    public void onInit() { }

    @Override
    public void onEnabled() { }

    @Override
    public void onDisabled() { }

    @Override
    public void onDestroy() { }

    @Override
    public void onUpdate(double dT, IInputEvent ie) { }

    @Override
    public void onCollision(List<ICollider> colliders) { }

    @Override
    public IBehaviour copy() {
        Behaviour b = new Behaviour();
        b.gameObject(this.gameObject);
        return b;
    }

}
