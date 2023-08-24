package cz.cvut.fel.pjv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;


class KeyHandlerTest {

    private KeyHandler keyHandler;
    private GamePanel gp;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        gp.gameState = GamePanel.GameState.PLAY;
        keyHandler = new KeyHandler(gp);
    }

    @Test
    void testPauseGameWhenPKeyPressed() {
        // Create a KeyEvent for the 'P' key
        KeyEvent keyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GamePanel.GameState actualResult = gp.gameState;
        GamePanel.GameState expectedResult = GamePanel.GameState.PAUSE;

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testMapStateWhenMKeyPressed() {
        // Create a KeyEvent for the 'M' key
        KeyEvent keyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_M, 'M');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GamePanel.GameState actualResult = gp.gameState;
        GamePanel.GameState expectedResult = GamePanel.GameState.MAP;

        Assertions.assertEquals(expectedResult, actualResult);
    }
}