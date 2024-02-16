package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Helmet_Ger extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_HELMET_GERMAN = "Missing image of the HELMET GERMAN";
    public static final String objName = "German Helmet";

    GamePanel gp;

    public Helmet_Ger(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_helmet;
        setName(objName);
        defenseValue = 3;
        description = "[" + name + "]\nA new German helmet.";
        price = 10;
        setupHelmetGermanImage();
    }

    /**
     * Sets up the image for the German helmet object.
     */
    private void setupHelmetGermanImage() {
        try {
            down1 = setup("/objects/helmet_ger", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_HELMET_GERMAN);
        }
    }
}
