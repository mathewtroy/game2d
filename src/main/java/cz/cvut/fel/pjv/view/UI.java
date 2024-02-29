package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.model.object.CoinGold;
import cz.cvut.fel.pjv.model.vitality.Heart;
import cz.cvut.fel.pjv.model.vitality.Mana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The UI class is responsible for rendering the user interface elements of the game.
 * It handles various game states and displays relevant information such as player stats,
 * messages, dialogue, and inventory.
 */
public class UI {

    GamePanel gp;
    Graphics2D g2;
    public Font arial_40;
    Font arial_80B;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = GameConstants.BLANK;
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public int subState = 0;
    int counter = 0;
    public Entity npc;

    /**
     * 
     * @param gp    The GamePanel reference
     */
    public UI(GamePanel gp) {

        this.gp = gp;
        arial_40 = new Font(GameConstants.FONT_ARIAL, Font.PLAIN, 40);
        arial_80B = new Font(GameConstants.FONT_ARIAL, Font.BOLD, 80);

        //  CREATE HUD OBJECT
        Entity heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new Mana(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity goldCoin = new CoinGold(gp);
        coin = goldCoin.down1;
    }

    /**
     * Add a message to be displayed in the UI.
     *
     * @param text The message text to be displayed
     */
    public void addMessage (String text) {
        message.add(text);
        messageCounter.add(0);
    }

    /**
     * Draw the UI elements on the screen.
     *
     * @param g2 The graphics context to draw on
     */
    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(UIColors.WHITE);

        //  States
        if (gp.gameState == GameState.TITLE) { drawTitleScreen(); }
        if (gp.gameState == GameState.PLAY) { drawPlayerLife(); drawMessage(); }
        if (gp.gameState == GameState.PAUSE) { drawPlayerLife(); drawPauseScreen(); }
        if(gp.gameState == GameState.DIALOGUE) { drawDialogueScreen(); }
        if (gp.gameState == GameState.CHARACTER) { drawCharacterScreen(); drawInventory(gp.player, true); }
        if (gp.gameState == GameState.OPTION) { drawOptionsScreen(); }
        if (gp.gameState == GameState.GAME_OVER) { drawGameOverScreen(); }
        if (gp.gameState == GameState.TRANSITION) { drawTransition(); }
        if (gp.gameState == GameState.TRADE) { drawTradeScreen(); }
    }

    /**
     * Draw the player's life
     */
    private void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // DRAW MAX HEART
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        // DRAW CURRENT HEART
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) { g2.drawImage(heart_full, x, y, null); }
            i++;
            x += gp.tileSize;
        }

        // DRAW MAX MANA
        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;

        while (i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // DRAW MANA
        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;

        while (i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    /**
     * Draw game messages
     */
    private void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {

                g2.setColor(UIColors.BLACK);
                g2.drawString(message.get(i), messageX+3, messageY+3); // added shadow message

                g2.setColor(UIColors.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    /**
     * Draw the title screen
     */
    private void drawTitleScreen() {
            g2.setColor(UIColors.BACKGROUND_GREEN);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Title name
            String text = GameConstants.TITLE_NAME;
            int x = getXForCenteredText(text);
            int y = gp.tileSize*3;

            g2.setColor(UIColors.GRAY); // Shadow
            g2.drawString(text, x+5, y+5);
            g2.setColor(UIColors.BLACK); // The black main color
            g2.drawString(text, x, y);

            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x,y*2, gp.tileSize*2, gp.tileSize*2, null);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)); // menu

            text = GameConstants.TITLE_NEW_GAME;
            x = getXForCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if (commandNum == 0) { g2.drawString(GameConstants.SELECTOR, x-gp.tileSize, y); }

            text = GameConstants.TITLE_LOAD_GAME;
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) { g2.drawString(GameConstants.SELECTOR, x-gp.tileSize, y); }

            text = GameConstants.TITLE_QUIT;
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) { g2.drawString(GameConstants.SELECTOR, x-gp.tileSize, y);}
    }

    /**
     * Draw the pause screen
     */
    private void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = GameConstants.PAUSE_PRESSED;
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x,y);
    }

    /**
     * Draw the dialogue screen
     */
    private void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*8);
        int height = gp.tileSize*5;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split(GameConstants.SEPARATOR)) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    /**
     * Draw the character screen
     */
    private void drawCharacterScreen() {
        // CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(UIColors.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // NAMES
        g2.drawString(GameConstants.LEVEL, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.LIFE, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.MANA, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.STRENGTH, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.DEXTERITY, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.ATTACK, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.DEFENSE, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.EXP, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.NEXT_LEVEL, textX, textY); textY += lineHeight;
        g2.drawString(GameConstants.COIN, textX, textY); textY += lineHeight + 10;
        g2.drawString(GameConstants.WEAPON, textX, textY); textY += lineHeight + 15;
        g2.drawString(GameConstants.HELMET, textX, textY); textY += lineHeight;

        // VALUES
        int tailX = (frameX + frameWidth) - 30 ;

        // RESET textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.getLevel());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + GameConstants.SLASH + gp.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + GameConstants.SLASH + gp.player.maxMana);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getExp());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getNextLevelExp());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentHelmet.down1,tailX - gp.tileSize, textY-24, null);
    }

    /**
     * Draws the inventory for a given entity with an optional cursor.
     *
     * @param entity The entity for which to draw the inventory.
     * @param cursor A boolean indicating whether to display a cursor in the inventory.
     */
    private void drawInventory(Entity entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }

        else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight); // Frame

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // Draw player's items
        for(int i = 0; i < entity.inventory.size(); i++) {
            // Equip cursor
            if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentHelmet) {
                g2.setColor(UIColors.CURSOR_COLOR);
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // Display amount
            if (entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32F));
                int amountX;
                int amountY;
                String s = GameConstants.BLANK + entity.inventory.get(i).amount;
                amountX = getXForAlignToRightText(s, slotX+44);
                amountY = slotY + gp.tileSize;

                g2.setColor(UIColors.SHADOW_COLOR); // shadow
                g2.drawString(s,amountX, amountY) ;

                g2.setColor(UIColors.WHITE); // numbers
                g2.drawString(s, amountX-3, amountY-3);
            }

            slotX += slotSize;

            if ( (i == 4) || (i == 9) || (i == 14) ) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Cursor
        if (cursor) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // Draw cursor
            g2.setColor(UIColors.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX,cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize*3;

            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            // Draw Description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {

                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                String[] descriptionLines = entity.inventory.get(itemIndex).description.split(GameConstants.SEPARATOR);
                for (String line : descriptionLines) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    /**
     * Draw the options screen
     */
    private void drawOptionsScreen() {
        g2.setColor(UIColors.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Sub window
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: option_control(frameX, frameY); break;
            case 2: option_endGame(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false;
    }

    /**
     * Draw the game over screen
     */
    private void drawGameOverScreen() {
        g2.setColor(UIColors.BLACK);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = GameConstants.GAME_OVER; // Game over
        g2.setColor(UIColors.BLACK);
        x = getXForCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        g2.setColor(UIColors.WHITE);
        g2.drawString(text, x-4, y);
        g2.setFont(g2.getFont().deriveFont(50f));

        text = GameConstants.RETRY; // Retry the game
        x = getXForCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(GameConstants.SELECTOR, x-40, y);
        }

        text = GameConstants.QUIT; // Back to menu
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        if (commandNum == 1) {
            g2.drawString(GameConstants.SELECTOR, x-40, y);
        }
    }

    /**
     * Draws the top-level options' menu, including music
     * and sound effect volume controls,
     * controls settings, and the option to end the game.
     *
     * @param frameX The X-coordinate of the frame for rendering the menu.
     * @param frameY The Y-coordinate of the frame for rendering the menu.
     */
    private void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        String text = GameConstants.OPTIONS; // Title
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize; // Music Volume
        textY += gp.tileSize*2;
        g2.drawString(GameConstants.MUSIC_VOLUME, textX, textY);
        if (commandNum == GameConstants.OPTION_MUSIC) {
            g2.drawString(GameConstants.SELECTOR, textX- GameConstants.INDENT_SIZE, textY);
        }

        textY += gp.tileSize; // SE Volume
        g2.drawString(GameConstants.SE_VOLUME, textX, textY);
        if (commandNum == GameConstants.OPTION_SOUND_EFFECTS) {
            g2.drawString(GameConstants.SELECTOR, textX- GameConstants.INDENT_SIZE, textY);
        }

        textY += gp.tileSize; // Controls
        g2.drawString(GameConstants.CONTROL, textX, textY);
        if (commandNum == GameConstants.OPTION_CONTROLS) {
            g2.drawString(GameConstants.SELECTOR, textX- GameConstants.INDENT_SIZE, textY);
            if (gp.keyH.enterPressed) { subState = 1; commandNum = 0; }
        }

        textY += gp.tileSize; // End Game
        g2.drawString(GameConstants.END_GAME, textX, textY);
        if (commandNum == GameConstants.OPTION_END_GAME) {
            g2.drawString(GameConstants.SELECTOR, textX- GameConstants.INDENT_SIZE, textY);
            if (gp.keyH.enterPressed) { subState = 2; commandNum = 0; }
        }

        textX = getXForCenteredText(text); // Go back
        textY += gp.tileSize * 2;
        g2.drawString(GameConstants.GO_BACK, textX, textY);
        if (commandNum == GameConstants.OPTION_GO_BACK) {
            g2.drawString(GameConstants.SELECTOR, textX- GameConstants.INDENT_SIZE, textY);
            if (gp.keyH.enterPressed) { gp.gameState = GameState.PLAY; commandNum = 0; }
        }

        // CHECKBOX Music Volume
        textX = frameX + (int) (gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // CHECKBOX SE Volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    /**
     * Draws the control settings menu, displaying key bindings for various actions.
     *
     * @param frameX The X-coordinate of the frame for rendering the menu.
     * @param frameY The Y-coordinate of the frame for rendering the menu.
     */
    private void option_control(int frameX, int frameY) {
        int textX;
        int textY;

        String text = GameConstants.CONTROL; // Title
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(GameConstants.MOVE, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.CONFIRM, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.ATTACKING, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.SHOOT, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.INVENTORY, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.PAUSE, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.OPTIONS, textX,textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize*5;
        textY = frameY + gp.tileSize*2;
        g2.drawString(GameConstants.WASD_BUTTONS, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.ENTER_BUTTON, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.ENTER_BUTTON, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.F_BUTTON, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.C_BUTTON, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.P_BUTTON, textX,textY); textY += gp.tileSize;
        g2.drawString(GameConstants.R_BUTTON, textX,textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize; // Back
        textY = frameY + gp.tileSize*9;
        g2.drawString(GameConstants.GO_BACK, textX, textY);
        if (commandNum == 0) {
            g2.drawString(GameConstants.SELECTOR, textX-25, textY);
            if (gp.keyH.enterPressed) { subState = 0; commandNum = 2; }
        }
    }

    /**
     * Draws the end game confirmation dialog,
     * allowing the player to quit the game or cancel.
     *
     * @param frameX The X-coordinate of the frame for rendering the dialog.
     * @param frameY The Y-coordinate of the frame for rendering the dialog.
     */
    private void option_endGame(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = GameConstants.GO_TO_MENU;
        String[] lines = currentDialogue.split(GameConstants.SEPARATOR);

        for (String line : lines) {
            g2.drawString(line, textX, textY);
            textY += g2.getFontMetrics().getHeight(); // Move to the next line
        }

        String text = GameConstants.YES_OPTION; // Accept quit
        textX = getXForCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);

        if (commandNum == 0) {
            g2.drawString(GameConstants.SELECTOR, textX- GameConstants.INDENT_SIZE, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.gameState = GameState.TITLE;
                gp.resetGame(true);
                gp.stopMusic();
            }
        }

        text = GameConstants.NO_OPTION; // Cancel quit
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);

        if (commandNum == 1) {
            g2.drawString(GameConstants.SELECTOR, textX- GameConstants.INDENT_SIZE, textY);
            if (gp.keyH.enterPressed) { subState = 0; commandNum = 3; }
        }
    }

    /**
     * Draw the transition screen
     */
    private void drawTransition() {

        counter++;
        g2.setColor(new Color(0,0,0, counter*5));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        if (counter == 50) {
            counter = 0;
            gp.gameState = GameState.PLAY;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    /**
     * Draws the trade screen based on the current sub-state,
     * which can be for selecting, buying, or selling items.
     * Handles player input for navigation within the trade screen.
     */
    private void drawTradeScreen() {

        switch (subState) {
            case 0: tradeSelect(); break;
            case 1: tradeBuy(); break;
            case 2: tradeSell(); break;
        }
        gp.keyH.enterPressed = false;
    }

    /**
     * Displays the initial trade selection menu, allowing the player
     * to choose between buying, selling, or leaving the trade screen.
     * Handles player input for menu selection.
     */
    private void tradeSelect() {

        drawDialogueScreen();
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 4;
        int height = (int) (gp.tileSize * 4);

        drawSubWindow(x, y, width, height);
        x += gp.tileSize;
        y += gp.tileSize;

        g2.drawString(GameConstants.BUY, x, y);
        if (commandNum == 0) {
            g2.drawString(GameConstants.SELECTOR, x-24, y);
            if(gp.keyH.enterPressed) { subState = 1; }
        }
        y += gp.tileSize;

        g2.drawString(GameConstants.SELL, x, y);
        if (commandNum == 1) {
            g2.drawString(GameConstants.SELECTOR, x-24, y);
            if(gp.keyH.enterPressed) { subState = 2; }
        }
        y += gp.tileSize;

        g2.drawString(GameConstants.LEAVE, x, y);
        if (commandNum == 2) {
            g2.drawString(GameConstants.SELECTOR, x-24, y);
            if(gp.keyH.enterPressed) {
                commandNum = 0;
                gp.gameState = GameState.DIALOGUE;
                currentDialogue = GameConstants.COME_AGAIN;
            }
        }
    }

    /**
     * Handles the process of buying items in the trade screen.
     * Displays player and NPC inventories, item prices,
     * and handles player input for buying items.
     */
    private void tradeBuy() {
        drawInventory(gp.player, false);
        drawInventory(npc, true);

        // DRAW HIT WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString(GameConstants.BACK_OPTION, x+24, y+60);

        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString(GameConstants.YOUR_COINS + gp.player.coin, x+24, y+60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {

            x = (int) (gp.tileSize*5.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+10, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = GameConstants.BLANK + price;
            x = getXForAlignToRightText(text, gp.tileSize*8-20);
            g2.drawString(text, x, y+34);

            // Buy items
            if (gp.keyH.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = GameState.DIALOGUE;
                    currentDialogue = GameConstants.NOT_ENOUGH_MONEY;
                    drawDialogueScreen();
                }

                else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        subState = 0;
                        gp.gameState = GameState.DIALOGUE;
                        currentDialogue = GameConstants.NO_BAG_SPACE;
                    }
                }
            }
        }
    }

    /**
     * Handles the process of selling items in the trade screen.
     * Displays player inventory, item prices,
     * and handles player input for selling items.
     */
    private void tradeSell() {

        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);
        int x;
        int y;
        int width;
        int height;

        // DRAW HIT WINDOW
        x = gp.tileSize*2;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString(GameConstants.BACK_OPTION, x+24, y+60);

        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString(GameConstants.YOUR_COINS + gp.player.coin, x+24, y+60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {

            x = (int) (gp.tileSize*15.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+10, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = GameConstants.BLANK + price;
            x = getXForAlignToRightText(text, gp.tileSize*18-20);
            g2.drawString(text, x, y+34);

            // SELL ITEMS
            if (gp.keyH.enterPressed) {

                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentHelmet ) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = GameState.DIALOGUE;
                    currentDialogue = GameConstants.FORBIDE_MESSAGE;
                }

                else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) { gp.player.inventory.get(itemIndex).amount--; }
                    else { gp.player.inventory.remove(itemIndex); }
                    gp.player.coin += price;
                }
            }
        }
    }

    /**
     * Calculate the index of an item in the inventory based
     * on the given slot column and slot row.
     *
     * @param slotCol The column of the slot.
     * @param slotRow The row of the slot.
     * @return The index of the item in the inventory.
     */
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow*5);
    }

    /**
     * Draw a sub-window with rounded corners.
     *
     * @param x      The x-coordinate of the sub-window.
     * @param y      The y-coordinate of the sub-window.
     * @param width  The width of the sub-window.
     * @param height The height of the sub-window.
     */
    private void drawSubWindow(int x, int y, int width, int height) {

        g2.setColor(UIColors.BLACK);
        g2.fillRoundRect(x,y,width ,height,35, 35);
        g2.setColor(UIColors.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    /**
     * Calculate the x-coordinate for centering text horizontally on the screen.
     *
     * @param text The text to be centered.
     * @return The x-coordinate for centered text.
     */
    private int getXForCenteredText (String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    /**
     * Calculate the x-coordinate for aligning text to the right
     * relative to a specified tail x-coordinate.
     *
     * @param text  The text to be aligned to the right.
     * @param tailX The x-coordinate that serves as the reference point for alignment.
     * @return The x-coordinate for aligned text.
     */
    private int getXForAlignToRightText (String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
