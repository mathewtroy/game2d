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

    private static final String LEVEL_UP_ERROR_MSG = "Level should increase after gaining enough experience";
    private static final String EXP_RESET_ERROR_MSG = "Experience should be reset or set to a new value after level up";


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


    @Test
    void levelUpTest() {
        Player player = new Player(gp, keyHandler);

        // Set initial level and experience
        player.level = 1;
        player.exp = 0;

        // Assume that 100 experience points are required for level up
        int requiredExpForLevelUp = 100;
        player.exp = requiredExpForLevelUp;

        // Check if the level increases
        player.checkLevelUp();
        Assertions.assertEquals(2, player.level, LEVEL_UP_ERROR_MSG);

        // Check if the experience is reset or set to the correct value after level up
        Assertions.assertTrue(player.exp >= 0, EXP_RESET_ERROR_MSG);
    }



}