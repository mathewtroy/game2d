package cz.cvut.fel.pjv.model.weapon;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.model.entity.Projectile;

import java.awt.*;
import java.util.logging.Logger;

public class Bullet extends Projectile {


    private static final Color PARTICLE_COLOR = new Color(240,50,0);
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_BULLET = "Missing image of the F";
    public static final String objName = "Bullet";

    GamePanel gp;

    public Bullet(GamePanel gp) {
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
        setupBulletImage();
    }

    /**
     * Sets up the image for the bullet object.
     */
    public void setupBulletImage() {
        try {
            up1 = setup("/projectiles/bullet_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/projectiles/bullet_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("/projectiles/bullet_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/projectiles/bullet_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/projectiles/bullet_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/projectiles/bullet_left_2", gp.tileSize, gp.tileSize);
            right1 = setup("/projectiles/bullet_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/projectiles/bullet_right_2", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_BULLET);
        }
    }

    /**
     * Checks if the user has sufficient mana to use the bullet.
     *
     * @param user The entity using the bullet.
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
     * Subtracts the mana cost of using the bullet from the user's mana.
     *
     * @param user The entity using the bullet.
     */
    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    /**
     * Gets the color of the bullet's particles.
     *
     * @return The color of the bullet's particles.
     */
    public Color getParticleColor() {
        return PARTICLE_COLOR;
    }

    /**
     * Gets the size of the bullet's particles.
     *
     * @return The size of the bullet's particles.
     */
    public int getParticleSize() {
        int size = 10;   // 6 pixels
        return size;
    }

    /**
     * Gets the speed of the bullet's particles.
     *
     * @return The speed of the bullet's particles.
     */
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    /**
     * Gets the maximum life of the bullet's particles.
     *
     * @return The maximum life of the bullet's particles.
     */
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
