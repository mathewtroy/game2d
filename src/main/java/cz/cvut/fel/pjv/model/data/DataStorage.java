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
    int currentHelmetSlot;
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootNames;
    boolean[][] mapObjectOpened;

    public DataStorage() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    public void setItemNames(ArrayList<String> itemNames) {
        this.itemNames = itemNames;
    }

    public ArrayList<Integer> getItemAmounts() {
        return itemAmounts;
    }

    public void setItemAmounts(ArrayList<Integer> itemAmounts) {
        this.itemAmounts = itemAmounts;
    }

    public int getCurrentWeaponSlot() {
        return currentWeaponSlot;
    }

    public void setCurrentWeaponSlot(int currentWeaponSlot) {
        this.currentWeaponSlot = currentWeaponSlot;
    }

    public int getCurrentHelmetSlot() {
        return currentHelmetSlot;
    }

    public void setCurrentHelmetSlot(int currentHelmetSlot) {
        this.currentHelmetSlot = currentHelmetSlot;
    }

    public String[][] getMapObjectNames() {
        return mapObjectNames;
    }

    public void setMapObjectNames(String[][] mapObjectNames) {
        this.mapObjectNames = mapObjectNames;
    }

    public int[][] getMapObjectWorldX() {
        return mapObjectWorldX;
    }

    public void setMapObjectWorldX(int[][] mapObjectWorldX) {
        this.mapObjectWorldX = mapObjectWorldX;
    }

    public int[][] getMapObjectWorldY() {
        return mapObjectWorldY;
    }

    public void setMapObjectWorldY(int[][] mapObjectWorldY) {
        this.mapObjectWorldY = mapObjectWorldY;
    }

    public String[][] getMapObjectLootNames() {
        return mapObjectLootNames;
    }

    public void setMapObjectLootNames(String[][] mapObjectLootNames) {
        this.mapObjectLootNames = mapObjectLootNames;
    }

    public boolean[][] getMapObjectOpened() {
        return mapObjectOpened;
    }

    public void setMapObjectOpened(boolean[][] mapObjectOpened) {
        this.mapObjectOpened = mapObjectOpened;
    }
}


