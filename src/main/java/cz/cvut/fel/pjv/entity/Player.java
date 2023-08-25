package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.KeyHandler;
import cz.cvut.fel.pjv.object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.CollisionChecker.MAX_COST;
import static cz.cvut.fel.pjv.Sound.*;

public class Player extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_PLAYER = "Missing image of the HERO";
    private static final String LOGGER_MESSAGE_PLAYER_SWORD = "Missing image of the ATTACKING HERO WITH SWORD";
    private static final String LOGGER_MESSAGE_PLAYER_AXE = "Missing image of the ATTACKING HERO WITH AXE";

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
     *
     * @param gp
     * @param keyH
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
     *
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
        maxMana = 6;
        mana = maxMana;
        ammo = 10;
        strength = 1;   // more strength -> more damage he gives
        dexterity = 1;  // more dexterity -> less damage he receives
        exp = 0;
        nextLevelExp = 0;
        coin = 150;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();

    }

    /**
     *
     */
    public void setDefaultPositions() {
        // default start position
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;

        direction = "down";

    }

    /**
     *
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
     *
     */
    private void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        // inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Potion_Blue(gp));


    }

    /**
     *
     * @return
     */
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    /**
     *
     * @return
     */
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    /**
     *
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
        planet = setup("/player/planet", gp.tileSize, gp.tileSize);
    }

    /**
     *
     */
    public void getPlayerAttackImage() {

        try {
            up1 = setup("/player/hero_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/hero_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/hero_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/hero_down_2", gp.tileSize, gp.tileSize);

            right1 = setup("/player/hero_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/hero_right_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/hero_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/hero_left_2", gp.tileSize, gp.tileSize);

        } catch (Exception e){
            logger.severe(LOGGER_MESSAGE_PLAYER);
        }

        // SWITCH TYPE OF WEAPON
        if (currentWeapon.type == type_sword) {

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
                logger.warning(LOGGER_MESSAGE_PLAYER_SWORD);
            }

        }

        if (currentWeapon.type == type_axe) {
            try {
                attackUp1 = setup("/player/hero_axe_up_1", gp.tileSize, gp.tileSize*2);
                attackUp2 = setup("/player/hero_axe_up_2", gp.tileSize, gp.tileSize*2);
                attackDown1 = setup("/player/hero_axe_down_1", gp.tileSize, gp.tileSize*2);
                attackDown2 = setup("/player/hero_axe_down_2", gp.tileSize, gp.tileSize*2);
                attackLeft1 = setup("/player/hero_axe_left_1", gp.tileSize*2, gp.tileSize);
                attackLeft2 = setup("/player/hero_axe_left_2", gp.tileSize*2, gp.tileSize);
                attackRight1 = setup("/player/hero_axe_right_1", gp.tileSize*2, gp.tileSize);
                attackRight2 = setup("/player/hero_axe_right_2", gp.tileSize*2, gp.tileSize);

            } catch (Exception e){
                logger.warning(LOGGER_MESSAGE_PLAYER_AXE);
            }
        }

    }

    /**
     *
     */
    public void update() {

        if (attacking) {
            attacking();
        }
        //  we want player move when we press button
        else if (keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed ||
                keyH.enterPressed) {


            if (keyH.upPressed) { direction = "up"; }
            else if (keyH.downPressed) { direction = "down"; }
            else if (keyH.leftPressed) { direction = "left"; }
            else if (keyH.rightPressed) { direction = "right"; }

            //  CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //  CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //  CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC (npcIndex);

            //  CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);

            //  CHECK EVENT
            gp.eHandler.checkEvent();
            gp.keyH.enterPressed = false;


            //  IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && (!keyH.enterPressed)) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }


            if (keyH.enterPressed && !attackCanceled) {
                gp.playSE(SOUND_FIVE);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > SPRITE_COUNTER_THRESHOLD) {
                if (spriteNum == 1) { spriteNum = 2; }
                else if (spriteNum == 2) { spriteNum = 1; }
                spriteCounter = 0;
            }
        }
        else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if (gp.keyH.shotKeyPressed && !projectile.alive
                && (shotAvailableCounter == SHOT_AVAILABLE_COUNTER_THRESHOLD) &&
                projectile.haveResource(this)) {

            // SET DEFAULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);


            // SUBTRACT THE COST ( MANA )
            projectile.subtractResource(this);


            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {

                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }

            }

            shotAvailableCounter = 0;

            gp.playSE(SOUND_NINE);
        }

        // This needs to be outside of key if statement
        if (invisible) {
            invisibleCounter++;

            if (invisibleCounter > INVISIBLE_COUNTER_THRESHOLD) {
                invisible = false;
                invisibleCounter = 0;
            }
        }

        if (shotAvailableCounter < SHOT_AVAILABLE_COUNTER_THRESHOLD) {
            shotAvailableCounter ++;
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (mana > maxMana) {
            mana = maxMana;
        }

        if (life <= 0) {
            gp.gameState = GamePanel.GameState.GAME_OVER;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(SOUND_ELEVEN);
        }

    }

    /**
     *
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

            // Check monster collision with updated worldX, worldY and solidArea
            int monsterIndex  = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

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
     *
     * @param i
     */
    public void pickUpObject (int i) {
        if (i != MAX_COST) {

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
                    gp.playSE(SOUND_ONE);
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
     *
     * @param i
     */
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed) {

            if (i != MAX_COST) {

                attackCanceled = true;

                gp.gameState = GamePanel.GameState.DIALOGUE;
                gp.npc[gp.currentMap][i].speak();
            }
            else {
                attacking = true;
            }

        }
    }

    /**
     *
     * @param i
     */
    public void contactMonster(int i) {

        if (i != MAX_COST) {

            if (!invisible && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(SOUND_SIX);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invisible = true;
            }
        }
    }

    /**
     *
     * @param i
     * @param attack
     * @param knockBackPower
     */
    public void damageMonster(int i, int attack, int knockBackPower) {
        if (i != MAX_COST) {
            if (!gp.monster[gp.currentMap][i].invisible) {

                gp.playSE(SOUND_FIVE);

                if (knockBackPower > 0) {
                    knockBack(gp.monster[gp.currentMap][i], knockBackPower);
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                logger.info("You caused " + damage + " damage");

                gp.monster[gp.currentMap][i].invisible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("killed the" + gp.monster[gp.currentMap][i].getName() + "!");
                    logger.info("You killed the " + gp.monster[gp.currentMap][i].getName() + "!");
                    gp.ui.addMessage("EXP + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();

                }
            }
        }
    }

    /**
     *
     * @param entity
     * @param knockBackPower
     */
    public void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed += KNOCK_BACK_POWER;
        entity.knockBack = true;
    }

    /**
     *
     * @param i
     */
    public void damageInteractiveTile(int i) {
        if (i != MAX_COST && gp.iTile[gp.currentMap][i].destructible
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
     *
     * @param i
     */
    public void damageProjectile(int i) {

        if (i != MAX_COST) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    /**
     *
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

            gp.playSE(SOUND_SEVEN);
            gp.gameState = GamePanel.GameState.DIALOGUE;
            gp.ui.currentDialogue = "Congrats, PLAYER!\n" +
                    "You are level " + level + "now!\n" +
                    "You became stronger";
        }

    }

    /**
     *
     */
    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword ||
            selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }

            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
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
     *
     * @param itemName
     * @return
     */
    public int searchItemInInventory(String itemName) {

        int itemIndex = MAX_COST;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;

    }

    /**
     *
     * @param item
     * @return
     */
    public boolean canObtainItem(Entity item) {

        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.getName());

        // Check if stackable
        if (item.stackable) {
            int index = searchItemInInventory(newItem.getName());

            if (index != MAX_COST) {
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
     *
     * @param g2
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
