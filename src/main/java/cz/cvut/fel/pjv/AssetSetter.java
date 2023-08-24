package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.NPC_Merchant;
import cz.cvut.fel.pjv.entity.NPC_OldMan;
import cz.cvut.fel.pjv.monster.MON_Ghost;
import cz.cvut.fel.pjv.monster.MON_Green;
import cz.cvut.fel.pjv.object.*;
import cz.cvut.fel.pjv.tile.IT_DryTree;

/**
 * The AssetSetter is responsible for initializing game objects and NPCs on different maps
 *
 */
public class AssetSetter {

    GamePanel gp;

    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*24;
        gp.obj[mapNum][i].worldY = gp.tileSize*24; // center of the map, grass tile (txt row: 23, txt col: 14)
        i++;


        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*24;
        gp.obj[mapNum][i].worldY = gp.tileSize*8; // corner at the top of forest, grass tile (txt row: 9, txt col: 50)
        i++;


        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*23; // corner at the top of forest, grass tile (txt row: 24, txt col: 48)
        i++;


        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*17;
        gp.obj[mapNum][i].worldY = gp.tileSize*12; // fel area
        i++;


        // Potions
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*30;
        gp.obj[mapNum][i].worldY = gp.tileSize*25; // corner at the top of forest, grass tile (txt row: 26, txt col: 60)
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*39;
        gp.obj[mapNum][i].worldY = gp.tileSize*15; // old man area
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*15;
        gp.obj[mapNum][i].worldY = gp.tileSize*41; // Island
        i++;


        // Heart
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*38;
        gp.obj[mapNum][i].worldY = gp.tileSize*15; // hp old man
        i++;

        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*19;
        gp.obj[mapNum][i].worldY = gp.tileSize*37; // Island
        i++;


        // Boots
        gp.obj[mapNum][i] = new OBJ_Boots(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*20;
        gp.obj[mapNum][i].worldY = gp.tileSize*37; // Island
        i++;


        //  DOOR
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*33;
        gp.obj[mapNum][i].worldY = gp.tileSize*13; // grass tile (txt row: 14, txt col: 68)
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*36;
        gp.obj[mapNum][i].worldY = gp.tileSize*10; // grass tile (txt row: 11, txt col: 74)
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*38;
        gp.obj[mapNum][i].worldY = gp.tileSize*13; // grass tile (txt row: 14, txt col: 76)
        i++;

        // CHEST
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*36;
        gp.obj[mapNum][i].worldY = gp.tileSize*7; // grass tile (txt row: 8, txt col: 74)
        i++;


        // Map PJV
        mapNum = 1;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*7; // below fel area
        i++;


        // Map Gold
        mapNum = 2;
        gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*27;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        i++;

    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        // MAP new
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*29;
        gp.npc[mapNum][i].worldY = gp.tileSize*20; // grass tile (txt row: 21, txt col: 60)
        i++;


        // MAP PJV
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*12;
        gp.npc[mapNum][i].worldY = gp.tileSize*8; //
        i++;



    }

    public void setMonster() {

        int mapNum = 0;
        int i = 0;

        gp.monster[mapNum][i] = new MON_Green(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*11;
        gp.monster[mapNum][i].worldY = gp.tileSize*21;
        i++;

        gp.monster[mapNum][i] = new MON_Green(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*21;
        gp.monster[mapNum][i].worldY = gp.tileSize*21;  // grass tile (txt row: 22, txt col: 44)
        i++;

        gp.monster[mapNum][i] = new MON_Green(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*11;
        gp.monster[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.monster[mapNum][i] = new MON_Green(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*21;
        gp.monster[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.monster[mapNum][i] = new MON_Green(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*31;
        gp.monster[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.monster[mapNum][i] = new MON_Green(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*33;
        gp.monster[mapNum][i].worldY = gp.tileSize*24;
        i++;


        // Map 3
        mapNum = 2;

        // Ghost
        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*28;
        gp.monster[mapNum][i].worldY = gp.tileSize*26;
        i++;

        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*20;
        gp.monster[mapNum][i].worldY = gp.tileSize*20;
        i++;

        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*28;
        gp.monster[mapNum][i].worldY = gp.tileSize*21;
        i++;

    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27,24); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,28,24); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,29,24); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,30,24); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,31,24); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,32,24); i++;

        gp.iTile[mapNum][i] = new IT_DryTree(gp,28,28); i++; // teleport Island tree
        gp.iTile[mapNum][i] = new IT_DryTree(gp,29,28); i++; // teleport Island tree
        gp.iTile[mapNum][i] = new IT_DryTree(gp,23,28); i++; // teleport Island tree
        gp.iTile[mapNum][i] = new IT_DryTree(gp,29,26); i++; // teleport Island tree

        gp.iTile[mapNum][i] = new IT_DryTree(gp,19,42); i++; // teleport FEL tree

        gp.iTile[mapNum][i] = new IT_DryTree(gp,23,10); i++; // key tree

    }
}
