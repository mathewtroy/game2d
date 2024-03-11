package cz.cvut.fel.pjv.model.data;

import java.util.ArrayList;


public class LevelData {
    private ArrayList<Position> tanks = new ArrayList<>();
    private ArrayList<Position> ghosts = new ArrayList<>();
    private ArrayList<Position> helmets = new ArrayList<>();

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

    public ArrayList<Position> getHelmets() {
        return helmets;
    }

    public void setHelmets(ArrayList<Position> helmets) {
        this.helmets = helmets;
    }
}

