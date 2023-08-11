package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

public class OBJ_Axe extends Entity {

    public  OBJ_Axe (GamePanel gp) {
        super(gp);

        type = type_axe;
        name = "Normal Axe";
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 4;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA powerful axe.";
        price = 30;
    }
}
