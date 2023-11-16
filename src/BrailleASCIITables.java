import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Store the translation information (from ASCII to braille, from
 * braille to ASCII, and from braille to Unicode) as bit trees.
 * 
 * @author Albert-Kenneth Okine
 */
public class BrailleASCIITables {

  // +--------+-----------------------------------------------------------
  // | Fields |
  // +--------+-----------------------------------------------------------

  // Store the ASCII to braille encodings in a bit tree.
  BitTree asciiTree = new BitTree(6);
  // Store the braille to ASCII encodings in a bit tree.
  BitTree brailleTree = new BitTree(8);
  // Store the braille to Unicode encodings in a bit tree.
  BitTree unicodeTree = new BitTree(6);

  // +--------------+-----------------------------------------------------
  // | Constructors |
  // +--------------+-----------------------------------------------------

  /**
   * Initially construct the translation trees, loading in each with the
   * corresponding soure to target conversions.
   */
  BrailleASCIITables() {
    try { // Try to create the translation trees with the given filenames.
      this.asciiTree.load(new FileInputStream("src/BrailleToASCII.txt"));
      this.brailleTree.load(new FileInputStream("src/ASCIIToBraille.txt"));
      this.unicodeTree.load(new FileInputStream("src/BrailleToUnicode.txt"));
    } catch (FileNotFoundException e) {
    } // try (loading conversion trees with the given tables)
  } // BrailleASCIITables()

  // +---------+----------------------------------------------------------
  // + Methods |
  // +---------+----------------------------------------------------------

  /**
   * Converts an ASCII character to a string of bits representing the
   * corresponding braille character.
   */
  String toBraille(char letter) {
    try { // Get and return the corresponding braille character.
      return this.brailleTree.get(String.format(
          "%8s", // Pad the binary representation with leading spaces.
          Integer.toBinaryString((int) letter)) // Convert to binary.
        .replace(" ", "0")); // Replace leading spaces with 0s.
    } catch (Exception e) { // 
      return "oof" + e; 
    } 
  } // toBraille(char)

  /**
   * Converts a string of bits representing a braille character to the
   * corresponding ASCII character.
   */
  String toASCII(String bits) {
    // Return the final representation of bits in ASCII.
    return this.convertString(bits, this.asciiTree, false); 
  } // toASCII(String)

  /**
   * Converts a string of bits representing a braille character to the
   * corresponding Unicode braille character for those bits.
   * 
   * @implNote Only supports six-bit braille characters.
   */
  String toUnicode(String bits) {
    // Return the final Unicode representation of bits in Unicode.
    return this.convertString(bits, this.unicodeTree, true);
  } // toUnicode(String)

  // +----------------+---------------------------------------------------
  // | Helper Methods |
  // +----------------+---------------------------------------------------

  /**
   * Convert the string of bits representing a braille character into
   * the corresponding character of the tree given.
   */
  private String convertString(String bits, BitTree tree, boolean asUnicode) {
    String str = ""; // Create a string to append the characters to.
    try { // Iterate through all six-bit characters, converting them.
      for (int bitChar = 6; bitChar <= bits.length(); bitChar += 6) {
        String convertStr = tree.get(bits.substring(bitChar - 6, bitChar));
        if (asUnicode) {
          int hex = Integer.parseInt(convertStr, 16);  // Find base-16 value.
          convertStr = String.valueOf(Character.toChars(hex)); // Update str.
        } // if (convert to unicode instead of ascii)
        // TODO: debuging System.out.println(convertStr);
        str += convertStr;
      } // for
    } catch (Exception e) { System.out.println("Error in convertStr " + e); }
    return str; // Return the final representation of bits.
  } // convertString(String, BitTree)

  /**
   * Converts a string of ASCII characters to a string of bits, 
   * each representing the corresponding braille character.
   */
  String toBraille(String letters) {
    String str = ""; // Create a string to append the bits to.
    for (int i = 0; i < letters.length(); i ++) 
      str += this.toBraille(letters.charAt(i)); 
    return str; // Return the final representation in bits.
  } // toBraille(String)

  public static void main(String[] args) { 
    BrailleASCIITables table = new BrailleASCIITables();

    System.out.println(table.toUnicode("110000100000"));
    //try { table.unicodeTree.dump(new java.io.PrintWriter(System.out, true));
    //} catch (Exception e) { System.out.println("uhoh"); }

    System.out.println(table.toBraille('h'));
    System.out.println(table.toASCII(table.toBraille("hello")));

    System.out.println("0" + Integer.toBinaryString((int) 'a'));
    table.brailleTree.dump(new java.io.PrintWriter(System.out, true));
  } // main(String[])

} // class BrailleASCIITables