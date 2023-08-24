package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

public class OBJ_Shield_Wood extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_SHIELD_WOOD = "Missing image of the SHIELD WOOD";
    public static final String objName = "Wood Shield";

    GamePanel gp;

    public  OBJ_Shield_Wood (GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_shield;
        setName(objName);
        defenseValue = 1;
        description = "[" + name + "]\nAn old shield.";
        price = 5;

        setupShieldWoodImage();
    }

    private void setupShieldWoodImage() {
        try {
            down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_SHIELD_WOOD);
        }
    }
}
