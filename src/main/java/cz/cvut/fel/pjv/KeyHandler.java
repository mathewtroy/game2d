package cz.cvut.fel.pjv;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static cz.cvut.fel.pjv.Sound.SOUND_EIGHT;
import static cz.cvut.fel.pjv.Sound.SOUND_ZERO;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

//    DEBUG
    public boolean checkDrawTime = false;

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
    public void keyTyped(KeyEvent e) {

    }

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

//        TITLE STATE
        if (gp.gameState == GamePanel.GameState.TITLE) {

            if (gp.ui.titleScreenState == 0) {
                titleState(code);
            }

            else if (gp.ui.titleScreenState == 1) {

                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) { gp.ui.commandNum = 3; }
                }

                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 3) { gp.ui.commandNum = 0; }
                }

                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        System.out.println("Do some fighter specific stuff");
                        gp.gameState = GamePanel.GameState.PLAY;
                    }

                    if (gp.ui.commandNum == 1) {
                        System.out.println("Do some thief specific stuff");
                        gp.gameState = GamePanel.GameState.PLAY;
                    }

                    if (gp.ui.commandNum == 2) {
                        System.out.println("Do some sorcerer specific stuff");
                        gp.gameState = GamePanel.GameState.PLAY;
                    }

                    if (gp.ui.commandNum == 3) { gp.ui.titleScreenState = 0; }
                }
            }



        }
        // PLAY STATE
        else if (gp.gameState == GamePanel.GameState.PLAY) {
            playState(code);
        }

        // PAUSE STATE
        else if (gp.gameState == GamePanel.GameState.PAUSE) {
            pauseState(code);
        }

        // DIALOGUE STATE
        else if (gp.gameState == GamePanel.GameState.DIALOGUE) {
            dialogueState(code);
        }

        // CHARACTER STATE
        else if (gp.gameState == GamePanel.GameState.CHARACTER) {
            characterState(code);
        }

        // OPTION STATE
        else if (gp.gameState == GamePanel.GameState.OPTION) {
            optionState(code);
        }

        // GAME OVER STATE
        else if (gp.gameState == GamePanel.GameState.GAME_OVER) {
            gameOverState(code);
        }

        // TRADE STATE
        else if (gp.gameState == GamePanel.GameState.TRADE) {
            tradeState(code);
        }

        // MAP STATE
        else if (gp.gameState == GamePanel.GameState.MAP) {
            mapState(code);
        }

    }


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
                gp.gameState = GamePanel.GameState.PLAY;
                // gp.ui.titleScreenState = 1;
                gp.playMusic(SOUND_ZERO);
            }

            // Load the game
            if (gp.ui.commandNum == 1) {
                gp.saveLoad.load();
                gp.gameState = GamePanel.GameState.PLAY;
                // gp.ui.titleScreenState = 1;
                gp.playMusic(SOUND_ZERO);
            }


            if (gp.ui.commandNum == 2) { System.exit(0); }
        }
    }


    private void playState (int code) {
        if (code == KeyEvent.VK_W) { upPressed = true; }
        if (code == KeyEvent.VK_S) { downPressed = true; }
        if (code == KeyEvent.VK_A) { leftPressed = true; }
        if (code == KeyEvent.VK_D) { rightPressed = true; }
        if (code == KeyEvent.VK_P) { gp.gameState = GamePanel.GameState.PAUSE; }
        if (code == KeyEvent.VK_C) { gp.gameState = GamePanel.GameState.CHARACTER;}
        if (code == KeyEvent.VK_ENTER) { enterPressed = true; }
        if (code == KeyEvent.VK_F) { shotKeyPressed = true; }
        if (code == KeyEvent.VK_R) { gp.gameState = GamePanel.GameState.OPTION;}

        if (code == KeyEvent.VK_M) { gp.gameState = GamePanel.GameState.MAP;}
        if (code == KeyEvent.VK_Q) {

            if (!gp.map.miniMapOn) {
                gp.map.miniMapOn = true;
            }
            else {
                gp.map.miniMapOn = false;
            }
        }



//  DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }

        if (code == KeyEvent.VK_E) {
            switch (gp.currentMap) {
                case 0: gp.tileM.loadMap("/maps/new2.txt", 0); break;

                case 1: gp.tileM.loadMap("/maps/pjv.txt", 1); break;

                case 2: gp.tileM.loadMap("/maps/gold.txt", 2); break;

            }
        }
    }


    private void pauseState (int code) {
        if(code == KeyEvent.VK_P) { gp.gameState = GamePanel.GameState.PLAY; }

    }


    private void dialogueState (int code) {
        if(code == KeyEvent.VK_ENTER) { gp.gameState = GamePanel.GameState.PLAY; }

    }

    private void characterState (int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = GamePanel.GameState.PLAY;
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }

        playerInventory(code);

    }

    private void optionState(int code) {
        if (code == KeyEvent.VK_R) {
            gp.gameState = GamePanel.GameState.PLAY;
        }

        else if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0: maxCommandNum = 4; break;
            case 2: maxCommandNum = 1; break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(SOUND_EIGHT);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(SOUND_EIGHT);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(SOUND_EIGHT);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(SOUND_EIGHT);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(SOUND_EIGHT);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(SOUND_EIGHT);
                }
            }
        }
    }


    private void gameOverState(int code) {

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(SOUND_EIGHT);
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(SOUND_EIGHT);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = GamePanel.GameState.PLAY;
                gp.resetGame(false);
                gp.playMusic(SOUND_ZERO);
            }
            else if (gp.ui.commandNum == 1) {
                gp.gameState = GamePanel.GameState.TITLE;
                gp.resetGame(true);
            }

        }

    }


    private void tradeState(int code) {

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
                gp.playSE(SOUND_EIGHT);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.playSE(SOUND_EIGHT);
            }
        }
        if (gp.ui.subState == 1) {
            npcInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }

        if (gp.ui.subState == 2) {
            playerInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }

    }

    private void mapState(int code) {

        if (code == KeyEvent.VK_M) {
            gp.gameState = GamePanel.GameState.PLAY;
        }
    }

    private void playerInventory(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(SOUND_EIGHT);
            }
        }
    }

    private void npcInventory(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(SOUND_EIGHT);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(SOUND_EIGHT);
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
