package ui.components;

import core.Core;
import core.GameControl;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    public static final int OUTER_SQUARE_SIZE = 100;
    public static final int INNER_SQUARE_SIZE = 80;
    public static final int GAP = (OUTER_SQUARE_SIZE - INNER_SQUARE_SIZE) / 2;

    public static final int WIDTH = Core.COL * OUTER_SQUARE_SIZE;
    public static final int HEIGHT = Core.ROW * OUTER_SQUARE_SIZE;
    public static final int CIRCLE_TOPLEFT_GAP = 8; // 圆外接矩形左上角的边距
    public static final int CIRCLE_RADIUS = (OUTER_SQUARE_SIZE) - (CIRCLE_TOPLEFT_GAP << 1);

    private static final Color bgColor1 = new Color(121, 161, 109);
    private static final Color bgColor2 = new Color(202, 196, 102);
    //TODO 下面几个颜色有点丑，后期再换
    private static final Color chessColor1 = Color.BLACK;
    private static final Color chessColor2 = Color.WHITE;

    // 与核心逻辑的接口
    private Core core;

    public Board() {
        super();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new GridLayout(Core.ROW, Core.COL));
        this.setCore(new GameControl());
//        // core stub
//        // 仅供测试
//        this.setCore(new Core() {
//            public int[][] board = new int[6][7];
//            public int currPlayer = 0; // 0: player1 1: player2
//
//            @Override
//            public void dropAt(int column) {
//                int row = ROW-1;
//                while(row >= 0 && board[row][column] != EMPTY) {
//                    row--;
//                }
//                if(row == -1) return; // 没有空位
//                board[row][column] = (currPlayer == 0) ? PLAYER_1 : PLAYER_2;
//                currPlayer = (currPlayer + 1) & 1; // equals to (currPlayer+1)%2
//            }
//
//            @Override
//            public int[][] getBoard() {
//                return board;
//            }
//
//            @Override
//            public void reset() {
//
//            }
//        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] boardStatus = core.getBoard();
        for (int r = 0; r < Core.ROW; r++) {
            for (int c = 0; c < Core.COL; c++) {
                g.setColor(Color.BLACK);
                g.fillRect((c * OUTER_SQUARE_SIZE), (r * OUTER_SQUARE_SIZE), OUTER_SQUARE_SIZE, OUTER_SQUARE_SIZE);

                if ((r + c) % 2 != 0) {
                    g.setColor(bgColor1);
                } else {
                    g.setColor(bgColor2);
                }
                g.fillRect((c * OUTER_SQUARE_SIZE + GAP), (r * OUTER_SQUARE_SIZE + GAP), INNER_SQUARE_SIZE, INNER_SQUARE_SIZE);

                //TODO paint chesses
                if (boardStatus[r][c] != Core.GridType.EMPTY.value()) {
                    g.setColor(boardStatus[r][c] == Core.GridType.PLAYER_1.value() ? chessColor1 : chessColor2);
                    g.fillOval(c * OUTER_SQUARE_SIZE + CIRCLE_TOPLEFT_GAP,
                            r * OUTER_SQUARE_SIZE + CIRCLE_TOPLEFT_GAP, CIRCLE_RADIUS, CIRCLE_RADIUS);
                }

            }
        }
    }

    public void dropAt(int column) {
        this.core.dropAt(column);
    }

    public Core.Status getGameStatus() {
        return this.core.getGameStatus();
    }

    public void switchPlayer() {
        this.core.switchPlayer();
    }

    public Core.Player getCurrPlayer() {
        return this.core.getCurrPlayer();
    }

    private void setCore(Core core) {
        this.core = core;
    }
}
