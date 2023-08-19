package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_THREE;

public class OBJ_Potion_Blue extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_BLUE_POTION = "Missing image of the BLUE POTION";
    public static final String objName = "Blue Potion";

    GamePanel gp;

    public  OBJ_Potion_Blue (GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Blue Potion";
        value = 3;
        description = "[Blue Potion]\nRecover mana by " + value + "!";
        price = 2;
        stackable = true;

        setupBluePotionImage();
    }

    private void setupBluePotionImage() {
        try {
            down1 = setup("/objects/potion_blue", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_BLUE_POTION);
        }
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;

        gp.ui.currentDialogue = "You drink the " + name + "!\n" +
                "Mana has been recovered by " + value + "!";
        entity.mana += value;

        gp.playSE(SOUND_THREE);

        return true;

    }
}