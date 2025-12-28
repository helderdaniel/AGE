package core.gui.elements;


public interface IColor {

    enum Color {
        BLACK,
        BLUE,
        CYAN,
        DARK_GRAY,
        GRAY,
        GREEN,
        LIGHT_GRAY,
        MAGENTA,
        ORANGE,
        PINK,
        RED,
        WHITE,
        YELLOW
    }

    /**
     * @return the color
     */
    Color color();

}