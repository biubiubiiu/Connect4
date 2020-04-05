package core;

public class GameControl implements Core {
    private int[][] board;
    private int currPlayer;
    private int gameState;   //游戏状态，见Core

    public GameControl(){
        this.board = new int[ROW][COL];

        //默认从第一个玩家开始
        this.currPlayer = PLAYER_1;

        this.gameState = CONTINUE;
    }


    @Override
    public void dropAt(int column){
        int top = -1;
        while (top < Core.ROW-1 && board[top + 1][column] == EMPTY) {
            top++;
        }
        if (top == -1) {
            // 没有空位
            return;
        }
        board[top][column] = currPlayer;

        //每次落子后判断一次游戏状态
        //top和column值可能有问题，两边没统一，需要微调
        this.gameState = Judge.judge(this.board, this.currPlayer, top, column, ROW);
    }

    @Override
    public int[][] getBoard(){
        return this.board;
    }

    @Override
    public int getGameStatus() {
        return this.gameState;
    }

    @Override
    public int getCurrPlayer() {
        return this.currPlayer;
    }

    @Override
    public void reset() {
        for (int i=0; i<ROW; i++)
            for (int j=0; j<COL; j++)
                this.board[i][j] = EMPTY;
        //这里是不是可以重新建一个数组效率高点
        this.currPlayer = PLAYER_1;
    }

    @Override
    public void switchPlayer() {
        this.currPlayer = (this.currPlayer + 1) & 1;
    }

}
