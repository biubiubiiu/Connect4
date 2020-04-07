package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import core.Core;
import core.GameControl;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ArchiveManager {

    public static final String BOARD = "board";
    public static final String CURRENT_PLAYER = "current_player";

    private static String PATH = "./save.json";

    public static JSONObject getJsonObject(Core core) throws RuntimeException {
        JSONObject object = new JSONObject();

        //board;
        String boardJsonString = JSON.toJSONString(core.getBoard());
        object.put(BOARD, boardJsonString);

        // current player
        object.put(CURRENT_PLAYER, core.getCurrPlayer().value());

        return object;
    }

    public static void loadJsonObject(JSONObject object, Core core) throws RuntimeException {
        int[][] savedBoard = new int[Core.ROW][Core.COL];
        JSONArray array = object.getJSONArray(BOARD);
        if (array.isEmpty()) {
            throw new RuntimeException("存档信息错误");
        }
        if (array.size() != Core.ROW) {
            throw new RuntimeException("存档格式错误");
        }
        for (int i = 0; i < array.size(); i++) {
            JSONArray arr = array.getJSONArray(i);
            if (arr.size() != Core.COL) {
                throw new RuntimeException("存档格式错误");
            }
            for (int j = 0; j < arr.size(); j++) {
                savedBoard[i][j] = (int) arr.get(j);
            }
        }

        int playerNum = object.getInteger(CURRENT_PLAYER);

        if (core != null) {
            core.setCurrPlayer(Core.Player.of(playerNum));
            core.setBoard(savedBoard);
        }
    }

    private static String loadFile(String path) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    public static boolean loadArchive(Core core) {
        File file = new File(PATH);
        if (!file.exists()) {
            return false;
        }
        try {
            JSONObject obj = JSON.parseObject(loadFile(PATH));
            loadJsonObject(obj, core);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public static boolean saveArchive(Core core) {
        File file = new File(PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            JSONObject obj = getJsonObject(core);
            byte[] bytes = obj.toString().getBytes(StandardCharsets.UTF_8);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        GameControl core = new GameControl();
        core.setBoard(new int[][]{{1, 1}, {0, 0}});
        JSONObject object = getJsonObject(core);

        GameControl obj = new GameControl();
        loadJsonObject(object, obj);
        GameControl.printBoard(obj.getBoard());
    }
}
