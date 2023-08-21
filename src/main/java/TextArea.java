import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

public class TextArea extends JPanel{
    private RSyntaxTextArea textArea;


    public TextArea(Rectangle bounds){

        // Panel Set up
        panelSetUp(bounds);

        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        add(scrollPane);
    }



    public TextArea(Rectangle bounds, String fileExtension){
        // Panel Set up
        panelSetUp(bounds);

        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(fileType(fileExtension));
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        add(scrollPane);
    }

    public String fileType(String fileExtension){
        String fileType;
        switch (fileExtension) {
            case "txt", "rtf", "odt":
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
            case "php":
                fileType = SyntaxConstants.SYNTAX_STYLE_PHP;
                break;
            case "py":
                fileType = SyntaxConstants.SYNTAX_STYLE_PYTHON;
                break;
            default:
                fileType = SyntaxConstants.SYNTAX_STYLE_NONE;
                break;
        }
        return fileType;
    }


    public void panelSetUp(Rectangle bounds){

        // Panel Set up
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
}
