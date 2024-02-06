package cz.cvut.fel.pjv.model.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a data storage class for storing
 * player stats, inventory, and map object information.
 *
 * This class is serializable, allowing it to be easily saved
 * and loaded from external sources.
 */
public class DataStorage implements Serializable {

    // Player stats
    int level;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    // Player Inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();

    int currentWeaponSlot;
    int currentShieldSlot;

    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootNames;
    boolean[][] mapObjectOpened;
}


