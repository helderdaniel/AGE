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

public class Test05L implements IRunnable {

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

        in.useDelimiter(System.lineSeparator());
        String vertex = in.next();
        String v[] = vertex.split(" ");
        ICollider[] c = new ICollider[1];
        if (v.length == 3)
            c[0] = new CollCircle(null, t, Double.parseDouble(v[2]), "");
        else {
            List<Point> pl = new ArrayList<>();
            for (int i = 0; i < v.length; i+=2)
                pl.add(new Point(
                        Double.parseDouble(v[i]),
                        Double.parseDouble(v[i+1])));
            c[0] = new CollPoly(null, t, pl, "");
        }

        //Create the objects
        IGameObject go = new GameObject(engine, name, t, null, c, null);

        //Print the objects
        System.out.println(go);
    }

    @Override
    public void run(String[] args) { main(args); }

}