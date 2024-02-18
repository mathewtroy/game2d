package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.model.object.*;
import cz.cvut.fel.pjv.model.weapon.Bullet;
import cz.cvut.fel.pjv.model.weapon.Ram;
import cz.cvut.fel.pjv.model.weapon.Spike;
import cz.cvut.fel.pjv.view.GamePanel;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Generates an entity object based on the given item name.
     *
     * @param itemName The name of the item for which an entity is generated.
     * @return An entity object corresponding to the given item name,
     * or null if the item name is not recognized.
     *
     */
    public Entity getObject(String itemName){
        Entity obj = null;

        switch (itemName){
            case Ram.objName: obj = new Ram(gp); break;
            case Boots.objName :obj = new Boots(gp); break;
            case Chest.objName : obj = new Chest(gp); break;
            case Coin_Gold.objName : obj = new Coin_Gold(gp); break;
            case Door.objName : obj = new Door(gp); break;
            case Bullet.objName : obj = new Bullet(gp); break;
            case Heart.objName :obj = new Heart(gp); break;
            case Key.objName :obj = new Key(gp); break;
            case ManaCrystal.objName :obj = new ManaCrystal(gp); break;
            case Potion_Blue.objName: obj = new Potion_Blue(gp); break;
            case Potion_Red.objName: obj = new Potion_Red(gp); break;
            case Helmet_Ger.objName : obj = new Helmet_Ger(gp); break;
            case Helmet.objName : obj = new Helmet(gp); break;
            case Spike.objName : obj = new Spike(gp); break;
        }
        return obj;
    }
}
