package cz.cvut.fel.pjv.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    /**
     * Scales a given BufferedImage to the specified width and height.
     *
     * @param original The original BufferedImage to be scaled.
     * @param width The desired width of the scaled image.
     * @param height The desired height of the scaled image.
     * @return A scaled BufferedImage with the specified dimensions.
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0,0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
