package cz.cvut.fel.pjv.model.tile;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.awt.*;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.model.Sound.SOUND_TEN;

public class DryTree extends InteractiveTile{

    GamePanel gp;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_DRY_TREE = "Missing image of the DRY TREE";
    private static final Color PARTICLE_COLOR_BROWN = new Color(65,50,30);

    /**
     * Constructs a Dry Tree interactive tile.
     *
     * @param gp  The GamePanel in which this tile exists.
     * @param col The column position of the tile.
     * @param row The row position of the tile.
     */
    public DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        destructible = true;
        life = 3;

        setupDryTreeImage();
    }

    /**
     * Sets up the image for the Dry Tree.
     */
    private void setupDryTreeImage() {
        try {
            down1 = setup("/tiles/drytree", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_DRY_TREE);
        }
    }

    /**
     * Checks if the provided entity is the correct item (ram) to interact with this Dry Tree.
     *
     * @param entity The entity to check.
     * @return True if the entity is the correct item (ram) for interaction, otherwise false.
     */
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.currentWeapon.type == type_ram) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    /**
     * Plays a sound effect associated with interacting with this Dry Tree.
     */
    public void playSE() {
        gp.playSE(SOUND_TEN);
    }

    /**
     * Gets the destroyed form of this Dry Tree, which is an Trunk.
     *
     * @return The destroyed form of the Dry Tree.
     */
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }

    /**
     * Gets the color of particles generated when interacting with this Dry Tree.
     *
     * @return The color of particles.
     */
    public Color getParticleColor() {
        Color color = PARTICLE_COLOR_BROWN;
        return color;
    }

    /**
     * Gets the size of particles generated when interacting with this Dry Tree.
     *
     * @return The size of particles in pixels.
     */
    public int getParticleSize() {
        int size = 6;   // 6 pixels
        return size;
    }

    /**
     * Gets the speed of particles generated when interacting with this Dry Tree.
     *
     * @return The speed of particles.
     */
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    /**
     * Gets the maximum life of particles generated when interacting with this Dry Tree.
     *
     * @return The maximum life of particles.
     */
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }

}
