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
        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].worldX = gp.tileSize*33;
        gp.obj[0].worldY = gp.tileSize*13;


        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = gp.tileSize*36;
        gp.obj[1].worldY = gp.tileSize*10;


    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*11;
        gp.npc[0].worldY = gp.tileSize*11;


        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = gp.tileSize*21;
        gp.npc[1].worldY = gp.tileSize*21;


        gp.npc[2] = new NPC_OldMan(gp);
        gp.npc[2].worldX = gp.tileSize*31;
        gp.npc[2].worldY = gp.tileSize*31;


        gp.npc[3] = new NPC_OldMan(gp);
        gp.npc[3].worldX = gp.tileSize*11;
        gp.npc[3].worldY = gp.tileSize*21;


        gp.npc[4] = new NPC_OldMan(gp);
        gp.npc[4].worldX = gp.tileSize*11;
        gp.npc[4].worldY = gp.tileSize*31;
    }

}
