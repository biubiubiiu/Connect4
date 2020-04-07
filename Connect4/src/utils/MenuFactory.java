package utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuFactory {


    public static void addMenu(JMenu parent, String name, ActionListener callback) {
        if (parent == null) {
            return;
        }
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(callback);
        parent.add(item);
    }
}
