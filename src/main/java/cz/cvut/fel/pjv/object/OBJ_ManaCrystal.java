package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_TWO;

public class OBJ_ManaCrystal extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_MANA = "Missing image of the MANA";
    public static final String objName = "Mana Crystal";

    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {

        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = objName;
        value = 1;
        price = 5;

        setupManaImage();
    }

    private void setupManaImage() {
        try {
            down1 = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
            image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
            image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_MANA);
        }
    }

    public boolean use(Entity entity) {
        gp.playSE(SOUND_TWO);
        gp.ui.addMessage("Mana +" + value) ;
        entity.mana += value;

        return true;
    }
}
