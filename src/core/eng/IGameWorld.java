package core.eng;


/**
 * @author hdaniel@ualg.pt
 * @version 202506030849
 */
public interface IGameWorld extends Iterable<IGameObject>{

    /**
     * add IGameObject go
     * @param go IGameObject to add
     * pre: go != null
     */
    public void add(IGameObject go);

    /**
     * remove IGameObject
     * @param go IGameObject to remove
     * pre: go != null
     */
    public void remove(IGameObject go);

    /**
     * @return true if the world contains go
     * @param go IGameObject to check
     */
    public boolean contains(IGameObject go);

    /**
     * Check collisions for all the enabled objects
     * pos: calls Behaviour.onCollision(gol) for all enabled GameObjects
     *      passing in the list of all the objects that collided with each IGameObject
     */
    public void checkCollisions();

}
