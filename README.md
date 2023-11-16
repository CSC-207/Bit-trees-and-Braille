## Bit-trees-and-Braille
Exploring mechanisms for representing text in standard Braille and
converting between Braille and other representations.

## Classes
### BrailleASCIITables
Take two command-line paramters, the first of which represents the
target character set and the second of which represents the source
characters, and translates the text.

### BrailleASCII
Store the translation information (from ASCII to braille, from braille
to ASCII, and from braille to Unicode) as BitTrees. Each is stored in
a text file in the format `SOURCEtoTARGET.txt`.

### BitTree
Stores the mappings from bits to values. 

#### BitTreeNode
Nodes in the BitTree.

#### BitTreeLeaf
Subclass of BitTreeNode that stores data.

## Usage
    BrailleASCII <TARGET> <SOURCE>

```
$ java BrailleASCII braille hello
110010100010111000111000101010
$ java BrailleASCII ascii 110010100010111000111000101010
hello
$ java BrailleASCII unicode hello
⠓⠑⠇⠇⠕
```

## Acknowledgements
- Project page https://rebelsky.cs.grinnell.edu/Courses/CSC207/2023Fa/mps/mp08.html