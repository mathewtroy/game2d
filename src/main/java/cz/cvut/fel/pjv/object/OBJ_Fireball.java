package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Projectile;

import java.awt.*;
import java.util.logging.Logger;

public class OBJ_Fireball extends Projectile {


    private static final Color PARTICLE_COLOR = new Color(240,50,0);
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_FIREBALL = "Missing image of the FIREBALL";
    public static final String objName = "Fireball";

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setName(objName);
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 8;
        knockBackPower = 5;
        useCost = 1;
        alive = false;
        setupFireballImage();
    }

    /**
     * Sets up the image for the fireball object.
     */
    public void setupFireballImage() {
        try {
            up1 = setup("/projectiles/fireball_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/projectiles/fireball_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("/projectiles/fireball_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/projectiles/fireball_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/projectiles/fireball_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/projectiles/fireball_left_2", gp.tileSize, gp.tileSize);
            right1 = setup("/projectiles/fireball_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/projectiles/fireball_right_2", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_FIREBALL);
        }
    }

    /**
     * Checks if the user has sufficient mana to use the fireball.
     *
     * @param user The entity using the fireball.
     * @return True if the user has enough mana, false otherwise.
     */
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    /**
     * Subtracts the mana cost of using the fireball from the user's mana.
     *
     * @param user The entity using the fireball.
     */
    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    /**
     * Gets the color of the fireball's particles.
     *
     * @return The color of the fireball's particles.
     */
    public Color getParticleColor() {
        return PARTICLE_COLOR;
    }

    /**
     * Gets the size of the fireball's particles.
     *
     * @return The size of the fireball's particles.
     */
    public int getParticleSize() {
        int size = 10;   // 6 pixels
        return size;
    }

    /**
     * Gets the speed of the fireball's particles.
     *
     * @return The speed of the fireball's particles.
     */
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    /**
     * Gets the maximum life of the fireball's particles.
     *
     * @return The maximum life of the fireball's particles.
     */
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
