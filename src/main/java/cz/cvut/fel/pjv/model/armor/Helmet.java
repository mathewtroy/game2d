package cz.cvut.fel.pjv.model.armor;

import cz.cvut.fel.pjv.model.object.Key;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Helmet extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_HELMET = "Missing image of the HELMET";
    private static final String OLD_HELMET_DESCRIPTION = "]\nAn old helmet.";
    private static final String HELMET_PATH = "/objects/helmet";
    public static final String objName = "Helmet";

    GamePanel gp;

    public Helmet(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_helmet;
        setName(objName);
        defenseValue = 1;
        description = Key.SQUARE_BRACKET + name + OLD_HELMET_DESCRIPTION;
        price = 5;
        setupHelmetImage();
    }

    /**
     * Sets up the image for the helmet object.
     */
    private void setupHelmetImage() {
        try {
            down1 = setup(HELMET_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_HELMET);
        }
    }
}
