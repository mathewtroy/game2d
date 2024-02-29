package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.view.GameConstants;

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

    private Clip clip;
    public URL[] soundURL = new URL[30];
    private FloatControl fc;
    public int volumeScale = 3;
    private float volume;

    // Sound file paths
    private static final String[] SOUND_PATHS = {
            GameConstants.IRISH_SOUND,
            GameConstants.COIN_SOUND,
            GameConstants.SUCCESS_SOUND,
            GameConstants.POWERUP_SOUND,
            GameConstants.UNLOCK_SOUND,
            GameConstants.HIT_ENEMY_SOUND,
            GameConstants.RECEIVE_DAMAGE_SOUND,
            GameConstants.LEVEL_UP_SOUND,
            GameConstants.CURSOR_SOUND,
            GameConstants.BURNING_SOUND,
            GameConstants.CUT_TREE_SOUND,
            GameConstants.GAME_OVER_SOUND,
            GameConstants.STAIRS_SOUND,
            GameConstants.PRIZE_SOUND
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
            case VOLUME_SCALE_ZERO: volume = GameConstants.MUTE; break;
            case VOLUME_SCALE_ONE: volume = GameConstants.FIRST_LEVEL; break;
            case VOLUME_SCALE_TWO: volume = GameConstants.SECOND_LEVEL; break;
            case VOLUME_SCALE_THREE: volume = GameConstants.THIRD_LEVEL; break;
            case VOLUME_SCALE_FOUR: volume = GameConstants.FOURTH_LEVEL; break;
            case VOLUME_SCALE_FIVE: volume = GameConstants.MAX_LEVEL; break;

        }
        fc.setValue(volume);
    }
}