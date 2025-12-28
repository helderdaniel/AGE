package clientDemos;

import util.observer.IObserver;
import util.observer.Observable;



class Score implements IObserver<Integer> {
    int score = 0;
    public void onMessage(Integer msg) {
        score += msg;
        System.out.println("score = " + score);
    }
}


class GO extends Observable<Integer> {
    public void destroy() { notify(10);  }
}


public class ObserverDemo {
    public static void main(String[] args) {

        Score score = new Score();

        GO g0 = new GO();
        g0.add(score);
        GO g1 = new GO();
        g1.add(score);
        GO g2 = new GO();
        g2.add(score);

        g0.destroy();
        g1.destroy();
        g2.destroy();
    }
}
