package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.awt.*;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_TEN;

public class IT_DryTree extends InteractiveTile{

    GamePanel gp;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_DRY_TREE = "Missing image of the DRY TREE";
    private static final Color PARTICLE_COLOR_BROWN = new Color(65,50,30);


    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        destructible = true;
        life = 3;

        setupDryTreeImage();
    }

    private void setupDryTreeImage() {
        try {
            down1 = setup("/tiles/drytree", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_DRY_TREE);
        }
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE() {
        gp.playSE(SOUND_TEN);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }

    public Color getParticleColor() {
        Color color = PARTICLE_COLOR_BROWN;
        return color;
    }

    public int getParticleSize() {
        int size = 6;   // 6 pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }

}
