package Alienoid;

import core.eng.*;
import core.gui.SoundClip;
import geometric.Point;
import util.Log;


/**
 * @author hdaniel@ualg.pt
 * @version 202502160404
 */
public class BehavPlayer extends Behaviour {

    protected double movSpeed;
    protected double rotSpeed;
    protected int reloadDelay;

    protected boolean dive = false;
    protected IGameObject missile;
    protected SoundClip fireSnd;

    protected int[] keyState = {0,0,0,0}; // < > ^ v keys

    /**
     * Initialize the player behaviour
     * @param movSpeed movement speed
     * @param rotSpeed rotation speed NOT used in this versiond
     * @param missile GameObject missile to be fired
     * @param reloadDelay delay between shots
     * @param fireSnd sound to play when firing
     */
    private void init(double movSpeed, double rotSpeed, IGameObject missile,
                      int reloadDelay, SoundClip fireSnd) {
        this.movSpeed = movSpeed;
        this.rotSpeed = rotSpeed;
        this.missile  = missile;
        this.reloadDelay = reloadDelay;
        this.fireSnd = fireSnd;
    }

    public BehavPlayer(double movSpeed, double rotSpeed, IGameObject missile,
                       int reloadDelay, SoundClip fireSnd) {
        init(movSpeed, rotSpeed, missile, reloadDelay, fireSnd);
    }

    public BehavPlayer(BehavPlayer b) {
        super(b);
        init(b.movSpeed, b.rotSpeed, b.missile, b.reloadDelay, b.fireSnd);
    }

    @Override
    public void onInit() {    }

    @Override
    public void onEnabled() {
        System.out.println("MyGameObject.onEnabled()");
    }

    @Override
    public void onDisabled() {
        System.out.println("MyGameObject.onDisabled()");
    }

    @Override
    public void onDestroy() { }

    private void onFire() {
        Point p = gameObject.centroid();
        IGameObject newMissileL = missile.copy();
        IGameObject newMissileR = missile.copy();
        newMissileL.transform().move(p.x()-20, p.y()+gameObject().width()/2-60);
        newMissileR.transform().move(p.x()+20, p.y()+gameObject().width()/2-60);
        gameObject.engine().addEnabled(newMissileL);
        gameObject.engine().addEnabled(newMissileR);
        fireSnd.play();
    }

    @Override
    public void onUpdate(double dt, IInputEvent ie) {

        if (ie != null) {
            if (ie.key() ==  37) keyState[0] = 1;
            if (ie.key() ==  39) keyState[1] = 1;
            if (ie.key() ==  38) keyState[2] = 1;
            if (ie.key() ==  40) keyState[3] = 1;
            if (ie.key() == -37) keyState[0] = 0;
            if (ie.key() == -39) keyState[1] = 0;
            if (ie.key() == -38) keyState[2] = 0;
            if (ie.key() == -40) keyState[3] = 0;

            if (ie.key() == 17) onFire();
            //Log.println(""+ie.key());
        }

        if (keyState[0] == 1) gameObject.transform().move(-movSpeed * dt, 0);
        if (keyState[1] == 1) gameObject.transform().move(movSpeed * dt, 0);
        if (keyState[2] == 1) gameObject.transform().move(0, -movSpeed * dt);
        if (keyState[3] == 1) gameObject.transform().move(0, movSpeed * dt);

        //Log.println(Arrays.toString(keyState));
    }


    @Override
    public IBehaviour copy() {
        return new BehavPlayer(this);
    }
}
