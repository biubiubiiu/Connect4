package ui.components;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private static int ROW = 6;
    private static int COL = 7;

    public static final int OUTER_SQUARE_SIZE = 100;
    public static final int INNER_SQUARE_SIZE = 80;
    public static final int GAP = (OUTER_SQUARE_SIZE-INNER_SQUARE_SIZE)/2;

    public static final int WIDTH = COL * OUTER_SQUARE_SIZE;
    public static final int HEIGHT = ROW * OUTER_SQUARE_SIZE;
    public static final int CIRCLE_RADIUS = (OUTER_SQUARE_SIZE) - 15;

    private static final Color color1 = new Color(22, 120, 255);
    private static final Color color2 = new Color(0, 160, 233);

    int[][] board = new int[ROW][COL];

    public Board() {
        super();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new GridLayout(ROW, COL));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int r = 0; r < ROW; r++) {
            for(int c = 0; c < COL; c++) {
                g.setColor(color1);
                g.fillRect( (c*OUTER_SQUARE_SIZE),(r * OUTER_SQUARE_SIZE),OUTER_SQUARE_SIZE,OUTER_SQUARE_SIZE);

                g.setColor(color2);
                g.fillRect( (c*OUTER_SQUARE_SIZE+GAP),(r * OUTER_SQUARE_SIZE+GAP),INNER_SQUARE_SIZE,INNER_SQUARE_SIZE);

                //TODO paint chesses
            }
        }
    }
}
