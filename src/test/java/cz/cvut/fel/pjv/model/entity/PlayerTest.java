package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.controller.KeyHandler;
import cz.cvut.fel.pjv.model.enemy.Ghost;
import cz.cvut.fel.pjv.model.enemy.Tank;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.view.GameState;
import org.junit.jupiter.api.*;


class PlayerTest {

    private GamePanel gp;
    private KeyHandler keyHandler;
    private int mapNum;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        gp.gameState = GameState.PLAY;
        keyHandler = new KeyHandler(gp);
        mapNum = 0;
    }

    @Test
    void damageTankEnemyTest() {
        Player player = new Player(gp, keyHandler);
        int attack = 5;
        int knockBackPower = 1;
        gp.player = player;
        gp.player.worldX = 0;

        Tank tank = new Tank(gp);
        int enemyNum = 0;
        int expectedTankHealth = 10;
        gp.enemy[mapNum][enemyNum] = tank;
        gp.enemy[mapNum][enemyNum].worldX = 0;
        gp.enemy[mapNum][enemyNum].worldY = 0;

        player.damageEnemy(enemyNum, attack, knockBackPower);
        int resultTankHealth = tank.life;

        Assertions.assertEquals(expectedTankHealth, resultTankHealth);
    }

    @Test
    void damageGhostEnemyTest() {
        Player player = new Player(gp, keyHandler);
        int attack = 10;
        int knockBackPower = 1;
        gp.player = player;
        gp.player.worldX = 1;
        gp.player.worldY = 1;

        Ghost ghost = new Ghost(gp);
        int enemyNum = 0;
        int expectedGhostHealth = 23;
        gp.enemy[mapNum][enemyNum] = ghost;
        gp.enemy[mapNum][enemyNum].worldX = 1;
        gp.enemy[mapNum][enemyNum].worldY = 1;

        player.damageEnemy(enemyNum, attack, knockBackPower);
        int resultGhostHealth = ghost.life;

        Assertions.assertEquals(expectedGhostHealth, resultGhostHealth);
    }

}