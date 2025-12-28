package core.gui.awt;

import core.eng.*;
import core.gui.IPainter;
import core.gui.IUserInterface;
import util.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author hdaniel@ualg.pt
 * @version 202506070117
 */
public class GUIAWT implements IUserInterface, IPainter {

    protected int rows;
    protected int cols;
    protected JFrame frame = new JFrame();
    protected JPanel panel = new Canvas();
    protected UserInputAWT input = new UserInputAWT();
    protected Iterable<IGameObject> gol = new CopyOnWriteArrayList<>();  //concurrent list
    protected final boolean showColliders;
    protected final List<IInputEvent> inputQueue = new ArrayList<>();

    protected Graphics graphic = null;

    //Default font
    Font font = new Font("TimesRoman", Font.PLAIN, 12);
    protected FontMetrics fm;
    protected int textWidth  = 0;
    protected int textHeight = 0;

    public GUIAWT(String title, int rows, int cols, boolean showColliders) {

        //Window setup
        this.rows = rows;
        this.cols = cols;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(rows, cols);
        frame.setContentPane(panel);
        frame.setTitle(title);
        frame.setVisible(true);
        //frame.createBufferStrategy(1);

        //Colliders mode
        this.showColliders = showColliders;

        //User Input
        input.addKeyListener(frame, inputQueue);
        input.addMouseListener(frame, inputQueue);
    }

    @Override
    public int rows() { return rows; }

    @Override
    public int cols() { return cols; }

    @Override
    public void update(Iterable<IGameObject> gol) {
        this.gol = gol;

        //Wait for the event dispatching thread to update the screen
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    panel.repaint();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            //e.printStackTrace();
            Log.println("Error updating GUI screen: " + e.getMessage());
        }
    }


    /*************************************
     * user input
     **************************************/

    @Override
    public IInputEvent input() {
        if    (inputQueue.isEmpty()) return null;
        else  return inputQueue.removeFirst();
    }



    /*************************************
     * elementar paint primitives
     **************************************/

    @Override
    public void drawCircle(int cx, int cy, int radius, Color color, boolean fill) {
        graphic.setColor(color);
        if (fill) graphic.fillOval(cx - radius, cy - radius, radius*2, radius*2);
        else      graphic.drawOval(cx - radius, cy - radius, radius*2, radius*2);
    }

    @Override
    public void drawOval(int cx, int cy, int width, int height, Color color, boolean fill) {
        graphic.setColor(color);
        if (fill) graphic.fillOval(cx - width/2, cy - height/2, width, height);
        else      graphic.drawOval(cx - width/2, cy - height/2, width, height);
    }

    @Override
    public void drawPolygon(int[] xCoord, int[] yCoord, Color color, boolean fill) {
        graphic.setColor(color);
        if (fill) graphic.fillPolygon(xCoord, yCoord, xCoord.length);
        else      graphic.drawPolygon(xCoord, yCoord, xCoord.length);
    }


    /*************************************
     * Transform based paint primitives
     **************************************/

    protected Graphics2D transform(ITransform t) {
        //based on: https://stackoverflow.com/a/37758533/9567003
        //          https://stackoverflow.com/a/66189875/9567003

        //Get Transform information
        double x      = t.x();
        double y      = t.y();
        double xScale = t.xScale();
        double yScale = t.yScale();
        double angle  = t.angle();
        double rads   = Math.toRadians(angle);

        //Need a new Graphics2d to each GOs (if commented GOs are not shown)
        Graphics2D g2d = (Graphics2D) graphic.create();

        //fill Affine Transform matrix
        g2d.translate(x, y);
        g2d.scale(xScale, yScale);
        g2d.rotate(rads, 0, 0);      // rotate around the centroid since the image is centered at (0,0)

        //setup rendering hints
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        return g2d;
    }


    @Override
    public void drawImage(ITransform t, Image bufImg) {

        //perform transformations
        Graphics2D g2d = transform(t);

        //draw image at center position (0,0) = (-width/2, -height/2)
        int width = bufImg.getWidth(null);
        int height = bufImg.getHeight(null);
        g2d.drawImage(bufImg, -width/2, -height/2, null);
        g2d.dispose();
    }


    @Override
    public void drawCircle(ITransform t, int radius, Color color, boolean fill) {

        //perform transformations
        Graphics2D g2d = transform(t);

        //draw circle at center position (0,0) = (-radius, -radius)
        g2d.setColor(color);
        if (fill)  g2d.fillOval(-radius, -radius, radius*2, radius*2);
        else       g2d.drawOval(-radius, -radius, radius*2, radius*2);
        //if (fill)  g2d.fillOval(0, 0, radius*2, radius*2);
        //else       g2d.drawOval(0, 0, radius*2, radius*2);
    }


    @Override
    public void drawRectangle(ITransform t, int width, int height, Color color, boolean fill) {

        //perform transformations
        Graphics2D g2d = transform(t);

        //draw image at center position (0,0) = (-width/2, -height/2)
        g2d.setColor(color);
        if (fill)  g2d.fillRect(-width/2, -height/2, width, height);
        else       g2d.drawRect(-width/2, -height/2, width, height);
    }


    @Override
    public void drawPolygon(ITransform t, int[] xCoord, int[] yCoord, Color color, boolean fill) {

        //perform transformations
        Graphics2D g2d = transform(t);

        //draw image at center position (0,0) since polygon was moved in constructor
        g2d.setColor(color);
        if (fill) g2d.fillPolygon(xCoord, yCoord, xCoord.length);
        else      g2d.drawPolygon(xCoord, yCoord, xCoord.length);
    }


    /*************************************
     * Text drawing primitives
     **************************************/

    @Override
    public int textWidth() { return textWidth; }

    @Override
    public int textHeight() { return textHeight; }

    @Override
    public void setFont(Font font) { this.font = font; }

    @Override
    //Compute metrics for this text font and size
    public void fontMetrics(String text) {
        // Because font metrics is based on a graphics context, we need to create a small,
        // temporary image, so we can ascertain the width and height of the final image
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gtmp = img.createGraphics();
        gtmp.setFont(font);
        fm = gtmp.getFontMetrics();
        textWidth = fm.stringWidth(text);
        textHeight = fm.getHeight();
        gtmp.dispose();
    }


    @Override
    public void drawText(ITransform t, String text, Color color) {

        //perform transformations
        Graphics2D g2d = transform(t);

        //setup further rendering hints
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        //render text at position (0,0) = (-width/2, -height/2)
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(text, -textWidth /2, fm.getAscent()- textHeight /2);
    }


    /*************************************************************
     * Inner class to paint IGameObjects and Colliders in a JPanel
     **************************************************************/
    class Canvas extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            //Update the current graphics context
            //for paint primitives
            graphic = g;

            for (IGameObject go : gol) {
                if (go.shape() != null)
                    go.shape().paint();

                if (showColliders) {
                    ICollider[] coll = go.colliders();
                    if (coll != null)
                        for (ICollider c : coll)
                            if (c != null)
                                c.paint();
                }
            }
        }
    }

}
