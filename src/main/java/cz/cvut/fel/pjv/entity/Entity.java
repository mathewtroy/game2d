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

    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";


    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2,
            attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1,attackRight2, planet;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48,48);
    public Rectangle attackArea = new Rectangle(0,0, 0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String[] dialogues = new String[20];


    // STATE
    public int worldX, worldY;
    public String direction = DOWN;
    public int spriteNum = 1;
    private int dialogueIndex = 0;
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
    public boolean opened = false;

    // COUNTER
    protected int actionLockCounter = 0;
    protected int spriteCounter = 0;
    protected int invisibleCounter = 0;
    protected int shotAvailableCounter = 0;
    private int dyingCounter = 0;
    private int hpBarCounter = 0;
    private int knockBackCounter = 0;

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
    protected int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;

    // TYPE
    public int type;
    private final int type_player = 0;
    private final int type_npc = 1;
    protected final int type_monster = 2;
    protected final int type_sword = 3;
    protected final int type_axe = 4;
    protected final int type_shield = 5;
    protected final int type_consumable = 6;
    protected final int type_pickupOnly = 7;
    protected final int type_obstacle = 8;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
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

    /**
     * Resets various counters associated with the entity.
     */
    public void resetCounter() {
        resetActionLockCounter();
        resetSpriteCounter();
        resetInvisibleCounter();
        resetShotAvailableCounter();
        resetDyingCounter();
        resetHpBarCounter();
        resetKnockBackCounter();
    }

    /**
     * Resets the action lock counter.
     * When this counter is reset to 0, it signifies that the entity
     * is no longer locked from performing specific actions.
     */
    private void resetActionLockCounter() {
        actionLockCounter = 0;
    }

    /**
     * Resets the sprite counter used for managing sprite animations.
     * This counter keeps track of the entity's sprite animation frames.
     */
    private void resetSpriteCounter() {
        spriteCounter = 0;
    }

    /**
     * Resets the invisible counter used to manage the entity's invisibility state.
     * This counter keeps track of how long the entity remains invisible.
     */
    private void resetInvisibleCounter() {
        invisibleCounter = 0;
    }

    /**
     * Resets the shot available counter used for controlling the entity's shooting abilities.
     * This counter determines when the entity can perform a shot action.
     */
    private void resetShotAvailableCounter() {
        shotAvailableCounter = 0;
    }

    /**
     * Resets the dying counter used for managing the entity's dying animation.
     * This counter is incremented during the dying animation sequence.
     */
    private void resetDyingCounter() {
        dyingCounter = 0;
    }

    /**
     * Resets the health bar counter used for displaying the entity's health bar.
     * This counter controls how long the health bar is displayed on the screen.
     */
    private void resetHpBarCounter() {
        hpBarCounter = 0;
    }

    /**
     * Resets the knockback counter used for handling knockback motion of the entity.
     * This counter tracks how long the entity is affected by knockback.
     */
    private void resetKnockBackCounter() {
        knockBackCounter = 0;
    }


    public void setLoot(Entity loot) {

    }

    protected void setAction() {

    }

    protected void damageReaction() {

    }

    /**
     * Initiates a speaking action for the entity.
     */
    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case UP: direction = DOWN; break;
            case DOWN: direction = UP; break;
            case LEFT: direction = RIGHT; break;
            case RIGHT: direction = LEFT; break;
        }
    }

    protected void interact() {

    }

    protected boolean use (Entity entity) {
        return false;
    }

    public void checkDrop()  {

    }

    /**
     * Drops an item entity onto the game map.
     *
     * This method is used to drop an item represented by the 'droppedItem' parameter onto the game map.
     * It searches for an available slot in the current map's object array and assigns the item's position
     * to match the current entity's world coordinates.
     *
     * @param droppedItem The item entity to be dropped onto the game map.
     */
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

    /**
     * Generates particle effects based on a generator entity and a target entity.
     *
     * This method generates particle effects using the provided 'generator' entity as the source
     * and the 'target' entity as the point of origin for the particles. It creates four particles
     * with specified characteristics such as color, size, speed, and maximum life span, and adds
     * them to the game's particle list.
     *
     * @param generator The entity responsible for generating the particles.
     * @param target The entity that serves as the point of origin for the particles.
     */
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

    /**
     * Checks for collisions involving the entity.
     *
     * This method checks for collisions involving the entity and sets the 'collisionOn' flag
     * accordingly. It performs collision checks with tiles, objects, NPCs, monsters, interactive
     * tiles, and the player. If the entity is of type 'type_monster' and comes into contact with
     * the player, it inflicts damage on the player based on the entity's attack power.
     */
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

    /**
     * Updates the entity's state and behavior.
     */
    public void update() {
        if (knockBack) {
            handleKnockBack();
        } else {
            handleMovement();
        }

        updateSprite();
        handleInvisibility();
        handleShotAvailableCounter();
    }

    /**
     * Handles the knockback behavior of the entity.
     */
    private void handleKnockBack() {
        checkCollision();

        if (collisionOn) {
            resetKnockBack();
        } else {
            moveWithKnockBack();
        }

        incrementKnockBackCounter();

        if (knockBackCounter == KNOCKBACK_COUNTER_THRESHOLD) {
            resetKnockBack();
        }
    }

    /**
     * Resets the entity's knockback state.
     */
    private void resetKnockBack() {
        knockBackCounter = 0;
        knockBack = false;
        speed = defaultSpeed;
    }

    /**
     * Moves the entity while in a knockback state.
     */
    private void moveWithKnockBack() {
        switch (gp.player.direction) {
            case UP: worldY -= speed; break;
            case DOWN: worldY += speed; break;
            case LEFT: worldX -= speed; break;
            case RIGHT: worldX += speed; break;
        }
    }

    private void incrementKnockBackCounter() {
        knockBackCounter++;
    }

    /**
     * Handles the entity's regular movement.
     */
    private void handleMovement() {
        setAction();
        checkCollision();

        if (!collisionOn) {
            moveInDirection();
        }
    }

    /**
     * Moves the entity in its current direction.
     */
    private void moveInDirection() {
        switch (direction) {
            case UP: worldY -= speed; break;
            case DOWN: worldY += speed; break;
            case LEFT: worldX -= speed; break;
            case RIGHT: worldX += speed; break;
        }
    }

    /**
     * Updates the entity's sprite animation.
     */
    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > MAX_SPRITE_COUNTER) {
            switchSprite();
            spriteCounter = 0;
        }
    }

    /**
     * Switches the entity's sprite.
     */
    private void switchSprite() {
        if (spriteNum == 1) {
            spriteNum = 2;
        } else if (spriteNum == 2) {
            spriteNum = 1;
        }
    }

    /**
     * Handles the entity's invisibility state.
     */
    private void handleInvisibility() {
        if (invisible) {
            incrementInvisibleCounter();
            if (invisibleCounter > MAX_INVISIBLE_COUNTER) {
                invisible = false;
                resetInvisibleCounter();
            }
        }
    }

    private void incrementInvisibleCounter() {
        invisibleCounter++;
    }

    /**
     * Handles the shot available counter for this entity.
     * Increments the shot available counter if it's less than the maximum threshold.
     */
    private void handleShotAvailableCounter() {
        if (shotAvailableCounter < MAX_SHOT_AVAILABLE_COUNTER) {
            incrementShotAvailableCounter();
        }
    }

    private void incrementShotAvailableCounter() {
        shotAvailableCounter++;
    }

    /**
     * Inflicts damage on the player entity.
     * This method damages the player entity if it is not in an invisible state.
     *
     * @param attack The amount of damage to inflict on the player.
     */
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

    /**
     * Draws the entity on the graphics context.
     *
     * @param g2 The Graphics2D object on which to draw the entity.
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {

            switch (direction) {
                case UP:
                    if (spriteNum == 1) { image = up1; }
                    if (spriteNum == 2) { image = up2; }
                    break;

                case DOWN:
                    if (spriteNum == 1) { image = down1; }
                    if (spriteNum == 2) { image = down2; }
                    break;

                case LEFT:
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                    break;

                case RIGHT:
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

            if (dying) { dyingAnimation(g2); }
            g2.drawImage(image, screenX, screenY, null);
            changeAlpha(g2, 1f);
        }
    }

    /**
     * Performs a dying animation effect for the entity.
     * This method alternates the alpha (transparency) of the entity's rendering
     * to create a fading effect when the entity is dying.
     *
     * @param g2 The Graphics2D object on which to apply the dying animation.
     */
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

    /**
     * Changes the alpha (transparency) of the Graphics2D object.
     *
     * @param g2          The Graphics2D object on which to change the alpha.
     * @param alphaValue  The new alpha (transparency) value.
     */
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    /**
     * Loads and sets up an image from a specified file path, scaling it to the specified width and height.
     *
     * @param imagePath The path to the image file (excluding the file extension).
     * @param width The desired width of the scaled image.
     * @param height The desired height of the scaled image.
     * @return A BufferedImage containing the loaded and scaled image, or null if an error occurs.
     */
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

    /**
     * Searches for a path to a specified goal position represented by column and row coordinates.
     *
     * @param goalCol The column coordinate of the goal position.
     * @param goalRow The row coordinate of the goal position.
     */
    protected void searchPath(int goalCol, int goalRow) {
        int startCol = calculateStartCol();
        int startRow = calculateStartRow();

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            determineDirection(nextX, nextY, enLeftX, enRightX, enTopY, enBottomY);

            if (reachedGoal(gp.pFinder.pathList.get(0).col, gp.pFinder.pathList.get(0).row, goalCol, goalRow)) {
                onPath = false;
            }
        }
    }

    /**
     * Calculates the starting column for pathfinding.
     *
     * @return The starting column.
     */
    private int calculateStartCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }

    /**
     * Calculates the starting row for pathfinding.
     *
     * @return The starting row.
     */
    private int calculateStartRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }

    /**
     * Determines the direction based on the next X and Y positions and entity's position.
     *
     * @param nextX    The next X position.
     * @param nextY    The next Y position.
     * @param enLeftX  The left X position of the entity.
     * @param enRightX The right X position of the entity.
     * @param enTopY   The top Y position of the entity.
     * @param enBottomY The bottom Y position of the entity.
     */
    private void determineDirection(int nextX, int nextY, int enLeftX, int enRightX, int enTopY, int enBottomY) {
        // Determine the direction based on the positions
        if ((enTopY > nextY) && (enLeftX >= nextX) && (enRightX < nextX + gp.tileSize)) {
            direction = UP;
        } else if ((enTopY < nextY) && (enLeftX >= nextX) && (enRightX < nextX + gp.tileSize)) {
            direction = DOWN;
        } else if ((enTopY >= nextY) && (enBottomY < nextY + gp.tileSize)) {
            // Left or right
            if (enLeftX > nextX) {
                direction = LEFT;
            }
            if (enLeftX < nextX) {
                direction = RIGHT;
            }
        } else if ((enTopY > nextY) && (enLeftX > nextX)) {
            // Up or left
            direction = UP;
            checkCollision();
            if (collisionOn) {
                direction = "left";
            }
        } else if ((enTopY > nextY) && (enLeftX < nextX)) {
            // Up or right
            direction = UP;
            checkCollision();
            if (collisionOn) {
                direction = RIGHT;
            }
        } else if ((enTopY < nextY) && (enLeftX > nextX)) {
            // Down or left
            direction = DOWN;
            if (collisionOn) {
                direction = LEFT;
            }
        } else if ((enTopY < nextY) && (enLeftX < nextX)) {
            // Down or right
            direction = DOWN;
            if (collisionOn) {
                direction = RIGHT;
            }
        }
    }


    /**
     * Checks if the entity has reached the goal.
     *
     * @param nextCol  The next column.
     * @param nextRow  The next row.
     * @param goalCol  The goal column.
     * @param goalRow  The goal row.
     * @return True if the entity has reached the goal, otherwise false.
     */
    private boolean reachedGoal(int nextCol, int nextRow, int goalCol, int goalRow) {
        return (nextCol == goalCol) && (nextRow == goalRow);
    }

    /**
     * Determines the index of a detected target entity in the specified target array.
     *
     * @param user The entity detecting the target.
     * @param target The array of target entities to search in.
     * @param targetName The name of the target entity to search for.
     * @return The index of the detected target in the target array, or a constant representing no detection.
     */
    protected int getDetected(Entity user, Entity[][] target, String targetName) {

        int index = MAX_COST;

        // Check the surrounding
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case UP: nextWorldY = user.getTopY() - gp.player.speed; break;
            case DOWN: nextWorldY = user.getBottomY() + gp.player.speed; break;
            case LEFT: nextWorldX = user.getLeftX() - gp.player.speed; break;
            case RIGHT: nextWorldX = user.getRightX() + gp.player.speed; break;
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
