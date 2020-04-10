package core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameControlTest {
    private GameControl testObj;

    @Before
    public void setUp() {
        this.testObj = new GameControl();
    }

    @After
    public void tearDown() {
        this.testObj = null;
    }

    @Test
    // TC-008
    public void dropAtTest1() {
        testObj.setBoard(new int[][]{{0, 0}, {0, 0}});
        testObj.dropAt(1);
        assertArrayEquals(new int[][]{{0, 0}, {0, 1}}, testObj.getBoard());
    }

    @Test
    // TC-009
    public void dropAtTest2() {
        int[][] board = new int[][]{{0, 0}, {0, 0}};
        testObj.setBoard(board);
        testObj.dropAt(-1);
        assertArrayEquals(board, testObj.getBoard());
    }

    @Test
    // TC-010
    public void dropAtTest3() {
        int[][] board = new int[][]{{0, 0}, {0, 0}};
        testObj.setBoard(board);
        testObj.dropAt(2);
        assertArrayEquals(board, testObj.getBoard());
    }

    @Test
    // TC-011
    public void dropAtTest4() {
        int[][] board = new int[][]{{0, 1}, {0, 1}};
        testObj.setBoard(board);
        testObj.dropAt(2);
        assertArrayEquals(board, testObj.getBoard());
    }

    @Test
    // TC-003
    public void resetBoardTest1() {
        testObj.setBoard(new int[][]{{1, 1}, {1, 1}});
        testObj.reset();
        assertArrayEquals(new int[][]{{0, 0}, {0, 0}}, testObj.getBoard());
    }

    @Test(expected = NullPointerException.class)
    // TC-004
    public void resetBoardTest2() {
        testObj.setBoard(null);
        testObj.reset();
    }

    @Test
    // TC-005
    public void resetBoardTest3() {
        testObj.setBoard(new int[][]{{}, {}});
        testObj.reset();
        assertArrayEquals(new int[][]{{}, {}}, testObj.getBoard());
    }

    @Test
    // TC-006
    public void resetPlayerTest1() {
        testObj.setCurrPlayer(Core.Player.PLAYER_1);
        testObj.reset();
        assertEquals(Core.Player.PLAYER_1, testObj.getCurrPlayer());
    }

    @Test
    // TC-007
    public void resetPlayerTest2() {
        testObj.setCurrPlayer(Core.Player.PLAYER_2);
        testObj.reset();
        assertEquals(Core.Player.PLAYER_1, testObj.getCurrPlayer());
    }

    @Test
    // TC-001
    public void switchPlayerTest1() {
        testObj.setCurrPlayer(Core.Player.PLAYER_1);
        testObj.switchPlayer();
        assertEquals(Core.Player.PLAYER_2, testObj.getCurrPlayer());
    }

    @Test
    // TC-002
    public void switchPlayerTest2() {
        testObj.setCurrPlayer(Core.Player.PLAYER_2);
        testObj.switchPlayer();
        assertEquals(Core.Player.PLAYER_1, testObj.getCurrPlayer());
    }
}