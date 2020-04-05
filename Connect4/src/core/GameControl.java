package core;

public class GameControl implements Core {
    private int[][] board;
    private Player currPlayer;
    private Status gameState;

    public GameControl() {
        this.board = new int[ROW][COL];

        //默认从第一个玩家开始
        this.currPlayer = Player.PLAYER_1;

        this.gameState = Status.CONTINUE;
    }


    @Override
    public void dropAt(int column) {
        int top = -1;
        while (top < ROW - 1 && board[top + 1][column] == GridType.EMPTY.value()) {
            top++;
        }
        if (top == -1) {
            // 没有空位
            return;
        }
        board[top][column] = GridType.of(currPlayer);

        printBoard(this.board);

        // 每次落子后判断一次游戏状态
        // (top, column) 为本次落子位置
        this.gameState = Judge.judge(this.board, top, column);
    }

    @Override
    public int[][] getBoard() {
        return this.board;
    }

    @Override
    public Status getGameStatus() {
        return this.gameState;
    }

    @Override
    public Player getCurrPlayer() {
        return this.currPlayer;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void setGameState(Status gameState) {
        this.gameState = gameState;
    }

    @Override
    public void reset() {
        resetBoard();
        resetPlayer();
    }

    private void resetBoard() {
        if (board.length == 0) {
            return;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                this.board[i][j] = GridType.EMPTY.value();
            }
        }
    }

    private void resetPlayer() {
        this.currPlayer = Player.PLAYER_1;
    }

    @Override
    public void switchPlayer() {
        this.currPlayer = currPlayer.next();
    }

    private static void printBoard(int[][] board) {
        System.out.println("[");
        for (int i = 0; i < board.length; i++) {
            System.out.print("[");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + ",");
            }
            System.out.print("]\n");
        }
        System.out.println("]");
    }
}
