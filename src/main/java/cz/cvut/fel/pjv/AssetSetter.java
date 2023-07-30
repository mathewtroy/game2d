package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.NPC_OldMan;
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


    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;


    }

}
