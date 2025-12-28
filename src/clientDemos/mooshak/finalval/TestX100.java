package clientDemos.mooshak.finalval;

/**
 * @author hdaniel@ualg.pt
 * @version 202503171105
 */

import core.eng.*;
import core.gui.IUserInterface;
import core.gui.awt.GUIAWT;
import geometric.Point;
import had.test.IRunnable;

import java.util.Scanner;


public class TestX100 implements IRunnable {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        double x      = in.nextDouble();
        double y      = in.nextDouble();
        double rot    = in.nextDouble();
        double offset = in.nextDouble();
        double rA     = in.nextDouble();
        double rB     = in.nextDouble();
        double d      = in.nextDouble();
        double frames = in.nextDouble();

        IUserInterface gui = new GUIAWT("", 0, 0, false);
        GameEngine engine = new GameEngine(new GameWorldFlat(), gui, (int) frames);

        //GameObject A position
        Point A = new Point(x, y);
        ITransform tA = new Transform(A, 0, 0, 1, 1);
        ICollider[] colA = { new CollCircle(null, tA, rA, "") };

        //GameObject B position
        //Move in the direction rot with offset
        PointX Bt = new PointX(x, y);
        Bt.move(offset, rot);
        Point B = new Point(Bt.x(), Bt.y());
        ITransform tB = new Transform(B, 0, 0, 1, 1);
        ICollider[] colB = { new CollCircle(null, tB, rB, "") };

        //add Behaviours and create GameObjects
        //One is enough to count collisions since there is only 2 objects
        int[] cnt = {0};
        MoveToCstCntCol roleA = new MoveToCstCntCol(d, cnt);
        IGameObject goA = new GameObject(engine, "A", tA, null, colA, new MoveToCst[]{roleA});
        roleA.gameObject(goA);
        roleA.rotateToTarget(B);
        engine.addEnabled(goA);

        MoveToCst roleB = new MoveToCst(d);
        IGameObject goB = new GameObject(engine, "B", tB, null, colB, new MoveToCst[]{roleB});
        roleB.gameObject(goB);
        roleB.rotateToTarget(A);
        engine.addEnabled(goB);

        //Simulate frames
        engine.run();

        System.out.println(cnt[0]);

    }

    @Override
    public void run(String[] args) { main(args); }

}