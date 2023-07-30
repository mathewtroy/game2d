package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Key extends SuperObject {

    GamePanel gp;
    public OBJ_Key(GamePanel gp) {

        this.gp = gp;

        name = "Key";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        solidArea.x = 5;
    }


}
