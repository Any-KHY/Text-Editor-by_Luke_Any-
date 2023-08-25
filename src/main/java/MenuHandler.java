import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static java.lang.System.exit;

public class MenuHandler implements ActionListener {
    private TextEditor textEditor;
    private JMenuBar menu;

    public MenuHandler(TextEditor textEditor){
        this.textEditor = textEditor;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                textEditor.openNewWindow();
                //textEditor.setTitle("Text Editor");
                break;

            case "Open":
                textEditor.openFile();
                break;

            case "Save":
                textEditor.saveFile();
                break;

            case "Print":
                textEditor.printText();
                break;

            case "Exit":
                exit(0);
                break;

            case "Search":
                textEditor.search();
                break;

            case "Convert to PDF":
                try {
                    PDFConversion.convertToPDF();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;

            case "Select All":
                textEditor.selectText();
                break;
            case "Copy":
                textEditor.copyText();
                break;
            case "Paste":
                textEditor.pasteText();
                break;
            case "Cut":
                textEditor.cutText();
                break;

            case "Time and Date":
                textEditor.insertTimeAndDate();
                break;
            case "About":
                textEditor.showAbout();
                break;

        }
    }
}
