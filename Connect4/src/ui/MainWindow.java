package ui;

import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    public static void main(String[] args){
        int windowWidth = 850;
        int windowHeight = 700;     //窗口大小参数

        int boardWindowSize = 600;
        int boardSize = 5;          //棋盘大小参数

        int gapHorizontal = 5;
        int gapVertical = 4;        //窗口四周间隙参数

        JFrame jf = new JFrame("测试窗口");
        jf.setSize(windowWidth, windowHeight);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setResizable(false);     //设置窗口大小固定
        jf.setLocationRelativeTo(null);

        //主体选用SpringLayout布局
        SpringLayout springLayout = new SpringLayout();
        JPanel panelMain = new JPanel(springLayout);
        jf.setContentPane(panelMain);


        //棋盘采用GridLayout布局
        GridLayout gridLayout = new GridLayout(boardSize, boardSize);
        JPanel panelChessBoard = new JPanel(gridLayout);
        panelChessBoard.setBorder(BorderFactory.createEtchedBorder());  //给设置一个边框，还有其他边框格式供选择

        //棋子采用CardLayout布局，实现图片直接的切换避免贴图粘图
        //三张图片，空白，黑棋，白棋
        JPanel[][] panelChess = new JPanel[boardSize][boardSize];
        for (int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++){
                panelChess[i][j] = new JPanel(new CardLayout());
                panelChess[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                //TODO:边框重叠画两次的问题
                panelChess[i][j].add(new JLabel("空白"));
                panelChess[i][j].add(new JLabel("黑棋"));
                panelChess[i][j].add(new JLabel("白棋"));
                panelChessBoard.add(panelChess[i][j]);
            }
        }


        //按键采用GridLayout布局
        //按键设置为后期棋盘尺寸变化预留了空间，但是相关界面设置是固定值，待后期修改
        //按键尺寸可调，考虑用图片做背景
        GridLayout gridLayoutForButtons = new GridLayout(1, 5);
        gridLayoutForButtons.setHgap(60);
        JPanel panelButtons = new JPanel(gridLayoutForButtons);
        JButton[] btns = new JButton[boardSize];
        for (int i=0; i<boardSize; i++){
            btns[i] = new JButton("↓");
            panelButtons.add(btns[i]);

            final int t = i;
            btns[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO:要与逻辑部分相结合
                    //这里做一个展效果
                    CardLayout temp = (CardLayout) panelChess[0][t].getLayout();
                    JPanel pTemp = panelChess[0][t];
                    temp.next(pTemp);
                }
            });
        }




        //将棋盘加到panelMain中并设置其格式
        //棋盘暂定为固定大小
        panelMain.add(panelChessBoard);
        SpringLayout.Constraints panelChessBoardCons = springLayout.getConstraints(panelChessBoard);
        panelChessBoardCons.setX(Spring.constant(gapHorizontal));
        panelChessBoardCons.setY(Spring.constant(gapVertical));
        panelChessBoardCons.setConstraint(SpringLayout.EAST, Spring.constant(gapHorizontal+boardWindowSize));
        panelChessBoardCons.setConstraint(SpringLayout.SOUTH, Spring.constant(gapVertical+boardWindowSize));


        //将按键面板和棋盘两端对齐，左右各空出30像素，每个按键间间隔60像素
        panelMain.add(panelButtons);
        SpringLayout.Constraints panelButtonsCons = springLayout.getConstraints(panelButtons);
        panelButtonsCons.setX(
                Spring.sum(
                        Spring.constant(gapHorizontal),
                        Spring.constant(30))
        );
        panelButtonsCons.setY(
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(20))
        );
        panelButtonsCons.setConstraint(SpringLayout.EAST,
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.EAST),
                        Spring.minus(Spring.constant(30)))
        );





        jf.setVisible(true);
    }
}
