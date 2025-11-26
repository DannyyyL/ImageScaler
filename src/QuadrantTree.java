//Author: Dan Lichtin
//Student Number: 251357888
/**
 * This class represents a Quadrant Tree data structure used for image processing.
 */
public class QuadrantTree {
    QTreeNode root;  // Root node of the quadrant tree

    /**
     * Constructs a QuadrantTree object with the provided pixel data.
     *
     * @param thePixels A 2D array representing the pixels of an image.
     */
    public QuadrantTree(int[][] thePixels) {
        // Build the quadrant tree recursively
        root = buildTree(0, 0, thePixels.length, thePixels);
    }

    /**
     * Recursively builds the quadrant tree structure.
     *
     * @param x       The x-coordinate of the current node.
     * @param y       The y-coordinate of the current node.
     * @param size    The size of the quadrant.
     * @param pixels  The 2D array of pixel values.
     * @return The root node of the quadrant tree.
     */
    private QTreeNode buildTree(int x, int y, int size, int[][] pixels) {
        if (size == 1) {
            // Create a leaf node if the quadrant size is 1
            return new QTreeNode(null, x, y, 1, pixels[x][y]);
        } else {
            // Calculate the half size of the quadrant
            int halfSize = size / 2;

            // Recursively build the quadrant tree for each quadrant
            QTreeNode[] children = new QTreeNode[4];
            children[0] = buildTree(x, y, halfSize, pixels); // Upper-left quadrant
            children[1] = buildTree(x + halfSize, y, halfSize, pixels); // Upper-right quadrant
            children[2] = buildTree(x, y + halfSize, halfSize, pixels); // Bottom-left quadrant
            children[3] = buildTree(x + halfSize, y + halfSize, halfSize, pixels); // Bottom-right quadrant

            // Create a non-leaf node with children and average color
            QTreeNode node = new QTreeNode(children, x, y, size, Gui.averageColor(pixels, x, y, size));

            // Set the parent of children nodes to the current node
            for (QTreeNode child : children) {
                child.setParent(node);
            }

            return node;
        }
    }

    /**
     * Gets the root node of the quadrant tree.
     *
     * @return The root node of the quadrant tree.
     */
    public QTreeNode getRoot() {
        return root;
    }

    /**
     * Retrieves a list of pixels at a specific level of the tree.
     *
     * @param r        The root node of the tree.
     * @param theLevel The level in the tree to retrieve pixels from.
     * @return A list of pixels at the specified level.
     */
    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        // Return null if the root node is null or if the level is invalid
        if (r == null || theLevel < 0) {
            return null;
        }

        // If the current node is at the desired level or it's a leaf, return a list containing this node
        if (theLevel == 0 || r.isLeaf()) {
            return new ListNode<QTreeNode>(r);
        }

        // Initialize a list to store pixels at the specified level
        ListNode<QTreeNode> list = null;

        // Traverse through the child nodes recursively
        for (int i = 0; i < 4; i++) {
            // Recursively retrieve pixels from child nodes at the next level
            ListNode<QTreeNode> childList = getPixels(r.getChild(i), theLevel - 1);

            // Append child pixels to the list
            if (childList != null) {
                if (list == null) {
                    list = childList;
                } else {
                    ListNode<QTreeNode> temp = list;
                    while (temp.getNext() != null) {
                        temp = temp.getNext();
                    }
                    temp.setNext(childList);
                }
            }
        }

        return list;  // Return the list of pixels at the specified level
    }


    /**
     * Finds matching nodes in the tree based on color similarity.
     *
     * @param r         The root node of the tree.
     * @param theColor  The color to match against.
     * @param theLevel  The level in the tree to search.
     * @return A Duple containing a list of matching nodes and the count of matches.
     */
    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        // If the current node is a leaf or the level is 0, check if color matches
        if (r.isLeaf() || theLevel == 0) {
            if (Gui.similarColor(r.getColor(), theColor)) {
                ListNode<QTreeNode> list = new ListNode<>(r);
                return new Duple(list, 1);  // Return a Duple containing the matching node and count 1
            } else {
                return new Duple(null, 0);  // Return a Duple with null list and count 0
            }
        }

        ListNode<QTreeNode> list = null;  // Initialize a list to store matching nodes
        int count = 0;  // Initialize a count to keep track of matching nodes

        if (theLevel > 0) {
            // Traverse through the child nodes recursively
            for (int i = 0; i < 4; i++) {
                // Recursively find matching nodes in child nodes
                Duple childDuple = findMatching(r.getChild(i), theColor, theLevel - 1);

                // Extract child list and count from the Duple
                if (childDuple != null) {
                    ListNode<QTreeNode> childList = childDuple.getFront();
                    int childCount = childDuple.getCount();

                    // Append child list to the main list
                    if (childList != null) {
                        if (list == null) {
                            list = childList;
                        } else {
                            ListNode<QTreeNode> temp = list;
                            while (temp.getNext() != null) {
                                temp = temp.getNext();
                            }
                            temp.setNext(childList);
                        }
                        count += childCount;  // Increment the count by child count
                    }
                }
            }
        }

        return new Duple(list, count);  // Return a Duple containing the list of matching nodes and count
    }

    /**
     * Finds a node in the tree at a specific level and position.
     *
     * @param r         The root node of the tree.
     * @param theLevel  The level in the tree to search.
     * @param x         The x-coordinate of the node to find.
     * @param y         The y-coordinate of the node to find.
     * @return The node found at the specified position and level.
     */
    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
        // Return null if the root node is null or the specified point is not within the quadrant
        if (r == null || !r.contains(x, y)) {
            return null;
        }

        if (theLevel == 0 || r.isLeaf()) {
            return r;
        }

        for (int i = 0; i < 4; i++) {
            QTreeNode child = r.getChild(i);
            if (child != null && child.contains(x, y)) {
                return findNode(child, theLevel - 1, x, y);
            }
        }

        return null;
    }
}
