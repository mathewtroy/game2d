package cz.cvut.fel.pjv.model.weapon;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Spike extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_SPIKE = "Missing image of the SPIKE";
    public static final String objName = "Normal Spike";

    GamePanel gp;

    public Spike(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_spike;
        setName(objName);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old spike.";
        price = 15;
        knockBackPower = 2;
        setupSpikeImage();
    }

    /**
     * Sets up the image for the spike object.
     */
    private void setupSpikeImage() {
        try {
            down1 = setup("/objects/spike", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_SPIKE);
        }
    }
}
