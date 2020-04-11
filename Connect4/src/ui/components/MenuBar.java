package ui.components;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Raymond
 */
public class MenuBar extends JMenuBar {

    /**
     * 在此添加菜单事件
     */
    public interface MenuBarEvent {
        /**
         * 新游戏
         */
        void newGame();

        /**
         * 存档
         */
        void saveGame();

        /**
         * 读档
         */
        void loadArchive();

        /**
         * 退游
         */
        void exit();

        /**
         * 打开设置页
         */
        void viewSettings();

        /**
         * 使计时器暂停计时
         */
        void hangOn();

        /**
         * 使计时器继续计时
         */
        void goOn();
    }

    MenuBarEvent handler;

    public void setHandler(MenuBarEvent handler) {
        this.handler = handler;
    }

    public MenuBar() {
        super();
        JMenu start = new JMenu("Start");
        addMenu(start, "New Game", e -> {
            handler.hangOn();

            int choice = JOptionPane.showOptionDialog(null,
                    "Start a new game?",
                    "Start",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            if (choice == JOptionPane.YES_OPTION) {
                handler.newGame();
            }
            else {
                handler.goOn();
            }
        });
        addMenu(start, "Save", e -> handler.saveGame());
        addMenu(start, "Load", e -> handler.loadArchive());
        addMenu(start, "Settings", e -> handler.viewSettings());
        addMenu(start, "Exit", e -> {
            handler.hangOn();

            int choice = JOptionPane.showOptionDialog(null,
                    "You really want to quit?",
                    "Quit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            if (choice == JOptionPane.YES_OPTION) {
                handler.exit();
            }
            else {
                handler.goOn();
            }
        });
        this.add(start);

        JMenu help = new JMenu("Help");
        addMenu(help, "How to play", e -> {
            handler.hangOn();
            JOptionPane.showMessageDialog(null,
                    "Click on the buttons or press 1-7 on your keyboard to insert a new checker."
                            + "\nTo win you must place 4 checkers in an row, horizontally, vertically or diagonally.",
                    "How to Play", JOptionPane.INFORMATION_MESSAGE);
            handler.goOn();
        });
        this.add(help);

    }

    /**
     * 对于添加菜单方法的简单封装
     *
     * @param handler  父菜单
     * @param name     菜单项名
     * @param callback 回调函数
     */
    void addMenu(JMenu handler, String name, ActionListener callback) {
        if (handler == null) {
            return;
        }
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(callback);
        handler.add(item);
    }
}
