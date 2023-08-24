package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Projectile;

import java.awt.*;
import java.util.logging.Logger;

public class OBJ_Rock extends Projectile {

    GamePanel gp;

    private static final Color PARTICLE_COLOR_OLIVE = new Color(40, 50, 0);
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_ROCK = "Missing image of the Rock";
    public static final String objName = "Rock";

    public OBJ_Rock (GamePanel gp) {
        super(gp);
        this.gp = gp;

        setName(objName);
        speed = 7;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;

        setupRockImage();
    }

    public void setupRockImage() {
        try {
            up1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            up2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            down1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            left1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            left2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            right1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            right2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_ROCK);
        }
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }

    public Color getParticleColor() {
        return PARTICLE_COLOR_OLIVE;
    }

    public int getParticleSize() {
        int size = 10;   // 6 pixels
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
