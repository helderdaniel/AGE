package Alienoid;

import core.eng.Behaviour;
import core.eng.IBehaviour;
import util.observer.IObserver;
import core.gui.ShapeText;

/**
 * Implements simple score incrementer when alien is hit
 * @author hdaniel@ualg.pt
 * @version 202502081132
 */
public class BehavScore extends Behaviour implements IObserver<Integer> {
    protected int score=0;

    public BehavScore() { }
    public BehavScore(BehavScore b) {
        super(b);
        score = b.score;
    }

    @Override
    public void onMessage(Integer msg) {
        score += msg;
        ((ShapeText) gameObject.shape()).text(String.format("%04d", score));
    }

    @Override
    public IBehaviour copy() {
        return new BehavScore(this);
    }
}
