package cz.cvut.fel.pjv.ai;

import cz.cvut.fel.pjv.GamePanel;

import java.util.ArrayList;

import static cz.cvut.fel.pjv.CollisionChecker.MAX_COST;

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
    private void instantiateNodes() {

        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while((col < gp.maxWorldCol) && (row < gp.maxWorldRow)) {
            node[col][row] = new Node(col,row);
            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Resets all nodes in the grid, clearing previous pathfinding data
     *
     */
    private void resetNodes() {
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            // Reset open, checked and solid state
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
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

        // Set Start and Goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            // SET SOLID NODE

            // CHECK TILES
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];

            if (gp.tileM.tile[tileNum].collision == true) {
                node[col][row].solid = true;
            }

            // CHECK INTERACTIVE TILES
            for (int i = 0; i < gp.iTile[1].length; i++) {
                if (gp.iTile[gp.currentMap][i] != null &&
                        gp.iTile[gp.currentMap][i].destructible == true) {
                    int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }
            // SET COST
            getCost(node[col][row]);

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
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
            if (row - 1 >= 0) {
                openNode(node[col][row-1]);
            }

            // Open the Left Node
            if (col - 1 >= 0) {
                openNode(node[col-1][row]);
            }

            // Open the Down Node
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row+1]);
            }

            // Open the Right Node
            if (col + 1 < gp.maxWorldCol) {
                openNode(node[col+1][row]);
            }

            // Find the best Node
            int bestNodeIdx = 0;
            int bestNodeCost = MAX_COST;

            for (int i = 0; i < openList.size(); i++) {

                // Check if this node's F cost is better
                if (openList.get(i).fCost < bestNodeCost) {
                    bestNodeIdx = i;
                    bestNodeCost = openList.get(i).fCost;

                }
                // If F cost is equal, check the G cost
                else if (openList.get(i).fCost == bestNodeCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIdx).gCost) {
                        bestNodeIdx = i;
                    }
                }
            }

            // If there is no bode in the openList, end the loop
            if (openList.size() == 0) {
                break;
            }

            // After the loop, openList[bestNodeIdx] is the next step (=currentNode)
            currentNode = openList.get(bestNodeIdx);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
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
