package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static cz.cvut.fel.pjv.CollisionChecker.MAX_COST;
import static cz.cvut.fel.pjv.Sound.SOUND_SIX;

public class Entity {

    GamePanel gp;

    private static final Color HP_BAR_BACKGROUND_COLOR = new Color(35, 35, 35);
    private static final Color HP_BAR_COLOR = new Color(255, 0, 30);

    private static final int MAX_INVISIBLE_COUNTER = 40;
    private static final int MAX_SHOT_AVAILABLE_COUNTER = 30;
    private static final int MAX_SPRITE_COUNTER = 20;
    private static final int KNOCKBACK_COUNTER_THRESHOLD = 10;


    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2,
            attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1,attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48,48);
    public Rectangle attackArea = new Rectangle(0,0, 0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String[] dialogues = new String[20];


    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean collision = false;
    public boolean invisible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public Entity loot;
    public boolean opened =false;

    // COUNTER
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int invisibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;

    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;

    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int durability = 100;

    // TYPE
    public int type;    // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;



    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    private int getLeftX() {
        return worldX + solidArea.x;
    }

    private int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }

    private int getTopY() {
        return worldY + solidArea.y;
    }

    private int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    protected int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }

    protected int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }

    public void resetCounter() {
        actionLockCounter = 0;
        spriteCounter = 0;
        invisibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
    }

    public void setLoot(Entity loot) {

    }

    protected void setAction() {

    }

    protected void damageReaction() {

    }

    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up": direction ="down"; break;
            case "down": direction ="up"; break;
            case "left": direction ="right"; break;
            case "right": direction ="left"; break;
        }
    }

    protected void interact() {

    }

    protected boolean use (Entity entity) {
        return false;
    }

    public void checkDrop()  {

    }

    protected void dropItem (Entity droppedItem) {
        for (int i = 0; i < gp.obj.length; i++ ) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;  // dead monster's worldX
                gp.obj[gp.currentMap][i].worldY = worldY;  // dead monster's worldY
                break;
            }
        }
    }

    protected Color getParticleColor() {
        Color color = null;
        return color;
    }

    protected int getParticleSize() {
        int size = 0;   // 6 pixels
        return size;
    }

    protected int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    protected int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    protected void generateParticle(Entity generator, Entity target) {

        Color color = generator.getParticleColor();

        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);

        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);

    }

    private void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }
    }

    public void update() {

        if (knockBack) {
            checkCollision();

            if (collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }

            else if (!collisionOn) {
                switch (gp.player.direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            knockBackCounter++;

            if(knockBackCounter == KNOCKBACK_COUNTER_THRESHOLD) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }

        else {
            setAction();

            checkCollision();


            //  IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

        }



        spriteCounter++;
        if (spriteCounter > MAX_SPRITE_COUNTER) {
            if (spriteNum == 1) { spriteNum = 2; }
            else if (spriteNum == 2) { spriteNum = 1; }

            spriteCounter = 0;
        }

        if (invisible) {
            invisibleCounter++;

            if (invisibleCounter > MAX_INVISIBLE_COUNTER) {
                invisible = false;
                invisibleCounter = 0;
            }
        }
        if (shotAvailableCounter < MAX_SHOT_AVAILABLE_COUNTER) {
            shotAvailableCounter ++;
        }

    }

    protected void damagePlayer(int attack) {
        if (!gp.player.invisible) {
            // we can give damage
            gp.playSE(SOUND_SIX);

            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invisible  = true;
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;


        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) { image = up1; }
                    if (spriteNum == 2) { image = up2; }
                    break;

                case "down":
                    if (spriteNum == 1) { image = down1; }
                    if (spriteNum == 2) { image = down2; }
                    break;

                case "left":
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                    break;

                case "right":
                    if (spriteNum == 1) { image = right1; }
                    if (spriteNum == 2) { image = right2; }
                    break;
            }

            // Monster Health Bar
            if (type == type_monster && hpBarOn) {

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(HP_BAR_BACKGROUND_COLOR);
                g2.fillRect(screenX-1, screenY - 16 , gp.tileSize+2, 12);

                g2.setColor(HP_BAR_COLOR);
                g2.fillRect(screenX, screenY - 15 , (int)hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            if (invisible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }

            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, null);

            changeAlpha(g2, 1f);

        }
    }

    private void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) { changeAlpha(g2, 0f); }
        if (dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 1f); }
        if (dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 0f); }
        if (dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 1f); }
        if (dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 0f); }
        if (dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 1f); }
        if (dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 0f); }
        if (dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 1f); }
        if (dyingCounter > i*8) {
            alive = false;
        }

    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    protected void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {

            // Next worldX, worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if ((enTopY > nextY) && (enLeftX >= nextX) &&
                (enRightX < nextX + gp.tileSize)) {
                direction = "up";
            }

            else if ((enTopY < nextY) && (enLeftX >= nextX) &&
                    (enRightX < nextX + gp.tileSize)) {
                direction = "down";
            }

            else if ((enTopY >= nextY) && (enBottomY < nextY + gp.tileSize)) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            }

            else if ((enTopY > nextY) && (enLeftX > nextX)) {
                //up or left
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            }

            else if ((enTopY > nextY) && (enLeftX < nextX)) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }

            else if ((enTopY < nextY) && (enLeftX > nextX)) {
                // down or left
                direction = "down";
                if (collisionOn == true) {
                    direction = "left";
                }
            }

            else if ((enTopY < nextY) && (enLeftX < nextX)) {
                // down or right
                direction = "down";
                if (collisionOn == true) {
                    direction = "right";
                }
            }

            // if reaches the goal
            int nextCol = gp.pFinder.pathList.get(0).col ;
            int nextRow = gp.pFinder.pathList.get(0).row ;

            if ((nextCol == goalCol) && (nextRow == goalRow)) {
                onPath = false;
            }
        }
    }

    protected int getDetected(Entity user, Entity[][] target, String targetName) {

        int index = MAX_COST;

        // Check the surrounding
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {

            case "up": nextWorldY = user.getTopY() - gp.player.speed; break;
            case "down": nextWorldY = user.getBottomY() + gp.player.speed; break;
            case "left": nextWorldX = user.getLeftX() - gp.player.speed; break;
            case "right": nextWorldX = user.getRightX() + gp.player.speed; break;
        }

        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
