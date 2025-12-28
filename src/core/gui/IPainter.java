package core.gui;

import core.eng.ITransform;
import java.awt.*;

/**
 * Graphics primitives dependent of graphics backbone lib
 * @author hdaniel@ualg.pt
 * @version 202506081213
 */
public interface IPainter {

    /*************************************
     * elementar paint primitives
     **************************************/

    /**
     * Draw a circle centered on point (cx, cy) with radius and color
     * @param cx       x-coordinate of the center of the circle
     * @param cy       y-coordinate of the center of the circle
     * @param radius   radius of the circle
     * @param color    color of the circle
     * @param fill     if true, fill the circle
     *
     * @pre radius > 0 && color != null
     */
    void drawCircle(int cx, int cy, int radius, Color color, boolean fill);

    /**
     * Draw an oval centered on point (cx, cy) with width, height and color
     * @param cx       x-coordinate of the center of the circle
     * @param cy       y-coordinate of the center of the circle
     * @param width    width of the oval
     * @param height   height of the oval
     * @param color    color of the circle
     * @param fill     if true, fill the oval
     *
     * @pre width > 0 && height > 0 && color != null
     */
    void drawOval(int cx, int cy, int width, int height, Color color, boolean fill);


    /**
     * Draw a polygon defined by its vertices
     * @param xCoord  array of x-coordinates of the polygon vertices
     * @param yCoord  array of y-coordinates of the polygon vertices
     * @param color   color of the polygon
     * @param fill    if true, fill the polygon
     *
     * @pre xCoord.length == yCoord.length && xCoord != null && yCoord != null && color != null
     */
    void drawPolygon(int[] xCoord, int[] yCoord, Color color, boolean fill);


    /*************************************
     * Transform based paint primitives
     **************************************/

    /**
     * Draw an image using the ITransform provided
     * @param t       the ITransform to apply to the image
     * @param bufImg  the image to draw
     *
     * @pre t != null && bufImg != null
     */
    void drawImage(ITransform t, Image bufImg);

    /**
     * Draw a circle with radius and color using the ITransform provided
     * @param t        the ITransform to apply to the circle
     * @param radius   radius of the circle
     * @param color    color of the circle
     * @param fill     if true, fill the circle
     *
     * @pre t != null && color != null && radius > 0
     */
    void drawCircle(ITransform t, int radius, Color color, boolean fill);

    /**
     * Draw a rectangle with width, height and color using the ITransform provided
     * @param t        the ITransform to apply to the circle
     * @param width    width of the rectangle
     * @param height   height of the rectangle
     * @param color    color of the circle
     * @param fill     if true, fill the circle
     *
     * @pre t != null && color != null && width > 0 && height > 0
     */
    void drawRectangle(ITransform t, int width, int height, Color color, boolean fill);

    /**
     * Draw a polygon defined by its vertices using the ITransform provided
     * @param t       the ITransform to apply to the circle
     * @param xCoord  array of x-coordinates of the polygon vertices
     * @param yCoord  array of y-coordinates of the polygon vertices
     * @param color   color of the polygon
     * @param fill    if true, fill the polygon
     *
     * @pre xCoord.length == yCoord.length && t != null && color != null && xCoord != null && yCoord != null
     */
    void drawPolygon(ITransform t, int[] xCoord, int[] yCoord, Color color, boolean fill);


    /*************************************
     * Text drawing primitives
     **************************************/

    /**
     * @return the text width for the previous call to fontMetrics
     */
    int textWidth();

    /**
     * @return the text height for the previous call to fontMetrics
     */
    int textHeight();

    /**
     * Set the font to be used for text drawing
     * @param font    the font to set
     *
     * @pre font != null
     */
    void setFont(Font font);

    /**
     * Compute font metrics for the current font and text
     * @param text    the text to compute metrics for
     */
    void fontMetrics(String text);

    /**
     * Draw a text defined by a string, with the current font and color, centered at the ITransform position
     * @param t       the ITransform to apply to the circle
     * @param color   color of the polygon
     *
     * @pre t != null && color != null
     */
    void drawText(ITransform t, String text, Color color);
}
