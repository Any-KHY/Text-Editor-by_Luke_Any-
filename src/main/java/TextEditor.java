import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDateTime;


public class TextEditor extends Component implements ActionListener{

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static JFrame mainFrame;
    private static TextEditorConfig config;


    private TextArea textArea;
    private JMenuBar menuBar;
    private JLabel timeLabel;
    private Rectangle bounds = new Rectangle(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);

    public static String fileExtension;

    public TextEditor(TextEditorConfig config) {
        this.config = config;
        System.out.println("Config: " + config);
        createOuterFrame();
        textArea.resetTextArea(config);
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

        // Set location and visibility
        mainFrame.setLocationRelativeTo(null); // set location to the center
        mainFrame.setVisible(true); // set visible

    }

    public void updateTimeLabel() {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.toString();
        timeLabel.setText(formattedDateTime);
    }

    public void openNewWindow() {
        TextEditor newEditor = new TextEditor(config);
        newEditor.mainFrame.setLocation(mainFrame.getX() + 50, mainFrame.getY() + 50);
        //newEditor.setLocation(mainFrame.getX() + 50, mainFrame.getY() + 50);
        newEditor.mainFrame.setVisible(true);
    }
    private void openOdtFile(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            textArea.setText(extractor.getText());
            setTitle("Text Editor - " + file.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading the ODT file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openFile() {

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(textFilter);

        FileNameExtensionFilter odtFilter = new FileNameExtensionFilter("OpenDocument Text Files" , "odt");
        fileChooser.addChoosableFileFilter(odtFilter);

        FileNameExtensionFilter sourceCodeFilter = new FileNameExtensionFilter("Source Code Files", "java","py","cpp");
        fileChooser.addChoosableFileFilter(sourceCodeFilter);

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            extractFileExtension(selectedFile.getName());

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

    public void saveFile() {

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(textFilter);

        FileNameExtensionFilter odtFilter = new FileNameExtensionFilter("OpenDocument Text Files" , "odt");
        fileChooser.addChoosableFileFilter(odtFilter);

        FileNameExtensionFilter sourceCodeFilter = new FileNameExtensionFilter("Source Code Files", "java","py","cpp");
        fileChooser.addChoosableFileFilter(sourceCodeFilter);

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String outputPath = selectedFile.getAbsolutePath();
//            if(!selectedFile.getName().endsWith(".txt")){
//                selectedFile.renameTo(new File(outputPath + "txt"));
//            }
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

    public void convertToPDF() throws IOException {

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(pdfFilter);

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String outputPath = selectedFile.getAbsolutePath();
            try {

                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                    String text = textArea.getTextArea().getText();
                    int fontSize = textArea.getTextArea().getFont().getSize();
                    String fontFamily = textArea.getTextArea().getFont().getFamily();
                    //PDFont fontType = PDType1Font.HELVETICA;
                    PDFont fontType;
                    Color fontColor = textArea.getTextArea().getForeground();
                    if (fontFamily.equalsIgnoreCase("Arial")) {
                        fontType = PDType1Font.HELVETICA;
                    } else if (fontFamily.equalsIgnoreCase("Times New Roman")) {
                        fontType = PDType1Font.TIMES_ROMAN;
                    } else {
                        fontType = PDType1Font.HELVETICA; //default
                    }

                    PDRectangle mediaBox = page.getMediaBox();

                    float margin;

                    if(fontSize < 50/2 ) {
                        margin = 50;
                    } else {
                        margin = fontSize/2 + 30;
                    }

                    float startY = mediaBox.getHeight() - margin;
                    float startX = margin;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, startY);

                    contentStream.setFont(fontType, fontSize);
                    contentStream.setNonStrokingColor(fontColor);

                    contentStream.setLeading(fontSize*1.5f);

                    String[] lines = text.split("\n");
                    for (String line : lines) {
                        contentStream.showText(line);
                        contentStream.newLine();
                    }
                    contentStream.endText();
                }

                document.save(outputPath);
                JOptionPane.showMessageDialog(mainFrame, "Text converted to PDF.", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        String dateTime = java.time.LocalDateTime.now().toString();
        textArea.getTextArea().insert(dateTime+"\n" , 0);
    }

    public void showAbout() {
        String message = "Text Editor\n\nDeveloped by:\nLuke\nAny";
        JOptionPane.showMessageDialog(mainFrame, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }


    public void extractFileExtension(String fileName){
        // extract file type for syntax highlight
        int extensionDotIndex = fileName.lastIndexOf(".");
        if (extensionDotIndex > 0) {
            fileExtension = fileName.substring(extensionDotIndex + 1);
        }
    }

    public static void main(String[] args) {
        try {
            config = new TextEditorConfig();
            config = ConfigLoader.loadConfig("src/main/resources/config.yaml");
            System.out.println("Default Font Size: " + config.getDefaultFontSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TextEditor(config));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
    public void windowClosing(WindowEvent e) {
        mainFrame.dispose(); // Close only this window
    }
}

