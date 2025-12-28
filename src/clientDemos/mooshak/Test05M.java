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

public class Test05M implements IRunnable {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        GameEngine engine = new GameEngine(new GameWorldFlat(), null);

        String name = in.next();
        double scale=1;
        ITransform t = new Transform(
                new Point(in.nextDouble(), in.nextDouble()),
                in.nextInt(),
                in.nextDouble(),
                scale=in.nextDouble(), scale); //do not use yScale

        String dummy = in.nextLine();  //Go to end of the line
        String vertex = in.nextLine();
        String v[] = vertex.split(" ");
        ICollider c = null;
        if (v.length == 3)
            c = new CollCircle(null, t, Double.parseDouble(v[2]), "");
        else {
            List<Point> pl = new ArrayList<>();
            for (int i = 0; i < v.length; i+=2)
                pl.add(new Point(
                        Double.parseDouble(v[i]),
                        Double.parseDouble(v[i+1])));
            c = new CollPoly(null, t, pl, "");
        }

        //Create the objects
        IGameObject go = new GameObject(engine, name, t, null, new ICollider[]{c}, null);

        //Transform the GameObjects
        while (in.hasNext()) {
            String cmd = in.next();
            switch (cmd) {
                case "move":
                    double dx = in.nextDouble();
                    double dy = in.nextDouble();
                    int dl = in.nextInt();
                    go.transform().move(dx, dy, dl);
                    go.colliders()[0].figure().move(dx, dy);
                    break;
                case "rotate":
                    double dTheta = in.nextDouble();
                    go.transform().rotate(dTheta);
                    go.colliders()[0].figure().rotate(dTheta);
                    break;
                case "scale":
                    double dScale = in.nextDouble();
                    go.transform().scale(dScale, dScale);
                    dScale = go.transform().xScale();
                    go.colliders()[0].figure().scale(dScale, dScale);
                    break;
                default:
                    System.out.println("Unknown command: " + cmd);
                    break;
            }
        }

        //Print the objects
        System.out.println(go);
    }

    @Override
    public void run(String[] args) { main(args); }
}