package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.CommonReturnType;
import core.Core;
import core.GameControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ArchiveManagerTest {

    Core core;
    JSONObject jsonObject;

    @Before
    public void setUp() {
        core = new GameControl();
        jsonObject = new JSONObject();
    }

    @Test
    // TC-023
    public void getJsonObjectTest1() {
        core.setBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        });
        core.setGameState(Core.Status.WIN);
        core.setCurrPlayer(Core.Player.PLAYER_1);
        JSONObject obj = ArchiveManager.getJsonObject(core);
        assertEquals(obj.getInteger(ArchiveManager.STATUS).intValue(), core.getGameStatus().value());
        assertEquals(obj.getInteger(ArchiveManager.CURRENT_PLAYER).intValue(), core.getCurrPlayer().value());
        assertEquals(obj.getJSONArray(ArchiveManager.BOARD).toString(), JSON.toJSONString(core.getBoard()));
    }

    @Test(expected = RuntimeException.class)
    // TC-024
    public void getJsonObjectTest2() {
        core.setBoard(null);
        core.setGameState(Core.Status.WIN);
        core.setCurrPlayer(Core.Player.PLAYER_1);
        ArchiveManager.getJsonObject(core);
    }

    @Test(expected = RuntimeException.class)
    // TC-025
    public void getJsonObjectTest4() {
        core.setBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        });
        core.setGameState(Core.Status.WIN);
        core.setCurrPlayer(null);
        ArchiveManager.getJsonObject(core);
    }

    @Test(expected = RuntimeException.class)
    // TC-026
    public void getJsonObjectTest3() {
        core.setBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        });
        core.setGameState(null);
        core.setCurrPlayer(Core.Player.PLAYER_1);
        ArchiveManager.getJsonObject(core);
    }

    @Test(expected = RuntimeException.class)
    // TC-027
    public void getJsonObjectTest5() {
        core.setBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
        });
        core.setGameState(Core.Status.WIN);
        core.setCurrPlayer(null);
        ArchiveManager.getJsonObject(core);
    }

    @Test(expected = RuntimeException.class)
    // TC-028
    public void getJsonObjectTest6() {
        core.setBoard(new int[][]{});
        core.setGameState(Core.Status.WIN);
        core.setCurrPlayer(null);
        ArchiveManager.getJsonObject(core);
    }

    @Test
    // TC-029
    public void loadJsonObjectTest1() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 1);
        jsonObject.put(ArchiveManager.STATUS, 1);
        ArchiveManager.loadJsonObject(jsonObject, core);
        assertEquals(JSON.toJSONString(core.getBoard()), jsonObject.get(ArchiveManager.BOARD));
        assertEquals(core.getCurrPlayer().value(), jsonObject.getInteger(ArchiveManager.CURRENT_PLAYER).intValue());
        assertEquals(core.getGameStatus().value(), jsonObject.getInteger(ArchiveManager.STATUS).intValue());
    }

    @Test(expected = RuntimeException.class)
    // TC-030
    public void loadJsonObjectTest2() {
        jsonObject.put(ArchiveManager.BOARD, null);
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 1);
        jsonObject.put(ArchiveManager.STATUS, 1);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-031
    public void loadJsonObjectTest3() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, null);
        jsonObject.put(ArchiveManager.STATUS, 1);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-032
    public void loadJsonObjectTest4() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 1);
        jsonObject.put(ArchiveManager.STATUS, null);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-033
    public void loadJsonObjectTest5() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 1);
        jsonObject.put(ArchiveManager.STATUS, null);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-034
    public void loadJsonObjectTest6() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, -1);
        jsonObject.put(ArchiveManager.STATUS, 1);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-035
    public void loadJsonObjectTest7() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 3);
        jsonObject.put(ArchiveManager.STATUS, 1);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-036
    public void loadJsonObjectTest8() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 1);
        jsonObject.put(ArchiveManager.STATUS, -1);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-037
    public void loadJsonObjectTest9() {
        jsonObject.put(ArchiveManager.BOARD, "[[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 1);
        jsonObject.put(ArchiveManager.STATUS, 3);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test(expected = RuntimeException.class)
    // TC-038
    public void loadJsonObjectTest10() {
        jsonObject.put(ArchiveManager.BOARD, "[[3,3,3,3,3,3,3],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,1,2,0,0]]");
        jsonObject.put(ArchiveManager.CURRENT_PLAYER, 1);
        jsonObject.put(ArchiveManager.STATUS, 3);
        ArchiveManager.loadJsonObject(jsonObject, core);
    }

    @Test
    // TC-039
    public void loadArchiveTest1() {
        CommonReturnType result = ArchiveManager.loadArchive(core, "./save.json");
        assertEquals(result.getStatus(), CommonReturnType.SUCCESS);
    }

    @Test
    // TC-040
    public void loadArchiveTest2() {
        String path = "./save2.json";
        CommonReturnType result = ArchiveManager.loadArchive(core, path);
        File file = new File(path);
        assertFalse(file.exists());
        assertEquals(result.getStatus(), CommonReturnType.FAIL);
    }

    @Test
    // TC-041
    public void saveArchiveTest1() {
        core.setBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        });
        core.setGameState(Core.Status.WIN);
        core.setCurrPlayer(Core.Player.PLAYER_1);
        CommonReturnType result = ArchiveManager.saveArchive(core, "./save3.json");
        assertEquals(result.getStatus(), CommonReturnType.SUCCESS);
    }

    @Test
    // TC-042
    public void saveArchiveTest2() {
        String path = "./save4.json";
        core.setBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 2, 1, 0, 0},
                {0, 0, 1, 1, 2, 0, 0},
        });
        core.setGameState(Core.Status.WIN);
        core.setCurrPlayer(Core.Player.PLAYER_1);
        CommonReturnType result = ArchiveManager.saveArchive(core, path);
        File file = new File(path);
        assertTrue(file.exists());
    }

    @After
    public void tearDown() {
        core = null;
        jsonObject = null;
    }
}