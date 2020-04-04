package core;

public class TestEndGame {
    public static boolean judgeBorder(int x, int size) {
        if (x >= size || x < 0) {
            return false;
        }
        return true;
    }

    public static int compuNum_main(int[][] map, int player, int x, int y, int dire_x, int dire_y, int size) {
        int i = 1;
        int j = 1;
        int count = 0;
        while (judgeBorder(i * dire_x + x, size) &&
                judgeBorder(j * dire_y + y, size) &&
                map[i * dire_x + x][j * dire_y + y] == player &&
                count <= 4) {
            count++;
            i++;
            j++;
        }
        return count;
    }

    public static int compuNum(int[][] map, int player, int x, int y, int dire, int size) {
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
        return compuNum_main(map, player, x, y, dire_x, dire_y, size) + compuNum_main(map, player, x, y, -dire_x, -dire_y, size) + 1;


    }

    public static boolean judgeEnd(int[][] map, int player, int x, int y, int size) {
        if (compuNum(map, player, x, y, 1, size) >= 4) {
            return true;
        }

        if (compuNum(map, player, x, y, 2, size) >= 4) {
            return true;
        }

        if (compuNum(map, player, x, y, 3, size) >= 4) {
            return true;
        }

        if (compuNum(map, player, x, y, 4, size) >= 4) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        int[][] map = {{0, 0, 1, 1, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}};
        map[3][1] = 1;
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println(judgeEnd(map, 1, 3, 1, 5));
        System.out.println(compuNum_main(map, 1, 3, 1, -1, -1, 5));


    }
}
