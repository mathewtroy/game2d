package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;


public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        down1 = setup("/objects/boots");

    }
}
