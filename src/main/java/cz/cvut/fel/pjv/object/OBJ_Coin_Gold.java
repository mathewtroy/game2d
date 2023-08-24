package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_ONE;

public class OBJ_Coin_Gold extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_COIN = "Missing image of the COIN";
    public static final String objName = "Gold Coin";

    GamePanel gp;

    public OBJ_Coin_Gold(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        setName(objName);
        value = 10;

        setupCoinImage();
    }

    private void setupCoinImage() {
        try {
            down1 = setup("/objects/coin_gold", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_COIN);
        }
    }

    public boolean use(Entity entity) {
        gp.playSE(SOUND_ONE);
        gp.ui.addMessage("Coin + " + value);
        gp.player.coin += value;
        return true;
    }

}
