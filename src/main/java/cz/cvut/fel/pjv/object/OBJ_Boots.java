package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_ONE;

public class OBJ_Boots extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_BOOTS = "Missing image of the BOOTS";
    public static final String objName = "Boots";

    GamePanel gp;

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        value = 2;
        name = objName;

        setupBootsImage();
    }

    private void setupBootsImage() {
        try {
            down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_BOOTS);
        }
    }

    public boolean use(Entity entity) {
        gp.playSE(SOUND_ONE);
        gp.ui.addMessage("Coin + " + value);
        gp.player.speed += value;
        return true;
    }
}
