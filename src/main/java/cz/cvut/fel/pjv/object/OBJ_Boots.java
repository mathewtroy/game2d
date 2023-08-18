package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;


public class OBJ_Boots extends Entity {

    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp) {

        super(gp);

        name = objName;
        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);

    }
}
