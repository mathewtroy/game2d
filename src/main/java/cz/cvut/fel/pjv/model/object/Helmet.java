package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Helmet extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_HELMET = "Missing image of the HELMET";
    public static final String objName = "Helmet";

    GamePanel gp;

    public Helmet(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_helmet;
        setName(objName);
        defenseValue = 1;
        description = "[" + name + "]\nAn old helmet.";
        price = 5;
        setupHelmetImage();
    }

    /**
     * Sets up the image for the helmet object.
     */
    private void setupHelmetImage() {
        try {
            down1 = setup("/objects/helmet", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_HELMET);
        }
    }
}
