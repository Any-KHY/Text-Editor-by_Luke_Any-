import javax.swing.*;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

import java.awt.*;

public class TextPanel extends JFrame{

    public TextPanel(JFrame frame, String sourceType){

        JPanel textPanel = new JPanel(new BorderLayout());

        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(sourceType);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        textPanel.add(scrollPane); // set scroll pane

        frame.setContentPane(textPanel); // add panel to frame
        frame.pack(); // to fit the frame
        frame.setVisible(true); // set visable
    }

    // Test Purpose

    public TextPanel(){

        JPanel textPanel = new JPanel(new BorderLayout());
        JFrame frame = new JFrame();

        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        textPanel.add(scrollPane);
        frame.add(textPanel);


        frame.setSize(500,500);
        frame.setContentPane(textPanel);
        frame.setVisible(true);
        //frame.pack();
    }

    // for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextPanel();
            }
        });
    }








}
