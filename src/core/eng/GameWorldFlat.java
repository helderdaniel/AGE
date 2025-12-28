package core.eng;

import java.util.Iterator;
import java.util.List;

// Cannot be an ArrayList because GameObject lists
// are accessed concurrently by Engine Thread and AWT Thread
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author hdaniel@ualg.pt
 * @version 202506030849
 */
public class GameWorldFlat extends GameWorld {

    protected List<IGameObject> gol = new CopyOnWriteArrayList<>();

    /**
     * @return an iterator for the GameObjects in this world
     */
    public Iterator<IGameObject> iterator() {
        return gol.iterator();
    }

    /**
     * add IGameObject go
     * @param go IGameObject to add
     * pre: go != null
     */
    public void add(IGameObject go) { gol.add(go); };

    /**
     * remove IGameObject
     * @param go IGameObject to remove
     * pre: go != null
     */
    public void remove(IGameObject go) { gol.remove(go); }

    /**
     * @return true if the world contains go
     * @param go IGameObject to check
     */
    @Override
    public boolean contains(IGameObject go) { return gol.contains(go);  }

    /**
     * Check collisions for all the enabled objects
     * pos: calls Behaviour.onCollision(gol) for all enabled GameObjects
     *      passing in the list of all the objects that collided with each IGameObject
     */
    public void checkCollisions() {

        checkCollisionsByLayer(gol);

        /*
        for (IGameObject go : gol) {
            List<IGameObject> goColl = new ArrayList<>();

            //Check collisions
            ICollider coll = go.collider();
            if (coll != null) {
                for (IGameObject other : gol)
                    if (go.transform().layer() == other.transform().layer())
                        if (go != other) {
                            ICollider otherColl = other.collider();
                            if (otherColl != null)
                                if (coll.isColliding(otherColl)) {
                                    goColl.add(other);
                                }
                        }
            }

            //Send collisions list
            if (!goColl.isEmpty())
                for (IBehaviour b : go.role())
                    b.onCollision(goColl);
        }*/
    }

}
