/** 
 * Nodes in the BitTree.
 */
class BitTreeNode {
  
  // +--------+-----------------------------------------------------------
  // | Fields |
  // +--------+-----------------------------------------------------------

  /** The tree to the left of this node. */
  BitTreeNode left;
  /** The tree to the right of this node. */
  BitTreeNode right;

  // +-------------+------------------------------------------------------
  // | Constructor |
  // +-------------+------------------------------------------------------

  /**
   *  Make a new node with the given data.
   */
  BitTreeNode() {
    // Initialize the left and right nodes to null.
    this.left = this.right = null;
  } // BitTreeNode()
  
} // class BitTreeNode

// +----------+-----------------------------------------------------------
// | Subclass |
// +----------+-----------------------------------------------------------

/**
 * Nodes in the BitTree that store data.
 */
class BitTreeLeaf extends BitTreeNode {

  /** The value stored in this node. */
  String data;

  /**
   * Make a new leaf node with the given data. Because this is a
   * leaf node, it will not have any pointers to children nodes.
   */
  BitTreeLeaf(String data) {
    // Store the data given in the current leaf node.
    this.data = data;
  } // BitTreeLeaf(T)

} // class BitTreeLeaf