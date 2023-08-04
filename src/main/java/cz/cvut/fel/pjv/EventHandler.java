package cz.cvut.fel.pjv;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;


    public EventHandler (GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 26;
            eventRect[col][row].y = 24;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;

            if (col == gp.maxWorldCol) {

                col = 0;
                row++;
            }

        }


    }

    public void checkEvent() {

        //  Check ifthe player character is more than
        //  one tile away from the last event

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {

            canTouchEvent = true;
        }

        if (canTouchEvent == true) {
            if (hit(29, 23, "right") == true) { damagePit(29,23,gp.dialogueState); }
            if (hit(27, 21, "any") == true) { damagePit(27,21,gp.dialogueState); }

            if (hit(29, 27, "up") == true) { teleport(29, 27, gp.dialogueState); }
            if (hit(25, 24, "up") == true) { healingPool(25, 24, gp.dialogueState);}
        }

    }

    public boolean hit (int col, int row, String reqDirection) {

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x =gp.player.solidAreaDefaultX;
        gp.player.solidArea.y =gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void teleport(int col, int row, int gameState) {

        gp.gameState = gameState;
        gp.ui.currentDialogue = "You use teleport to PJV!";
        gp.player.worldX = gp.tileSize*2;
        gp.player.worldY = gp.tileSize*45;
    }


    public void damagePit(int col, int row, int gameState) {

        gp.gameState = gameState;
        gp.playSE(6);
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
        //eventRect[col][row].eventDone = true;
        // one time damage Pit

        canTouchEvent = false;
    }

    public void healingPool (int col, int row, int gameState) {

        //System.out.println("Healing");

        if(gp.keyH.enterPressed == true) {

            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(3);
            gp.ui.currentDialogue = "You drink the VODKA\nYour life has been recovered";
            gp.player.life = gp.player.maxLife;
        }
        //gp.keyH.enterPressed = false;
    }

}
