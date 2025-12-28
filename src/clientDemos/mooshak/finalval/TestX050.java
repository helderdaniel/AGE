package clientDemos.mooshak.finalval;

/**
 * @author hdaniel@ualg.pt
 * @version 202505011705
 */

import had.test.IRunnable;

import java.util.Scanner;

public class TestX050 implements IRunnable {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //GameEngine engine = new GameEngine(null);

        double x      = in.nextDouble();
        double y      = in.nextDouble();
        double rot    = in.nextDouble();
        double offset = in.nextDouble();
        double rA     = in.nextDouble();
        double rB     = in.nextDouble();

        PointX A = new PointX(x, y);
        PointX B = new PointX(A); // copy A to B
        B.move(offset, rot);

        CircleX cA = new CircleX(A, rA);
        CircleX cB = new CircleX(B, rB);

        System.out.println(cA.collides(cB) ? 1 : 0);
    }

    @Override
    public void run(String[] args) { main(args); }
}