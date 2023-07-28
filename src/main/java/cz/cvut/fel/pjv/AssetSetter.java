package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.object.OBJ_Boots;
import cz.cvut.fel.pjv.object.OBJ_Chest;
import cz.cvut.fel.pjv.object.OBJ_Door;
import cz.cvut.fel.pjv.object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter (GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 24 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;


        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 24 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;


        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 36 * gp.tileSize;
        gp.obj[2].worldY = 10 * gp.tileSize;


        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 31 * gp.tileSize;
        gp.obj[3].worldY = 13 * gp.tileSize;


        gp.obj[4] = new OBJ_Chest();
        gp.obj[4].worldX = 35 * gp.tileSize;
        gp.obj[4].worldY = 8 * gp.tileSize;


        gp.obj[7] = new OBJ_Boots();
        gp.obj[7].worldX = 30 * gp.tileSize;
        gp.obj[7].worldY = 25 * gp.tileSize;

    }

}
