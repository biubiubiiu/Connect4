package ui.components;

import javax.swing.*;
import java.awt.*;

public class BtnGroup extends JPanel {

    private static final int HEIGHT = 30;

    private Board connectedBoard;

    public BtnGroup(Board connected) {
        this.connectedBoard = connected;
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
                connectedBoard.dropAt(column);
                connectedBoard.repaint();

                switch (connectedBoard.getGameStatus()) {
                    case WIN:
                        System.out.println("win!");
                        JOptionPane.showMessageDialog(null, connectedBoard.getCurrPlayer() + " wins!");
                        break;
                    case FAIL:
                        System.out.println("fail!");
                        JOptionPane.showMessageDialog(null, "Draw!");
                        break;
                    case CONTINUE:
                        //交换玩家
                        connectedBoard.switchPlayer();
                        break;
                    default:
                        break;
                }
            });
        }
    }
}
