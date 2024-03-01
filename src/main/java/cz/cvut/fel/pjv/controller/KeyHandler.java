package cz.cvut.fel.pjv.controller;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.view.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e Value of the button on keyboard
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e Value of the button on keyboard
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // States
        if (gp.gameState == GameState.TITLE) { if (gp.ui.titleScreenState == 0) { titleState(code); } }
        else if (gp.gameState == GameState.PLAY) { playState(code); }
        else if (gp.gameState == GameState.PAUSE) { pauseState(code); }
        else if (gp.gameState == GameState.DIALOGUE) { dialogueState(code); }
        else if (gp.gameState == GameState.CHARACTER) { characterState(code); }
        else if (gp.gameState == GameState.OPTION) { optionState(code); }
        else if (gp.gameState == GameState.GAME_OVER) { gameOverState(code); }
        else if (gp.gameState == GameState.TRADE) { tradeState(code); }
        else if (gp.gameState == GameState.MAP) { mapState(code); }
    }

    /**
     * Handles key events when the game is in the title state
     *
     * @param code The key code of the pressed key
     *
     */
    private void titleState (int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) { gp.ui.commandNum = 2; }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) { gp.ui.commandNum = 0; }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = GameState.PLAY;
                gp.playMusic(GameConstants.SOUND_ZERO);
            }

            // Load the game
            if (gp.ui.commandNum == 1) {
                gp.saveLoad.load();
                gp.gameState = GameState.PLAY;
                gp.playMusic(GameConstants.SOUND_ZERO);
            }
            if (gp.ui.commandNum == 2) { System.exit(0); }
        }
    }

    /**
     * Handles key events when the game is in the play state
     *
     * @param code The key code of the pressed key
     *
     */
    private void playState (int code) {
        if (code == KeyEvent.VK_W) { upPressed = true; }
        if (code == KeyEvent.VK_S) { downPressed = true; }
        if (code == KeyEvent.VK_A) { leftPressed = true; }
        if (code == KeyEvent.VK_D) { rightPressed = true; }
        if (code == KeyEvent.VK_P) { gp.gameState = GameState.PAUSE; }
        if (code == KeyEvent.VK_C) { gp.gameState = GameState.CHARACTER;}
        if (code == KeyEvent.VK_ENTER) { enterPressed = true; }
        if (code == KeyEvent.VK_F) { shotKeyPressed = true; }
        if (code == KeyEvent.VK_R) { gp.gameState = GameState.OPTION;}
        if (code == KeyEvent.VK_M) { gp.gameState = GameState.MAP;}
        if (code == KeyEvent.VK_Q) {

            if (!gp.map.miniMapOn) { gp.map.miniMapOn = true; }
            else { gp.map.miniMapOn = false; }
        }

        if (code == KeyEvent.VK_E) {
            switch (gp.currentMap) {
                case 0: gp.tileM.loadMap(MapConstants.MAP_PATH_NEW2, MapConstants.MAP_NEW); break;
                case 1: gp.tileM.loadMap(MapConstants.MAP_PATH_PJV, MapConstants.MAP_PJV); break;
                case 2: gp.tileM.loadMap(MapConstants.MAP_PATH_GOLD, MapConstants.MAP_GOLD); break;
            }
        }
    }

    private void pauseState (int code) {
        if(code == KeyEvent.VK_P) { gp.gameState = GameState.PLAY; }
    }

    /**
     * Handles key events when the game is in the dialogue state
     *
     * @param code The key code of the pressed key
     *
     */
    private void dialogueState (int code) {
        if(code == KeyEvent.VK_ENTER) { gp.gameState = GameState.PLAY; }
    }

    /**
     * Handles key events when the game is in the character state
     *
     * @param code The key code of the pressed key
     *
     */
    private void characterState (int code) {
        if (code == KeyEvent.VK_C) { gp.gameState = GameState.PLAY; }
        if (code == KeyEvent.VK_ENTER) { gp.player.selectItem(); }
        playerInventory(code);
    }

    /**
     * Handles key events when the game is in the option state
     *
     * @param code The key code of the pressed key
     *
     */
    private void optionState(int code) {
        if (code == KeyEvent.VK_R) { gp.gameState = GameState.PLAY; }
        else if (code == KeyEvent.VK_ENTER) { enterPressed = true; }
        int maxCommandNum = 0;

        switch (gp.ui.subState) {
            case 0: maxCommandNum = 4; break;
            case 2: maxCommandNum = 1; break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(GameConstants.SOUND_EIGHT);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(GameConstants.SOUND_EIGHT);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(GameConstants.SOUND_EIGHT);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(GameConstants.SOUND_EIGHT);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(GameConstants.SOUND_EIGHT);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(GameConstants.SOUND_EIGHT);
                }
            }
        }
    }

    /**
     * Handles key events when the game is in the game over state
     *
     * @param code The key code of the pressed key
     *
     */
    private void gameOverState(int code) {

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) { gp.ui.commandNum = 1; }
            gp.playSE(GameConstants.SOUND_EIGHT);
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) { gp.ui.commandNum = 0; }
            gp.playSE(GameConstants.SOUND_EIGHT);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = GameState.PLAY;
                gp.resetGame(false);
                gp.playMusic(GameConstants.SOUND_ZERO);
            }
            else if (gp.ui.commandNum == 1) {
                gp.gameState = GameState.TITLE;
                gp.resetGame(true);
            }
        }
    }

    /**
     * Handles key events when the game is in the trade state
     *
     * @param code The key code of the pressed key
     */
    private void tradeState(int code) {

        if (code == KeyEvent.VK_ENTER) { enterPressed = true; }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) { gp.ui.commandNum = 2; }
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) { gp.ui.commandNum = 0; }
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }
        if (gp.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) { gp.ui.subState = 0; }
        }

        if (gp.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) { gp.ui.subState = 0; }
        }
    }

    /**
     * Handles key events when the game is in the map state
     *
     * @param code The key code of the pressed key
     */
    private void mapState(int code) {
        if (code == KeyEvent.VK_M) { gp.gameState = GameState.PLAY; }
    }

    /**
     * Handles key events related to the player's inventory
     *
     * @param code The key code of the pressed key
     *
     */
    private void playerInventory(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }
    }

    /**
     * Handles key events related to the NPC's inventory
     *
     * @param code The key code of the pressed key
     *
     */
    private void npcInventory(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(GameConstants.SOUND_EIGHT);
            }
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e Value of the button on keyboard
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) { upPressed = false; }
        if (code == KeyEvent.VK_S) { downPressed = false; }
        if (code == KeyEvent.VK_A) { leftPressed = false; }
        if (code == KeyEvent.VK_D) { rightPressed = false; }
        if (code == KeyEvent.VK_F) { shotKeyPressed = false; }
    }
}
