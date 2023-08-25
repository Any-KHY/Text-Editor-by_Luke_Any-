import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;


public class TextArea extends JPanel{
    private RSyntaxTextArea textArea;

    public TextArea(Rectangle bounds){
        panelSetUp(bounds);
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        add(scrollPane);
    }

    public String fileType(String fileExtension){
        String fileType;
        switch (fileExtension) {
            case "txt", "rtf", "odt":
            default:
                fileType = SyntaxConstants.SYNTAX_STYLE_NONE;
                break;
            case "csv":
                fileType = SyntaxConstants.SYNTAX_STYLE_CSV;
                break;
            case "java":
                fileType = SyntaxConstants.SYNTAX_STYLE_JAVA;
                break;
            case "c":
                fileType = SyntaxConstants.SYNTAX_STYLE_C;
                break;
            case "cpp":
                fileType = SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS;
                break;
            case "py":
                fileType = SyntaxConstants.SYNTAX_STYLE_PYTHON;
                break;
        }
        return fileType;
    }


    public void panelSetUp(Rectangle bounds){
        setLayout(new BorderLayout());
        setBounds(bounds);
        setOpaque(false);
    }

    public RSyntaxTextArea getTextArea() {
        return textArea;
    }
    public void resetTextArea(String fileExtension) {
        textArea.setSyntaxEditingStyle(fileType(fileExtension));
        textArea.setCodeFoldingEnabled(true);
    }

    public void defaulTextArea(TextEditorConfig config) {
        textArea.setFont(new Font(config.getDefaultFont(), Font.PLAIN, config.getDefaultFontSize()));
        textArea.setForeground(config.getDefaultFontColour());
    }

    public void setFontStyle(String fontName) {
        int currentFontSize = textArea.getFont().getSize();
        createFont(fontName,currentFontSize);
    }

    public void createFont(String fontName, int fontSize){
        switch (fontName) {
            case "Helvetica":
                textArea.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
                break;
            case "Calibri":
                textArea.setFont(new Font("Calibri", Font.PLAIN, fontSize));
                break;
            case "Times New Roman":
                textArea.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
                break;
            case "Comic Sans MS":
                textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
                break;
            case "Impact":
                textArea.setFont(new Font("Impact", Font.PLAIN, fontSize));
                break;
        }
    }

    public void setFontSize(String fontSizeStr) {
        int newFontSize = Integer.parseInt(fontSizeStr);
        Font currentFont = textArea.getFont();
        textArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), newFontSize));
    }

    public void setFontColor(String fontColor) {
        switch (fontColor) {
            case "Black":
                textArea.setForeground(Color.BLACK);
                break;
            case "Red":
                textArea.setForeground(Color.RED);
                break;
            case "Blue":
                textArea.setForeground(Color.BLUE);
                break;
            case "Dark Gray":
                textArea.setForeground(Color.DARK_GRAY);
                break;
        }
    }
}