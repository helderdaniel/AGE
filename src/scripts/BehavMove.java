package scripts;

import core.eng.*;

import java.util.List;

/**
 * Implements simple move behaviour
 * @author hdaniel@ualg.pt
 * @version 202502081132
 */
public class BehavMove extends Behaviour {

    protected double speedX, speedY;

    private void init(double speedX, double speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public BehavMove(double speedX, double speedY) {
        init(speedX, speedY);
    }

    public BehavMove(BehavMove b) {
        super(b);
        init(b.speedX, b.speedY);
    }

    @Override
    public void onInit() {  }

    @Override
    public void onDestroy() {  }

    @Override
    public void onUpdate(double dt, IInputEvent ie) {
        gameObject.transform().move(speedX * dt, speedY * dt);
    }

    @Override
    public IBehaviour copy() {
        return new BehavMove(this);
    }
}
