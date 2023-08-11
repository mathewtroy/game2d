package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;


public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {

        super(gp);

        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nAn old key.\nMade in China.";
        price = 10;

    }

}
