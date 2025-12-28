package clientDemos.mooshak.finalval;

/**
 * @author hdaniel@ualg.pt
 * @version 202505011705
 */
import had.test.IRunnable;
import java.util.Scanner;

public class TestX025 implements IRunnable {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //GameEngine engine = new GameEngine(null);

        double x      = in.nextDouble();
        double y      = in.nextDouble();
        double rot    = in.nextDouble();
        double offset = in.nextDouble();

        PointX A = new PointX(x, y);
        A.move(offset, rot);
        System.out.println(A);
    }

    @Override
    public void run(String[] args) { main(args); }
}