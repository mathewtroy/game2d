package cz.cvut.fel.pjv.model.ai;

import cz.cvut.fel.pjv.view.GamePanel;
import org.junit.jupiter.api.*;

public class PathFinderTest {

    private PathFinder pathFinder;
    private GamePanel gamePanel;

    private static final String NODES_NOT_NULL_MSG = "Node array should not be null after instantiation";
    private static final String NODE_NOT_NULL_MSG_TEMPLATE = "Node should not be null at [%d][%d]";
    private static final String COLUMNS_MATCH_MSG = "Number of columns should match GamePanel maxWorldCol";
    private static final String ROWS_MATCH_MSG = "Number of rows in the first column should match GamePanel maxWorldRow";

    private static final String RESET_NODES_EFFECTIVENESS_MSG = "All nodes should be reset correctly";
    private static final String LISTS_CLEARED_MSG = "Open and path lists should be cleared";
    private static final String GOAL_NOT_REACHED_MSG = "Goal reached should be false";
    private static final String STEP_RESET_MSG = "Step should be reset to 0";

    @BeforeEach
    void setUp() {

        // Preparing a fake or spoofed GamePanel with the specified parameters
        gamePanel = new GamePanel() {
            public final int maxWorldCol = 10;
            public final int maxWorldRow = 10;
            // Define required methods or stubs here
        };
        pathFinder = new PathFinder(gamePanel);
    }

    @Test
    void testInstantiateNodes() {
        pathFinder.instantiateNodes();
        Assertions.assertNotNull(pathFinder.node, NODES_NOT_NULL_MSG);

        // Checking that all cells are initialized
        for (int col = 0; col < gamePanel.maxWorldCol; col++) {
            for (int row = 0; row < gamePanel.maxWorldRow; row++) {
                Assertions.assertNotNull(pathFinder.node[col][row], String.format(NODE_NOT_NULL_MSG_TEMPLATE, col, row));
            }
        }

        // Checking that the size of the array matches the dimensions of the GamePanel
        Assertions.assertEquals(gamePanel.maxWorldCol, pathFinder.node.length, COLUMNS_MATCH_MSG);
        Assertions.assertEquals(gamePanel.maxWorldRow, pathFinder.node[0].length, ROWS_MATCH_MSG);
    }

    @Test
    void testResetNodes() {
        // Preparing the environment
        pathFinder.instantiateNodes();
        // Manually modifying the state of nodes and other variables to test the reset functionality
        for (int col = 0; col < gamePanel.maxWorldCol; col++) {
            for (int row = 0; row < gamePanel.maxWorldRow; row++) {
                pathFinder.node[col][row].open = true;
                pathFinder.node[col][row].checked = true;
                pathFinder.node[col][row].solid = true;
            }
        }
        pathFinder.openList.add(new Node(0, 0)); // Simulate nodes in open list
        pathFinder.pathList.add(new Node(1, 1)); // Simulate nodes in path list
        pathFinder.goalReached = true;
        pathFinder.step = 100;

        // Resetting nodes
        pathFinder.resetNodes();

        // Assertions
        for (int col = 0; col < gamePanel.maxWorldCol; col++) {
            for (int row = 0; row < gamePanel.maxWorldRow; row++) {
                Node node = pathFinder.node[col][row];
                Assertions.assertFalse(node.open, RESET_NODES_EFFECTIVENESS_MSG);
                Assertions.assertFalse(node.checked, RESET_NODES_EFFECTIVENESS_MSG);
                Assertions.assertFalse(node.solid, RESET_NODES_EFFECTIVENESS_MSG);
            }
        }
        Assertions.assertTrue(pathFinder.openList.isEmpty(), LISTS_CLEARED_MSG);
        Assertions.assertTrue(pathFinder.pathList.isEmpty(), LISTS_CLEARED_MSG);
        Assertions.assertFalse(pathFinder.goalReached, GOAL_NOT_REACHED_MSG);
        Assertions.assertEquals(0, pathFinder.step, STEP_RESET_MSG);
    }

}