package core.gui;

import core.eng.ITransform;

import java.awt.Image;
import java.awt.Graphics2D;

/**
 * Represents an image that can be painted on the screen
 * can be an animated gif if read with ImageIcon rather than BufferedImage
 * @author hdaniel@ualg.pt
 * @version 202502151124
 *
 * @inv image centroid is at (0,0)
 */
public class ShapeImage2D extends Shape {

    protected Image bufImg;

    public ShapeImage2D(IPainter painter, ITransform t, Image bufImg) {
        super(painter, t, false);
        this.bufImg = bufImg;
        width = bufImg.getWidth(null);
        height = bufImg.getHeight(null);
    }

    public ShapeImage2D(ShapeImage2D other) {
        super(other);
        this.bufImg = other.bufImg;
    }

    @Override
    public void paint() {
        painter.drawImage(transform, bufImg);
    }

    @Override
    public IShape copy() { return new ShapeImage2D(this); }
}
