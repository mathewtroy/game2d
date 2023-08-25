package cz.cvut.fel.pjv.ai;

public class Node {

    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    /**
     * Creates a new node with the specified column and row indices.
     *
     * @param col The column index of the node.
     * @param row The row index of the node.
     */
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
