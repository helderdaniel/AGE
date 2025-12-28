package clientDemos.mooshak.finalval;

import core.eng.ICollider;

import java.util.List;

/**
 * @author hdaniel@ualg.pt
 * @version 202505242021
 */


//Moves at a constant speed to the initial target position
//If a collision is detected increment cnt
public class MoveToCstCntCol extends MoveToCst {

    protected double speed;
    protected double rot;
    protected int[] cnt;

    public MoveToCstCntCol(double speed, int[] cnt) {
        super(speed);
        this.cnt = cnt;
    }

    @Override
    public void onCollision(List<ICollider> colliders) {
        if (!colliders.isEmpty())
            cnt[0]++;
    }


}
