package core.eng;

import core.gui.IShape;

/**
 * @author hdaniel@ualg.pt
 * @version 202506181046
 */
public abstract class GOFactory implements IGOFactory {

    protected IGameEngine engine;
    protected IShape shape;
    protected ICollider[] colliders = null;
    protected IBehaviour[] behaviours = null;

    protected GOFactory(IGameEngine engine) {
        this.engine = engine;
    }

    @Override
    public IGameObject create(String name, ITransform t) {
        return new GameObject(engine, name, t, shape, null, behaviours);
    }

    public IShape       shape()      { return shape; }
    public ICollider[]  colliders()  { return colliders; }
    public IBehaviour[] behaviours() { return behaviours; }
}
