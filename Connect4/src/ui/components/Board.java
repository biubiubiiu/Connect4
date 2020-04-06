package ui.components;

import core.Core;
import core.GameControl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;

public class Board extends JPanel {
    public static final int SQUARE_SIZE = 80;
    public static final int GAP = 10;

    public static final int PADDING_HORIZONTAL = 8;
    public static final int PADDING_VERTICAL = 8;
    public static final int WIDTH = Core.COL * SQUARE_SIZE + (Core.COL-1)*GAP + (PADDING_HORIZONTAL <<1);
    public static final int HEIGHT = Core.ROW * SQUARE_SIZE + (Core.ROW-1)*GAP + (PADDING_VERTICAL << 1);

    private static final Color BG_COLOR1 = new Color(121, 161, 109);
    private static final Color BG_COLOR2 = new Color(202, 196, 102);
    //TODO 下面几个颜色有点丑，后期再换
    private static final Color CHESS_COLOR1 = Color.BLACK;
    private static final Color CHESS_COLOR2 = Color.WHITE;

    // 与核心逻辑的接口
    private Core core;

    public Board() {
        super();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new GridLayout(Core.ROW, Core.COL));

        //TODO 右边框暂时不能显示
        this.setBorder(BorderFactory.createEtchedBorder());

        this.setCore(new GameControl());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        int[][] boardStatus = core.getBoard();
        for (int r = 0; r < Core.ROW; r++) {
            for (int c = 0; c < Core.COL; c++) {
                if ((r + c) % 2 != 0) {
                    g.setColor(BG_COLOR1);
                } else {
                    g.setColor(BG_COLOR2);
                }
                g.fillRect((PADDING_HORIZONTAL + c * (SQUARE_SIZE + GAP)), ( PADDING_VERTICAL + r * (SQUARE_SIZE + GAP)), SQUARE_SIZE, SQUARE_SIZE);

                if (boardStatus[r][c] != Core.GridType.EMPTY.value()) {
                    g.setColor(boardStatus[r][c] == Core.GridType.PLAYER_1.value() ? CHESS_COLOR1 : CHESS_COLOR2);
                    g.fillOval((PADDING_HORIZONTAL + c * (SQUARE_SIZE + GAP)), (PADDING_VERTICAL + r * (SQUARE_SIZE + GAP)), SQUARE_SIZE, SQUARE_SIZE);
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

    public int getBoardColumns() {
        return Core.COL;
    }
}
