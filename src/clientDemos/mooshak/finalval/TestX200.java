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

import java.util.List;
import java.util.Scanner;

/* Not used
   Simulates a duel GameoBjects move only verticaly

        Se houver uma colisão os objetos envolvidos são removidos.
        Apresentar o nome dos objetos que ainda existem apoś n frames.

        Layer será sempre a zero, escala será sempre 1 sem necessidade de considerar shape.
        A velocidade no eixo x dos objetos é 0.
        A velocidade no eixo y do projetil é 0.
        Raio do projetil é 1.
        O projetil quando disparado é colocado na mesma posição em que está correntemente o centroide do objeto.

        Número de frames a simular
        nome
        x, y, raio, velocidade no eixo y do objeto 1 por frame, velocidade no eixo x do projetil disparado pelo do objeto 1 por frame, frame em que o objeto 1 dispara

Exemplo 1
10
Player1
0 0 1 1 1 3
Player2
10 0 1 1 -0.5 3

Exemplo 2
7
Player1
2 2 2 0 1 1
Player2
10 0 1 1 -2 1

Exemplo 3
25
Player1
2 1 1 1 1 1
Player2
10 1 1 2 -1 5

Exemplo 4
5
Player1
2 10 3 0 1 2
Player2
11 13 3 0 -2 1

 */

public class TestX200 implements IRunnable {

    static class MoveCst extends Behaviour {

        protected double sx, sy;

        public MoveCst(double sx, double sy) {
            this.sx = sx;
            this.sy = sy;
        }

        @Override
        public void onUpdate(double dT, IInputEvent ie) {
            gameObject.transform().move(sx, sy);
        }

        @Override
        public void onCollision(List<ICollider> colliders) {
            for (ICollider c : colliders) {
                if (c.gameObject().name().equals(gameObject.name()))
                    continue;
                gameObject.engine().destroy(gameObject);
            }
        }
    }

    static class MoveCstShoot extends MoveCst {
        protected double psx, psy;
        private int shootFrame;
        private int frame = 0;

        public MoveCstShoot(double sx, double sy, double psx, double psy, int shootFrame) {
            super(sx, sy);
            this.psx = psx;
            this.psy = psy;
            this.shootFrame = shootFrame;
        }

        @Override
        public void onUpdate(double dT, IInputEvent ie) {
            super.onUpdate(dT, ie);
            frame++;

            //shoot
            if (frame == this.shootFrame) {
                double x = gameObject.transform().x();
                double y = gameObject.transform().y();
                ITransform t = new Transform(new Point(x, y), 0, 0, 1, 1);
                ICollider[] coll = { new CollCircle(null, t, 1, "") };
                MoveCst role = new MoveCst(psx, psy);
                var projectile = new GameObject(gameObject.engine(), gameObject().name(), t, null, coll, new MoveCst[]{role});
                role.gameObject(projectile);
                gameObject.engine().addEnabled(projectile);
            }
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        boolean debug = false;

        int layer = 0;
        double angle = 0;
        double scale = 1;

        int players = 2;
        int frames = in.nextInt();
        IUserInterface gui = new GUIAWT("", 0, 0, false);
        GameEngine engine = new GameEngine(new GameWorldFlat(), gui, frames);

        for (int n = 0; n < players; n++) {
            String name = in.next();

            ITransform t = new Transform(
                    new Point(in.nextDouble(), in.nextDouble()),
                    layer,
                    angle,
                    scale, scale); //do not use yScale

            ICollider[] coll = { new CollCircle(null, t, in.nextDouble(), "") };

            //Create the objects
            MoveCstShoot role = new MoveCstShoot(0, in.nextDouble(), in.nextDouble(), 0, in.nextInt());
            IGameObject go = new GameObject(engine, name, t, null, coll, new MoveCstShoot[]{role});
            role.gameObject(go);
            engine.addEnabled(go);

        }

        //simulate frames
        engine.run();

        //print remaining objects
        for (var go : engine.getEnabled()) {
            System.out.println(go.name()+String.format(" %.2f %.2f", go.transform().x(), go.transform().y()));
            if(debug) {
                System.out.println(go.transform());
                System.out.println(go.colliders());
            }
        }
    }

    @Override
    public void run(String[] args) { main(args); }

}