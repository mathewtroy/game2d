package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.view.GamePanel;

import java.awt.*;

public class Particle extends Entity {

    Entity generator;
    Color color;
    private int size;
    private int xd;
    private int yd;

    /**
     * Creates a new Particle instance.
     *
     * @param gp       The GamePanel where the particle exists.
     * @param generator The entity generating this particle.
     * @param color    The color of the particle.
     * @param size     The size of the particle.
     * @param speed    The speed at which the particle moves.
     * @param maxLife  The maximum lifespan of the particle.
     * @param xd       The horizontal movement direction.
     * @param yd       The vertical movement direction.
     */
    public Particle(GamePanel gp, Entity generator, Color color, int size,
                    int speed, int maxLife, int xd, int yd) {
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;
        life = maxLife;
        int offset = (gp.tileSize/2) - (size/2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    /**
     * Updates the particle's position and lifespan.
     */
    public void update() {
        life--;
        if (life < maxLife/3) { yd++; }
        worldX += xd*speed;
        worldY += yd*speed;
        if (life == 0) { alive = false; }
    }

    /**
     * Draws the particle on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
}
