package core.eng;

public class InputEvent implements IInputEvent {
    private int key, button, x, y;

    public InputEvent(int key, int button, int x, int y) {
        this.key = key;
        this.button = button;
        this.x = x;
        this.y = y;
    }

    @Override
    public int key() {
        return key;
    }

    @Override
    public int button() {
        return button;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + key + ", " + button + ", " + x + ", " + y + "]";
    }
}
