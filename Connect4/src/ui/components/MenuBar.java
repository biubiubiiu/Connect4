package ui.components;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MenuBar extends JMenuBar {

    Map<String, JMenuItem> map = new HashMap<>();

    public MenuBar() {
        super();
        JMenu start = new JMenu();
        addMenu(start, "New Game", e -> {
            int choice = JOptionPane.showOptionDialog(null,
                    "You really want to quit?",
                    "Quit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            // interpret the user's choice
            if (choice == JOptionPane.YES_OPTION) {

            }
        });
        addMenu(start, "Save", e -> {
        });
        addMenu(start, "Exit", e -> {
            int choice = JOptionPane.showOptionDialog(null,
                    "You really want to quit?",
                    "Quit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            // interpret the user's choice
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        this.add(start);
    }

    /**
     * 对于添加菜单方法的简单封装
     *
     * @param parent   父菜单
     * @param name     菜单项名
     * @param callback 回调函数
     */
    public static void addMenu(JMenu parent, String name, ActionListener callback) {
        if (parent == null) {
            return;
        }
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(callback);
        parent.add(item);
    }
}
