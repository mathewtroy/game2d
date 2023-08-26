package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;

import java.util.logging.Logger;

public class IT_Trunk extends InteractiveTile {

    GamePanel gp;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_TRUNK = "Missing image of the TRUNK";

    /**
     * Constructs a Trunk interactive tile.
     *
     * @param gp  The GamePanel in which this tile exists.
     * @param col The column position of the tile.
     * @param row The row position of the tile.
     */
    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setupTrunkImage();
    }

    /**
     * Sets up the image for the Trunk.
     */
    private void setupTrunkImage() {
        try {
            down1 = setup("/tiles/trunk", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_TRUNK);
        }
    }
}
