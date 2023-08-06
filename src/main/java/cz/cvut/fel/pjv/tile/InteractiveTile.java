package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

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

}
