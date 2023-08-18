package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

public class OBJ_Shield_Blue extends Entity {

    public static final String objName = "Blue Shield";

    public  OBJ_Shield_Blue (GamePanel gp) {
        super(gp);

        type = type_shield;
        name = objName;
        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 3;
        description = "[" + name + "]\nA new iron shield.";
        price = 10;
    }
}
