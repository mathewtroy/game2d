package cz.cvut.fel.pjv;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    private static final int VOLUME_SCALE_ZERO = 0 ;
    private static final int VOLUME_SCALE_ONE = 1 ;
    private static final int VOLUME_SCALE_TWO = 2 ;
    private static final int VOLUME_SCALE_THREE = 3 ;
    private static final int VOLUME_SCALE_FOUR = 4 ;
    private static final int VOLUME_SCALE_FIVE = 5 ;

    Clip clip;

    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/fanfare.wav");
        soundURL[3] = getClass().getResource("/sound/powerup.wav");
        soundURL[4] = getClass().getResource("/sound/unlock.wav");
        soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/levelup.wav");
        soundURL[8] = getClass().getResource("/sound/cursor.wav");
        soundURL[9] = getClass().getResource("/sound/burning.wav");
        soundURL[10] = getClass().getResource("/sound/cuttree.wav");
        soundURL[11] = getClass().getResource("/sound/gameover.wav");
        soundURL[12] = getClass().getResource("/sound/stairs.wav");

    }

    public void setFile(int i) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }
        catch (Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case VOLUME_SCALE_ZERO: volume = -80f; break;
            case VOLUME_SCALE_ONE: volume = -20f; break;
            case VOLUME_SCALE_TWO: volume = -12f; break;
            case VOLUME_SCALE_THREE: volume = -5f; break;
            case VOLUME_SCALE_FOUR: volume = -1f; break;
            case VOLUME_SCALE_FIVE: volume = 6f; break;

        }
        fc.setValue(volume);
    }



}
