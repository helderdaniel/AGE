package core.eng;

import java.util.List;

/**
 * Defines the behaviour of a GameObject
 * @author hdaniel@ualg.pt
 * @version 202502151530
 */
public interface IBehaviour {

    /**
     * @return GameObject assigned to this Behaviour
     */
    public IGameObject gameObject();

    /**
     * set GameObject assigned to this Behaviour
     */
    public void gameObject(IGameObject go);

    /**
     * Called by Engine.addEnabled() and Engine.addDisabled()
     */
    void onInit();

    /**
     * Called by Engine.enable()
     */
    void onEnabled();

    /**
     * Called by Engine.disable()
     */
    void onDisabled();

    /**
     * Called by Engine.destroy()
     */
    void onDestroy();

    /**
     * Called by Engine.update()
     * @param dT time since onUpdate() was called last time,
     *           which is the time elapsed between this frame and the last
     * @param ie first InputEvent in queue or null if none
     */
    void onUpdate(double dT, IInputEvent ie);

    /**
     * Called by Engine when this object collides with
     */
    void onCollision(List<ICollider> colliders);


    /**
     * @return a copy of this Behaviour
     */
    public IBehaviour copy();
}
