package ui;

import ui.components.*;
import utils.MenuFactory;

import javax.swing.*;

public class MainWindow {

    /**
     * 窗口大小参数
     */
    static final int WINDOW_WIDTH = 850;
    static final int WINDOW_HEIGHT = 670;

    /**
     * 窗口四周间隙参数
     */
    static final int GAP_HORIZONTAL = 5;
    static final int GAP_VERTICAL = 4;

    public static void main(String[] args) {
        JFrame jf = new JFrame("Connect4");
        jf.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        //主体选用SpringLayout布局
        SpringLayout springLayout = new SpringLayout();
        JPanel panelMain = new JPanel(springLayout);
        jf.setContentPane(panelMain);

        // 添加菜单栏
        // TODO
        MenuBar menuBar = new MenuBar();
        jf.setJMenuBar(menuBar);

        // 棋盘组件
        Board panelChessBoard = new Board();

        //玩家信息组件
        PlayerPanel[] players = new PlayerPanel[2];
        players[0] = new PlayerPanel("Player 1");
        players[1] = new PlayerPanel("Player 2");
        //玩家1先手
        players[0].switchStatus();

        //计时器组件
        CountdownTimer timeDisplay = new CountdownTimer();
        timeDisplay.setTimeoutCallback(() -> JOptionPane.showMessageDialog(null, panelChessBoard.getCurrPlayer() + " 超时了!")
        );

        // 按键组
        JPanel panelButtons = new BtnGroup(panelChessBoard, players, timeDisplay);

        //将棋盘加到panelMain中并设置其格式
        panelMain.add(panelChessBoard);
        SpringLayout.Constraints panelChessBoardCons = springLayout.getConstraints(panelChessBoard);
        panelChessBoardCons.setX(Spring.constant(GAP_HORIZONTAL));
        panelChessBoardCons.setY(Spring.constant(GAP_VERTICAL));

        // 将按钮组加到panelMain并设置其格式
        panelMain.add(panelButtons);
        SpringLayout.Constraints panelButtonsCons = springLayout.getConstraints(panelButtons);
        panelButtonsCons.setX(Spring.constant(GAP_HORIZONTAL));
        panelButtonsCons.setY(
                // padding-top: 20
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(20))
        );

        //将计时器组件加入到panelMain
        panelMain.add(timeDisplay);
        SpringLayout.Constraints timeDisplayCons = springLayout.getConstraints(timeDisplay);
        timeDisplayCons.setX(
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.EAST),
                        Spring.constant(80)
                )
        );
        timeDisplayCons.setY(Spring.constant(50));

        // 将玩家信息组加到panelMain并设置其格式
        panelMain.add(players[0]);
        SpringLayout.Constraints player1Cons = springLayout.getConstraints(players[0]);
        player1Cons.setX(
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.EAST),
                        Spring.constant(10)
                )
        );
        player1Cons.setY(
                Spring.sum(
                        timeDisplayCons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(50)
                )
        );

        panelMain.add(players[1]);
        SpringLayout.Constraints player2Cons = springLayout.getConstraints(players[1]);
        player2Cons.setX(
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.EAST),
                        Spring.constant(10)
                )
        );
        player2Cons.setY(
                Spring.sum(
                        player1Cons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(20)
                )
        );

        jf.setVisible(true);

        //开始计时
        timeDisplay.restartCountdown();
    }
}