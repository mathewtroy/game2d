package cz.cvut.fel.pjv.controller;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.view.GameState;
import org.junit.jupiter.api.*;
import java.awt.event.KeyEvent;

public class KeyHandlerTest {

    private KeyHandler keyHandler;
    private GamePanel gp;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        gp.gameState = GameState.PLAY;
        keyHandler = new KeyHandler(gp);
    }

    @Test
    void testPauseGameWhenPKeyPressed() {
        // Create a KeyEvent for the 'P' key
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GameState actualResult = gp.gameState;
        GameState expectedResult = GameState.PAUSE;

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testMapStateWhenMKeyPressed() {
        // Create a KeyEvent for the 'M' key
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_M, 'M');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GameState actualResult = gp.gameState;
        GameState expectedResult = GameState.MAP;

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testCharacterStateWhenPKeyPressed() {
        // Create a KeyEvent for the 'C' key
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_C, 'C');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GameState actualResult = gp.gameState;
        GameState expectedResult = GameState.CHARACTER;

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testOptionStateWhenPKeyPressed() {
        // Create a KeyEvent for the 'R' key
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R, 'R');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GameState actualResult = gp.gameState;
        GameState expectedResult = GameState.OPTION;

        Assertions.assertEquals(expectedResult, actualResult);
    }

}