package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public  OBJ_Shield_Wood (GamePanel gp) {
        super(gp);

        name = "Shield Wood";
        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
    }
}
