package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

public class OBJ_Axe extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_AXE = "Missing image of the AXE";
    public static final String objName = "Normal Axe";

    GamePanel gp;

    public  OBJ_Axe (GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_axe;
        setName(objName);
        attackValue = 4;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA powerful axe.";
        price = 30;
        knockBackPower = 10;

        setupAxeImage();
    }

    private void setupAxeImage() {
        try {
            down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_AXE);
        }
    }
}
