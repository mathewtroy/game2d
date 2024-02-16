package cz.cvut.fel.pjv.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    // Constants for volume scaling
    private static final int VOLUME_SCALE_ZERO = 0;
    private static final int VOLUME_SCALE_ONE = 1;
    private static final int VOLUME_SCALE_TWO = 2;
    private static final int VOLUME_SCALE_THREE = 3;
    private static final int VOLUME_SCALE_FOUR = 4;
    private static final int VOLUME_SCALE_FIVE = 5;

    // Constants for volume levels
    private static final float MUTE = -80;
    private static final float FIRST_LEVEL = -20;
    private static final float SECOND_LEVEL = -12;
    private static final float THIRD_LEVEL = -5;
    private static final float FOURTH_LEVEL = -1;
    private static final float MAX_LEVEL = 6;

    // Sound indices
    public static final int SOUND_ZERO = 0;
    public static final int SOUND_ONE = 1;
    public static final int SOUND_TWO = 2;
    public static final int SOUND_THREE = 3;
    static final int SOUND_FOUR = 4;
    public static final int SOUND_FIVE = 5;
    public static final int SOUND_SIX = 6;
    public static final int SOUND_SEVEN = 7;
    public static final int SOUND_EIGHT = 8;
    public static final int SOUND_NINE = 9;
    public static final int SOUND_TEN = 10;
    public static final int SOUND_ELEVEN = 11;
    public static final int SOUND_TWELVE = 12 ;
    public static final int SOUND_THIRTEEN = 13 ;

    // Sound file paths
    private static final String IRISH_SOUND = "/sound/irish.wav";
    private static final String COIN_SOUND = "/sound/coin.wav";
    private static final String SUCCESS_SOUND = "/sound/success.wav";
    private static final String POWERUP_SOUND = "/sound/powerup.wav";
    private static final String UNLOCK_SOUND = "/sound/unlock.wav";
    private static final String HIT_ENEMY_SOUND = "/sound/hitenemy.wav";
    private static final String RECEIVE_DAMAGE_SOUND = "/sound/receivedamage.wav";
    private static final String LEVEL_UP_SOUND = "/sound/levelup.wav";
    private static final String CURSOR_SOUND = "/sound/cursor.wav";
    private static final String BURNING_SOUND = "/sound/burning.wav";
    private static final String CUT_TREE_SOUND = "/sound/cuttree.wav";
    private static final String GAME_OVER_SOUND = "/sound/gameover.wav";
    private static final String STAIRS_SOUND = "/sound/stairs.wav";
    private static final String PRIZE_SOUND = "/sound/prize.wav";

    private Clip clip;

    public URL[] soundURL = new URL[30];
    private FloatControl fc;
    public int volumeScale = 3;
    private float volume;

    // Sound file paths
    private static final String[] SOUND_PATHS = {
            IRISH_SOUND,
            COIN_SOUND,
            SUCCESS_SOUND,
            POWERUP_SOUND,
            UNLOCK_SOUND,
            HIT_ENEMY_SOUND,
            RECEIVE_DAMAGE_SOUND,
            LEVEL_UP_SOUND,
            CURSOR_SOUND,
            BURNING_SOUND,
            CUT_TREE_SOUND,
            GAME_OVER_SOUND,
            STAIRS_SOUND,
            PRIZE_SOUND
    };

    /**
     * Constructor for the Sound class. Initializes sound URLs.
     */
    public Sound() {
        // Initialize sound URLs
        for (int i = 0; i < SOUND_PATHS.length; i++) {
            soundURL[i] = getClass().getResource(SOUND_PATHS[i]);
        }
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
     * Starts playback of the audio clip
     */
    public void play() {
        clip.start();
    }

    /**
     * Loops playback of the audio clip continuously
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playback of the audio clip
     */
    public void stop() {
        clip.stop();
    }

    /**
     * Adjusts the volume of the audio clip based on the current volume scale
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
