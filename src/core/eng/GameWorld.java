
package core.eng;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hdaniel@ualg.pt
 * @version 202506030849
 */
public abstract class GameWorld implements IGameWorld {


    /**
     * Check collisions for all the enabled objects
     * pos: calls Behaviour.onCollision(gol) for all enabled GameObjects
     *      passing in the list of all the objects that collided with each IGameObject
     */
    protected void checkCollisionsByLayer(List<IGameObject> gol) {

        for (IGameObject go : gol) {
            ICollider[] coll = go.colliders();
            if (coll != null) {
                //List<IGameObject> goColl = new ArrayList<>();
                List<ICollider> goColl = new ArrayList<>();

                //Check collisions
                for(ICollider c : coll)
                    if (c != null)
                        for (IGameObject other : gol)
                            if (go.transform().layer() == other.transform().layer())
                                if (go != other) {
                                    ICollider[] otherColl = other.colliders();
                                    if (otherColl != null)
                                        for(ICollider oc : otherColl)
                                            if (oc != null)
                                                if (c.isColliding(oc))
                                                    goColl.add(oc);
                                }

                //Send collisions list
                for (IBehaviour b : go.behaviours())
                    b.onCollision(goColl);
            }
        }
    }

}
