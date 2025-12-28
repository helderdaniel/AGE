package Alienoid;

import core.eng.*;
import core.gui.*;
import core.gui.ShapeImage2D;
import geometric.Point;
import scripts.BehavCollXplode;
import scripts.BehavMove;
import scripts.BehavTTL;
import util.observer.IObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


/**
 * @author hdaniel@ualg.pt
 * @version 202502081132
 */
public class Alienoid implements IGame {

    IGameEngine engine;
    String resPath;
    ClassLoader classloader = ClassLoader.getSystemClassLoader();

    public Alienoid(IGameEngine engine, String resPath) {
        this.engine = engine;
        this.resPath = resPath;
        }

    @Override
    public void init() throws IOException {

        IPainter painter = (IPainter) engine.ui();

        //load sounds
        SoundClip expSnd00 = new SoundClip(resPath + "sounds/explosion00.wav");
        SoundClip gunSnd00 = new SoundClip(resPath + "sounds/gun00.wav");
        SoundClip gunSnd01 = new SoundClip(resPath + "sounds/gun01.wav");

        //Explosions
        Image bigExpImg    = new ImageIcon(resPath + "images/explosion01-anim.gif").getImage();
        ITransform bigExpT = new Transform(0, 0, 1, 0, 1, 1);
        IBehaviour[] roleExp = { new BehavTTL(250) };
        IGameObject bigExp = new GameObject(engine, "bigExp", bigExpT, new ShapeImage2D(painter, bigExpT, bigExpImg), null, roleExp);

        //Image smlExpImg = new ImageIcon(resPath + "images/explosion02-anim.gif").getImage();
        ITransform smlExpT = new Transform(0, 0, 1, 0, 0.3, 0.3);
        //todo: Using smlExpImg only show the image on the first collisions. why(?)
        //IGameObject smlExp = new GameObject(engine, "smlExp", smlExpT, new ShapeImage2D(smlExpImg), null, roleExp);
        IGameObject smlExp = new GameObject(engine, "smlExp", smlExpT, new ShapeImage2D(painter, smlExpT, bigExpImg), null, roleExp);

        // Add background
        //Image bkgd = new ImageIcon(resPath + "images/starfield1.png").getImage();
        Image bkgd = new ImageIcon(resPath + "images/planet.png").getImage();
        ITransform tb = new Transform(0, 0, 0, 45, 1, 1);
        IGameObject bg = new GameObject(engine, "bkgd", tb, new ShapeImage2D(painter, tb, bkgd), null);
        this.engine.addEnabled(bg);

        //Add score
        ITransform ts = new Transform(770, 10, 2, 0, 1, 1);
        IGOFactory sf = new GOFactoryScore(painter,  "0000", engine);
        IGameObject score = sf.create("score", ts);
        this.engine.addEnabled(score);

        initAlienSquad(85, 45, 3, 10, 1000, 40000, score.behaviours()[0], bigExp, smlExp, gunSnd01, expSnd00, painter);
        //initAlienSquad(85, 45, 1, 1, 1000, 40000, score.behaviours()[0], bigExp, smlExp, gunSnd00, expSnd00, painter);
        initPlayer(350, 500, 250, bigExp, smlExp, gunSnd00, expSnd00, painter);

    }


    public void initPlayer(int x, int y, int reloadDelay,
                           IGameObject bigExp, IGameObject smlExp,
                           SoundClip fireSnd, SoundClip expSnd, IPainter painter) throws IOException {

        //load player resources
        //BufferedImage shape = ImageIO.read(new File(resPath + "ship-full.png"));
        Image shape = new ImageIcon(resPath + "images/ship-anim-v2.gif").getImage();
        Image missile = new ImageIcon(resPath + "images/missileB.gif").getImage();

        //Define missile model
        //start at (0,0) to be centered on ship when firing move(ship.centroid())
        ITransform tam = new Transform(0, 0, 1, 0, 1, 1);
        Behaviour[] roleMissilePlayer = {
                new BehavMove(0, -150),
                new BehavTTL(new Random().nextInt(5000) + 10000),
                new BehavCollXplode(smlExp, "alien", expSnd),
                //new BehaviourTTL(10000),
        };

        //ICollider[] collm = { new CollCircle("", tam, 10) };
        Point[] collmv = new Point[] { new Point(0, 0), new Point(0, 15), new Point(4, 15), new Point(4, 0) };
        ICollider[] collm = { new CollPoly(painter, tam, Arrays.asList(collmv), "") };
        IGameObject playerMissile = new GameObject(engine, "player", tam, new ShapeImage2D(painter, tam, missile), collm, roleMissilePlayer);

        //Add player ship
        ITransform tp = new Transform(x, y, 1, 0, 0.3f, 0.3f);
        IBehaviour[] rolePlayer = {
                new BehavPlayer(150, 10, playerMissile, reloadDelay, fireSnd),
                new BehavCollXplode(bigExp, "alien", expSnd),
        };

        ICollider[] coll = { new CollCircle(painter, tp, 100, "") };
        //Point[] collv = new Point[] { new Point(100, 0), new Point(0, 200), new Point(200, 200) };
        //ICollider[] coll = { new CollPoly("", tp, Arrays.asList(collv)) };

        IGameObject player = new GameObject(engine, "player", tp, new ShapeImage2D(painter, tp, shape), coll, rolePlayer);
        this.engine.addEnabled(player);
    }


    public void initAlienSquad(int x, int y, int rows, int cols,
                               int minFireRate, int maxFireRate, IBehaviour roleScore,
                               IGameObject bigExp, IGameObject smlExp,
                               SoundClip fireSnd, SoundClip expSnd, IPainter painter) throws IOException {

        //load Alien resources
        //Buffered image does not work with animated gifs
        BufferedImage shape   = ImageIO.read(new File(resPath + "images/smallAlien.png"));
        //Image shape = new ImageIcon(resPath + "smallAlien.png").getImage();
        Image missile = new ImageIcon(resPath + "images/missileA.gif").getImage();

        //Define missile model
        //start at (0,0) to be centered on ship when firing move(ship.centroid())
        ITransform tam = new Transform(0, 0, 1, 0, 1, 1);
        Behaviour[] roleMissileAlien = {
                new BehavMove(0, 40),
                new BehavTTL(new Random().nextInt(5000) + 10000),
                new BehavCollXplode(smlExp, "player", expSnd),
                //new BehaviourTTL(10000),
        };

        ICollider[] collm = { new CollCircle(painter, tam, 5, "") };
        IGameObject alienMissile = new GameObject(engine, "alien", tam, new ShapeImage2D(painter, tam, missile), collm, roleMissileAlien);

        //Add Alien squad
        SquadIterator si = new SquadIterator(rows, cols, x, y, 70, 60);
        while (si.hasNext()) {
            Point p = si.next();
            ITransform t = new Transform(p.x(), p.y(), 1, 0, 0.2f, 0.2f);
            IBehaviour[] roleAlien = {
                    new BehavAlien(50, 10, alienMissile, minFireRate, maxFireRate, fireSnd),
                    new BehavCollXplode(bigExp, "player", expSnd),
            };
            ((BehavAlien) roleAlien[0]).observableScore().add((IObserver<Integer>) roleScore);
            IShape shapeAlien = new ShapeImage2D(painter, t, shape); //shape is the same for all instances

            //ICollider[] coll = { new CollCircle("", t,100) };
            //ICollider[] coll = { new CollPoly("", t, Arrays.asList(collv)), new CollCircle("", t,100) };
            Point[] collv = new Point[] { new Point(100, 0), new Point(0, 200), new Point(200, 200) };
            ICollider[] coll = { new CollPoly(painter, t, Arrays.asList(collv), "") };

            IGameObject go = new GameObject(engine, "alien", t, shapeAlien, coll, roleAlien);
            this.engine.addEnabled(go);
        }
    }

}
