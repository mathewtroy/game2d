package cz.cvut.fel.pjv.model.ai;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * This class use A* search algorithm
 *
 * The provided code implements the A* pathfinding algorithm, a widely used and efficient algorithm for finding
 * the shortest path between two points on a grid.
 * The A* algorithm combines features of the best-first search and Dijkstra's algorithm,
 * ensuring both efficiency and accuracy in reaching the destination.
 * Key characteristics of the A* algorithm as seen in the code include:
 *
 *     Open List: This list contains nodes that have been discovered but not yet explored.
 *     It acts as a pool of potential candidates for the next step in finding the path.
 *
 *     Cost Calculation (G, H, and F costs):
 *         • G cost represents the movement cost from the starting node to a given node,
 *         essentially the path length.
 *
 *         • H cost, or heuristic cost, estimates the cost to reach the goal from a given node,
 *         often using the Manhattan or Euclidean distance.
 *
 *         • F cost is the sum of G and H, representing the total cost of a path through a given node.
 *
 *     Node Selection for Exploration: The algorithm selects the node with the lowest F cost from the open list
 *     for exploration, guiding the search towards the goal.
 *
 *     Termination of Search: The search continues until the goal node is reached or the open list is empty,
 *     indicating that no path exists.
 *
 *
 * More:
 * https://en.wikipedia.org/wiki/A*_search_algorithm
 *
 */

/**
 *  The class is responsible for finding the path from the start node to the target node
 *
 */
public class PathFinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_INVALID_INDEX = "Attempted to access an invalid index: ";
    private static final String LOGGER_OUT_BOUND = "Start or goal node is out of map boundaries.";

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }

    /**
     * Instantiates a grid of nodes to represent a world.
     * Each node corresponds to a specific position in the world grid.
     * This method initializes the grid of nodes and assigns each node
     * its column and row indices.
     */
    protected void instantiateNodes() {

        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while((col < gp.maxWorldCol) && (row < gp.maxWorldRow)) {
            node[col][row] = new Node(col,row);
            col++;
            if (col == gp.maxWorldCol) { col = 0; row++; }
        }
    }

    /**
     * Resets all nodes in the grid, clearing previous pathfinding data
     *
     */
    protected void resetNodes() {
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            // Reset open, checked and solid state
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if (col == gp.maxWorldCol) { col = 0; row++; }
        }

        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    /**
     * Sets the start and goal nodes for the pathfinding process
     * and initializes the open list with the start node.
     *
     * @param startCol The column index of the starting node
     * @param startRow The row index of the starting node
     * @param goalCol  The column index of the goal node
     * @param goalRow  The row index of the goal node
     *
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {

        resetNodes();

        if (startCol < 0 || startCol >= gp.maxWorldCol || startRow < 0 || startRow >= gp.maxWorldRow ||
                goalCol < 0 || goalCol >= gp.maxWorldCol || goalRow < 0 || goalRow >= gp.maxWorldRow) {
            logger.warning(LOGGER_OUT_BOUND);
            return;
        }

        try {
            // Set Start and Goal node
            startNode = node[startCol][startRow];
            currentNode = startNode;
            goalNode = node[goalCol][goalRow];
            openList.add(currentNode);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warning(LOGGER_INVALID_INDEX + e.getMessage());
            return;
        }

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            // Set Solid Mode
            // Check Tiles
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];

            if (gp.tileM.tile[tileNum].collision == true) { node[col][row].solid = true; }

            // Check Iterative Tiles
            for (int i = 0; i < gp.iTile[1].length; i++) {
                if (gp.iTile[gp.currentMap][i] != null &&
                        gp.iTile[gp.currentMap][i].destructible == true) {
                    int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }
            // Set COST
            getCost(node[col][row]);
            col++;
            if (col == gp.maxWorldCol) { col = 0; row++; }
        }
    }

    /**
     * Calculates the G, H, and F costs for a given node
     *
     * @param node The node for which to calculate costs
     *
     */
    private void getCost(Node node) {
        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F cost
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Performs a search operation to find a path from the current node to the goal node.
     * The search continues until the goal is reached or a maximum number of steps is reached.
     *
     * @return True if the goal is reached; otherwise, false.
     */
    public boolean search() {
        while (goalReached == false  && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // Check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the Up Node
            if (row - 1 >= 0) { openNode(node[col][row-1]); }

            // Open the Left Node
            if (col - 1 >= 0) { openNode(node[col-1][row]); }

            // Open the Down Node
            if (row + 1 < gp.maxWorldRow) { openNode(node[col][row+1]); }

            // Open the Right Node
            if (col + 1 < gp.maxWorldCol) { openNode(node[col+1][row]); }

            // Find the best Node
            int bestNodeIdx = 0;
            int bestNodeCost = GameConstants.MAX_COST;

            for (int i = 0; i < openList.size(); i++) {

                // Check if this node's F cost is better
                if (openList.get(i).fCost < bestNodeCost) {
                    bestNodeIdx = i;
                    bestNodeCost = openList.get(i).fCost;

                }
                // If F cost is equal, check the G cost
                else if (openList.get(i).fCost == bestNodeCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIdx).gCost) { bestNodeIdx = i; }
                }
            }

            // If there is no bode in the openList, end the loop
            if (openList.size() == 0) { break; }

            // After the loop, openList[bestNodeIdx] is the next step (=currentNode)
            currentNode = openList.get(bestNodeIdx);
            if (currentNode == goalNode) { goalReached = true; trackThePath(); }
            step++;
        }
        return goalReached;
    }

    /**
     * Adds a node to the open list if it meets the criteria for being open (not solid, not checked)
     *
     * @param node The node to potentially add to the open list
     *
     */
    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Traces the path from the goal node to the start node and populates the pathList with nodes.
     * This method is called when the goal is reached.
     */
    private void trackThePath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}