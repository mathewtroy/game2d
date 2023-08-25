package cz.cvut.fel.pjv;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    private static final int VOLUME_SCALE_ZERO = 0;
    private static final int VOLUME_SCALE_ONE = 1;
    private static final int VOLUME_SCALE_TWO = 2;
    private static final int VOLUME_SCALE_THREE = 3;
    private static final int VOLUME_SCALE_FOUR = 4;
    private static final int VOLUME_SCALE_FIVE = 5;

    private static final float MUTE = -80;
    private static final float FIRST_LEVEL = -20;
    private static final float SECOND_LEVEL = -12;
    private static final float THIRD_LEVEL = -5;
    private static final float FOURTH_LEVEL = -1;
    private static final float MAX_LEVEL = 6;

    static final int SOUND_ZERO = 0;
    public static final int SOUND_ONE = 1;
    public static final int SOUND_TWO = 2;
    public static final int SOUND_THREE = 3;
    static final int SOUND_FOUR = 4;
    public static final int SOUND_FIVE = 5;
    public static final int SOUND_SIX = 6;
    public static final int SOUND_SEVEN = 7;
    static final int SOUND_EIGHT = 8;
    public static final int SOUND_NINE = 9;
    public static final int SOUND_TEN = 10;
    public static final int SOUND_ELEVEN = 11;
    static final int SOUND_TWELVE = 12 ;
    public static final int SOUND_THIRTEEN = 13 ;


    Clip clip;

    URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    Sound() {

        soundURL[SOUND_ZERO] = getClass().getResource("/sound/irish.wav");
        soundURL[SOUND_ONE] = getClass().getResource("/sound/coin.wav");
        soundURL[SOUND_TWO] = getClass().getResource("/sound/success.wav");
        soundURL[SOUND_THREE] = getClass().getResource("/sound/powerup.wav");
        soundURL[SOUND_FOUR] = getClass().getResource("/sound/unlock.wav");
        soundURL[SOUND_FIVE] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[SOUND_SIX] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[SOUND_SEVEN] = getClass().getResource("/sound/levelup.wav");
        soundURL[SOUND_EIGHT] = getClass().getResource("/sound/cursor.wav");
        soundURL[SOUND_NINE] = getClass().getResource("/sound/burning.wav");
        soundURL[SOUND_TEN] = getClass().getResource("/sound/cuttree.wav");
        soundURL[SOUND_ELEVEN] = getClass().getResource("/sound/gameover.wav");
        soundURL[SOUND_TWELVE] = getClass().getResource("/sound/stairs.wav");
        soundURL[SOUND_THIRTEEN] = getClass().getResource("/sound/prize.wav");

    }

    /**
     * Sets up an audio file at the specified index for playback.
     *
     * @param i The index of the audio file in the soundURL array.
     */
    public void setFile(int i) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void play() {
        clip.start();
    }

    /**
     *
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     *
     */
    public void stop() {
        clip.stop();
    }

    /**
     *
     */
    public void checkVolume() {
        switch (volumeScale) {
            case VOLUME_SCALE_ZERO: volume = MUTE; break;
            case VOLUME_SCALE_ONE: volume = FIRST_LEVEL; break;
            case VOLUME_SCALE_TWO: volume = SECOND_LEVEL; break;
            case VOLUME_SCALE_THREE: volume = THIRD_LEVEL; break;
            case VOLUME_SCALE_FOUR: volume = FOURTH_LEVEL; break;
            case VOLUME_SCALE_FIVE: volume = MAX_LEVEL; break;

        }
        fc.setValue(volume);
    }


}
