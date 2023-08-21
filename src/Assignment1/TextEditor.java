package Assignment1;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;

public class TextEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JLabel timeLabel;
    public TextEditor() {
        createGUI();
    }

    private void createGUI() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        updateTimeLabel(); // Initialize time and date


        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu searchMenu = new JMenu("Search");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem printMenuItem = new JMenuItem("Print");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem searchMenuItem = new JMenuItem("Search");
        JMenuItem scpcMenuItem = new JMenuItem("SCPC");
        JMenuItem timeDateMenuItem = new JMenuItem("Time and Date");
        JMenuItem aboutMenuItem = new JMenuItem("About");


        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(printMenuItem);
        fileMenu.add(exitMenuItem);
        searchMenu.add(searchMenuItem);
        viewMenu.add(scpcMenuItem);
        viewMenu.add(timeDateMenuItem);
        helpMenu.add(aboutMenuItem);


        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        printMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        searchMenuItem.addActionListener(this);
        scpcMenuItem.addActionListener(this);
        timeDateMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);


        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                openNewWindow();
                setTitle("Text Editor");
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Print":
                printText();
                break;
            case "Exit":
                dispose();
                break;
            case "Search":
                search();
                break;
            case "SCPC":
                // Implement select, copy, paste, cut
                break;
            case "Time and Date":
                insertTimeAndDate();
                break;
            case "About":
                showAbout();
                break;

        }
    }