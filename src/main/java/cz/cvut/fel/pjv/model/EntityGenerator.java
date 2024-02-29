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
            case CoinGold.objName : obj = new CoinGold(gp); break;
            case Door.objName : obj = new Door(gp); break;
            case Bullet.objName : obj = new Bullet(gp); break;
            case Heart.objName :obj = new Heart(gp); break;
            case Key.objName :obj = new Key(gp); break;
            case Mana.objName :obj = new Mana(gp); break;
            case Ammunition.objName: obj = new Ammunition(gp); break;
            case FirstAid.objName: obj = new FirstAid(gp); break;
            case HelmetGerman.objName : obj = new HelmetGerman(gp); break;
            case Helmet.objName : obj = new Helmet(gp); break;
            case Spike.objName : obj = new Spike(gp); break;
        }
        return obj;
    }
}
