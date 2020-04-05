package core;

/**
 * 将各种判定函数独立出来
 * 写在一个单独的类里
 */

public class Judge {
    //对下面两个判断函数封装了一下
    //返回游戏状态
    //相关定义在Core中
    public static int judge(int[][] board, int currPlayer, int x, int y, int size){
        boolean flag = judgeSuccess(board, currPlayer, x, y, size);
        if (flag)
            return Core.WIN;

        flag = judgeFail(board);
        if (flag)
            return Core.FAIL;

        return Core.CONTINUE;
    }

    //判定是否连成4个
    //这个判断不太完整，没有针对都放满的情况
    //修改了一下
    private static boolean judgeSuccess(int[][] board, int player, int x, int y, int size){
        if (compuNum(board, player, x, y, 1, size) >= 4){
            return true;
        }

        if (compuNum(board, player, x, y, 2, size) >= 4){
            return true;
        }

        if (compuNum(board, player, x, y, 3, size) >= 4){
            return true;
        }

        if (compuNum(board, player, x, y, 4, size) >= 4){
            return true;
        }
        return false;
    }

    //这个判断是否全填满
    //未填满false，填满true
    private static boolean judgeFail(int[][] board){
        for (int i=0; i<Core.ROW; i++)
            for (int j=0; j<Core.COL; j++)
                if (board[i][j] == Core.EMPTY)
                    return false;

        return true;
    }




    //这是judgeSuccess依赖的一些函数
    public static int compuNum(int[][] board, int player, int x, int y,int dire, int size){
        int dire_x = 0;
        int dire_y = 0;
        if (dire == 1){ //横向
            dire_x = 1;
            dire_y = 0;
        }

        if (dire == 2){ //竖向
            dire_x = 0;
            dire_y = 1;
        }

        if (dire == 3){ //斜向
            dire_x = 1;
            dire_y = 1;
        }

        if (dire == 4){ //斜向
            dire_x = -1;
            dire_y = 1;
        }

        //从两个方向计算连子
        return compuNum_main(board, player, x, y, dire_x, dire_y, size) + compuNum_main(board, player, x, y, -dire_x, -dire_y, size) + 1;
    }

    public static int compuNum_main(int[][] board, int player, int x, int y,int dire_x, int dire_y ,int size){
        int i = 1;
        int j = 1;
        int count = 0;
        while(  judgeBorder(i * dire_x + x , size) &&
                judgeBorder(j * dire_y + y, size) &&
                board[i * dire_x + x][j * dire_y + y] == player &&
                count <= 4){
            count ++;
            i++;
            j++;
        }
        return count;
    }

    public static  boolean judgeBorder(int x, int size){
        if (x >= size || x < 0){
            return false;
        }
        return true;
    }

}

