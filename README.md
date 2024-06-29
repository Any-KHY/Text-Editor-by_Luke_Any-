# 251-Assignment1-2023-Luke-Any - Simple Text Editor

### by : [Luke](https://github.com/lukew3531) & [Any](https://github.com/Any-KHY)

### **13065772 Luke Wakefield**

#### Main commits:
 - Gui code started #cd9369e
 - Updated GUI #617392c
 - Added SCPC functions #57ac764
 - Fixed closing windows issue #e1e40cc

### **22000531 Any KWOK Hoi Yi**

#### Main commits:
- Implement of Syntax_highlight feature #2be00e8
- Program Restructure #c726ef9
- Config File Set up #0f42190
- Amendment on PDF Convection Function #8bf5c13
- Implement of Display Format menu, Menu Bar Update #416c036
- Add Unit Test #f8a3f44


# Text Editor README

This is a simple Java text editor with a graphical user interface (GUI) that allows users to create, open, save, and manipulate text files. 

## Features

1. **Configuration File for Font Type**
   - A configuration file (`config.yaml`) is included to customize the default displayed font format for the text editor.

2. **Menu Bar Functions**
   - **File:** Basic functions of Opening, saving, printing file and window control.
   - **Display Setting:** Font formatting for Display. 
   - **Edit:** Basic SCPC functions.
   - **Other:** Search and Time inserting feature.
   - **Help:** Basic information of the program. 


## How to Use

### File Menu:
1. **New**
   - Click on the "New" option in the File menu to open a new text editor window.

2. **Open**
   - Click on "Open" option in the File menu to browse and open an existing text file.
   - Supported file formats: `.txt`, `.csv`, `.odt`, `.java`, `.py`, `.cpp`,`.c`.

3. **Save**
   - Click on "Save" option in the File menu to save the file in `.txt` format.

4. **Print**
   - Click on "Print" option in the File menu to print the file.
   
5. **Convert to PDF**
   - Click on "Convert to PDF" option in the File menu to convert and save the file as a PDF document.

7. **Exit**
   - Click on "Exit" option in the File menu to close the current text editor.

### Display Menu:
1. **Font Style**
   - Click on the "Font Style" option in the Display menu to choose the display Font Style from list.

2. **Font Size**
   - Click on the "Font Size" option in the Display menu to choose the display Font Size from list.

3. **Font Color**
   - Click on the "Font Color" option in the Display menu to choose the display Font Color from list.

### Edit Menu:
1. **Select All**
   - Click on the "Select All" option in the Edit menu to Select All the text.

2. **Copy**
   - Click on the "Copy" option in the Edit menu to copy the selected content to clipboard.

3. **Paste**
   - Click on the "Paste" option in the Edit menu to Paste the copied content at the current position.
   
4. **Cut**
   - Click on the "Copy" option in the Edit menu to cut and save the selected content to clipboard. 

### Other Menu:
1. **Search**
   - Click on the "Search" option in the Other menu to search for specific content.

2. **Time and Date**
   - Click on "Time and Date" in the Other menu to insert the current date and time at the first line.

### Help Menu:
1. **Help**
   - Click on the "Help" option in the Other menu to recall the basic instructions of the program.

2. **About**
   - Click on "About" in the Help menu to get the information of the program.


## Tool Required

To use this text editor, you'll need to have Java and Swing installed. 
You can run the `TextEditor` class provided in the code with your own Computer.

## Configuration

The text editor allows you to configure font type by editing the `config.yaml` file in the `src/main/resources` directory.

## About

This text editor was developed by Luke(13065772) and Any(22000531).

For more information or to report issues, feel free to contact us.
