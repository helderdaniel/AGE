package Alienoid;

import core.gui.SoundClip;
import geometric.Point;
import core.eng.*;
import util.Invoker;
import util.observer.IObservable;
import util.observer.Observable;


/**
 * Implements simple alien behaviour: swing and dive
 * @author hdaniel@ualg.pt
 * @version 202502081132
 */
public class BehavAlien extends Behaviour {

    protected final static int hitPoints = 10;
    protected double movSpeed;
    protected double rotSpeed;
    protected double swingX = 0;
    protected int dir = 1;

    protected boolean dive = false;
    protected Invoker switchDive, fire;
    protected IGameObject missile;
    protected int minFireRate, maxFireRate;
    protected SoundClip fireSnd;

    protected IObservable<Integer> observableScore = new Observable<>();

    private void init(double movSpeed, double rotSpeed, IGameObject missile,
                      int minFireRate, int maxFireRate, SoundClip fireSnd) {
        this.movSpeed = movSpeed;
        this.rotSpeed = rotSpeed;
        this.missile  = missile;
        this.minFireRate = minFireRate;
        this.maxFireRate = maxFireRate;
        this.fireSnd = fireSnd;
        fireSnd.play();
    }

    public BehavAlien(double movSpeed, double rotSpeed, IGameObject missile,
                      int minFireRate, int maxFireRate, SoundClip fireSnd) {
        init(movSpeed, rotSpeed, missile, minFireRate, maxFireRate, fireSnd);
    }

    public BehavAlien(BehavAlien b) {
        super(b);
        init(b.movSpeed, b.rotSpeed, b.missile, b.minFireRate, b.maxFireRate, b.fireSnd);
    }

    public IObservable<Integer> observableScore() { return observableScore; }

    private void onFire() {
        IGameObject newMissile = missile.copy();
        Point p = gameObject.centroid();
        newMissile.transform().move(p.x(), p.y()+gameObject().width()/2);
        gameObject.engine().addEnabled(newMissile);
    }

    @Override
    public void onInit() {
        switchDive = new Invoker(() -> { dive = true; }, 0, 10000, 60000);
        fire = new Invoker(this::onFire, 0, minFireRate, maxFireRate);
    }

    @Override
    public void onDestroy() {
        switchDive.cancel();
        fire.cancel();
        observableScore.notify(hitPoints);
    }

    @Override
    public void onUpdate(double dt, IInputEvent ie) {
        //Log.println(String.format("fps=%d dt=%.5f", (int) (1 / dt), dt));
        run(dt, gameObject.transform());
    }


    public void run(double dt, ITransform transform) {

        //swing left to right
        swingX += dt * movSpeed * dir;
        if (swingX >= 50) dir = -1;
        else if (swingX <= -50) dir = 1;
        double dx = movSpeed * dt;
        double dy = 0;
        double dTheta = rotSpeed * dt * dir;

        //dive
        if (dive) {
            switchDive.cancel(); //do not call it again
            dy = movSpeed * dt / 2;
            dTheta = (180-transform.angle()) * dt;
        }

        //update transform
        transform.move(dx * dir, dy);
        transform.rotate(dTheta);
    }

    @Override
    public IBehaviour copy() {
        return new BehavAlien(this);
    }

}
