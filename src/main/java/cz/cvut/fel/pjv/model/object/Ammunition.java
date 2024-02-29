package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameState;

import java.util.logging.Logger;

public class Ammunition extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_AMMUNITION = "Missing image of the AMMUNITION";
    private static final String AMMUNITION_DESCRIPTION = "[Ammunition]\nRecover mana by ";
    private static final String AMMUNITION_IMAGE_PATH = "/objects/ammunition";
    private static final String MANA_RECOVERY_MESSAGE = "Mana has been recovered by ";
    public static final String objName = "Ammunition";

    GamePanel gp;

    public Ammunition(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        setName(objName);
        value = 3;
        description = AMMUNITION_DESCRIPTION + value + Chest.EXCLAMATION_MARK;
        price = 2;
        stackable = true;
        setupAmmunitionImage();
    }

    /**
     * Sets up the image for the ammunition object.
     */
    private void setupAmmunitionImage() {
        try {
            down1 = setup(AMMUNITION_IMAGE_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_AMMUNITION);
        }
    }

    /**
     * Uses the Ammunition to restore mana to the player.
     *
     * @param entity The entity (usually the player) that uses the Ammunition.
     * @return True if the Ammunition was successfully used, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.gameState = GameState.DIALOGUE;
        gp.ui.currentDialogue = Key.USE_MESSAGE + name + Chest.EXCLAMATION_MARK
                + FirstAid.NEW_LINE + MANA_RECOVERY_MESSAGE + value + Chest.EXCLAMATION_MARK;
        entity.mana += value;
        gp.playSE(GameConstants.SOUND_THREE);
        return true;
    }
}