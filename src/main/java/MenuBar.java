import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;

public class MenuBar extends Component {

    public static JMenuBar createMenuBar(ActionListener listener){

        JMenuBar menuBar = new JMenuBar();

        // File tab
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem printMenuItem = new JMenuItem("Print");
        JMenuItem convertToPDF = new JMenuItem("Convert to PDF");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(printMenuItem);
        fileMenu.add(convertToPDF);
        fileMenu.add(exitMenuItem);

        newMenuItem.addActionListener(listener);
        openMenuItem.addActionListener(listener);
        saveMenuItem.addActionListener(listener);
        printMenuItem.addActionListener(listener);
        convertToPDF.addActionListener(listener);
        exitMenuItem.addActionListener(listener);

        // Search tab
        JMenu searchMenu = new JMenu("Search");
        menuBar.add(searchMenu);

        JMenuItem searchMenuItem = new JMenuItem("Search");

        searchMenu.add(searchMenuItem);

        searchMenuItem.addActionListener(listener);


        // View tab
        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        JMenuItem scpcMenuItem = new JMenuItem("SCPC");
        JMenuItem timeDateMenuItem = new JMenuItem("Time and Date");

        viewMenu.add(scpcMenuItem);
        viewMenu.add(timeDateMenuItem);

        scpcMenuItem.addActionListener(listener);
        timeDateMenuItem.addActionListener(listener);

        // Help tab
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(listener);

        return menuBar;
    }
}
