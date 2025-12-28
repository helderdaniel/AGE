package core.eng;

import core.gui.IUserInterface;
import util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hdaniel@ualg.pt
 * @version 202501310808
 *
 * Keeps a list of Enabled GameObjects
 * Updated synchronously with the GameWorld
 *
 * It is faster to send in each frame the Enabled update list
 * than extracting form the GameWorld a list.
 *
 * The penalty is that when adding a new GameObject it is needed to update also Enabled List
 * However, adding GameObjects in not as frequent as rendering frames
 */
public class GameEngine implements IGameEngine {
    protected IGameWorld enabled;
    protected List<IGameObject> disabled = new ArrayList<>();
    protected List<IGameObject> destroyed = new ArrayList<>(); //to destroy only after collision detection

    protected long lastTime;
    protected IUserInterface ui;
    protected int frames;


    /**
     * Create a GameEngine with a user interface
     *
     * @param ui      the User interface to associate to this GameEngine
     * @param frames  the number of frames to simulate. If < 0 the engine will run indefinitely.
     */
    public GameEngine(IGameWorld world, IUserInterface ui, int frames) {
        this.enabled = world;
        this.ui = ui;
        this.frames = frames;
    }
    public GameEngine(IGameWorld world, IUserInterface ui) { this(world, ui, -1); }

    @Override
    public IUserInterface ui() { return ui; }

    @Override
    public void addEnabled(IGameObject go) {
        if (go.behaviours() != null)
            for (IBehaviour b : go.behaviours())
                b.onInit();
        enabled.add(go);
    }


    @Override
    public void addDisabled(IGameObject go) {
        if (go.behaviours() != null)
            for (IBehaviour b : go.behaviours())
                b.onInit();
        disabled.add(go);
    }


    /**
     * Destroy marked GameObjects in the destroyed list
     * This function is called only after collision detection.
     * to handle object destruction only after all collisions have been detected
     * pos: calls onDestroy() for all GameObject in enabled and disabled queue
     */
    private void destroyMarked() {

        for (IGameObject go : destroyed) {
            if (isEnabled(go))
                enabled.remove(go);

            //Needed for DestroyAll
            else if (isDisabled(go)) disabled.remove(go);

            //Call onDestroy even for disabled objects
            //it may be needed, cannot make assumption on users' code
            for (IBehaviour b : go.behaviours())
                b.onDestroy();

        }
        destroyed.clear();
    }


    /**
     * Add the GameObject go to the destroy list
     * whether it is enabled or disabled
     * Objects in the destroy list will be handled after all collisions have been detected
     * @param go: a GameObject to add to the destroy list
     * pre: go != null
     */
    @Override
    public void destroy(IGameObject go) {
        destroyed.add(go);
    }


    /**
     * Add all the GameObjects to the destroy list
     * Objects in the destroy list will be handled after all collisions have been detected
     */
    @Override
    public void destroyAll() {
        //destroyed.addAll(enabled); //cannot use this because it is not a Collection
        for (IGameObject go : enabled) destroyed.add(go);
        destroyed.addAll(disabled);
    }


    @Override
    public boolean isEnabled(IGameObject go) {
        return enabled.contains(go);
    }


    @Override
    public boolean isDisabled(IGameObject go) { return disabled.contains(go);  }


    @Override
    public void enable(IGameObject go) {
        if (isDisabled(go)) {
            for (IBehaviour b : go.behaviours())
                b.onEnabled();
            disabled.remove(go);
            enabled.add(go);
        }
    }


    @Override
    public void disable(IGameObject go) {
        if (go != null && isEnabled(go)) {
            for (IBehaviour b : go.behaviours())
                b.onDisabled();
            disabled.add(go);
            enabled.remove(go);
        }
    }


    @Override
    public Iterable<IGameObject> getEnabled() { return enabled; }


    @Override
    public Iterable<IGameObject> getDisabled() { return disabled; }


    @Override
    public void checkCollisions() { enabled.checkCollisions(); }


    @Override
    public void update(double dt, IInputEvent ie) {
        for (IGameObject go : enabled) {

            //Update shape
            for (IBehaviour b : go.behaviours())
                b.onUpdate(dt, ie);

            //todo: should not be needed
            //updating ITransform should be enough:
            //Probably needed to rewrite ICollider based on the transform and not on the IGeometricFigures

            //update collider only after all Behaviours have run
            ICollider[] coll = go.colliders();
            if (coll != null)
                for (ICollider c : coll)
                    if (c != null)
                        c.onUpdate();
        }
    }


    @Override
    public void run() {
        //init time for first frame
        lastTime = System.nanoTime();

        for (;;) {

            //IF it is expected to simulate only n frames
            //exit game loop when the threshold is reached
            if (frames >= 0 && frames-- == 0) {
                //Log.printf("GameEngine: exiting after %d frames%n", frames);
                break;
            }

            //compute dt, time elapsed between frames
            long curTime = System.nanoTime();
            double dt = (curTime - lastTime)/1000_000_000.0f;

            // slow down loop to max frames per second
            int maxFPS = 60;
            int fps = (int) (1 / dt);

            try {
                double sleepTime = 1.0f/maxFPS-dt;
                long sleepTimeMillies = (long)(sleepTime*1000);
                if (sleepTime > 0) {
                    fps = (int) (1 / (dt + sleepTime));
                    Thread.sleep(sleepTimeMillies);
                }
                Log.printf("fps=%d dt=%.5f, st=%.5f%n", fps, dt, sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //update system time
            lastTime = curTime;

            //get input
            IInputEvent ie = ui.input();

            //GameObjects call Behaviours onUpdate
            update(dt, ie);

            //handle collisions
            checkCollisions();
            destroyMarked();

            //UI update screen
            ui.update(enabled);
        }
    }


    private void delay(long millies, int nanos ) {
        try {
            Thread.sleep(millies, nanos);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
