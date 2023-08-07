package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public void playSE() {

    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public void update() {
        if (invisible) {
            invisibleCounter++;

            if (invisibleCounter > 20) {
                invisible = false;
                invisibleCounter = 0;
            }
        }

    }

    /**
     * this method delete transparent while cutting tree
     * @param g2
     */
    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {

            g2.drawImage(down1, screenX, screenY, null);

        }
    }

}
