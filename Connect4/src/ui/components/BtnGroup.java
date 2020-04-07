package ui.components;

import core.Core;

import javax.swing.*;
import java.awt.*;

public class BtnGroup extends JPanel {

    private static final int HEIGHT = 30;

    BtnEvent handler;

    public void setHandler(BtnEvent handler) {
        this.handler = handler;
    }

    public interface BtnEvent {
        void buttonClick(int i);
    }

    public BtnGroup(Board connected, PlayerPanel[] players, CountdownTimer timeDisplay) {
        int nums = connected.getBoardColumns();

        //按键采用GridLayout布局
        GridLayout gridLayoutForButtons = new GridLayout(1, nums);
        gridLayoutForButtons.setHgap(Board.GAP);
        this.setLayout(gridLayoutForButtons);
        this.setPreferredSize(new Dimension(Board.WIDTH, HEIGHT));
        JButton[] btns = new JButton[nums];
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
}
