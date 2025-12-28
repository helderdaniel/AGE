package util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author hdaniel@ualg.pt
 * @version 202502131206
 */
public class Invoker {

    private final Timer timer = new Timer();
    private boolean cancelled = false;

    /**
     * Create a new Invoker that executes a TimerTask after a delay
     * @param task TimerTask to be executed
     * @param delay delay in milliseconds
     */
    public Invoker(Runnable task, long delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }

    /*
    This alternate implementation:

        public Invoker(TimerTask task, long delay, long period) { timer.schedule(task, delay, period); }

    needs to create a new TimerTask when calling:

        new Invoker(new TimerTask() {
            @Override
            public void run() { // ... }
         }, 1000);
     */

    /**
     * Create a new Invoker that executes a TimerTask with a period
     * with an initial delay
     * @param task TimerTask to be executed
     * @param delay initial delay in milliseconds
     * @param period period in milliseconds
     */
    public Invoker(Runnable task, long delay, long period) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay, period);
    }

    /**
     * Create a new Invoker that executes a TimerTask at random intervals between: [minPeriod, maxPeriod]
     * after an initial delay
     * @param task TimerTask to be executed
     * @param delay initial delay in milliseconds
     * @param minPeriod minimum period in milliseconds
     * @param maxPeriod maximum period in milliseconds
     */
    public Invoker(Runnable task, long delay, long minPeriod, long maxPeriod) {
        delay += random(minPeriod, maxPeriod);
        timer.schedule(new RandomRunner(task, minPeriod, maxPeriod), delay);
    }

    public void cancel() {
        cancelled = true;
        timer.cancel();
    }

    private long random(long min, long max) {
        return min + (long)(Math.random() * (max - min));
    }

    private class RandomRunner extends TimerTask
    {
        private final Runnable task;
        private final long minPeriod;
        private final long maxPeriod;

        RandomRunner(Runnable task, long minPeriod, long maxPeriod) {
            this.task = task;
            this.minPeriod = minPeriod;
            this.maxPeriod = maxPeriod;
        }

        public void run() {
            task.run();
            long delay = random(minPeriod, maxPeriod);
            if (!cancelled) //avoid scheduling new task if timer already cancelled
                timer.schedule(new RandomRunner(task, minPeriod, maxPeriod), delay);
        }

    }

}
