package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.model.Sound.SOUND_THREE;

public class OBJ_Potion_Blue extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_BLUE_POTION = "Missing image of the BLUE POTION";
    public static final String objName = "Blue Potion";

    GamePanel gp;

    public  OBJ_Potion_Blue (GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        setName(objName);
        value = 3;
        description = "[Blue Potion]\nRecover mana by " + value + "!";
        price = 2;
        stackable = true;

        setupBluePotionImage();
    }

    /**
     * Sets up the image for the blue potion object.
     */
    private void setupBluePotionImage() {
        try {
            down1 = setup("/objects/potion_blue", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_BLUE_POTION);
        }
    }

    /**
     * Uses the Blue Potion to restore mana to the player.
     *
     * @param entity The entity (usually the player) that uses the Blue Potion.
     * @return True if the Blue Potion was successfully used, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.gameState = GamePanel.GameState.DIALOGUE;

        gp.ui.currentDialogue = "You drink the " + name + "!\n" +
                "Mana has been recovered by " + value + "!";
        entity.mana += value;

        gp.playSE(SOUND_THREE);

        return true;

    }
}