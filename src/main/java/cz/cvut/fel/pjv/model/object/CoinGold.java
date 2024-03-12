package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class CoinGold extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_COIN = "Missing image of the COIN";
    private static final String COIN_PREFIX = "Coin + ";
    private static final String GOLD_COIN_PATH = "/objects/coin_gold";
    public static final String objName = "Gold Coin";

    GamePanel gp;

    public CoinGold(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        setName(objName);
        value = 10;
        setupCoinImage();
    }

    /**
     * Sets up the image for the coin object.
     */
    private void setupCoinImage() {
        try {
            down1 = setup(GOLD_COIN_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_COIN + ": " + e.getMessage());
        }
    }

    /**
     * Handles the use of the Gold Coin by adding its value to the player's coin count.
     *
     * @param entity The entity using the coin (usually the player).
     * @return True, indicating that the coin was successfully used.
     */
    public boolean use(Entity entity) {
        gp.playSE(GameConstants.SOUND_ONE);
        gp.ui.addMessage(COIN_PREFIX + value);
        gp.player.coin += value;
        return true;
    }
}
