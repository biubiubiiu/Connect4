package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.CommonReturnType;
import core.Core;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ArchiveManager {

    public static final String BOARD = "board";
    public static final String CURRENT_PLAYER = "current_player";
    public static final String STATUS = "status";

    private static String PATH = "./save.json";

    public static JSONObject getJsonObject(Core core) throws RuntimeException {
        JSONObject object = new JSONObject();

        //board;
        int[][] board = core.getBoard();
        if (board == null) {
            throw new RuntimeException("Internal info error");
        }
        if (board.length != Core.ROW) {
            throw new RuntimeException("Internal info error");
        }
        if (board.length != 0 && board[0].length != Core.COL) {
            throw new RuntimeException("Internal info error");
        }
        String boardJsonString = JSON.toJSONString(core.getBoard());
        object.put(BOARD, boardJsonString);

        // current player
        Core.Player player = core.getCurrPlayer();
        if (player == null) {
            throw new RuntimeException("Internal info error");
        }
        object.put(CURRENT_PLAYER, player.value());

        // game status
        Core.Status status = core.getGameStatus();
        if (status == null) {
            throw new RuntimeException("Internal info error");
        }
        object.put(STATUS, status.value());
        return object;
    }

    public static void loadJsonObject(JSONObject object, Core core) throws RuntimeException {
        if (core == null) {
            throw new RuntimeException("内部错误");
        }
        int[][] savedBoard = new int[Core.ROW][Core.COL];
        JSONArray array = object.getJSONArray(BOARD);
        if (array.isEmpty()) {
            throw new RuntimeException("存档信息不正确，无法读取");
        }
        if (array.size() != Core.ROW) {
            throw new RuntimeException("存档信息不正确，无法读取");
        }
        for (int i = 0; i < array.size(); i++) {
            JSONArray arr = array.getJSONArray(i);
            if (arr.size() != Core.COL) {
                throw new RuntimeException("存档信息不正确，无法读取");
            }
            for (int j = 0; j < arr.size(); j++) {
                savedBoard[i][j] = (int) arr.get(j);
            }
        }

        int playerNum = object.getInteger(CURRENT_PLAYER);
        int status = object.getInteger(STATUS);

        core.setCurrPlayer(Core.Player.of(playerNum));
        core.setGameState(Core.Status.of(status));
        core.setBoard(savedBoard);
    }

    private static String File2String(String path) throws RuntimeException {
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
            throw new RuntimeException(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return builder.toString();
    }

    public static CommonReturnType loadArchive(Core core) {
        File file = new File(PATH);
        if (!file.exists()) {
            return new CommonReturnType(CommonReturnType.FAIL, "没有找到存档文件");
        }
        try {
            JSONObject obj = JSON.parseObject(File2String(PATH));
            loadJsonObject(obj, core);
        } catch (RuntimeException e) {
            return new CommonReturnType(CommonReturnType.FAIL, "加载失败");
        }
        return new CommonReturnType(CommonReturnType.SUCCESS, "加载成功");
    }

    public static CommonReturnType saveArchive(Core core) {
        File file = new File(PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return new CommonReturnType(CommonReturnType.FAIL, e.getMessage());
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            JSONObject obj = getJsonObject(core);
            byte[] bytes = obj.toString().getBytes(StandardCharsets.UTF_8);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return new CommonReturnType(CommonReturnType.FAIL, e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    return new CommonReturnType(CommonReturnType.FAIL, e.getMessage());
                }
            }
        }
        return new CommonReturnType(CommonReturnType.SUCCESS, "Successfully saved");
    }
}
