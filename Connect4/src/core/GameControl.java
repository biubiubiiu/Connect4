package core;

/**
 * @author Lianghao, Fimon, Raymond
 */
public class GameControl implements Core {
    private int[][] board;
    private Player currPlayer;
    private Status gameState;
    private MiniMaxAI ai;

    public GameControl() {
        this.board = new int[ROW][COL];

        //默认从第一个玩家开始
        this.currPlayer = Player.PLAYER_1;

        this.gameState = Status.CONTINUE;
        this.ai = new MiniMaxAI();
    }

    @Override
    public void dropAt(int column) {
        if (!Judge.judgeBorder(column, board[0].length)) {
            return;
        }
        int top = -1;
        while (top < board.length - 1 && board[top + 1][column] == GridType.EMPTY.value()) {
            top++;
        }
        if (top == -1) {
            // 没有空位
            return;
        }
        board[top][column] = GridType.of(currPlayer);

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

    @Override
    public void reset() {
        this.gameState = Status.CONTINUE;
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
    public void setBoard(int[][] board) {
        this.board = board;
    }

    @Override
    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    @Override
    public void setGameState(Status gameState) {
        this.gameState = gameState;
    }

    @Override
    public void setSearchDepth(int depth) {
        this.ai.setMaxDepth(depth);
        this.ai.setAiPlayer(Player.PLAYER_2);
        System.out.println("setDepth: " + depth);
    }

    @Override
    public void aiMove() {
        System.out.println("ai moving");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int col = ai.generateMove(board);
        dropAt(col);
        switchPlayer();
    }

//    @Override
//    public void checkStatus() {
//        if (gameState == Status.CONTINUE) {
//            switchPlayer();
//            if (this.currPlayer.isAI()) {
//                aiMove();
//            }
//        }
//    }

    @Override
    public void switchPlayer() {
        this.currPlayer = currPlayer.next();
    }
}
