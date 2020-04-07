package utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuFactory {

    public static JMenu addMenu(JMenu parent, String name, ActionListener callback) {
        if(parent == null) { return null; }
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(callback);
        return parent;
    }
}
