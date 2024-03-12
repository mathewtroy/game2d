package cz.cvut.fel.pjv.model.data;

import java.util.ArrayList;

public class LevelData {

    private ArrayList<Position> tanks = new ArrayList<>();
    private ArrayList<Position> ghosts = new ArrayList<>();
    private ArrayList<Position> coins = new ArrayList<>();
    private ArrayList<Position> keys = new ArrayList<>();
    private ArrayList<Position> rams = new ArrayList<>();
    private ArrayList<Position> helmets = new ArrayList<>();
    private ArrayList<Position> firstAids = new ArrayList<>();
    private ArrayList<Position> ammunitions = new ArrayList<>();
    private ArrayList<Position> hearts = new ArrayList<>();
    private ArrayList<Position> boots = new ArrayList<>();
    private ArrayList<Position> doors = new ArrayList<>();
    private ArrayList<Position> chests = new ArrayList<>();
    private ArrayList<Position> oldMans = new ArrayList<>();
    private ArrayList<Position> merchants = new ArrayList<>();
    private ArrayList<Position> dryTrees = new ArrayList<>();

    public LevelData() {
    }

    public ArrayList<Position> getTanks() {
        return tanks;
    }

    public void setTanks(ArrayList<Position> tanks) {
        this.tanks = tanks;
    }

    public ArrayList<Position> getGhosts() {
        return ghosts;
    }

    public void setGhosts(ArrayList<Position> ghosts) {
        this.ghosts = ghosts;
    }

    public ArrayList<Position> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Position> coins) {
        this.coins = coins;
    }

    public ArrayList<Position> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Position> keys) {
        this.keys = keys;
    }

    public ArrayList<Position> getRams() {
        return rams;
    }

    public void setRams(ArrayList<Position> rams) {
        this.rams = rams;
    }

    public ArrayList<Position> getHelmets() {
        return helmets;
    }

    public void setHelmets(ArrayList<Position> helmets) {
        this.helmets = helmets;
    }

    public ArrayList<Position> getFirstAids() {
        return firstAids;
    }

    public void setFirstAids(ArrayList<Position> firstAids) {
        this.firstAids = firstAids;
    }

    public ArrayList<Position> getAmmunitions() {
        return ammunitions;
    }

    public void setAmmunitions(ArrayList<Position> ammunitions) {
        this.ammunitions = ammunitions;
    }

    public ArrayList<Position> getHearts() {
        return hearts;
    }

    public void setHearts(ArrayList<Position> hearts) {
        this.hearts = hearts;
    }

    public ArrayList<Position> getBoots() {
        return boots;
    }

    public void setBoots(ArrayList<Position> boots) {
        this.boots = boots;
    }

    public ArrayList<Position> getDoors() {
        return doors;
    }

    public void setDoors(ArrayList<Position> doors) {
        this.doors = doors;
    }

    public ArrayList<Position> getChests() {
        return chests;
    }

    public void setChests(ArrayList<Position> chests) {
        this.chests = chests;
    }

    public ArrayList<Position> getOldMans() {
        return oldMans;
    }

    public void setOldMans(ArrayList<Position> oldMans) {
        this.oldMans = oldMans;
    }

    public ArrayList<Position> getMerchants() {
        return merchants;
    }

    public void setMerchants(ArrayList<Position> merchants) {
        this.merchants = merchants;
    }

    public ArrayList<Position> getDryTrees() {
        return dryTrees;
    }

    public void setDryTrees(ArrayList<Position> dryTrees) {
        this.dryTrees = dryTrees;
    }
}

