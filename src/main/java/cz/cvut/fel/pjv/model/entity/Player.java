package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.model.object.*;
import cz.cvut.fel.pjv.model.weapon.Bullet;
import cz.cvut.fel.pjv.model.weapon.Spike;
import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.controller.KeyHandler;
import cz.cvut.fel.pjv.view.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.model.Sound.*;

public class Player extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_PLAYER = "Missing image of the HERO";
    private static final String LOGGER_MESSAGE_PLAYER_SPIKE = "Missing image of the ATTACKING HERO WITH SPIKE";
    private static final String LOGGER_MESSAGE_PLAYER_RAM = "Missing image of the ATTACKING HERO WITH RAM";

    // CONSTANTS
    private static final int SPRITE_COUNTER_THRESHOLD = 10;
    private static final int SHOT_AVAILABLE_COUNTER_THRESHOLD = 30;
    private static final int INVISIBLE_COUNTER_THRESHOLD = 60;
    private static final int ATTACK_DURATION = 25;
    private static final int KNOCK_BACK_POWER = 10;

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;
    public boolean attackCanceled = false;

    /**
     * Constructs a new Player object.
     *
     * @param gp    The GamePanel instance.
     * @param keyH  The KeyHandler instance for handling player input.
     */
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        // SOLID AREA
        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 14;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;
        setDefaultValues();

    }

    /**
     * Sets default values and initializes the player.
     */
    public void setDefaultValues () {

        // default start position
        worldX = gp.tileSize * 25;  // original new2.txt
        worldY = gp.tileSize * 25;


        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 12;
        life = maxLife;
        maxMana = 12;
        mana = maxMana;
        ammo = 10;
        strength = 1;   // more strength -> more damage he gives
        dexterity = 1;  // more dexterity -> less damage he receives
        exp = 0;
        nextLevelExp = 0;
        coin = 150;
        currentWeapon = new Spike(gp);
        currentHelmet = new Helmet(gp);
        projectile = new Bullet(gp);
        attack = getAttack();
        defense = getDefense();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();

    }

    /**
     * Sets the default positions of the player.
     */
    public void setDefaultPositions() {
        // default start position
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;

        direction = "down";

    }

    /**
     * Restores the player's status, including life, mana, and visibility.
     */
    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invisible = false;
        attacking = false;
        knockBack = false;

    }

    /**
     * Initializes the player's inventory with default items.
     */
    private void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentHelmet);
        inventory.add(new Potion_Red(gp));
        inventory.add(new Potion_Blue(gp));


    }

    /**
     * Calculates and returns the player's attack value based on equipped weapon and strength.
     *
     * @return The player's attack value.
     */
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    /**
     * Calculates and returns the player's defense value based on equipped helmet and dexterity.
     *
     * @return The player's defense value.
     */
    public int getDefense() {
        return defense = dexterity * currentHelmet.defenseValue;
    }

    /**
     * Retrieves the index of the current weapon in the player's inventory.
     *
     * @return The index of the current weapon.
     */
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    /**
     * Retrieves the index of the current helmet in the player's inventory.
     *
     * @return The index of the current helmet.
     */
    public int getCurrentHelmetSlot() {
        int currentHelmetSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentHelmet) {
                currentHelmetSlot = i;
            }
        }
        return currentHelmetSlot;
    }








    /**
     * Loads player images for different directions
     */


    private void getPlayerImage() {

        up1 = setup("/player/hero_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/hero_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/hero_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/hero_down_2", gp.tileSize, gp.tileSize);

        right1 = setup("/player/hero_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/hero_right_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/hero_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/hero_left_2", gp.tileSize, gp.tileSize);
    }

    /**
     * Loads player attack images based on the equipped weapon.
     */
    public void getPlayerAttackImage() {

        try {
            getPlayerImage();

        } catch (Exception e){
            logger.severe(LOGGER_MESSAGE_PLAYER);
        }

        // SWITCH TYPE OF WEAPON
        if (currentWeapon.type == type_spike) {

            try {
                attackUp1 = setup("/player/hero_attack_up_1", gp.tileSize, gp.tileSize*2);
                attackUp2 = setup("/player/hero_attack_up_2", gp.tileSize, gp.tileSize*2);
                attackDown1 = setup("/player/hero_attack_down_1", gp.tileSize, gp.tileSize*2);
                attackDown2 = setup("/player/hero_attack_down_2", gp.tileSize, gp.tileSize*2);
                attackLeft1 = setup("/player/hero_attack_left_1", gp.tileSize*2, gp.tileSize);
                attackLeft2 = setup("/player/hero_attack_left_2", gp.tileSize*2, gp.tileSize);
                attackRight1 = setup("/player/hero_attack_right_1", gp.tileSize*2, gp.tileSize);
                attackRight2 = setup("/player/hero_attack_right_2", gp.tileSize*2, gp.tileSize);
            } catch (Exception e){
                logger.warning(LOGGER_MESSAGE_PLAYER_SPIKE);
            }

        }

        if (currentWeapon.type == type_ram) {
            try {
                attackUp1 = setup("/player/hero_ram_up_1", gp.tileSize, gp.tileSize*2);
                attackUp2 = setup("/player/hero_ram_up_2", gp.tileSize, gp.tileSize*2);
                attackDown1 = setup("/player/hero_ram_down_1", gp.tileSize, gp.tileSize*2);
                attackDown2 = setup("/player/hero_ram_down_2", gp.tileSize, gp.tileSize*2);
                attackLeft1 = setup("/player/hero_ram_left_1", gp.tileSize*2, gp.tileSize);
                attackLeft2 = setup("/player/hero_ram_left_2", gp.tileSize*2, gp.tileSize);
                attackRight1 = setup("/player/hero_ram_right_1", gp.tileSize*2, gp.tileSize);
                attackRight2 = setup("/player/hero_ram_right_2", gp.tileSize*2, gp.tileSize);

            } catch (Exception e){
                logger.warning(LOGGER_MESSAGE_PLAYER_RAM);
            }
        }

    }






    /**
     * Updates the player's position and performs various game interactions.
     */
    public void update() {
        if (attacking) {
            attacking();
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            handleMovementAndInteractions();
        } else {
            handleStandingState();
        }

        handleProjectile();
        handleInvisibility();
        handleLifeAndMana();
    }

    /**
     * Handles the player's movement and various interactions when a movement key or enter key is pressed.
     */
    private void handleMovementAndInteractions() {
        if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);

        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
        contactEnemy(enemyIndex);

        int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

        gp.eHandler.checkEvent();
        gp.keyH.enterPressed = false;

        if (!collisionOn && !keyH.enterPressed) {
            handlePlayerMovement();
        }

        if (keyH.enterPressed && !attackCanceled) {
            startAttack();
        }

        attackCanceled = false;
        gp.keyH.enterPressed = false;

        updateSpriteCounter();
    }

    /**
     * Handles the player's movement based on the current direction.
     */
    private void handlePlayerMovement() {
        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }
    }

    /**
     * Starts the player's attack animation.
     */
    private void startAttack() {
        gp.playSE(GameConstants.SOUND_FIVE);
        attacking = true;
        spriteCounter = 0;
    }

    /**
     * Updates the sprite counter and switches between player sprites.
     */
    private void updateSpriteCounter() {
        spriteCounter++;
        if (spriteCounter > SPRITE_COUNTER_THRESHOLD) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    /**
     * Handles the player's standing state and sprite updates.
     */
    private void handleStandingState() {
        standCounter++;
        if (standCounter == 20) {
            spriteNum = 1;
            standCounter = 0;
        }
    }

    /**
     * Handles the player's projectile attack.
     */
    private void handleProjectile() {
        if (gp.keyH.shotKeyPressed && !projectile.alive
                && (shotAvailableCounter == SHOT_AVAILABLE_COUNTER_THRESHOLD)
                && projectile.haveResource(this)) {

            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractResource(this);

            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
            gp.playSE(GameConstants.SOUND_NINE);
        }

        if (shotAvailableCounter < SHOT_AVAILABLE_COUNTER_THRESHOLD) {
            shotAvailableCounter++;
        }
    }

    /**
     * Handles the player's invisibility state.
     */
    private void handleInvisibility() {
        if (invisible) {
            invisibleCounter++;

            if (invisibleCounter > INVISIBLE_COUNTER_THRESHOLD) {
                invisible = false;
                invisibleCounter = 0;
            }
        }
    }

    /**
     * Handles the player's life and mana attributes.
     */
    private void handleLifeAndMana() {
        if (life > maxLife) {
            life = maxLife;
        }

        if (mana > maxMana) {
            mana = maxMana;
        }

        if (life <= 0) {
            handlePlayerDeath();
        }
    }

    /**
     * Handles the player's death state.
     */
    private void handlePlayerDeath() {
        gp.gameState = GameState.GAME_OVER;
        gp.ui.commandNum = -1;
        gp.stopMusic();
        gp.playSE(GameConstants.SOUND_ELEVEN);
    }


    /**
     * Handles player attacks, including animations and collision checks.
     */
    public void attacking() {

        spriteCounter++;

        if (spriteCounter <=5) { spriteNum = 1; }

        if (spriteCounter > 5 && spriteCounter <= ATTACK_DURATION) {
            spriteNum = 2;

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX, worldY for the attackArea

            switch(direction) {

                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.height; break;
                case "right": worldX += attackArea.height; break;
            }

            // attackArea becomes solid
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check enemy collision with updated worldX, worldY and solidArea
            int enemyIndex  = gp.cChecker.checkEntity(this, gp.enemy);
            damageEnemy(enemyIndex, attack, currentWeapon.knockBackPower);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            // After checking collision restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }

        if (spriteCounter > ATTACK_DURATION) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    /**
     * Handles picking up objects and interacting with them.
     *
     * @param i The index of the object in the game world.
     */
    public void pickUpObject (int i) {
        if (i != GameConstants.MAX_COST) {

            // PICKUP ONLY ITEMS
            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use (this);
                gp.obj[gp.currentMap][i] = null;
            }

            // OBSTACLE
            else if (gp.obj[gp.currentMap][i].type == type_obstacle) {

                if (keyH.enterPressed) {
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }


            //INVENTORY ITEMS
            else {
                String text;

                if (canObtainItem(gp.obj[gp.currentMap][i])) {
                    gp.playSE(GameConstants.SOUND_ONE);
                    text = "Got a " + gp.obj[gp.currentMap][i].getName() + "!";
                }
                else {
                    text = "You cannot carry any more!";
                }

                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }


        }
    }

    /**
     * Initiates a conversation with an NPC if the player interacts with them.
     *
     * @param i The index of the NPC in the game world.
     */
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed) {

            if (i != GameConstants.MAX_COST) {

                attackCanceled = true;

                gp.gameState = GameState.DIALOGUE;
                gp.npc[gp.currentMap][i].speak();
            }
            else {
                attacking = true;
            }

        }
    }

    /**
     * Handles interactions when the player comes into contact with a enemy.
     *
     * @param i The index of the enemy in the game world.
     */
    public void contactEnemy(int i) {

        if (i != GameConstants.MAX_COST) {

            if (!invisible && !gp.enemy[gp.currentMap][i].dying) {
                gp.playSE(GameConstants.SOUND_SIX);

                int damage = gp.enemy[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invisible = true;
            }
        }
    }

    /**
     * Damages a enemy based on the player's attack and knockback power.
     *
     * @param i              The index of the enemy in the game world.
     * @param attack         The player's attack value.
     * @param knockBackPower The knockback power applied to the enemy.
     */
    public void damageEnemy(int i, int attack, int knockBackPower) {
        if (i != GameConstants.MAX_COST) {
            if (!gp.enemy[gp.currentMap][i].invisible) {

                gp.playSE(GameConstants.SOUND_FIVE);

                if (knockBackPower > 0) {
                    knockBack(gp.enemy[gp.currentMap][i], knockBackPower);
                }

                int damage = attack - gp.enemy[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gp.enemy[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                logger.info("You caused " + damage + " damage");

                gp.enemy[gp.currentMap][i].invisible = true;
                gp.enemy[gp.currentMap][i].damageReaction();

                if (gp.enemy[gp.currentMap][i].life <= 0) {
                    gp.enemy[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("killed the" + gp.enemy[gp.currentMap][i].getName() + "!");
                    logger.info("You killed the " + gp.enemy[gp.currentMap][i].getName() + "!");
                    gp.ui.addMessage("EXP + " + gp.enemy[gp.currentMap][i].exp);
                    exp += gp.enemy[gp.currentMap][i].exp;
                    checkLevelUp();

                }
            }
        }
    }

    /**
     * Applies knockback to an entity based on a specified knockback power.
     *
     * @param entity         The entity to apply knockback to.
     * @param knockBackPower The knockback power to apply.
     */
    public void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed += KNOCK_BACK_POWER;
        entity.knockBack = true;
    }

    /**
     * Damages an interactive tile if it is destructible, the player has the correct item to destroy it,
     * and the tile is not already invisible. Generates particles and updates the tile if it reaches zero life.
     *
     * @param i The index of the interactive tile to damage.
     */
    public void damageInteractiveTile(int i) {
        if (i != GameConstants.MAX_COST && gp.iTile[gp.currentMap][i].destructible
                && gp.iTile[gp.currentMap][i].isCorrectItem(this)
                && !gp.iTile[gp.currentMap][i].invisible) {
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invisible = true;

            // Generate particle
            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }

    /**
     * Damages a projectile by marking it as not alive and generates particles.
     *
     * @param i The index of the projectile to damage.
     */
    public void damageProjectile(int i) {

        if (i != GameConstants.MAX_COST) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    /**
     * Checks if the player's experience points have reached the required amount for the next level-up.
     * If so, increases the player's level, updates their attributes, and displays a level-up message.
     */
    public void checkLevelUp() {
        if (getExp() >= getNextLevelExp()) {
            level++;
            nextLevelExp = getNextLevelExp()*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense =getDefense();

            gp.playSE(GameConstants.SOUND_SEVEN);
            gp.gameState = GameState.DIALOGUE;
            gp.ui.currentDialogue = "Congrats, PLAYER!\n" +
                    "You are level " + level + "now!\n" +
                    "You became stronger";
        }

    }

    /**
     * Allows the player to select and equip items from their inventory, such as weapons, helmets, and consumables.
     */
    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_spike ||
            selectedItem.type == type_ram) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }

            if (selectedItem.type == type_helmet) {
                currentHelmet = selectedItem;
                defense = getDefense();
            }

            if (selectedItem.type == type_consumable) {

                if (selectedItem.use(this)) {
                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(itemIndex);
                    }

                }
            }
        }
    }

    /**
     * Searches for an item with the given name in the player's inventory.
     *
     * @param itemName The name of the item to search for.
     * @return The index of the item in the inventory, or MAX_COST if not found.
     */
    public int searchItemInInventory(String itemName) {

        int itemIndex = GameConstants.MAX_COST;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;

    }

    /**
     * Determines if the player can obtain an item, considering factors like item stackability and available inventory slots.
     * Increases the stack count or adds a new item to the inventory accordingly.
     *
     * @param item The item to obtain.
     * @return True if the item was successfully obtained, false otherwise.
     */
    public boolean canObtainItem(Entity item) {

        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.getName());

        // Check if stackable
        if (item.stackable) {
            int index = searchItemInInventory(newItem.getName());

            if (index != GameConstants.MAX_COST) {
                inventory.get(index).amount++;
                canObtain = true;
            }

            else {
                // New item so need to check vacancy
                if (inventory.size() != maxInventorySize)  {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }

        else {
            // New item so need to check vacancy
            if (inventory.size() != maxInventorySize)  {
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }

    /**
     * Draws the player character on the screen based on their current state and direction.
     *
     * @param g2 The graphics context on which to draw the player.
     */
    public void draw (Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;


        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) { image = up1; }
                    if (spriteNum == 2) { image = up2; }
                }

                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) { image = attackUp1; }
                    if (spriteNum == 2) { image = attackUp2; }
                }

                break;

            case "down":
                if (!attacking) {
                    if (spriteNum == 1) { image = down1; }
                    if (spriteNum == 2) { image = down2; }
                }

                if (attacking) {
                    if (spriteNum == 1) { image = attackDown1; }
                    if (spriteNum == 2) { image = attackDown2; }
                }

                break;

            case "left":
                if (!attacking) {
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                }

                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) { image = attackLeft1; }
                    if (spriteNum == 2) { image = attackLeft2; }
                }

                break;

            case "right":
                if (!attacking) {
                    if (spriteNum == 1) { image = right1; }
                    if (spriteNum == 2) { image = right2; }
                }

                if (attacking) {
                    if (spriteNum == 1) { image = attackRight1; }
                    if (spriteNum == 2) { image = attackRight2; }
                }

                break;
        }

        if (invisible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f ));
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f ));


    }


}
