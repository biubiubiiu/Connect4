package ui.components;

import core.Core;

import javax.swing.*;

/**
 * @author Raymond
 */
public class Settings extends JFrame {

    public interface SettingsEvent {
        /**
         * 修改游戏模式
         * @param mode 游戏模式
         */
        void changeGameMode(int mode);

        /**
         * 修改 ai 的搜索深度
         * @param depth 深度
         */
        void changeDepth(int depth);
    }

    private JComboBox<String> gameModeDropDown;
    private JComboBox<String> maxDepthDropDown;

    private SettingsEvent handler;

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 290;

    public Settings() {
        super("Settings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel gameModeLabel = new JLabel("Game mode: ");
        JLabel maxDepthLabel = new JLabel("Difficulty: ");

        this.add(gameModeLabel);
        this.add(maxDepthLabel);

        gameModeDropDown = new JComboBox<>();
        gameModeDropDown.addItem(Core.GAME_MODE.HUMAN_VS_HUMAN.description);
        gameModeDropDown.addItem(Core.GAME_MODE.HUMAN_VS_AI.description);

        gameModeDropDown.setSelectedIndex(0);

        maxDepthDropDown = new JComboBox<>();
        maxDepthDropDown.addItem("Depth 1");
        maxDepthDropDown.addItem("Depth 2 (Passive)");
        maxDepthDropDown.addItem("Depth 3");
        maxDepthDropDown.addItem("Depth 4 (Easy)");
        maxDepthDropDown.addItem("Depth 5");
        maxDepthDropDown.addItem("Depth 6 (Moderate)");
        maxDepthDropDown.addItem("Depth 7");
        maxDepthDropDown.addItem("Depth 8 (Tougher)");

        maxDepthDropDown.setSelectedIndex(3);

        add(gameModeDropDown);
        add(maxDepthDropDown);

        gameModeLabel.setBounds(25, 55, 175, 30);
        maxDepthLabel.setBounds(25, 105, 175, 30);

        gameModeDropDown.setBounds(195, 55, 160, 30);
        maxDepthDropDown.setBounds(195, 105, 160, 30);

        JButton apply = new JButton("Apply");
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> dispose());

        this.add(apply);
        this.add(cancel);

        apply.setBounds((WINDOW_WIDTH >> 1) - 110 - 20, 200, 100, 30);
        apply.addActionListener(e -> {
            handler.changeGameMode(gameModeDropDown.getSelectedIndex());
            if (gameModeDropDown.getSelectedIndex() == Core.GAME_MODE.HUMAN_VS_AI.value) {
                handler.changeDepth(maxDepthDropDown.getSelectedIndex() + 1);
            }
            JOptionPane.showMessageDialog(null,
                    "Game settings have been changed.\nThe changes will be applied in the next game.",
                    "", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
        cancel.setBounds((WINDOW_WIDTH >> 1) - 10 + 20, 200, 100, 30);
    }

    public void setHandler(SettingsEvent handler) {
        this.handler = handler;
    }
}
