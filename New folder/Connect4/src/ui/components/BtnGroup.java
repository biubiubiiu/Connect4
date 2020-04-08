package ui.components;

import core.Core;

import javax.swing.*;
import java.awt.*;

public class BtnGroup extends JPanel {

    private static final int HEIGHT = 30;

    BtnEvent handler;

    private JButton[] btns;

    public void setHandler(BtnEvent handler) {
        this.handler = handler;
    }

    public interface BtnEvent {
        void buttonClick(int i);
    }

    public BtnGroup(Board connected) {
        int nums = connected.getBoardColumns();

        //按键采用GridLayout布局
        GridLayout gridLayoutForButtons = new GridLayout(1, nums, Board.GAP, 0);
        this.setLayout(gridLayoutForButtons);
        this.setPreferredSize(new Dimension(Board.WIDTH, HEIGHT));
        btns = new JButton[nums];
        for (int i = 0; i < nums; i++) {
            btns[i] = new JButton("↓");
            btns[i].setSize(Board.SQUARE_SIZE, HEIGHT);
            this.add(btns[i]);

            final int column = i;
            btns[i].addActionListener(e -> {
                handler.buttonClick(column);
            });
        }
    }

    public void disableBtns() {
        for (JButton button : btns) {
            button.setText("");
            button.setEnabled(false);
        }
    }

    public void enableBtns() {
        for (JButton button : btns) {
            button.setText("↓");
            button.setEnabled(true);
        }
    }
}
