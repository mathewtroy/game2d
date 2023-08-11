package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.Entity;

import static cz.cvut.fel.pjv.Sound.SOUND_THREE;
import static cz.cvut.fel.pjv.Sound.SOUND_TWELVE;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    private static final int MAP_ZERO = 0 ;
    private static final int MAP_ONE = 1 ;




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
            if (hit(0,29, 23, "right")) { damagePit(gp.dialogueState); }
            else if (hit(0,27, 21, "any")) { damagePit(gp.dialogueState); }

            else if (hit(0,29, 27, "up")) { teleportPJV(gp.dialogueState); }
            else if (hit(0,44, 15, "up")) { teleportFEL(gp.dialogueState); }

            else if (hit(0,25, 24, "up")) { healingPool( gp.dialogueState);}

            /*
              teleport to new map
              Our map new.txt (txt row: 4, txt col: 40)
            */
            else if (hit(0,19, 3, "any")) { teleportHouse( 1, 12, 13);}
            else if (hit(1,12, 13, "any")) { teleportHouse( 0, 19, 3);}

            else if (hit(1,12, 9, "up")) { speak( gp.npc[1][0]);}


        }
    }

    public boolean hit (int map, int col, int row, String reqDirection) {

        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row])
                    && !eventRect[map][col][row].eventDone) {

                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
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

    public void teleportPJV(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You used teleport to PJV!";
        gp.player.worldX = gp.tileSize*2;
        gp.player.worldY = gp.tileSize*45;
    }


    public void teleportFEL(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You used teleport to FEL!";
        gp.player.worldX = gp.tileSize*8;
        gp.player.worldY = gp.tileSize*8;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
        //eventRect[col][row].eventDone = true;
        // one time damage Pit

        canTouchEvent = false;
    }

    public void healingPool (int gameState) {

        //System.out.println("Healing");

        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(SOUND_THREE);
            gp.ui.currentDialogue = "You drink the Russian VODKA\nYour life and mana have been recovered";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }
        //gp.keyH.enterPressed = false;
    }

    public void teleportHouse(int map, int col, int row) {
        gp.gameState = gp.transitionState;

        tempMap = map;
        tempCol = col;
        tempRow = row;

        canTouchEvent = false;
        gp.playSE(SOUND_TWELVE);
    }

    public void speak(Entity entity) {

        if (gp.keyH.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
