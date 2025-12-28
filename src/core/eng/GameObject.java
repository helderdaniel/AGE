package core.eng;
import core.gui.IShape;
import geometric.Point;

import java.util.StringJoiner;


/**
 * The GameObject class implements the IGameObject interface
 * and represents a game object with position, rotation and a collider
 * without any behaviour
 */
public class GameObject implements IGameObject {

    protected IGameEngine engine;
    protected ITransform transform;
    protected IShape shape;
    protected ICollider[] colliders;
    protected IBehaviour[] behaviours;
    protected String name;

    /**
     * Initializes a GameObject at position x, y, with rotation angle, a collider and a role
     * If collider is null, collisions are not detected
     */
    public GameObject(IGameEngine eng, String name, ITransform t, IShape shape,
                      ICollider[] colliders, IBehaviour[] behaviours) {
        this.engine = eng;
        this.name = name;
        this.transform = t;
        this.shape = shape;
        if (this.shape != null)
            this.shape.gameObject(this);

        this.colliders = colliders;
        if (colliders != null)
            for (ICollider c : colliders)
                c.gameObject(this);

        this.behaviours = behaviours;
        if (behaviours != null)
            for (IBehaviour b : behaviours)
                b.gameObject(this);
    }

    /**
     * Initializes a GameObject at position x, y, with rotation angle, a collider and the Default empty Behaviour
     * If collider is null, collisions are not detected
     */
    public GameObject(IGameEngine eng, String name, ITransform t, IShape shape, ICollider[] colliders) {
        this(eng, name, t, shape, colliders, new IBehaviour[0]);
    }

    /**
     * Copy constructor to return a new equal object that shares
     * the same engine, name
     * but with an independent transform and collider
     */
    public GameObject(GameObject other) {
        this.engine    = other.engine;   //refs same engine
        this.name      = other.name;     //Strings are immutable
        this.transform = new Transform(other.transform);
        if (other.shape != null) {
            this.shape = other.shape.copy(); //Needed because each implementation of IShape is different
            this.shape.transform(this.transform);
            }
        else this.shape = null;

        //Copy all IColliders
        if (other.colliders == null)  this.colliders = null;
        else {
            this.colliders = new Collider[other.colliders.length];
            for (int i = 0; i < other.colliders.length; i++) {
                this.colliders[i] = other.colliders[i].copy();
                this.colliders[i].transform(this.transform);
            }
        }

        //Copy all IBehaviours
        if (other.behaviours == null)  this.colliders = null;
        else {
            this.behaviours = new IBehaviour[other.behaviours.length];
            for (int i = 0; i < other.behaviours.length; i++) {
                this.behaviours[i] = other.behaviours[i].copy();
                this.behaviours[i].gameObject(this);
            }
        }
    }

    @Override
    public String name() { return name; }
    @Override
    public IGameEngine engine() { return engine; }
    @Override
    public ITransform transform() { return transform; }
    @Override
    public IShape shape() { return shape; }
    @Override
    public ICollider[] colliders() { return colliders; }
    @Override
    public IBehaviour[] behaviours() { return behaviours; }

    @Override
    public Point centroid() {
        Point c = shape.centroid();

        //Scale centroid from original shape
        /*return new Point(
                transform.x() + c.x() * transform.xScale(),
                transform.y() + c.y() * transform.yScale());
        */
        //todo: testing
        return new Point(transform.x(), transform.y());
    }

    @Override
    public double width() { return shape.width() * transform.xScale(); }

    @Override
    public double height() { return shape.height() * transform.yScale(); }

    @Override
    public String toString() {
        //StringBuilder out = new StringBuilder(name + "\n" + transform.toString() + "\n");
        String out = name + "\n" + transform.toString() + "\n";
        StringJoiner sj = new StringJoiner("\n");
        if (colliders == null)
            sj.add("no colliders");
        else
            for (ICollider c : colliders) {
                if (c != null) sj.add(c.toString());
                else sj.add("null");
                //out.append("\n");
            }

        //remove last '\n'
        //if (!out.isEmpty()) out.deleteCharAt(out.length() - 1);

        return out + sj.toString();
    }

    @Override
    public IGameObject copy() { return new GameObject(this); }
}

