package core.gui;

import core.eng.ITransform;

import java.awt.Font;
import java.awt.Color;


/**
 * Represents a string that can be written on the screen
 * @author hdaniel@ualg.pt
 * @version 202502151124
 *
 * @inv text centroid is at (0,0)
 */
public class ShapeText extends Shape {

    protected String text;
    protected Font font;
    protected Color color;

    public ShapeText(IPainter painter, ITransform t, String text, Font font, Color color) {
        super(painter, t,false);
        this.text = text;
        this.font = font;
        this.color = color;
        this.painter = painter;

        painter.setFont(font);
        computeMetrics();
    }

    //Compute size for this text font and size
    protected void computeMetrics() {
        painter.fontMetrics(text);
        width = painter.textWidth();
        height = painter.textHeight();
        centerX = width / 2;
        centerY = height / 2;
    }

    public ShapeText(ShapeText other) {
        super(other);
        this.text  = other.text;
        this.font  = other.font;
        this.color = other.color;
        this.painter = other.painter;
    }

    @Override
    public void paint() {
        painter.drawText(transform, text, color);
    }

    @Override
    public IShape copy() { return new ShapeText(this); }

    public String text() { return text; }
    public void text(String text) {
        //Compute size for this text font and size
        this.text = text;
        computeMetrics();
    }
}
