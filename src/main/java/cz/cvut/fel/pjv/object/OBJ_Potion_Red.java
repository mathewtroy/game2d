package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;

    public  OBJ_Potion_Red (GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = "Red Potion";
        value = 5;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[Red Potion]\nHeals your life by " + value + "!";
        price = 3;
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n" +
                "Your life has been recovered by " + value + "!";
        entity.life += value;

        gp.playSE(3);

    }
}
