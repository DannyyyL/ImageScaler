/*
//Author: Dan Lichtin
//Student Number: 251357888
/**
 * This class represents a node in a Quadrant Tree data structure used for image processing.
 */
public class QTreeNode {
    int x, y;                   // x and y coordinates of the node
    int size;                   // size of the quadrant
    int color;                  // color value of the node
    QTreeNode parent;           // reference to the parent node
    QTreeNode[] children;       // array of child nodes
    
    /**
     * Constructs a QTreeNode with default values.
     */
    public QTreeNode() {
        parent = null;
        children = new QTreeNode[4];
        x = 0;
        y = 0;
        size = 0;
        color = 0;
    }
    
    /**
     * Constructs a QTreeNode with specified parameters.
     *
     * @param theChildren Array of child nodes.
     * @param xcoord      The x-coordinate of the node.
     * @param ycoord      The y-coordinate of the node.
     * @param theSize     The size of the quadrant.
     * @param theColor    The color value of the node.
     */
    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
        parent = null;
        children = theChildren;
        x = xcoord;
        y = ycoord;
        size = theSize;
        color = theColor;
    }
    
    /**
     * Retrieves the x-coordinate of the node.
     *
     * @return The x-coordinate of the node.
     */
    public int getx() {
        return x;
    }

    /**
     * Sets the x-coordinate of the node.
     *
     * @param x The new x-coordinate of the node.
     */
    public void setx(int x) {
        this.x = x;
    }

    /**
     * Retrieves the y-coordinate of the node.
     *
     * @return The y-coordinate of the node.
     */
    public int gety() {
        return y;
    }

    /**
     * Sets the y-coordinate of the node.
     *
     * @param y The new y-coordinate of the node.
     */
    public void sety(int y) {
        this.y = y;
    }

    /**
     * Retrieves the size of the quadrant.
     *
     * @return The size of the quadrant.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the quadrant.
     *
     * @param size The new size of the quadrant.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Retrieves the color value of the node.
     *
     * @return The color value of the node.
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets the color value of the node.
     *
     * @param color The new color value of the node.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Retrieves the parent node of the current node.
     *
     * @return The parent node of the current node.
     */
    public QTreeNode getParent() {
        return parent;
    }

    /**
     * Sets the parent node of the current node.
     *
     * @param parent The new parent node of the current node.
     */
    public void setParent(QTreeNode parent) {
        this.parent = parent;
    }
    
    /**
     * Retrieves a child node at a specified index.
     *
     * @param index The index of the child node to retrieve.
     * @return The child node at the specified index.
     * @throws QTreeException if the index is out of bounds or children array is null.
     */
    public QTreeNode getChild(int index) throws QTreeException {
        if (children == null || (index < 0 || index > 3)) {
            throw new QTreeException("");
        }
        return children[index];
    }
    
    /**
     * Sets a child node at a specified index.
     *
     * @param newChild The new child node to set.
     * @param index    The index at which to set the child node.
     * @throws QTreeException if the index is out of bounds or children array is null.
     */
    public void setChild(QTreeNode newChild, int index) {
        if (children == null || (index < 0 || index > 3)) {
            throw new QTreeException("");
        }
        children[index] = newChild;
    }
    
    /**
     * Checks if a specified point lies within the bounds of the node's quadrant.
     *
     * @param xcoord The x-coordinate of the point.
     * @param ycoord The y-coordinate of the point.
     * @return true if the point is contained within the quadrant, false otherwise.
     */
    public boolean contains(int xcoord, int ycoord) {
        if (xcoord >= x && xcoord <= x + size - 1) {
            if (ycoord >= y && ycoord <= y + size - 1) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the node is a leaf node (has no children).
     *
     * @return true if the node is a leaf node, false otherwise.
     */
    public boolean isLeaf() {
        if (children == null) {
            return true; // No children array, considered as a leaf
        }
        
        for (int i = 0; i < 4; i++) {
            if (children[i] != null) {
                return false; // At least one child is not null, so it's not a leaf
            }
        }
        
        return true; // All children are null, considered as a leaf
    }

}
