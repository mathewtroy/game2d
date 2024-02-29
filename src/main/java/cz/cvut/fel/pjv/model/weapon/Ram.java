package cz.cvut.fel.pjv.model.weapon;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Ram extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_RAM = "Missing image of the RAM";
    private static final String RAM_PATH = "/weapon/ram";
    public static final String objName = "Normal Ram";

    GamePanel gp;

    public Ram(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_ram;
        setName(objName);
        attackValue = 4;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA powerful ram.";
        price = 30;
        knockBackPower = 10;
        setupRamImage();
    }

    /**
     * Sets up the image for the ram object.
     */
    private void setupRamImage() {
        try {
            down1 = setup(RAM_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_RAM);
        }
    }
}
