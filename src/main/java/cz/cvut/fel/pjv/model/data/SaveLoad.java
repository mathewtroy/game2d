package cz.cvut.fel.pjv.model.data;

import cz.cvut.fel.pjv.model.object.*;
import cz.cvut.fel.pjv.model.weapon.Ram;
import cz.cvut.fel.pjv.model.weapon.Spike;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.io.*;
import java.util.Objects;
import java.util.logging.Logger;

public class SaveLoad {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_SAVE = "NO FILE TO SAVE";
    private static final String LOGGER_MESSAGE_LOAD = "NO FILE TO LOAD";


    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Retrieves an Entity object based on the provided item name
     *
     * @param itemName The name of the item for which to retrieve an Entity object
     * @return An Entity object corresponding to the provided item name.
     * Returns null if the item name is not recognized.
     *
     */
    public Entity getObject(String itemName){
        Entity obj = gp.nullEntity;

        switch (itemName){
            case"Normal Ram":obj = new Ram(gp); break;
            case"Red Potion":obj = new Potion_Red(gp); break;
            case"Blue Potion":obj = new Potion_Blue(gp); break;
            case"Boots":obj = new Boots(gp); break;
            case"Key":obj = new Key(gp); break;
            case"German Helmet":obj = new Helmet_Ger(gp); break;
            case"Helmet":obj = new Helmet(gp); break;
            case"Normal Spike":obj = new Spike(gp); break;
            case"Chest":obj = new Chest(gp); break;
            case"Door":obj = new Door(gp); break;
        }
        return obj;
    }

    /**
     * Saves the current game state to a file named "save.dat".
     */
    public void save() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            // Player stats
            ds.level = gp.player.getLevel();
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.getExp();
            ds.nextLevelExp = gp.player.getNextLevelExp();
            ds.coin = gp.player.coin;

            // Player Inventory
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).getName());
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }

            // Player equipment
            ds.currentHelmetSlot = gp.player.getCurrentHelmetSlot();
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();

            // Objects on map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean [gp.maxMap][gp.obj[1].length];

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                for(int i = 0; i < gp.obj[1].length; i++) {

                    if(gp.obj[mapNum][i] == gp.nullEntity){
                        ds.mapObjectNames[mapNum][i] = "NA";
                    }

                    else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].getName();
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;

                        if(gp.obj[mapNum][i].loot != gp.nullEntity) {
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.getName();
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            // Write the DBS object
            oos.writeObject(ds);

        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_SAVE);
        }
    }

    /**
     * Loads the game state from the "save.dat" file and updates the game accordingly.
     */
    public void load() {

        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // Read the DataStorage object
            DataStorage ds = (DataStorage)ois.readObject();

            // Player stats
            gp.player.setLevel(ds.level);
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.setExp(ds.exp);
            gp.player.setNextLevelExp(ds.nextLevelExp);
            gp.player.coin = ds.coin;

            // Player Inventory
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }

            // Player equipment
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentHelmet = gp.player.inventory.get(ds.currentHelmetSlot);

            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getPlayerAttackImage();

            // Objects on map
            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                for(int i = 0; i < gp.obj[1].length; i++){

                    if (ds.mapObjectNames[mapNum][i].equals("NA")){
                        gp.obj[mapNum][i] = gp.nullEntity;
                    }

                    else {
                        gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];

                        if (!Objects.equals(ds.mapObjectNames[mapNum][i], "")) {
                            gp.obj[mapNum][i].loot = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        }

                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];

                        if (gp.obj[mapNum][i].opened) {
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }
        }

        catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_LOAD);
        }
    }
}
