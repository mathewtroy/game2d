package cz.cvut.fel.pjv.model.tile;

import cz.cvut.fel.pjv.view.GamePanel;

import java.util.logging.Logger;

public class Trunk extends InteractiveTile {

    GamePanel gp;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_TRUNK = "Missing image of the TRUNK";
    private static final String TRUNK_IMAGE_PATH = "/tiles/trunk";

    /**
     * Constructs a Trunk interactive tile.
     *
     * @param gp  The GamePanel in which this tile exists.
     * @param col The column position of the tile.
     * @param row The row position of the tile.
     */
    public Trunk(GamePanel gp, int col, int row) {
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
        try { down1 = setup(TRUNK_IMAGE_PATH, gp.tileSize, gp.tileSize); }
        catch (Exception e) { logger.warning(LOGGER_MESSAGE_TRUNK + e.getMessage()); }
    }
}
