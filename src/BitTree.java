import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Store the mappings from bits to values.
 * 
 * @author Albert-Kenneth Okine
 */
public class BitTree {

  // +--------+-----------------------------------------------------------
  // | Fields |
  // +--------+-----------------------------------------------------------

  /** Pointer to the root node in the BitTree. */
  BitTreeNode root;

  /** The length of each bit location in this BitTree */
  int levels;

  // +--------------+-----------------------------------------------------
  // | Constructors |
  // +--------------+-----------------------------------------------------

  /**
   * Build a tree that will store mappings from strings of n bits to
   * strings.
   * 
   * @implNote Nodes in the tree are not created until needed.
   */
  BitTree(int n) {
    // Construct a new BitTree at the node value.
    this.root = new BitTreeNode();
    // 
    this.levels = n;
  } // BitTree(int)

  // +---------+----------------------------------------------------------
  // + Methods |
  // +---------+----------------------------------------------------------

  /** 
   * Follow the path through the tree given by bits, (adding nodes as
   * appropriate) and adds or replaces the value at the end with value.
   * 
   * @throws IllegalArgumentException If bits is the inappropriate length
   *                                  or contains values other than 0 or 1.
   */
  void set(String bits, String value) throws IllegalArgumentException {
    if (!isValidBits(bits)) { // Throw a new exception.
      throw new IllegalArgumentException("Invalid bits given: " + bits);
    } else this.root = this.setHelper(bits, value, this.root);
  } // set(String, String)

  /**
   * Follows the path through the tree given by bits, returning the
   * value at the end. 
   * 
   * @throws IllegalArgumentException If there is no such path, or if bits
   *                                  is the incorrect length.
   */
  String get(String bits) throws IllegalArgumentException {
    if (!isValidBits(bits)) { // Throw a new exception.
      throw new IllegalArgumentException("Invalid bits given: " + bits);
    } else return this.getHelper(bits, this.root);
  } // get(String)

  /**
   * Print the contents of the tree in CSV format. For example, one
   * row of our braille tree will be "101100,M".
   */
  void dump(PrintWriter pen) {
    this.dumpHelper(pen, this.root, "");
  } // dump(PrintWriter)

  /**
   * Read a series of lines of the form `bits, values`, and stores
   * them in the tree.
   */
  void load(InputStream source) {
    try { // Try to read in data from the soure InputStream given.
      Scanner scanner = new Scanner(source);
      // Iteratively read the next line, and store them in the tree.
      while (scanner.hasNextLine()) {
        // Split the next line according to ", " or ",".
        String[] line = scanner.nextLine().split(",");
        this.set(line[0], line[1]);
      } scanner.close(); // Close the scanner once we are done.
    } catch (Exception e) { // Report the error back to the user.
      System.out.println("Error loading in from InputStream " + source);
      System.out.println(e);
    } // try...catch
  } // load(InputStream)

  // +----------------+---------------------------------------------------
  // | Helper Methods |
  // +----------------+---------------------------------------------------

  /**
   * Check if the string of bits given is a valid bit, meaning that it
   * has the appropriate length (this.levels) and each character is 
   * either a '1' or a '0'.
   */
  private boolean isValidBits(String bits) {
    return ((bits.length() == this.levels) // Compare the string length.
            && (bits.replace("1", "") // Empty the string of all 1s.
                    .replace("0", "") // Empty the string of all 0s.
                    .equals("")));    // Check the string is empty.
  } // isValidBits(String)

  /**
   * Recursively traverse through the BitTree using the bit location given,
   * returning the modified subtree.
   * 
   * @implNote Assumes that the bit location given is valid.
   */
  private BitTreeNode setHelper(String bits, String value, BitTreeNode node) {
    if (bits.length() == 0) { // Return the updated BitTreeLeaf.
      return new BitTreeLeaf(value);
    } else { // Traverse the rest of the BitTree.
      if (node == null) node = new BitTreeNode(); // Make a new BitTreeNode.
      
      if (bits.charAt(0) == '0') { // Recurse through the left subtree.
        node.left = setHelper(bits.substring(1), value, node.left);
        return node; // Return the new tree with the proper leaf modified.
      } else {                     // Recurse through the right subtree.
        node.right = setHelper(bits.substring(1), value, node.right);
        return node; // Return the new tree with proper the leaf modified.
      } // else
    } // else
  } // setHelper(String, String, BItTreeNode)

  /**
   * Recursively traverse through the BitTree using the bit location given,
   * returning the associated String data.
   * 
   * @implNote Assumes that the bit location given is valid, and that the
   *           node at the location was previously initialized.
   */
  private String getHelper(String bits, BitTreeNode node) 
    throws IllegalArgumentException
  { // Check if we have reached the given bit location.
    if (bits.length() == 0) { // Return the data at that location.
      return ((BitTreeLeaf) node).data;
    } else { // Traverse the rest of the BitTree.
      if (node == null) throw new IllegalArgumentException();
      return (bits.charAt(0) == '0')
        ? getHelper(bits.substring(1), node.left)   // Recurse left tree.
        : getHelper(bits.substring(1), node.right); // Recurse right tree.
    } // else
  } // getHelper(String, BitTreeNode)

  /**
   * Recursivly traverse through the BitTree, updating the String bits with
   * the current bit location, and print out the data and bit location for
   * each BitTreeLeaf.
   */
  private void dumpHelper(PrintWriter pen, BitTreeNode node, String bits) {
    if (node instanceof BitTreeLeaf) { // Check for the terminating condition/
      pen.printf("%s,%s\n", bits, ((BitTreeLeaf) node).data);
    } else if (node != null) { // Traverse the rest of the BitTree.
      dumpHelper(pen, node.left, bits + "0");  // Recruse left tree
      dumpHelper(pen, node.right, bits + "1"); // Recruse right tree.
    } // if...else
  } // dumpHelper(PrintWriter pen, BitTreeNode, String)

} // class BitTree