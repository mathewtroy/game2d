package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Projectile;

import java.awt.*;

public class OBJ_Fireball extends Projectile {

    public static final String objName = "Fireball";

    GamePanel gp;

    private static final Color particleColor = new Color(240,50,0);


    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 5;
        knockBackPower = 5;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectiles/fireball_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectiles/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectiles/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/projectiles/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectiles/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectiles/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectiles/fireball_right_2", gp.tileSize, gp.tileSize);

    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {
        return particleColor;
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
