package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

public class OBJ_Sword_Normal extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_SWORD = "Missing image of the SWORD";
    public static final String objName = "Normal Sword";

    GamePanel gp;

    public  OBJ_Sword_Normal (GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_sword;
        setName(objName);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 15;
        knockBackPower = 2;

        setupSwordImage();
    }

    public void setupSwordImage() {
        try {
            down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_SWORD);
        }
    }
}
