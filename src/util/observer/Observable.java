package util.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for Observables in the Observer pattern
 * @author hdaniel@ualg.pt
 * @version 202502160035
 */
public class Observable<T> implements IObservable<T> {
    private List<IObserver<T>> observers = new ArrayList<>();

    public void add(IObserver<T> observer) {
        observers.add(observer);
    }
    public void del(IObserver<T> observer) {
        observers.remove(observer);
    }

    public void notify(T msg) {
        for (IObserver<T> observer : observers) {
            observer.onMessage(msg);
        }
    }
}
