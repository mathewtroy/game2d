package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_THREE;

public class OBJ_Potion_Red extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_RED_POTION = "Missing image of the RED POTION";
    public static final String objName = "Red Potion";

    GamePanel gp;

    public  OBJ_Potion_Red (GamePanel gp) {
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

    private void setupRedPotionImage() {
        try {
            down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_RED_POTION);
        }
    }

    public boolean use(Entity entity) {
        gp.gameState = GamePanel.GameState.DIALOGUE;

        gp.ui.currentDialogue = "You drink the " + name + "!\n" +
                "Life has been recovered by " + value + "!";
        entity.life += value;

        gp.playSE(SOUND_THREE);

        return true;

    }
}
