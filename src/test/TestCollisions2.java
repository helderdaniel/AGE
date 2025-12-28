package test;

/**
 * @author hdaniel@ualg.pt
 * @version 202503171105
 */
import core.eng.*;
import geometric.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;



public class TestCollisions2 {

    private static final boolean debug = true;

    protected String[] input = {
            "1\n" +
            "3\n" +
            "Square\n" +
            "2 2 0 0 1\n" +
            "1 1 1 3 3 3 3 1\n" +
            "2 1 0 0 0\n" +
            "Rect\n" +
            "5 5 0 0 1\n" +
            "4 3 4 7 7 7 7 3\n" +
            "0 0 0 0 0\n" +
            "Circle\n" +
            "10 4 0 0 1\n" +
            "10 4 1\n" +
            "-3 0 0 0 0\n",

            "3\n" +
            "3\n" +
            "SmallC\n" +
            "0 4 0 0 1\n" +
            "0 4 1\n" +
            "0 -1 1 0 0\n" +
            "BigC\n" +
            "0 0 0 0 1\n" +
            "0 0 2\n" +
            "0 1 0 0 0\n" +
            "Tri\n" +
            "5.5 2 0 0 1\n" +
            "6 1 5 2 5 3\n" +
            "-2 0 0 0 0\n",

            "10\n" +
            "2\n" +
            "bullet\n" +
            "1.5 2.5 4 0 1\n" +
            "1.5 2.5 1\n" +
            "2 0 0 0 0\n" +
            "target\n" +
            "8.5 2.5 4 0 1\n" +
            "8 1 8 4 9 4 9 1\n" +
            "1 0 0 0 0\n",

            "3\n" +
            "2\n" +
            "Grower\n" +
            "2.5 7.5 2 0 1\n" +
            "2 7 2 8 3 8 3 7\n" +
            "2 0 0 0 1\n" +
            "Rotor\n" +
            "4.5 10.5 2 0 1\n" +
            "4 8 4 13 5 13 5 8\n" +
            "0 0 0 -45 0\n",

            "3\n" +
            "3\n" +
            "fastBall\n" +
            "1 4 0 0 1\n" +
            "1 1 1\n" +
            "3 0 0 0 0\n" +
            "lowBall\n" +
            "1 1 0 0 1\n" +
            "0 0 1\n" +
            "1 0 0 0 0\n" +
            "rotorBat\n" +
            "8.5 2.5 0 0 1\n" +
            "8 1 8 4 9 4 9 1\n" +
            "0 0 0 45 0\n",

            "4\n" +
            "4\n" +
            "lMissile\n" +
            "2 6 0 0 1\n" +
            "0 0 0.5\n" +
            "1 0 0 0 0\n" +
            "rMissile\n" +
            "11 6 0 0 1\n" +
            "0 0 1\n" +
            "-1 0 0 0 0\n" +
            "upMissile\n" +
            "1 1 0 0 1\n" +
            "0 0 0.5\n" +
            "0 0.5 0 0 0\n" +
            "target\n" +
            "6 6 0 0 1\n" +
            "5 5 5 7 7 7 7 5\n" +
            "0 0 0 90 0\n",

            "3\n" +
            "4\n" +
            "ball\n" +
            "2 6 -5 0 1\n" +
            "0 0 1\n" +
            "1 -1 0 -30 0\n" +
            "upSquare\n" +
            "5 6 -5 0 1\n" +
            "4 5 4 7 6 7 6 5\n" +
            "0 -1 0 0 0\n" +
            "dnSquare\n" +
            "2 2 3 0 1\n" +
            "1 1 1 3 3 3 3 1\n" +
            "1 0 0 0 0\n" +
            "rect\n" +
            "8.5 2.5 3 0 1\n" +
            "7 2 7 3 10 3 10 2\n" +
            "-1 0 0 0 0\n"
    };

    protected String[] output = {
            "Square Rect\n" +
            "Rect Square Circle\n" +
            "Circle Rect\n",

            "BigC Tri\n" +
            "Tri BigC\n",

            "",

            "Grower Rotor\n" +
            "Rotor Grower\n",

            "fastBall rotorBat\n" +
            "rotorBat fastBall\n",

            "lMissile rMissile target\n" +
            "rMissile lMissile target\n" +
            "target lMissile rMissile\n",

            "ball upSquare\n" +
            "upSquare ball\n" +
            "dnSquare rect\n" +
            "rect dnSquare\n"
    };

    protected List<String> resultList = new ArrayList<>();

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
        protected List<String> resultList;

        public void init(Attitude at, List<String> resultList) {
            this.at = at;
            this.resultList = resultList;
        }

        @Override
        public void onUpdate(double dT, IInputEvent ie) {
            gameObject.transform().move(at.x, at.y, at.l);
            gameObject.transform().scale(at.s, at.s);
            gameObject.transform().rotate(at.r);
        }

        @Override
        public void onCollision(List<ICollider> colliders) {
            String result = "";
            if (!colliders.isEmpty()) {
                result += gameObject.name() + " ";
                StringJoiner sj = new StringJoiner(" ");
                for (ICollider c : colliders)
                    sj.add(c.gameObject().name());
                result += sj.toString();
                resultList.add(result);
            }
        }

    }

    public void testCollisions(String input, String expected) {
        Scanner in = new Scanner(input);
        GameEngine engine = new GameEngine(new GameWorldFlat(), null);

        MoveCst role = null;

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
            role = new MoveCst();
            IGameObject go = new GameObject(engine, name, t, null, c, new MoveCst[]{role});
            engine.addEnabled(go);

            //Attitude
            in.useDelimiter(" |\\n");
            Attitude at = new Attitude(in.nextDouble(), in.nextDouble(), in.nextInt(), in.nextDouble(), in.nextDouble());
            role.init(at,resultList);
        }

        //Simulate frames
        for (int f0 = 0; f0 < f; f0++) {
            //update GameObjects
            //snippet from game engine
            engine.update(1, null);
        }

        engine.checkCollisions();
        String result = "";
        for (String s : resultList)
            result += s + "\n";

        if (debug)
            System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    void tester() {
        for (int i = 0; i < input.length; i++) {
            resultList = new ArrayList<>();
            testCollisions(input[i], output[i]);
        }
    }
}