package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Key extends SuperObject {


    public OBJ_Key() {

        name = "Key";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        solidArea.x = 5;
    }


}
