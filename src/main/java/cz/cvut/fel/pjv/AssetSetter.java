package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.NPC_Merchant;
import cz.cvut.fel.pjv.entity.NPC_OldMan;
import cz.cvut.fel.pjv.monster.MON_GreenSlime;
import cz.cvut.fel.pjv.object.*;
import cz.cvut.fel.pjv.tile.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*24;
        gp.obj[mapNum][i].worldY = gp.tileSize*24; // center of the map, grass tile (txt row: 23, txt col: 14)
        i++;


        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*6;
        gp.obj[mapNum][i].worldY = gp.tileSize*22; // center of the map, grass tile (txt row: 23, txt col: 14)
        i++;

        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*46;
        gp.obj[mapNum][i].worldY = gp.tileSize*19; // east part of the map, grass tile (txt row: 20, txt col: 94)
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
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*25; // corner at the top of forest, grass tile (txt row: 26, txt col: 56)
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*30;
        gp.obj[mapNum][i].worldY = gp.tileSize*25; // corner at the top of forest, grass tile (txt row: 26, txt col: 60)
        i++;

        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*43;
        gp.obj[mapNum][i].worldY = gp.tileSize*19; // corner at the top of forest, grass tile (txt row: , txt col: )
        i++;


        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*15; // corner at the top of forest, grass tile (txt row: 26, txt col: 60)
        i++;


        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*43;
        gp.obj[mapNum][i].worldY = gp.tileSize*18; // corner at the top of forest, grass tile (txt row: 26, txt col: 60)
        i++;

//        gp.obj[i] = new OBJ_ManaCrystal(gp);
//        gp.obj[i].worldX = gp.tileSize*32;
//        gp.obj[i].worldY = gp.tileSize*24; // corner at the top of forest, grass tile (txt row: 25, txt col: 56)
//        i++;

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


        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*36;
        gp.obj[mapNum][i].worldY = gp.tileSize*7; // grass tile (txt row: 8, txt col: 74)
        i++;


    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        // MAP new.txt
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*29;
        gp.npc[mapNum][i].worldY = gp.tileSize*20; // grass tile (txt row: 21, txt col: 60)
        i++;


        // MAP interior.txt
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

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*11;
        gp.monster[mapNum][i].worldY = gp.tileSize*21;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*21;
        gp.monster[mapNum][i].worldY = gp.tileSize*21;  // grass tile (txt row: 22, txt col: 44)
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*11;
        gp.monster[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*21;
        gp.monster[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*31;
        gp.monster[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*41;
        gp.monster[mapNum][i].worldY = gp.tileSize*31;
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

//        gp.iTile[i] = new IT_DryTree(gp,29,21); i++;
//        gp.iTile[i] = new IT_DryTree(gp,29,19); i++;
//        gp.iTile[i] = new IT_DryTree(gp,30,21); i++;
//        gp.iTile[i] = new IT_DryTree(gp,30,19); i++;
//        gp.iTile[i] = new IT_DryTree(gp,30,20); i++;


    }
}
