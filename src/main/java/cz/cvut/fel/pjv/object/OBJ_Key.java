package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.CollisionChecker.MAX_COST;
import static cz.cvut.fel.pjv.Sound.SOUND_THREE;


public class OBJ_Key extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_KEY = "Missing image of the KEY";
    public static final String objName = "Key";

    GamePanel gp;

    public OBJ_Key(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_consumable;
        setName(objName);

        description = "[" + name + "]\nAn old key.\nMade in China.";
        price = 10;
        stackable = true;
        setupKeyImage();
    }

    private void setupKeyImage() {
        try {
            down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_KEY);
        }
    }

    public boolean use(Entity entity) {

        gp.gameState = GamePanel.GameState.DIALOGUE;

        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != MAX_COST) {
            gp.ui.currentDialogue = "You use the " + name + " and open the door";
            gp.playSE(SOUND_THREE);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "What are you doing man ??";
            return false;
        }
    }
}
