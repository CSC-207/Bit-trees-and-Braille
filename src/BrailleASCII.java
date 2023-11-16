import java.io.PrintWriter;

/**
 * Take in two command-line parameters, the first of which represents
 * the source characters, and the second of which represents the source
 * characters, and then translates the text.
 * 
 * @author Albert-Kenneth Okine
 */
public class BrailleASCII {
  /** 
   * <pre>
   * BrailleASCII TARGET SOURCE
   * </pre>
   */
  public static void main(String[] args) {
    // Create a new PrintWriter object directed to StdOut.
    PrintWriter pen = new PrintWriter(System.out, true);
    // Check that the user entered both command-line parameters.
    if (args.length != 2) {
      pen.println("BrailleASCII TARGET CHARACTERS SOURCE CHARACTERS");
    } else {
      // Translate the text, then print it to the console.
      pen.println( translateText(args[0], args[1]) );
    } // if...else
  } // main(String[])

  /** 
   * Translate the text of the source to the target format, returning
   * the final, translated text.
   */
  static String translateText(String target, String source) {
    // Make a new object storing the translation information.
    BrailleASCIITables table = new BrailleASCIITables();
    switch (target) { // Return the translation of the source.
      case "braille": return table.toBraille( source );
      case "ascii":   return table.toASCII(   source );
      case "unicode": return table.toUnicode( source );
      default: return "Invalid target character set " + target;
    } // switch (target character set)
  } // translateText(String, String)

} // class BrailleASCII
