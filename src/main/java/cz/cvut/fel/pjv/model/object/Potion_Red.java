package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameState;

import java.util.logging.Logger;

public class Potion_Red extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_RED_POTION = "Missing image of the RED POTION";
    public static final String objName = "Red Potion";

    GamePanel gp;

    public Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        setName(objName);
        value = 5;
        description = "[Red Potion]\nHeals your life by " + value + "!";
        price = 3;
        stackable = true;
        setupRedPotionImage();
    }

    /**
     * Sets up the image for the red potion object.
     */
    private void setupRedPotionImage() {
        try {
            down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_RED_POTION);
        }
    }

    /**
     * Uses the Red Potion to restore life to the player.
     *
     * @param entity The entity (usually the player) that uses the Red Potion.
     * @return True if the Red Potion was successfully used, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.gameState = GameState.DIALOGUE;

        gp.ui.currentDialogue = "You drink the " + name + "!\n" +
                "Life has been recovered by " + value + "!";
        entity.life += value;

        gp.playSE(GameConstants.SOUND_THREE);

        return true;

    }
}
