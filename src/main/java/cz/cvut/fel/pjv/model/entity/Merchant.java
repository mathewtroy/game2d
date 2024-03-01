package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.controller.MapConstants;
import cz.cvut.fel.pjv.model.armor.Helmet;
import cz.cvut.fel.pjv.model.armor.HelmetGerman;
import cz.cvut.fel.pjv.model.object.*;
import cz.cvut.fel.pjv.model.refill.Ammunition;
import cz.cvut.fel.pjv.model.refill.FirstAid;
import cz.cvut.fel.pjv.model.weapon.Ram;
import cz.cvut.fel.pjv.model.weapon.Spike;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.view.GameState;

public class Merchant extends Entity  {


    private static final String DOWN1_IMAGE_MERCHANT_PATH = "/npc/merchant_down_1";
    private static final String DOWN2_IMAGE_MERCHANT_PATH = "/npc/merchant_down_2";
    private static final String DIALOGUE_GREETING = "Hey! \nDo you want to trade, Iwan?? ";
    private static final String DIALOGUE_GOODLUCK = "Good luck, Iwan";

    public Merchant(GamePanel gp) {
        super(gp);
        direction = MapConstants.DOWN;
        speed = 1;
        getImage();
        setDialogue();
        setItems();
    }

    /**
     * Loads and sets the images for the merchant's different directions.
     */
    private void getImage() {
        up1 = setup(DOWN1_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
        up2 = setup(DOWN2_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
        down1 = setup(DOWN1_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
        down2 = setup(DOWN2_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
        right1 = setup(DOWN1_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
        right2 = setup(DOWN2_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
        left1 = setup(DOWN1_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
        left2 = setup(DOWN2_IMAGE_MERCHANT_PATH, gp.tileSize, gp.tileSize);
    }

    /**
     * Sets the dialogue options for the merchant.
     */
    private void setDialogue() {
        dialogues[0] = DIALOGUE_GREETING;
        dialogues[1] = DIALOGUE_GOODLUCK;
    }

    /**
     * Fills the item merchant's inventory.
     */
    private void setItems() {
        inventory.add(new Ram(gp));
        inventory.add(new Key(gp));
        inventory.add(new FirstAid(gp));
        inventory.add(new Ammunition(gp));
        inventory.add(new Spike(gp));
        inventory.add(new HelmetGerman(gp));
        inventory.add(new Helmet(gp));
    }

    /**
     * Initiates a conversation with the merchant.
     * Sets the game state to TRADE and associates the NPC with the game's user interface.
     */
    public void speak() {
        super.speak();
        gp.gameState = GameState.TRADE;
        gp.ui.npc = this;
    }
}
