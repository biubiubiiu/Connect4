package core;

public interface Core {
    int PLAYER_1 = 1;
    int PLAYER_2 = 2;
    int EMPTY = 0;

    /**
     * 在某一列落子
     * @param column 对应列
     */
    public void dropAt(int column);

    /**
     * 返回棋盘状态
     * @return
     */
    public int[][] getBoard();

    /**
     * 重置棋盘
     */
    public void reset();
}
