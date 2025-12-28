package clientDemos.mooshak;

/**
 * @author hdaniel@ualg.pt
 * @version 202503171105
 */
import core.eng.*;
import geometric.Point;
import had.test.IRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class Test06N implements IRunnable {

    static class Attitude {
        double x, y, r, s;
        int l;

        public Attitude(double x, double y, int l, double r, double s) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.r = r;
            this.s = s;
        }
    }

    static class MoveCst extends Behaviour {

        private Attitude at;

        public void init(Attitude at) { this.at = at; }

        @Override
        public void onUpdate(double dT, IInputEvent ie) {
            gameObject.transform().move(at.x, at.y, at.l);
            gameObject.transform().scale(at.s, at.s);
            gameObject.transform().rotate(at.r);
        }

        @Override
        public void onCollision(List<ICollider> colliders) {
            if (!colliders.isEmpty()) {
                System.out.print(gameObject.name() + " ");
                StringJoiner sj = new StringJoiner(" ");
                for (ICollider c : colliders)
                    sj.add(c.gameObject().name());
                System.out.println(sj);
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        GameEngine engine = new GameEngine(new GameWorldFlat(), null);

        int f = in.nextInt();
        int n = in.nextInt();

        for (int n0 = 0; n0 < n; n0++) {
            String name = in.next();
            double scale = 1;
            ITransform t = new Transform(
                    new Point(in.nextDouble(), in.nextDouble()),
                    in.nextInt(),
                    in.nextDouble(),
                    scale = in.nextDouble(), scale); //do not use yScale

            in.useDelimiter(System.lineSeparator());
            String vertex = in.next();
            String v[] = vertex.split(" ");
            ICollider[] c = new ICollider[1];
            if (v.length == 3)
                c[0] = new CollCircle(null, t, Double.parseDouble(v[2]), "") ;
            else {
                List<Point> pl = new ArrayList<>();
                for (int i = 0; i < v.length; i += 2)
                    pl.add(new Point(
                            Double.parseDouble(v[i]),
                            Double.parseDouble(v[i + 1])));
                c[0] = new CollPoly(null, t, pl, "");
            }

            //Create the objects
            MoveCst role = new MoveCst();
            IGameObject go = new GameObject(engine, name, t, null, c, new MoveCst[]{role});
            engine.addEnabled(go);

            //Attitude
            in.useDelimiter(" |\\n");
            Attitude at = new Attitude(in.nextDouble(), in.nextDouble(), in.nextInt(), in.nextDouble(), in.nextDouble());
            role.init(at);
        }

        //Simulate frames
        for (int f0 = 0; f0 < f; f0++) {
            //update GameObjects
            //snippet from game engine
            engine.update(1, null);
        }

        engine.checkCollisions();
    }

    @Override
    public void run(String[] args) { main(args); }

}