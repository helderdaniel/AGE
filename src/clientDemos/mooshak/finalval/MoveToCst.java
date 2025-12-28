package clientDemos.mooshak.finalval;

import core.eng.Behaviour;
import core.eng.IInputEvent;
import geometric.Point;

/**
 * @author hdaniel@ualg.pt
 * @version 202505242021
 */

//Moves at a constant speed to the initial target position
public class MoveToCst extends Behaviour {
    static private double toNorth = 90;
    protected double speed;
    protected double rot;

    public MoveToCst(double speed) {
        this.speed = speed;
        this.rot = 0;
    }

    public void rotateToTarget(Point target) {
        double dx = target.x() - gameObject.transform().x();
        double dy = target.y() - gameObject.transform().y();
        rot = Math.toDegrees(Math.atan2(dy, dx));
    }

    protected Point moveTo() {
        double x = Math.cos(Math.toRadians(rot)) * speed;
        double y = Math.sin(Math.toRadians(rot)) * speed;
        return new Point(x, y);
    }

    @Override
    public void onUpdate(double dT, IInputEvent ie) {
        Point off = moveTo();
        gameObject.transform().move(off.x(), off.y());
    }

}
