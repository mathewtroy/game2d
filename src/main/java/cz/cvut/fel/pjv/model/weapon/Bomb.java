package cz.cvut.fel.pjv.model.weapon;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.model.entity.Projectile;

import java.awt.*;
import java.util.logging.Logger;

public class Bomb extends Projectile {

    GamePanel gp;

    private static final Color PARTICLE_COLOR_OLIVE = new Color(40, 50, 0);
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_BOMB = "Missing image of the Bomb";
    private static final String BOMB_PATH = "/projectiles/bomb_picture";
    public static final String objName = "Bomb";

    public Bomb(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setName(objName);
        speed = 7;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        setupBombImage();
    }

    /**
     * Sets up the image for the bomb object.
     */
    public void setupBombImage() {
        try {
            up1 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
            up2 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
            down1 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
            down2 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
            left1 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
            left2 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
            right1 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
            right2 = setup(BOMB_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_BOMB);
        }
    }

    /**
     * Checks if the user has enough resources (ammo) to use this bomb.
     *
     * @param user The entity (usually the player) using the bomb.
     * @return True if the user has enough resources, false otherwise.
     */
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    /**
     * Subtracts the resource cost (ammo) when using this bomb.
     *
     * @param user The entity (usually the player) using the bomb.
     */
    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }

    /**
     * Gets the color of particles emitted when this bomb hits a target.
     *
     * @return The color of the particles.
     */
    public Color getParticleColor() {
        return PARTICLE_COLOR_OLIVE;
    }

    /**
     * Gets the size of particles emitted when this bomb hits a target.
     *
     * @return The size of the particles.
     */
    public int getParticleSize() {
        int size = 10;   // 6 pixels
        return size;
    }

    /**
     * Gets the speed of particles emitted when this bomb hits a target.
     *
     * @return The speed of the particles.
     */
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    /**
     * Gets the maximum life of particles emitted when this bomb hits a target.
     *
     * @return The maximum life of the particles.
     */
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
