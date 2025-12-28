package util.observer;

/**
 * Interface for Observers in the Observer pattern
 * @author hdaniel@ualg.pt
 * @version 202502160035
 */
public interface IObserver<T> {
    void onMessage(T msg);
}
