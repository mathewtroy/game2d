package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.view.GamePanel;

import static cz.cvut.fel.pjv.model.CollisionChecker.MAX_COST;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    /**
     * Sets the initial properties of the projectile.
     *
     * @param worldX    The world X-coordinate of the projectile.
     * @param worldY    The world Y-coordinate of the projectile.
     * @param direction The direction in which the projectile is moving.
     * @param alive     Whether the projectile is currently active.
     * @param user      The entity that fired the projectile.
     */
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;

    }

    /**
     * Updates the projectile's position and checks for collisions with enemies or the player.
     */
    public void update() {
        // Check collisions and apply damage if user is the player
        if (user == gp.player) {
            checkAndDamageEnemy();
        }

        // Check collisions and apply damage if user is not the player
        if (user != gp.player) {
            checkAndDamagePlayer();
        }

        // Update projectile's position based on its direction
        updateProjectilePosition();

        // Decrement the projectile's lifespan
        decrementLife();

        // Handle projectile animation
        handleAnimation();
    }

    /**
     * Checks for collisions with enemies and damages them if necessary.
     */
    private void checkAndDamageEnemy() {
        int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);

        if (enemyIndex != MAX_COST) {
            gp.player.damageEnemy(enemyIndex, attack, knockBackPower);
            generateParticle(user.projectile, gp.enemy[gp.currentMap][enemyIndex]);
            alive = false;
        }
    }

    /**
     * Checks for collisions with the player and damages them if necessary.
     */
    private void checkAndDamagePlayer() {
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (!gp.player.invisible && contactPlayer) {
            damagePlayer(attack);
            generateParticle(user.projectile, gp.player);
            alive = false;
        }
    }

    /**
     * Updates the projectile's position based on its direction.
     */
    private void updateProjectilePosition() {
        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
    }

    /**
     * Decrements the projectile's remaining lifespan.
     */
    private void decrementLife() {
        life--;

        if (life <= 0) {
            alive = false;
        }
    }

    /**
     * Handles the animation of the projectile.
     */
    private void handleAnimation() {
        spriteCounter++;

        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    /**
     * Checks if the user entity has the required resources to fire this projectile.
     *
     * @param user The entity that fired the projectile.
     * @return True if the user has the required resources, otherwise false.
     */
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        return haveResource;
    }

    /**
     * Subtracts the required resources from the user entity after firing the projectile.
     *
     * @param user The entity that fired the projectile.
     */
    public void subtractResource(Entity user) {
    }


}
