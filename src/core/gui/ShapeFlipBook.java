package core.gui;

import core.eng.ITransform;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a set of images that can be presented in sequence. on the screen
 * @author hdaniel@ualg.pt
 * @version 202502151124
 *
 * @inv flipbook centroid is at (0,0)
 */
public class ShapeFlipBook extends Shape {

    protected int index = 0;
    protected List<BufferedImage> imgList = new ArrayList<>();

    public ShapeFlipBook(IPainter painter, ITransform t, List<BufferedImage> imgL) {
        super(painter, t,false);
        this.imgList = imgL;
        width = 0;
        height = 0;
        for (BufferedImage img : imgList) {
            int w = img.getWidth();
            int h = img.getHeight();
            if (w>width) width = w;
            if (h>height) height = h;
        }
    }

    public ShapeFlipBook(ShapeFlipBook other) {
        super(other);
        this.index = other.index;
        this.imgList = new ArrayList<>(other.imgList);
    }

    public int size() { return imgList.size(); }

    /**
     * Display image at index
     * @param index of image to display
     * @pre 0 <= index < this.size()
     */
    public void displayImage(int index) {
        this.index = index;
    }

    @Override
    public void paint() {
        painter.drawImage(transform, imgList.get(index));

        /*OLD
        //draw image at center position (0,0) = (-width/2, -height/2)
        g.drawImage(imgList.get(index), -width/2, -height/2, null);
        */
    }

    @Override
    public IShape copy() { return new ShapeFlipBook(this); }
}
