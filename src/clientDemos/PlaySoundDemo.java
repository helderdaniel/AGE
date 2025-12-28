package clientDemos;

import core.gui.SoundClip;

public class PlaySoundDemo {
    public static void main(String[] args) throws InterruptedException {

        SoundClip player = new SoundClip("src/Alienoid/resources/sounds/gun00.wav");
        player.play();

        Thread.sleep(2000);
        player.play();
        Thread.sleep(2000);
        player.play();
    }

}



