package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.Entity;

import static cz.cvut.fel.pjv.Sound.*;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    // Map Constants
    public static final int MAP_NEW = 0;
    public static final int MAP_PJV = 1;
    public static final int MAP_GOLD = 2;

    private static final int MAP_NEW_COL = 19;
    private static final int MAP_NEW_ROW = 6;

    private static final int MAP_PJV_COL = 9;
    private static final int MAP_PJV_ROW = 15;

    private static final int MAP_PJV_TO_GOLD_COL = 28;
    private static final int MAP_PJV_TO_GOLD_ROW = 28;

    private static final int MAP_GOLD_COL = 9 ;
    private static final int MAP_GOLD_ROW = 15 ;

    // General Constants
    private static final String DIRECTION = "Any";

    // Merchant Constants
    private static final int MERCHANT_COL = 12;
    private static final int MERCHANT_ROW = 9;



    public EventHandler (GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 26;
            eventRect[map][col][row].y = 24;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    /**
     * Checks for in-game events and triggers corresponding actions when necessary
     */
    public void checkEvent() {

        //  Check if the player character is more than
        //  one tile away from the last event

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(MAP_NEW,29, 23, DIRECTION)) { damagePit(); }
            else if (hit(MAP_NEW,27, 21, DIRECTION)) { damagePit(); }

            else if (hit(MAP_NEW,29, 27, DIRECTION)) { teleportIsland(); }
            else if (hit(MAP_NEW,20, 42, DIRECTION)) { teleportFEL(); }

            else if (hit(MAP_NEW,25, 24, DIRECTION)) { healingPool();}

            /*
              teleport to new map
              Our map new.txt (txt row: 4, txt col: 40)
            */
            else if (hit(MAP_NEW,MAP_NEW_COL, MAP_NEW_ROW, DIRECTION)) {
                teleportMap( MAP_PJV, MAP_PJV_COL, MAP_PJV_ROW);
            }

            // teleport to pjv map
            else if (hit(MAP_PJV,MAP_PJV_COL, MAP_PJV_ROW, DIRECTION)) {
                teleportMap( MAP_NEW, MAP_NEW_COL, MAP_NEW_ROW);
            }

            // teleport to gold map
            else if (hit(MAP_PJV,MAP_PJV_TO_GOLD_COL, MAP_PJV_TO_GOLD_ROW, DIRECTION)) {
                teleportMap( MAP_GOLD, MAP_GOLD_COL, MAP_GOLD_ROW);
            }

            // back to pjv map from gold map
            else if (hit(MAP_GOLD,MAP_GOLD_COL, MAP_GOLD_ROW, DIRECTION)) {
                teleportMap( MAP_PJV, MAP_PJV_TO_GOLD_COL, MAP_PJV_TO_GOLD_ROW);
            }

            else if (hit(MAP_PJV,MERCHANT_COL, MERCHANT_ROW, DIRECTION)) {
                speak( gp.npc[1][0]);
            }


        }
    }

    /**
     * Determines whether an event has been triggered at the specified map coordinates and direction.
     *
     * @param map           The map where the event should be checked.
     * @param col           The column where the event should be checked.
     * @param row           The row where the event should be checked.
     * @param reqDirection  The required direction for the event, or "Any" if direction is not relevant.
     * @return True if an event has been triggered, false otherwise.
     *
     */
    private boolean hit (int map, int col, int row, String reqDirection) {

        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row])
                    && !eventRect[map][col][row].eventDone) {

                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals(DIRECTION)) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x =gp.player.solidAreaDefaultX;
            gp.player.solidArea.y =gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }


        return hit;
    }

    /**
     * Initiates the teleportation to the Island map
     */
    private void teleportIsland() {
        gp.gameState = GamePanel.GameState.DIALOGUE;
        gp.ui.currentDialogue = "You used teleport to Island!";
        gp.player.worldX = gp.tileSize*12;
        gp.player.worldY = gp.tileSize*42;
    }

    /**
     * Initiates the teleportation to the FEL map
     */
    private void teleportFEL() {
        gp.gameState = GamePanel.GameState.DIALOGUE;
        gp.ui.currentDialogue = "You used teleport to FEL!";
        gp.player.worldX = gp.tileSize*12;
        gp.player.worldY = gp.tileSize*12;
    }


    /**
     * Handles damage when the player character falls into a pit
     */
    private void damagePit() {
        gp.gameState = GamePanel.GameState.DIALOGUE;
        gp.playSE(SOUND_SIX);
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 2;
        // two time damage Pit

        canTouchEvent = false;
    }

    /**
     * Handles healing when the player character drinks from a healing pool
     * Save the game
     */
    private void healingPool() {

        if(gp.keyH.enterPressed) {
            gp.gameState = GamePanel.GameState.DIALOGUE;
            gp.player.attackCanceled = true;
            gp.playSE(SOUND_THREE);
            gp.ui.currentDialogue = "You drink the Russian VODKA\nLife and mana have been recovered\n"+
            "(The progress has been saved)";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();

        }
    }

    /**
     * Initiates the teleportation to a different map based
     * on the provided map, column, and row coordinates.
     *
     * @param map The map to teleport to.
     * @param col The column to teleport to.
     * @param row The row to teleport to.
     */
    private void teleportMap(int map, int col, int row) {
        gp.gameState = GamePanel.GameState.TRANSITION;

        tempMap = map;
        tempCol = col;
        tempRow = row;

        canTouchEvent = false;
        gp.playSE(SOUND_TWELVE);
    }

    /**
     * Initiates a conversation with an NPC or merchant entity.
     *
     * @param entity The entity representing the NPC or merchant.
     */
    private void speak(Entity entity) {

        if (gp.keyH.enterPressed) {
            gp.gameState = GamePanel.GameState.DIALOGUE;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
