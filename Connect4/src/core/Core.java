package core;

public interface Core {
    int EMPTY = 0;
    int PLAYER_1 = 1;
    int PLAYER_2 = 2;

    // 如果要自定义棋盘大小，可以把这两行放入接口 Core 中，并增加获取棋盘大小的方法
    int ROW = 6;
    int COL = 6;

    //游戏状态
    int WIN = 1;
    int FAIL = 2;
    int CONTINUE = 3;

    /**
     * 在某一列落子
     * @param column 对应列
     */
    void dropAt(int column);

    /**
     * 返回棋盘状态
     * @return
     */
    int[][] getBoard();

    /**
     * 返回游戏状态
     * @return
     */
    int getGameStatus();

    /**
     * 返回当前玩家
     * @return
     */
    int getCurrPlayer();

    /**
     * 重置棋盘
     */
    void reset();

    /**
     * 交换玩家
     */
    void switchPlayer();
}
