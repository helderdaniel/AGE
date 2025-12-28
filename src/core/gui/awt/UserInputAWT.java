package core.gui.awt;

import core.eng.IInputEvent;
import core.eng.InputEvent;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

/**
 * User input dependent of graphics backbone lib
 *
 * @author hdaniel@ualg.pt
 * @version 202506070117
 */
public class UserInputAWT {

    static final int numkeys = 256;
    int[] keymap = new int[numkeys];


    UserInputAWT() {
        //reset keymap
        for (int i = 0; i < 256; i++)
            keymap[i] = 0;
    }


    public void addKeyListener(JFrame frame, List<IInputEvent> inputQueue) {

        frame.addKeyListener(new KeyListener() {

            //Invoked when a key has been pressed
            public void keyPressed(KeyEvent ke) {
                int key = ke.getKeyCode();
                if (keymap[key] == 0) {
                    keymap[key] = 1;
                    IInputEvent e = new InputEvent(key, -1, -1, -1);
                    inputQueue.add(e);
                }
            }

            //Invoked when a key has been released
            public void keyReleased(KeyEvent ke) {
                int key = ke.getKeyCode();
                keymap[key] = 0;
                IInputEvent e = new InputEvent(-key, -1, -1, -1);
                inputQueue.add(e);
            }

            //Invoked when a key has been typed (pressed and then released)
            public void keyTyped(KeyEvent ke) {
            }
        });

    }


    public void addMouseListener(JFrame frame, List<IInputEvent> inputQueue) {

        frame.addMouseListener(new MouseAdapter() {

            //Invoked when the mouse button has been clicked (pressed and released) on a component.
            public void mouseClicked(MouseEvent me) {
            }

            //Invoked when a mouse button is pressed on a component and then dragged.
            public void mouseDragged(MouseEvent me) {
            }

            //Invoked when the mouse enters a component
            public void mouseEntered(MouseEvent me) {
            }

            //Invoked when the mouse exits a component.
            public void mouseExited(MouseEvent me) {
            }

            //Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
            public void mouseMoved(MouseEvent me) {
            }

            //Invoked when a mouse button has been pressed on a component
            public void mousePressed(MouseEvent me) {
                IInputEvent e = new InputEvent(-1, me.getButton(), me.getX(), me.getY());
                inputQueue.add(e);
            }

            //Invoked when a mouse button has been released on a component.
            public void mouseReleased(MouseEvent me) {
            }

            //Invoked when the mouse wheel is rotated
            public void mouseWheelMoved(MouseWheelEvent mwe) {
            }

        });
    }


}
