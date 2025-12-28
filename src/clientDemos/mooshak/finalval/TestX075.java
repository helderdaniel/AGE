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


public class TestX075 implements IRunnable {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        IUserInterface gui = new GUIAWT("", 0, 0, false);
        GameEngine engine = new GameEngine(new GameWorldFlat(), gui, 1);

        double x      = in.nextDouble();
        double y      = in.nextDouble();
        double rot    = in.nextDouble();
        double offset = in.nextDouble();
        double rA     = in.nextDouble();
        double rB     = in.nextDouble();
        double d      = in.nextDouble();

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
        MoveToCst roleA = new MoveToCst(d);
        IGameObject goA = new GameObject(engine, "A", tA, null, colA, new MoveToCst[]{roleA});
        roleA.gameObject(goA);
        roleA.rotateToTarget(B);
        engine.addEnabled(goA);

        MoveToCst roleB = new MoveToCst(d);
        IGameObject goB = new GameObject(engine, "B", tB, null, colB, new MoveToCst[]{roleB});
        roleB.gameObject(goB);
        roleB.rotateToTarget(A);
        engine.addEnabled(goB);

        //Move all in 1 frame
        //engine.update(1, null);
        //or:
        engine.run();

        System.out.printf("(%.2f,%.2f) (%.2f,%.2f)\n",
                          goA.transform().x(), goA.transform().y(),
                          goB.transform().x(), goB.transform().y());
    }

    @Override
    public void run(String[] args) { main(args); }

}