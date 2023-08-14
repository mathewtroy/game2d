package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import static cz.cvut.fel.pjv.CollisionChecker.MAX_COST;
import static cz.cvut.fel.pjv.Sound.SOUND_THREE;


public class OBJ_Key extends Entity {

    GamePanel gp;

    public OBJ_Key(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nAn old key.\nMade in China.";
        price = 10;

    }

    public boolean use(Entity entity) {

        gp.gameState = gp.dialogueState;

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
