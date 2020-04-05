package ui;

import ui.components.Board;
import utils.MenuFactory;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    /**
     * 窗口大小参数
     */
    static final int WINDOW_WIDTH = 850;
    static final int WINDOW_HEIGHT = 800;

    /**
     * 棋盘列数
     */
    static final int BOARD_SIZE = 7;

    /**
     * 窗口四周间隙参数
     */
    static final int GAP_HORIZONTAL = 5;
    static final int GAP_VERTICAL = 4;

    public static void main(String[] args) {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        // 添加菜单栏
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(MenuFactory.createJMenu("Start", new String[]{"New Game", "Save", "Exit"}));
        jf.setJMenuBar(menuBar);

        //主体选用SpringLayout布局
        SpringLayout springLayout = new SpringLayout();
        JPanel panelMain = new JPanel(springLayout);
        jf.setContentPane(panelMain);

        Board panelChessBoard = new Board();

        //TODO 右边框暂时不能显示
        panelChessBoard.setBorder(BorderFactory.createEtchedBorder());

        //按键采用GridLayout布局
        //按键设置为后期棋盘尺寸变化预留了空间，但是相关界面设置是固定值，待后期修改
        //按键尺寸可调，考虑用图片做背景
        GridLayout gridLayoutForButtons = new GridLayout(1, BOARD_SIZE);
        gridLayoutForButtons.setHgap(Board.GAP << 1);
        JPanel panelButtons = new JPanel(gridLayoutForButtons);
        panelButtons.setPreferredSize(new Dimension(Board.WIDTH, 30));
        JButton[] btns = new JButton[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            btns[i] = new JButton("↓");
            panelButtons.add(btns[i]);

            final int column = i;
            btns[i].addActionListener(e -> {
                panelChessBoard.dropAt(column);
                panelChessBoard.repaint();

                switch (panelChessBoard.getGameStatus()) {
                    case WIN:
                        //TODO: 弹出相应提示，结束游戏
                        System.out.println("win!");
                        JOptionPane.showMessageDialog(null, panelChessBoard.getCurrPlayer() + " wins!");
                        break;
                    case FAIL:
                        //TODO: 弹出相应提示，结束游戏
                        System.out.println("fail!");
                        JOptionPane.showMessageDialog(null, "Draw!");
                        break;
                    case CONTINUE:
                        //交换玩家
                        panelChessBoard.switchPlayer();
                        break;
                    default:
                        break;
                }
            });
        }

        //将棋盘加到panelMain中并设置其格式
        //棋盘暂定为固定大小
        panelMain.add(panelChessBoard);
        SpringLayout.Constraints panelChessBoardCons = springLayout.getConstraints(panelChessBoard);
        panelChessBoardCons.setX(Spring.constant(GAP_HORIZONTAL));
        panelChessBoardCons.setY(Spring.constant(GAP_VERTICAL));

        //TODO 通过Board中提供的大小参数，计算按键的间隔和宽度
        panelMain.add(panelButtons);
        SpringLayout.Constraints panelButtonsCons = springLayout.getConstraints(panelButtons);
        panelButtonsCons.setX(Spring.constant(GAP_HORIZONTAL));
        panelButtonsCons.setY(
                // padding-top: 20
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(20))
        );

        jf.setVisible(true);
    }
}