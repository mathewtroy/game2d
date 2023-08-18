package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import static cz.cvut.fel.pjv.Sound.SOUND_TWO;


public class OBJ_Heart extends Entity {

    public static final String objName = "Heart";

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.playSE(SOUND_TWO);
        gp.ui.addMessage("Life +" + value) ;
        entity.life += value;

        return true;
    }
}
