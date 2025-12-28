package core.gui.elements;

/**
 * Wrapper class for font objects.
 * To support several graphic backends: AWT, SDK, LWJGL, etc.
 *
 * @author hdaniel@ualg.pt
 * @version 202506191024
 */


class AvailableFonts {
    public static void main(String[] args) {
        System.out.printf("Available font types\n");
        for (IFont.Type font : IFont.Type.values()) {
            System.out.printf("%15s ", font.name());
            System.out.println(font.values());
        }

        System.out.printf("\nAvailable font styles\n");
        for (IFont.Style style : IFont.Style.values()) {
            System.out.printf("%15s ", style.name());
            System.out.println(style.values());
        }
    }
}
