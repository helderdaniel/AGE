package core.eng;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import util.Log;

/**
 * @author hdaniel@ualg.pt
 * @version 202506030849
 */
public class GameWorldLayered extends GameWorld {

    protected List<CopyOnWriteArrayList<IGameObject>> layers = new CopyOnWriteArrayList<>();
    protected int minLayer;
    protected int maxLayer;

    /**
     * Create a layered GameWorld
     * @param minLayer minimum layer index
     * @param maxLayer maximum layer index (inclusive)
     */
    public GameWorldLayered(int minLayer, int maxLayer) {
        this.minLayer = minLayer;
        this.maxLayer = maxLayer;

        //Create layers
        for (int i = minLayer; i <= maxLayer; i++)
            layers.add(new CopyOnWriteArrayList<>());
    }

    /**
     * @return an iterator for the GameObjects in this world
     */
    public Iterator<IGameObject> iterator() {
        return new Iterator<IGameObject>() {
            private int layerIndex = 0;
            private Iterator<IGameObject> currentLayerIterator = layers.get(layerIndex).iterator();

            @Override
            public boolean hasNext() {
                while (layerIndex < layers.size()) {
                    if (currentLayerIterator.hasNext()) return true;
                    layerIndex++;
                    if (layerIndex < layers.size())
                        currentLayerIterator = layers.get(layerIndex).iterator();
                }
                return false;
            }

            @Override
            public IGameObject next() {
                return currentLayerIterator.next();
            }
        };

    }

    /**
     * add IGameObject go
     * @param go IGameObject to add
     * pre: go != null
     */
    public void add(IGameObject go) {
        int goLayer = layerListIndex(go.transform().layer());
        layers.get(goLayer).add(go);
    };

    /**
     * remove IGameObject
     * @param go IGameObject to remove
     * pre: go != null
     */
    public void remove(IGameObject go) {
        int goLayer = layerListIndex(go.transform().layer());
        layers.get(goLayer).remove(go);
    }

    /**
     * @return true if the world contains go
     * @param go IGameObject to check
     */
    @Override
    public boolean contains(IGameObject go) {
        for (CopyOnWriteArrayList<IGameObject> gol : layers)
            if (gol.contains(go))
                return true;
        return false;
    }

    /**
     * Check collisions for all the enabled objects
     * pos: calls Behaviour.onCollision(gol) for all enabled GameObjects
     *      passing in the list of all the objects that collided with each IGameObject
     */
    public void checkCollisions() {
        for (CopyOnWriteArrayList<IGameObject> gol : layers) {
            checkCollisionsByLayer(gol);
        }
    }


    /**
     * @param  goLayer Game Object world layer
     * @return the index of the layer in the layers list
     */
    private int layerListIndex(int goLayer) {
        if (goLayer < minLayer || goLayer > maxLayer) {
            Log.printf("GameObject layer = %d out of bounds: [%d, %d)\n", goLayer, minLayer, maxLayer);
            return -1; //Invalid layer
        }
        return goLayer - minLayer;
    }
}
