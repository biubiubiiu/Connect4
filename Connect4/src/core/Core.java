package core;

/**
 * @author Raymond
 */
public interface Core {
    enum GridType {
        /**
         * 表示棋盘上某个位置的状态
         *
         * EMPTY: 未落子
         * PLAYER_1: 玩家1落子
         * PLAYER_2: 玩家2落子
         */
        EMPTY(0), PLAYER_1(1), PLAYER_2(2);

        private final int value;

        GridType(int val) {
            this.value = val;
        }

        public int value() {
            return this.value;
        }

        public static int of(Player p) {
            return p == Player.PLAYER_1 ? PLAYER_1.value : PLAYER_2.value;
        }
    }

    enum Player {
        /**
         * 表示玩家
         * PLAYER_1: 玩家1
         * PLAYER_2: 玩家2
         */
        PLAYER_1(1), PLAYER_2(2);

        private final int value;
        private boolean isAi;

        Player(int val) {
            this.value = val;
            this.isAi = false;
        }

        public int value() {
            return this.value;
        }

        public boolean isAi() {
            return this.isAi;
        }

        public void setAiPlayer() {
            System.out.println("set to ai");
            this.isAi = true;

            //修改playerPanel配置
        }

        public void setHumanPlayer() {
            this.isAi = false;
        }

        public Player next() {
            return this == PLAYER_1 ? PLAYER_2 : PLAYER_1;
        }

        public static Player of(int value) {
            if (value == PLAYER_1.value) {
                return PLAYER_1;
            } else if (value == PLAYER_2.value) {
                return PLAYER_2;
            }
            return null;
        }
    }

    /**
     * 如果要自定义棋盘大小，可以把这两行放入接口 Core 中，并增加获取棋盘大小的方法
     */
    int ROW = 6;
    int COL = 7;

    enum Status {
        /**
         * 表示游戏状态
         * WIN: 一方胜利
         * FAIL: 和局
         * CONTINUE: 未决胜负
         */
        WIN(0), FAIL(1), CONTINUE(2);

        private final int value;

        Status(int val) {
            this.value = val;
        }

        public int value() {
            return this.value;
        }

        public static Status of(int value) {
            if (value == WIN.value) {
                return WIN;
            } else if (value == FAIL.value) {
                return FAIL;
            } else if (value == CONTINUE.value) {
                return CONTINUE;
            }
            return null;
        }
    }

    /**
     * 在某一列落子
     *
     * @param column 落子列
     * @return 本次操作是否成功
     */
    boolean dropAt(int column);

    /**
     * 返回棋盘状态
     *
     * @return 表示棋盘状态的二维数组
     */
    int[][] getBoard();

    /**
     * 返回游戏状态
     *
     * @return 游戏状态
     */
    Status getGameStatus();

    /**
     * 返回当前玩家
     *
     * @return 当前玩家
     */
    Player getCurrPlayer();

    /**
     * 更新当前游戏状态
     * 在每次落子后触发
     *
     * @param r 上次落子所在行
     * @param c 上次落子所在列
     */
    void updateGameStatus(int r, int c);

    /**
     * 重置棋盘
     */
    void reset();

    /**
     * 交换玩家
     */
    void switchPlayer();

    /**
     * 设置棋盘
     *
     * @param board 棋盘
     */
    void setBoard(int[][] board);

    /**
     * 设置当前玩家
     *
     * @param currPlayer 当前玩家
     */
    void setCurrPlayer(Player currPlayer);

    /**
     * 设置游戏状态
     *
     * @param gameState 游戏状态
     */
    void setGameState(Status gameState);

    enum GAME_MODE {
        /**
         * 游戏模式
         * <p>
         * HUMAN_VS_HUMAN: pvp
         * HUMAN_VS_AI: pve
         */
        HUMAN_VS_HUMAN(0, "Human Vs Human"), HUMAN_VS_AI(1, "Human Vs AI");

        public final int value;
        public final String description;

        GAME_MODE(int val, String description) {
            this.value = val;
            this.description = description;
        }
    }

    /**
     * 设置 minimax 算法的搜索深度
     *
     * @param depth 最大深度
     */
    void setSearchDepth(int depth);

    /**
     * 完成 ai 移动
     */
    void aiMove();
}
