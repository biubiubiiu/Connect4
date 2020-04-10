package core;

import java.util.Random;

/**
 * @author Raymond
 * @reference https://github.com/Iptamenos/Connect4
 */
public class MiniMaxAI {

    private Core.Player aiPlayer;

    /**
     * 最大搜索深度
     */
    private int maxDepth;

    /**
     * 可以落子的列
     */
    private boolean[] validColumns;
    private int[][] memo;

    public MiniMaxAI() {
        this.memo = new int[Core.ROW][Core.COL];
        this.validColumns = new boolean[Core.COL];
    }

    public void setAiPlayer(Core.Player aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int generateMove(int[][] board) {
        updateMemo(board);
        updateValidCols();
        Move bestMove;

        // If player1 plays then it wants to Maximize the heuristics value
        if (aiPlayer == Core.Player.PLAYER_1) {
            bestMove = maximize(memo, 0, null);
        }
        //If player2 plays then it wants to MINimize the heuristics value
        else {
            bestMove = minimize(memo, 0, null);
        }
        return bestMove.col;
    }

    private void updateValidCols() {
        for (int i = 0; i < Core.COL; i++) {
            validColumns[i] = (memo[0][i] == Core.GridType.EMPTY.value());
        }
    }

    public Move maximize(int[][] board, int depth, Move lastMove) {
        // 如果对手走完这步棋会直接结束，则不让他走
        if (lastMove != null && Judge.judge(board, lastMove.row, lastMove.col) != Core.Status.CONTINUE) {
            return new Move(lastMove.row, lastMove.col, evaluate(board));
        }
        if (depth == maxDepth) {
            assert lastMove != null;
            return new Move(lastMove.row, lastMove.col, evaluate(board));
        }
        // 向下搜索状态树
        Move bestMove = new Move(Integer.MIN_VALUE);
        for (int i = 0; i < Core.COL; i++) {
            if (validColumns[i]) {
                final Move m = simulateDropAt(board, i, Core.Player.PLAYER_1);
                final Move move = minimize(board, depth + 1, m);
                if (move.value == bestMove.value && new Random().nextInt(2) == 0) {
                    bestMove.row = m.row;
                    bestMove.col = m.col;
                    bestMove.value = move.value;
                } else if (move.value > bestMove.value) {
                    bestMove.row = m.row;
                    bestMove.col = m.col;
                    bestMove.value = move.value;
                }
                recover(board, move);
                recover(board, m);
            }
        }
        return bestMove;
    }

    private Move minimize(int[][] board, int depth, Move lastMove) {
        if (lastMove != null && Judge.judge(board, lastMove.row, lastMove.col) != Core.Status.CONTINUE) {
            return new Move(lastMove.row, lastMove.col, evaluate(board));
        }
        if (depth == maxDepth) {
            assert lastMove != null;
            return new Move(lastMove.row, lastMove.col, evaluate(board));
        }
        // 向下搜索状态树
        Move bestMove = new Move(Integer.MAX_VALUE);
        for (int i = 0; i < Core.COL; i++) {
            if (validColumns[i]) {
                final Move m = simulateDropAt(board, i, Core.Player.PLAYER_2);
                final Move move = maximize(board, depth + 1, m);
                if (move.value == bestMove.value && new Random().nextInt(2) == 0) {
                    bestMove.row = m.row;
                    bestMove.col = m.col;
                    bestMove.value = move.value;
                } else if (move.value < bestMove.value) {
                    bestMove.row = m.row;
                    bestMove.col = m.col;
                    bestMove.value = move.value;
                }
                recover(board, move);
                recover(board, m);
            }
        }
        return bestMove;
    }

    private Move simulateDropAt(int[][] board, int col, Core.Player currentPlayer) {
        int top = -1;
        while (top < board.length - 1 && board[top + 1][col] == Core.GridType.EMPTY.value()) {
            top++;
        }
        board[top][col] = Core.GridType.of(currentPlayer);

        if (top <= 0) {
            validColumns[col] = false;
        }
        return new Move(top, col);
    }

    private void recover(int[][] board, Move lastMove) {
        int row = lastMove.row;
        int col = lastMove.col;
        board[row][col] = Core.GridType.EMPTY.value();
        if (!validColumns[col]) {
            validColumns[col] = true;
        }
    }

    /**
     * 启发式的局势评估函数
     *
     * @param board 棋盘状态
     * @return 评估得分
     */
    public int evaluate(int[][] board) {
        // +100 'Player1' wins, -100 'Player2' wins,
        // +10 for each three 'Player1' in a row, -10 for each three 'Player2' in a row,
        // +1 for each two 'Player1' in a row, -1 for each two 'Player2' in a row
        int player1Score = 0;
        int player2Score = 0;

        for (int r = 0; r < Core.ROW; r++) {
            for (int c = 0; c < Core.COL; c++) {
                for (int dirc = 0; dirc < Judge.N_DIRECTION; dirc++) {
                    int count = Judge.count(board, dirc, r, c);
                    int score = (int) Math.pow(10, count - 1);
                    if (board[r][c] == Core.Player.PLAYER_1.value()) {
                        player1Score += score;
                    } else if (board[r][c] == Core.Player.PLAYER_2.value()) {
                        player2Score += score;
                    }
                }
            }
        }

        // if the result is 0, then it'a a draw
        return player1Score - player2Score;
    }

    private void updateMemo(int[][] source) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, memo[i], 0, source[i].length);
        }
    }

    static class Move {
        int row;
        int col;
        int value;

        public Move(int value) {
            this.value = value;
        }

        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Move(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }
}