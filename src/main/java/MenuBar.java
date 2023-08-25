import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuBar extends Component {

    public static JMenuBar createMenuBar(ActionListener listener){

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        String[] fileItems = {"New", "Open", "Save", "Print", "Convert to PDF", "Exit"};
        addMenuItemsWithListener(fileMenu, fileItems, listener);

        JMenu displayMenu = new JMenu("Display Setting");
        JMenu fontStyleMenu = new JMenu("Font Style");
        JMenu fontSizeMenu = new JMenu("Font Size");
        JMenu fontColorMenu = new JMenu("Font Color");

        menuBar.add(displayMenu);
        displayMenu.add(fontStyleMenu);
        displayMenu.add(fontSizeMenu);
        displayMenu.add(fontColorMenu);

        String[] fontStyleItems = {"Helvetica", "Calibri", "Times New Roman","Comic Sans MS","Impact"};
        addMenuItemsWithListener(fontStyleMenu, fontStyleItems, listener);

        String[] fontSizeItems = {"8", "12", "24","36", "72"};
        addMenuItemsWithListener(fontSizeMenu, fontSizeItems, listener);

        String[] fontColorItems = {"Black", "Red", "Blue", "Dark Gray"};
        addMenuItemsWithListener(fontColorMenu, fontColorItems, listener);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        String[] editItems = {"Select All", "Copy", "Paste", "Cut"};
        addMenuItemsWithListener(editMenu, editItems, listener);

        JMenu otherMenu = new JMenu("Other");
        menuBar.add(otherMenu);
        String[] functionItems = {"Search", "Time and Date"};
        addMenuItemsWithListener(otherMenu, functionItems, listener);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        String[] helpItems = {"Help", "About"};
        addMenuItemsWithListener(helpMenu, helpItems, listener);

        return menuBar;
    }

    private static void addMenuItemsWithListener(JMenu menu, String[] itemNames, ActionListener listener) {
        for (String itemName : itemNames) {
            JMenuItem item = new JMenuItem(itemName);
            item.addActionListener(listener);
            menu.add(item);
        }
    }
}
