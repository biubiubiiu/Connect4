package ui.components;

import core.Core;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    // 如果要自定义棋盘大小，可以把这两行放入接口 Core 中，并增加获取棋盘大小的方法
    private static int ROW = 6;
    private static int COL = 7;

    public static final int OUTER_SQUARE_SIZE = 100;
    public static final int INNER_SQUARE_SIZE = 80;
    public static final int GAP = (OUTER_SQUARE_SIZE - INNER_SQUARE_SIZE) / 2;

    public static final int WIDTH = COL * OUTER_SQUARE_SIZE;
    public static final int HEIGHT = ROW * OUTER_SQUARE_SIZE;
    public static final int CIRCLE_TOPLEFT_GAP = 8; // 圆外接矩形左上角的边距
    public static final int CIRCLE_RADIUS = (OUTER_SQUARE_SIZE) - (CIRCLE_TOPLEFT_GAP << 1);

    private static final Color bgColor1 = new Color(22, 120, 255);
    private static final Color bgColor2 = new Color(0, 160, 233);
    //TODO 下面几个颜色有点丑，后期再换
    private static final Color chessColor1 = Color.GRAY;
    private static final Color chessColor2 = Color.YELLOW;
    private static final Color chessColor3 = Color.RED;

    private Core core;  // 通过此接口与核心交互

    public Board() {
        super();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new GridLayout(ROW, COL));

        // core stub
        // 仅供测试
        this.setCore(new Core() {
            public int[][] board = new int[6][7];
            public int currPlayer = 0; // 0: player1 1: player2

            @Override
            public void dropAt(int column) {
                int row = ROW-1;
                while(row >= 0 && board[row][column] != EMPTY) {
                    row--;
                }
                if(row == -1) return; // 没有空位
                board[row][column] = (currPlayer == 0) ? PLAYER_1 : PLAYER_2;
                currPlayer = (currPlayer + 1) & 1; // equals to (currPlayer+1)%2
            }

            @Override
            public int[][] getBoard() {
                return board;
            }

            @Override
            public void reset() {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] boardStatus = core.getBoard();
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                g.setColor(bgColor1);
                g.fillRect((c * OUTER_SQUARE_SIZE), (r * OUTER_SQUARE_SIZE), OUTER_SQUARE_SIZE, OUTER_SQUARE_SIZE);

                g.setColor(bgColor2);
                g.fillRect((c * OUTER_SQUARE_SIZE + GAP), (r * OUTER_SQUARE_SIZE + GAP), INNER_SQUARE_SIZE, INNER_SQUARE_SIZE);

                //TODO paint chesses
                if (boardStatus[r][c] == Core.EMPTY) {
                    g.setColor(chessColor1);
                } else {
                    g.setColor(boardStatus[r][c] == Core.PLAYER_1 ? chessColor2 : chessColor3);
                }
                g.fillOval(c * OUTER_SQUARE_SIZE + CIRCLE_TOPLEFT_GAP, r * OUTER_SQUARE_SIZE + CIRCLE_TOPLEFT_GAP, CIRCLE_RADIUS, CIRCLE_RADIUS);
            }
        }
    }

    public void dropAt(int column) {
        core.dropAt(column);
    }

    private void setCore(Core core) {
        this.core = core;
    }
}
