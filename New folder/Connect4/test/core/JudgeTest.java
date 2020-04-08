package core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JudgeTest {
    /**
     * 待测试方法：judge
     */


    @Test
    //TC-012
    public void judgeTest1() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0},
                {1, 1, 1, 1, 0, 1, 0},
        };
        assertEquals(Core.Status.WIN, Judge.judge(board, 5, 3));
    }

    @Test
    //TC-013
    public void judgeTest2() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 1, 2, 0, 0},
                {0, 0, 1, 1, 2, 1, 0},
        };
        assertEquals(Core.Status.WIN, Judge.judge(board, 2, 4));
    }

    @Test
    //TC-014
    public void judgeTest3() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 0},
                {0, 2, 2, 1, 2, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        };
        assertEquals(Core.Status.WIN, Judge.judge(board, 2, 5));
    }

    @Test
    //TC-015
    public void judgeTest4() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        };
        assertEquals(Core.Status.WIN, Judge.judge(board, 2, 1));
    }

    @Test
    //TC-016
    public void judgeTest5() {
        int[][] board = new int[][]{
                {1, 2, 1, 2, 1, 2, 1},
                {2, 1, 2, 1, 1, 1, 2},
                {1, 2, 1, 1, 2, 2, 1},
                {2, 1, 1, 2, 2, 1, 2},
                {1, 2, 1, 2, 1, 2, 1},
                {2, 1, 2, 2, 2, 1, 2},
        };
        assertEquals(Core.Status.FAIL, Judge.judge(board, 0, 0));
    }

    @Test
    //TC-017
    public void judgeTest6() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        };
        assertEquals(Core.Status.CONTINUE, Judge.judge(board, 4, 3));
    }

    @Test
    //TC-018
    public void judgeTest7() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0},
                {1, 1, 1, 0, 0, 1, 0},
        };
        assertEquals(Core.Status.CONTINUE, Judge.judge(board, 5, 2));
    }

    @Test
    //TC-019
    public void judgeTest8() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 1, 2, 0, 0},
                {0, 0, 1, 1, 2, 1, 0},
        };
        assertEquals(Core.Status.CONTINUE, Judge.judge(board, 3, 4));
    }

    @Test
    //TC-020
    public void judgeTest9() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0},
                {0, 2, 2, 1, 2, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        };
        assertEquals(Core.Status.CONTINUE, Judge.judge(board, 3, 4));
    }

    @Test
    //TC-021
    public void judgeTest10() {
        int[][] board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        };
        assertEquals(Core.Status.CONTINUE, Judge.judge(board, 3, 2));
    }
}