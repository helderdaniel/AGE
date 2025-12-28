package clientDemos.age;

/**
 * @author hdaniel@ualg.pt
 * @version 202501310756
 */

import core.eng.*;
import core.gui.*;
import core.gui.awt.GUIAWT;
import geometric.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameObjectsDemo {

    public static void main(String[] args) throws IOException {

        IUserInterface gui = new GUIAWT("test03", 800, 600, false);
        IGameEngine engine = new GameEngine(new GameWorldLayered(0,2), gui);
        IPainter painter = (IPainter) gui;

        ITransform t = new Transform(100,100, 0, 45);
        //ITransform t = new Transform(100, 100, 0, 90);
        //ITransform t = new Transform(0,0, 0, 0);

        // Add GameObjects
        String resPath = "src/Alienoid/resources/";
        BufferedImage rawimg = ImageIO.read(new File(resPath + "smallAlien.png"));
        IShape image = new ShapeImage2D(painter, t, rawimg);
        IGameObject go = new GameObject(engine, "alien", t, image, null);
        image.gameObject(go);
        engine.addEnabled(go);

        IShape circle = new ShapeCircle(painter, t, 50, Color.BLUE, true);
        go = new GameObject(engine, "circle", t, circle, null);
        image.gameObject(go);
        engine.addEnabled(go);

        IShape rect = new ShapeRectangle(painter, t, 20, 50, Color.GREEN);
        go = new GameObject(engine, "rect", t, rect, null);
        image.gameObject(go);
        engine.addEnabled(go);

        Point[] triv = {
                new Point(0, 0),
                new Point(10, 50),
                new Point(20, 0) };
        IShape tri = new ShapePolygon(painter, t, new ArrayList<Point>(List.of(triv)), Color.GRAY);
        go = new GameObject(engine, "tri", t, tri, null);
        image.gameObject(go);
        engine.addEnabled(go);

        Point[] octv = {
                new Point(20, 20),
                new Point(30, 30),
                new Point(40, 30),
                new Point(50, 20),
                new Point(50, 10),
                new Point(40,  0),
                new Point(30,  0),
                new Point(20, 10) };
        IShape oct = new ShapePolygon(painter, t, new ArrayList<Point>(List.of(octv)), Color.YELLOW);
        go = new GameObject(engine, "oct", t, oct, null);
        image.gameObject(go);
        engine.addEnabled(go);

        Font font = new Font("TimesRoman", Font.PLAIN, 24);
        IShape text = new ShapeText(painter, t, "Hello World", font, Color.RED);
        go = new GameObject(engine, "text", t, text, null);
        image.gameObject(go);
        engine.addEnabled(go);

        //Run Engine
        engine.run();
    }
}