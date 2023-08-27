import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextEditor extends Component{

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static JFrame mainFrame;
    public static TextArea textArea;

    private static TextEditorConfig config;

    static {
        try {
            config = ConfigLoader.loadConfig("src/main/resources/config.yaml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JMenuBar menuBar;
    private JLabel timeLabel;
    private Rectangle bounds = new Rectangle(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);

    public static String fileExtension;

    public TextEditor(TextEditorConfig config) {
        this.config = config;
        createOuterFrame();
        textArea.defaulTextArea(config);
    }

    private void createOuterFrame() {
        ActionListener actionListener = new MenuHandler(this);

        // Frame set up
        mainFrame = new JFrame();
        mainFrame.setTitle("Text Editor");
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Menu Bar
        menuBar = MenuBar.createMenuBar(actionListener);
        mainFrame.setJMenuBar(menuBar);

        // DisplayArea
        Container content = mainFrame.getContentPane();
        content.setLayout(new BorderLayout());
        textArea = new TextArea(bounds);
        content.add(textArea);

        // Update Time
        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        updateTimeLabel(); // Initialize time and date

        mainFrame.setLocationRelativeTo(null); // set location to the center
        mainFrame.setVisible(true);
    }

    public void updateTimeLabel() {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.toString();
        timeLabel.setText(formattedDateTime);
    }

    public void openNewWindow() {
        TextEditor newEditor = new TextEditor(config);
        newEditor.mainFrame.setLocation(mainFrame.getX() + 50, mainFrame.getY() + 50);
        newEditor.mainFrame.setVisible(true);
    }

    public void openFile() {

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text Files", "txt","csv");
        fileChooser.addChoosableFileFilter(textFilter);

        FileNameExtensionFilter odtFilter = new FileNameExtensionFilter("OpenDocument Text Files" , "odt");
        fileChooser.addChoosableFileFilter(odtFilter);

        FileNameExtensionFilter sourceCodeFilter = new FileNameExtensionFilter("Source Code Files", "java","py","cpp","c");
        fileChooser.addChoosableFileFilter(sourceCodeFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            extractFileExtension(selectedFile.getName());
            if (fileExtension == "odt"){
                openOdtFile(selectedFile);
            } else {

                try {
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    textArea.resetTextArea(fileExtension);
                    textArea.getTextArea().setText("");
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        textArea.getTextArea().append(line + "\n");
                    }
                    mainFrame.setTitle("Text Editor - " + selectedFile.getName());
                    bufferedReader.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Error reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void openOdtFile(File file) {
        try (
             FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            textArea.getTextArea().setText(extractor.getText());
            mainFrame.setTitle("Text Editor - " + file.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading the ODT file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void saveFile() { //only allow saving in txt format

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(textFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showSaveDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String outputPath = selectedFile.getAbsolutePath();

            if(!outputPath.endsWith(".txt")){
                outputPath += ".txt";
                selectedFile = new File(outputPath);
            }

            extractFileExtension(selectedFile.getName());

            try {
                FileWriter fileWriter = new FileWriter(selectedFile);
                fileWriter.write(textArea.getTextArea().getText());
                fileWriter.close();
                textArea.resetTextArea(fileExtension);
                mainFrame.setTitle("Text Editor - " + selectedFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void printText() {
        try {
            textArea.getTextArea().print();
        } catch (java.awt.print.PrinterException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error printing the text.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void search() {
        String searchText = JOptionPane.showInputDialog(mainFrame, "Enter search text:");
        if (searchText != null && !searchText.isEmpty()) {
            String text = textArea.getTextArea().getText();
            int index = text.indexOf(searchText);
            if (index != -1) {
                textArea.getTextArea().setCaretPosition(index);
                textArea.getTextArea().select(index, index + searchText.length());
            } else {
                JOptionPane.showMessageDialog(this, "Text not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void selectText() {
        textArea.getTextArea().selectAll();
    }

    public void copyText() {
        textArea.getTextArea().copy();
    }

    public void pasteText() {
        textArea.getTextArea().paste();
    }

    public void cutText() {
        textArea.getTextArea().cut();
    }

    public void insertTimeAndDate() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        String dateTime = currentTime.format(formatter);
        textArea.getTextArea().insert(dateTime+"\n" , 0);
    }

    public void showAbout() {
        String message = "251-Assignment1-2023-Luke-Any\n\nText Editor\n\nDeveloped by:\nLuke\nAny";
        JOptionPane.showMessageDialog(mainFrame, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showHelp() {
        String message = "How to Use the Text Editor:\n\n"
                + "1. File Menu:\n"
                + "   - New: Create a new text editor window.\n"
                + "   - Open: Open an existing text file.\n"
                + "   - Save: Save the file in .txt format.\n"
                + "   - Print: Print the current document.\n"
                + "   - Convert to PDF: Convert and save the file as a PDF document.\n"
                + "   - Exit: Close the current text editor.\n\n"
                + "2. Display Settings:\n"
                + "   - Font Style: Change the font style for text.\n"
                + "   - Font Size: Change the font size for text.\n"
                + "   - Font Color: Change the font color for text.\n\n"
                + "3. Edit Menu:\n"
                + "   - Select All: Select all text in the editor.\n"
                + "   - Copy: Copy selected text to the clipboard.\n"
                + "   - Paste: Paste text from the clipboard.\n"
                + "   - Cut: Cut selected text and copy it to the clipboard.\n\n"
                + "4. Other Menu:\n"
                + "   - Search: Search for particular text within the document.\n"
                + "   - Time and Date: Insert the current date and time at the 1st line.\n\n";
        JOptionPane.showMessageDialog(mainFrame, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }


    public void extractFileExtension(String fileName){ // for syntax highlight
        int extensionDotIndex = fileName.lastIndexOf(".");
        if (extensionDotIndex > 0) {
            fileExtension = fileName.substring(extensionDotIndex + 1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextEditor(config));
    }


}