package core.gui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author hdaniel@ualg.pt
 * @version 202503092300
 */
public class SoundClip {

    protected Clip clip;
    protected AudioInputStream stream;

    public SoundClip(String fname) {
        try {
            stream = AudioSystem.getAudioInputStream(new File(fname));
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        //try {
            clip.setFramePosition(0);
            clip.start();
        //} catch (LineUnavailableException | IOException e) {
        //    e.printStackTrace();
        //}
    }
}
