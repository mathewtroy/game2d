package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.enemy.Tank;
import cz.cvut.fel.pjv.model.entity.Merchant;
import cz.cvut.fel.pjv.model.entity.OldMan;
import cz.cvut.fel.pjv.model.enemy.Ghost;
import cz.cvut.fel.pjv.model.object.*;
import cz.cvut.fel.pjv.model.tile.DryTree;
import cz.cvut.fel.pjv.model.weapon.Ram;
import cz.cvut.fel.pjv.view.GamePanel;

/**
 * The AssetSetter is responsible for initializing game objects and NPCs on different maps
 *
 */
public class AssetSetter {

    GamePanel gp;

    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Initializes game objects on the current map
     */
    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new CoinGold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*24;
        gp.obj[mapNum][i].worldY = gp.tileSize*24; // center of the map, grass tile (txt row: 23, txt col: 14)
        i++;

        gp.obj[mapNum][i] = new Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*24;
        gp.obj[mapNum][i].worldY = gp.tileSize*8; // corner at the top of forest, grass tile (txt row: 9, txt col: 50)
        i++;

        gp.obj[mapNum][i] = new Ram(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*23; // corner at the top of forest, grass tile (txt row: 24, txt col: 48)
        i++;

        gp.obj[mapNum][i] = new HelmetGerman(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*17;
        gp.obj[mapNum][i].worldY = gp.tileSize*12; // fel area
        i++;

        // First Aid and Ammunition
        gp.obj[mapNum][i] = new FirstAid(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*30;
        gp.obj[mapNum][i].worldY = gp.tileSize*25; // corner at the top of forest, grass tile (txt row: 26, txt col: 60)
        i++;

        gp.obj[mapNum][i] = new FirstAid(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*39;
        gp.obj[mapNum][i].worldY = gp.tileSize*15; // old man area
        i++;

        gp.obj[mapNum][i] = new Ammunition(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*15;
        gp.obj[mapNum][i].worldY = gp.tileSize*41; // Island
        i++;

        // Heart
        gp.obj[mapNum][i] = new Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*38;
        gp.obj[mapNum][i].worldY = gp.tileSize*15; // hp old man
        i++;

        gp.obj[mapNum][i] = new Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*19;
        gp.obj[mapNum][i].worldY = gp.tileSize*37; // Island
        i++;

        // Boots
        gp.obj[mapNum][i] = new Boots(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*20;
        gp.obj[mapNum][i].worldY = gp.tileSize*37; // Island
        i++;

        // Door
        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*33;
        gp.obj[mapNum][i].worldY = gp.tileSize*13; // grass tile (txt row: 14, txt col: 68)
        i++;

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*36;
        gp.obj[mapNum][i].worldY = gp.tileSize*10; // grass tile (txt row: 11, txt col: 74)
        i++;

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*38;
        gp.obj[mapNum][i].worldY = gp.tileSize*13; // grass tile (txt row: 14, txt col: 76)
        i++;

        // Chest
        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*36;
        gp.obj[mapNum][i].worldY = gp.tileSize*7; // grass tile (txt row: 8, txt col: 74)
        i++;

        // Map PJV
        mapNum = 1;
        gp.obj[mapNum][i] = new Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*7; // below fel area
        i++;

        // Map Gold
        mapNum = 2;
        gp.obj[mapNum][i] = new CoinGold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new CoinGold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new CoinGold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*27;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new CoinGold(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        i++;
    }

    /**
     * Initializes NPCs on the current map
     */
    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        // MAP new
        gp.npc[mapNum][i] = new OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*29;
        gp.npc[mapNum][i].worldY = gp.tileSize*20; // grass tile (txt row: 21, txt col: 60)
        i++;

        // MAP PJV
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*12;
        gp.npc[mapNum][i].worldY = gp.tileSize*8; //
        i++;
    }

    /**
     * Initializes enemies on the current map
     */
    public void setEnemy() {
        int mapNum = 0;
        int i = 0;

        gp.enemy[mapNum][i] = new Tank(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*11;
        gp.enemy[mapNum][i].worldY = gp.tileSize*21;
        i++;

        gp.enemy[mapNum][i] = new Tank(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*21;
        gp.enemy[mapNum][i].worldY = gp.tileSize*21;  // grass tile (txt row: 22, txt col: 44)
        i++;

        gp.enemy[mapNum][i] = new Tank(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*11;
        gp.enemy[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.enemy[mapNum][i] = new Tank(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*21;
        gp.enemy[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.enemy[mapNum][i] = new Tank(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*31;
        gp.enemy[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.enemy[mapNum][i] = new Tank(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*33;
        gp.enemy[mapNum][i].worldY = gp.tileSize*24;
        i++;

        // Map 3
        mapNum = 2;

        // Ghost
        gp.enemy[mapNum][i] = new Ghost(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*28;
        gp.enemy[mapNum][i].worldY = gp.tileSize*26;
        i++;

        gp.enemy[mapNum][i] = new Ghost(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*20;
        gp.enemy[mapNum][i].worldY = gp.tileSize*20;
        i++;

        gp.enemy[mapNum][i] = new Ghost(gp);
        gp.enemy[mapNum][i].worldX = gp.tileSize*28;
        gp.enemy[mapNum][i].worldY = gp.tileSize*21;
        i++;
    }

    /**
     * Initializes interactive tiles on the current map
     */
    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;

        gp.iTile[mapNum][i] = new DryTree(gp, 27,24); i++;
        gp.iTile[mapNum][i] = new DryTree(gp,28,24); i++;
        gp.iTile[mapNum][i] = new DryTree(gp,29,24); i++;
        gp.iTile[mapNum][i] = new DryTree(gp,30,24); i++;
        gp.iTile[mapNum][i] = new DryTree(gp,31,24); i++;
        gp.iTile[mapNum][i] = new DryTree(gp,32,24); i++;

        gp.iTile[mapNum][i] = new DryTree(gp,28,28); i++; // teleport Island tree
        gp.iTile[mapNum][i] = new DryTree(gp,29,28); i++; // teleport Island tree
        gp.iTile[mapNum][i] = new DryTree(gp,23,28); i++; // teleport Island tree
        gp.iTile[mapNum][i] = new DryTree(gp,29,26); i++; // teleport Island tree

        gp.iTile[mapNum][i] = new DryTree(gp,19,42); i++; // teleport FEL tree
        gp.iTile[mapNum][i] = new DryTree(gp,23,10); i++; // key tree
    }
}
