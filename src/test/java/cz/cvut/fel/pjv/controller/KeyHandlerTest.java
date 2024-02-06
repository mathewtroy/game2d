package cz.cvut.fel.pjv.controller;

import cz.cvut.fel.pjv.view.GamePanel;
import org.junit.jupiter.api.*;
import java.awt.event.KeyEvent;

public class KeyHandlerTest {

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
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GamePanel.GameState actualResult = gp.gameState;
        GamePanel.GameState expectedResult = GamePanel.GameState.PAUSE;

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testMapStateWhenMKeyPressed() {
        // Create a KeyEvent for the 'M' key
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_M, 'M');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GamePanel.GameState actualResult = gp.gameState;
        GamePanel.GameState expectedResult = GamePanel.GameState.MAP;

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testCharacterStateWhenPKeyPressed() {
        // Create a KeyEvent for the 'C' key
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_C, 'C');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GamePanel.GameState actualResult = gp.gameState;
        GamePanel.GameState expectedResult = GamePanel.GameState.CHARACTER;

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testOptionStateWhenPKeyPressed() {
        // Create a KeyEvent for the 'R' key
        KeyEvent keyEvent =
                new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R, 'R');
        keyHandler.keyPressed(keyEvent);

        // Define expected and actual results
        GamePanel.GameState actualResult = gp.gameState;
        GamePanel.GameState expectedResult = GamePanel.GameState.OPTION;

        Assertions.assertEquals(expectedResult, actualResult);
    }

}