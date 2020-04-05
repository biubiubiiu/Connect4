package core;

/**
 * 将各种判定函数独立出来
 * 写在一个单独的类里
 */

/**
 * 2020-04-05 15:51 修改记录：
 * 1. 重写了 {@link #judgeSuccess(int[][], int, int)} 方法
 * 2. 重写了 {@link #judge(int[][], int, int)} 方法
 */

public class Judge {
    //对下面两个判断函数封装了一下
    //返回游戏状态
    //相关定义在Core中
    public static int judge(int[][] board, int currPlayer, int x, int y, int size) {
        boolean flag = judgeSuccess(board, currPlayer, x, y, size);
        if (flag) {
            return Core.WIN;
        }

        flag = judgeFail(board);
        if (flag) {
            return Core.FAIL;
        }

        return Core.CONTINUE;
    }

    //判定是否连成4个
    //这个判断不太完整，没有针对都放满的情况
    //修改了一下
    private static boolean judgeSuccess(int[][] board, int player, int x, int y, int size) {
        if (compuNum(board, player, x, y, 1, size) >= 4) {
            return true;
        }

        if (compuNum(board, player, x, y, 2, size) >= 4) {
            return true;
        }

        if (compuNum(board, player, x, y, 3, size) >= 4) {
            return true;
        }

        if (compuNum(board, player, x, y, 4, size) >= 4) {
            return true;
        }
        return false;
    }

    //这个判断是否全填满
    //未填满false，填满true
    private static boolean judgeFail(int[][] board) {
        for (int i = 0; i < Core.ROW; i++) {
            for (int j = 0; j < Core.COL; j++) {
                if (board[i][j] == Core.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    //这是judgeSuccess依赖的一些函数
    public static int compuNum(int[][] board, int player, int x, int y, int dire, int size) {
        int dire_x = 0;
        int dire_y = 0;
        if (dire == 1) { //横向
            dire_x = 1;
            dire_y = 0;
        }

        if (dire == 2) { //竖向
            dire_x = 0;
            dire_y = 1;
        }

        if (dire == 3) { //斜向
            dire_x = 1;
            dire_y = 1;
        }

        if (dire == 4) { //斜向
            dire_x = -1;
            dire_y = 1;
        }

        //从两个方向计算连子
        return compuNum_main(board, player, x, y, dire_x, dire_y, size) + compuNum_main(board, player, x, y, -dire_x, -dire_y, size) + 1;
    }

    public static int compuNum_main(int[][] board, int player, int x, int y, int dire_x, int dire_y, int size) {
        int i = 1;
        int j = 1;
        int count = 0;
        while (judgeBorder(i * dire_x + x, size) &&
                judgeBorder(j * dire_y + y, size) &&
                board[i * dire_x + x][j * dire_y + y] == player &&
                count <= 4) {
            count++;
            i++;
            j++;
        }
        return count;
    }

    public static boolean judgeBorder(int x, int size) {
        if (x >= size || x < 0) {
            return false;
        }
        return true;
    }

    /**
     * 判定当前游戏状态
     *
     * @param board 表示棋盘状态的二维数组
     * @param r     本次落子的行
     * @param c     本次落子的列
     * @return 游戏状态
     */
    public static int judge(int[][] board, int r, int c) {
        if (judgeFail(board)) {
            return Core.FAIL;
        } else if (judgeSuccess(board, r, c)) {
            return Core.WIN;
        }
        return Core.CONTINUE;
    }

    /**
     * dr[0], dc[0]: ←
     * dr[1], dc[1]: ↓
     * dr[2], dc[2]: ↖
     * dr[3], dc[3]: ↘
     */
    private static int[][] dr = {{0, 0}, {-1, 1}, {-1, 1}, {1, -1}};
    private static int[][] dc = {{-1, 1}, {0, 0}, {-1, 1}, {-1, 1}};

    /**
     * 判断是否连成四子
     * 也许没有必要每次都遍历整个棋盘
     * 可以以“上次落子位置”为中心，向四个方向进行搜索，判断是否连成四子
     *
     * @param board 表示棋盘状态的二维数组
     * @param r     上次落子的行
     * @param c     上次落子的列
     * @return 当前落子后，是否连成四子
     */
    private static boolean judgeSuccess(int[][] board, int r, int c) {
        System.out.println("checking (" + r + "," + c + ")");
        if (board.length == 0 || board.length < 4 && board[0].length < 4) {
            return false;
        }
        for (int dirc = 0; dirc < 4; dirc++) {
            int count = count(board, dirc, r, c);
            System.out.println(count + " continual chesses at dirc " + dirc);
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
     * @return
     */
    private static int count(int[][] board, int dirc, int r, int c) {
        // nr, nc表示下一个查看位置
        int nr = r, nc = c;
        int result = 0;
        do {
            nr += dr[dirc][0];
            nc += dc[dirc][0];
            result++;
            // 如果(nr, nc)在棋盘范围内，并且与当前(r,c)处棋子颜色相同
            // 则继续往后查看
        } while (withinBoard(board, nr, nc) && board[nr][nc] == board[r][c]);
        nr = r;
        nc = c;
        do {
            nr += dr[dirc][1];
            nc += dc[dirc][1];
            result++;
        } while (withinBoard(board, nr, nc) && board[nr][nc] == board[r][c]);
        return result - 1;
    }

    private static boolean withinBoard(int[][] board, int r, int c) {
        return (r >= 0 && c >= 0 && r < board.length && c < board[0].length);
    }
}

