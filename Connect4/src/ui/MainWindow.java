package ui;

import ui.components.Board;
import utils.MenuFactory;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    static final int windowWidth = 850;
    static final int windowHeight = 800;     //窗口大小参数

    static final int boardSize = 7;          //棋盘大小参数

    static final int gapHorizontal = 5;
    static final int gapVertical = 4;        //窗口四周间隙参数

    public static void main(String[] args) {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(windowWidth, windowHeight);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setResizable(false);     //设置窗口大小固定
        jf.setLocationRelativeTo(null);

        // 添加菜单栏
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(MenuFactory.createJMenu("Start", new String[]{"New Game", "Save", "Exit"}));
        jf.setJMenuBar(menuBar);

        //主体选用SpringLayout布局
        SpringLayout springLayout = new SpringLayout();
        JPanel panelMain = new JPanel(springLayout);
        jf.setContentPane(panelMain);

        //棋盘采用GridLayout布局
        JPanel panelChessBoard = new Board();
        //TODO 右边框暂时不能显示
        panelChessBoard.setBorder(BorderFactory.createEtchedBorder());  //给棋盘设置一个边框，还有其他边框格式供选择

        //按键采用GridLayout布局
        //按键设置为后期棋盘尺寸变化预留了空间，但是相关界面设置是固定值，待后期修改
        //按键尺寸可调，考虑用图片做背景
        GridLayout gridLayoutForButtons = new GridLayout(1, boardSize);
        gridLayoutForButtons.setHgap(Board.GAP<<1);
        JPanel panelButtons = new JPanel(gridLayoutForButtons);
        panelButtons.setPreferredSize(new Dimension(Board.WIDTH, 30));
        JButton[] btns = new JButton[boardSize];
        for (int i = 0; i < boardSize; i++) {
            btns[i] = new JButton("↓");
            panelButtons.add(btns[i]);

//            final int t = i;
//            btns[i].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    //TODO:要与逻辑部分相结合
//                }
//            });
        }

        //将棋盘加到panelMain中并设置其格式
        //棋盘暂定为固定大小
        panelMain.add(panelChessBoard);
        SpringLayout.Constraints panelChessBoardCons = springLayout.getConstraints(panelChessBoard);
        panelChessBoardCons.setX(Spring.constant(gapHorizontal));
        panelChessBoardCons.setY(Spring.constant(gapVertical));

        //TODO 通过Board中提供的大小参数，计算按键的间隔和宽度
        panelMain.add(panelButtons);
        SpringLayout.Constraints panelButtonsCons = springLayout.getConstraints(panelButtons);
        panelButtonsCons.setX(Spring.constant(gapHorizontal));
        panelButtonsCons.setY(
                // padding-top: 20
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(20))
        );

        jf.setVisible(true);
    }
}