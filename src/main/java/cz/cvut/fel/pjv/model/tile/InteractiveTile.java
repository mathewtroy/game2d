package cz.cvut.fel.pjv.model.tile;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.awt.*;

public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    /**
     * Checks if the provided entity is the correct item to interact with this tile.
     *
     * @param entity The entity to check.
     * @return True if the entity is the correct item for interaction, otherwise false.
     */
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    /**
     * Plays a sound effect associated with this interactive tile.
     */
    public void playSE() {

    }

    /**
     * Gets the destroyed form of this interactive tile.
     *
     * @return The destroyed form of the interactive tile.
     */
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    /**
     * Updates the state of the interactive tile.
     */
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
     * This method delete transparent while cutting tree.
     *
     * @param g2 The Graphics2D object used for drawing.
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
