package util.observer;

/**
 * Interface for Observables in the Observer pattern
 * @author hdaniel@ualg.pt
 * @version 202502160035
 */
public interface IObservable<T> {
    void add(IObserver<T> observer);

    void del(IObserver<T> observer);

    void notify(T msg);
}
