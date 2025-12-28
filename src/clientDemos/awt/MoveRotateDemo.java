package clientDemos.awt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

class Panel extends JPanel {

    private BufferedImage alien, bkgd;
    private int x = 0;
    private int y = 0;
    private int angle = 0;

    public void x(int x) { this.x = x; }
    public void y(int y) { this.y = y; }



    protected BufferedImage scaleImage(BufferedImage img, double xScale, double yScale) {
        int newW = (int) (img.getWidth()  * xScale);
        int newH = (int) (img.getHeight() * yScale);

        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(newW, newH, img.getType()) ; // alien.getType() should be: BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(tmp, 0, 0, null);
        return outputImage;
    }


    public BufferedImage rotateImage (BufferedImage img, double angle) {
        //https://stackoverflow.com/a/37758533/9567003
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));

        int w = img.getWidth();
        int h = img.getHeight();

        int newWidth =  (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        AffineTransform at = new AffineTransform();
        // needed to avoid clipping the image at the edges
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);
        // rotation around the center point
        at.rotate(rads, w/2, h/2);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, this);
        g2d.dispose();

        return rotated;
    }

    private static BufferedImage rotateImage2(BufferedImage img, double angle) {
        //https://stackoverflow.com/a/66189875/9567003
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));

        int w = img.getWidth();
        int h = img.getHeight();

        int newWidth =  (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        //needed?
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // needed to avoid clipping the image at the edges
        g2d.translate((newWidth - w) / 2, (newHeight - h) / 2);
        // rotation around the center point
        g2d.rotate(rads, w/2, h/2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }


    private static BufferedImage moveImage(Graphics g, BufferedImage img, double x, double y) {

        //Create a copy of the image including new Graphics2D
        BufferedImage movedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //Graphics2D g2d = movedImage.createGraphics();
        Graphics2D g2d = (Graphics2D) g.create();

        //needed?
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        //Copy the image with a new position
        g2d.translate(x, y);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return movedImage;
    }


    public BufferedImage moveRotateImage (Graphics2D g, BufferedImage img, double x, double y, double angle) {
        //based on: https://stackoverflow.com/a/37758533/9567003
        //          https://stackoverflow.com/a/66189875/9567003
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));

        int w = img.getWidth();
        int h = img.getHeight();

        int newWidth =  (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        //Use panel graphics to avoid clipping
        Graphics2D g2d = (Graphics2D) g.create();
        //Graphics2D g2d = newImage.createGraphics();

        //needed?
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // move
        g2d.translate(x, y);
        // rotation around the center point
        g2d.rotate(rads, w/2, h/2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return newImage;
    }


    public Panel() throws IOException {
        //try {
        String path = "src/Alienoid/resources/";
        alien = ImageIO.read(new File(path + "smallAlien.png"));
        bkgd = ImageIO.read(new File(path + "planet.png"));

        alien = scaleImage (alien, 0.25, 1/4.0);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    //@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bkgd, 0, 0, this);

        for (int i=0; i<2; i++) {

            //move and rotate, more efficient
            BufferedImage newImage = moveRotateImage((Graphics2D) g, alien, x + i * 20, y, angle++);
            g.drawImage(newImage, 0, 0, this);

            /*
            //rotate
            BufferedImage rotated = rotateImage(alien, angle++);
            // needed to reposition image
            int rx = (alien.getWidth()  - rotated.getWidth()) / 2;
            int ry = (alien.getHeight() - rotated.getHeight()) / 2;
            g.drawImage(rotated, rx + x + i * 20, ry + y + i * 0, this);
            */

            /*
            //move
            BufferedImage moved = moveImage(g, alien, x + i * 20, y + i * 0);
            g.drawImage(moved, x, y, this);
            */
        }
    }

}

public class MoveRotateDemo {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel gui = new Panel();
        frame.add(gui);
        frame.setSize(800, 600);
        frame.setVisible(true);

        int x=0, inc=1;
        long lastTime = System.nanoTime(); //  nanoTime();
        for (;;){
            x+=inc;
            long curTime = System.nanoTime();
            double frameTime = (curTime - lastTime) / 1000_000_000.0f;
            lastTime = curTime;
            System.out.println(x + " " + (int) (1/frameTime) + " fps");

            if (x>600) inc=-1;
            gui.x(x);

            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        frame.repaint();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            //frame.repaint();
            //try { Thread.sleep  (10); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        /*
        Timer timer = new Timer(16, new ActionListener() {
            int x=0, inc=1;
            @Override
            public void actionPerformed(ActionEvent e) {
                x+=inc;
                if (x>600) inc=-1;
                core.gui.x(x);
                frame.repaint();
            }
        });
        timer.start();
        */
    }

}
