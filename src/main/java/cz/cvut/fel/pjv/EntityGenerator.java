package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.object.*;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName){
        Entity obj = null;

        switch (itemName){
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
            case OBJ_Boots.objName :obj = new OBJ_Boots(gp); break;
            case OBJ_Chest.objName : obj = new OBJ_Chest(gp); break;
            case OBJ_Coin_Gold.objName : obj = new OBJ_Coin_Gold(gp); break;
            case OBJ_Door.objName : obj = new OBJ_Door(gp); break;
            case OBJ_Fireball.objName : obj = new OBJ_Fireball(gp); break;
            case OBJ_Heart.objName :obj = new OBJ_Heart(gp); break;
            case OBJ_Key.objName :obj = new OBJ_Key(gp); break;
            case OBJ_ManaCrystal.objName :obj = new OBJ_ManaCrystal(gp); break;
            case OBJ_Potion_Blue.objName: obj = new OBJ_Potion_Blue(gp); break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp); break;
            case OBJ_Shield_Blue.objName : obj = new OBJ_Shield_Blue(gp); break;
            case OBJ_Shield_Wood.objName : obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Sword_Normal.objName : obj = new OBJ_Sword_Normal(gp); break;
        }
        return obj;
    }


}
