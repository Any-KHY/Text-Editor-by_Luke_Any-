import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;


public class TextEditor extends Component implements ActionListener{

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEGHT = 600;
    public static JFrame mainFrame;

    //private JFrame mainFrame;
    private TextArea textArea;
    private JMenuBar menuBar;
    private JLabel timeLabel;
    private Rectangle bounds = new Rectangle(0,0,WINDOW_WIDTH, WINDOW_HEGHT);

    public static String fileExtension;

    public TextEditor(){
        createOuterFrame();
    }

    private void createOuterFrame() {
        ActionListener actionListener = new MenuHandler(this);

        // Frame set up
        mainFrame = new JFrame();
        mainFrame.setTitle("Text Editor");
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        TextEditor newEditor = new TextEditor();
        //newEditor.setLocation(mainFrame.getX() + 50, mainFrame.getY() + 50);
        newEditor.mainFrame.setVisible(true);
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

                    PDRectangle mediaBox = page.getMediaBox();
                    float margin = 50;
                    float width = mediaBox.getWidth() - 2 * margin;
                    float startY = mediaBox.getHeight() - margin;
                    float startX = margin;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.setLeading(14.5f);

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
        SwingUtilities.invokeLater(() -> new TextEditor());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}

