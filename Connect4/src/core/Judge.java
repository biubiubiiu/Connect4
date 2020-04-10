package core;

/**
 * 将各种判定函数独立出来
 * 写在一个单独的类里
 *
 * @author Fimon, Raymond
 * <p>
 * 核心方法：
 * 1. {@link #judge(int[][], int, int)} 判定当前游戏状态
 * 2. {@link #judgeSuccess(int[][], int, int)} 判断是否有一方连成四子
 * 3. {@link #judgeFail(int[][])} 判断棋盘是否为满
 * 4. {@link #judgeBorder(int, int)} 判断落子列是否在边界范围内
 */

public class Judge {

    /**
     * 判断棋盘是否全部填满
     *
     * @param board 棋盘状态
     * @return false：为填满；true：填满
     */
    private static boolean judgeFail(int[][] board) {
        for (int i = 0; i < Core.ROW; i++) {
            for (int j = 0; j < Core.COL; j++) {
                if (board[i][j] == Core.GridType.EMPTY.value()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean judgeBorder(int x, int size) {
        return x < size && x >= 0;
    }

    /**
     * 判定当前游戏状态
     *
     * @param board 表示棋盘状态的二维数组
     * @param r     本次落子的行
     * @param c     本次落子的列
     * @return 游戏状态
     */
    public static Core.Status judge(int[][] board, int r, int c) {
        if (judgeSuccess(board, r, c)) {
            return Core.Status.WIN;
        } else if (judgeFail(board)) {
            return Core.Status.FAIL;
        }
        return Core.Status.CONTINUE;
    }

    /**
     * dr[0], dc[0]: ←
     * dr[1], dc[1]: ↓
     * dr[2], dc[2]: ↖
     * dr[3], dc[3]: ↘
     */
    private static final int[][] DELTA_R = {{0, 0}, {-1, 1}, {-1, 1}, {1, -1}};
    private static final int[][] DELTA_C = {{-1, 1}, {0, 0}, {-1, 1}, {-1, 1}};
    private static final int N_DIRECTION = DELTA_R.length;

    /**
     * 判断是否连成四子
     * 以“上次落子位置”为中心，向四个方向进行搜索，判断是否连成四子
     *
     * @param board 表示棋盘状态的二维数组
     * @param r     上次落子的行
     * @param c     上次落子的列
     * @return 当前落子后，是否连成四子
     */
    private static boolean judgeSuccess(int[][] board, int r, int c) {
        if (board.length == 0) {
            return false;
        }
        if (board.length < N_DIRECTION && board[0].length < N_DIRECTION) {
            return false;
        }

        for (int dirc = 0; dirc < N_DIRECTION; dirc++) {
            if (count(board, dirc, r, c) >= 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统计以(r, c)为中心，在某个方向上的连续棋子总数
     *
     * @param board 表示棋盘状态的二维数组
     * @param dirc  表示扩展方向，与 dr,dc的下标一致
     * @param r     棋子所在行
     * @param c     棋子所在列
     * @return 以(r, c)为中心的最大连续棋子数
     */
    private static int count(int[][] board, int dirc, int r, int c) {
        // nr, nc表示下一个查看位置
        int nr = r, nc = c;
        int result = 0;
        do {
            nr += DELTA_R[dirc][0];
            nc += DELTA_C[dirc][0];
            result++;
            // 如果(nr, nc)在棋盘范围内，并且与当前(r,c)处棋子颜色相同
            // 则继续往后查看
        } while (withinBoard(board, nr, nc) && board[nr][nc] == board[r][c]);
        nr = r;
        nc = c;
        do {
            nr += DELTA_R[dirc][1];
            nc += DELTA_C[dirc][1];
            result++;
        } while (withinBoard(board, nr, nc) && board[nr][nc] == board[r][c]);
        return result - 1;
    }

    /**
     * 判定是否在棋盘内
     *
     * @param board 表示棋盘的二维数组
     * @param r     行
     * @param c     列
     * @return 是否在棋盘范围内
     */
    private static boolean withinBoard(int[][] board, int r, int c) {
        return (r >= 0 && c >= 0 && r < board.length && c < board[0].length);
    }
}

