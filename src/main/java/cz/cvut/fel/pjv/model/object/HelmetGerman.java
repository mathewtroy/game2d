package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class HelmetGerman extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_HELMET_GERMAN = "Missing image of the HELMET GERMAN";
    private static final String NEW_GERMAN_HELMET_DESCRIPTION = "]\nNew German helmet.";
    private static final String GERMAN_HELMET_PATH = "/objects/helmet_ger";
    public static final String objName = "German Helmet";

    GamePanel gp;

    public HelmetGerman(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_helmet;
        setName(objName);
        defenseValue = 3;
        description = Key.SQUARE_BRACKET + name + NEW_GERMAN_HELMET_DESCRIPTION;
        price = 10;
        setupHelmetGermanImage();
    }

    /**
     * Sets up the image for the German helmet object.
     */
    private void setupHelmetGermanImage() {
        try {
            down1 = setup(GERMAN_HELMET_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_HELMET_GERMAN);
        }
    }
}
