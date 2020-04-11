package presenter;

import common.CommonReturnType;
import core.Core;
import ui.MainWindow;
import utils.ArchiveManager;

import java.util.concurrent.*;

/**
 * @author 19471
 */
public class Presenter {

    private MainWindow window;
    private Core core;
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            (ThreadFactory) Thread::new);

    public Presenter(MainWindow window, Core core) {
        this.window = window;
        this.core = core;
    }

    public void newGame() {
        core.reset();
        window.onNewGame();
        window.refreshBoard(core.getBoard());
    }

    public void saveGame() {
        CommonReturnType result = ArchiveManager.saveArchive(core);
        window.onSaveFinish(result);
    }

    public void loadArchive() {
        CommonReturnType result = ArchiveManager.loadArchive(core);
        window.onLoadArchiveFinish(result);
        if (core.getGameStatus() == Core.Status.CONTINUE) {
            window.enableComponents();
        }
        window.refreshBoard(core.getBoard());
    }

    public void dropAt(int col) {
        boolean succeed = core.dropAt(col);
        if(succeed) {
            window.refreshBoard(core.getBoard());
            checkStatus();
            checkAiMove();
        }
    }

    public void checkAiMove() {
        if(!core.getCurrPlayer().isAI()) {
            window.enableComponents();
            return;
        }
        window.disableComponents();
        if (core.getGameStatus() == Core.Status.CONTINUE) {
            executor.execute(() -> {
                core.aiMove();
                window.refreshBoard(core.getBoard());
                checkStatus();
                checkAiMove();
            });
        }
    }

    private void checkStatus() {
        if(core.getGameStatus() != Core.Status.CONTINUE) {
            window.onGameOver(core.getCurrPlayer().toString(), core.getGameStatus() == Core.Status.FAIL);
        } else {
            window.onPlayerSwitch(core.getCurrPlayer() == Core.Player.PLAYER_1 ? 0 : 1);
        }
    }

    public void timeout() {
        core.setGameState(Core.Status.FAIL);
        window.onTimeout();
    }

    public Core.Player getCurrentPlayer() {
        return core.getCurrPlayer();
    }

    public void changeGameMode(int mode) {
        if (mode == Core.GAME_MODE.HUMAN_VS_AI.value) {
            Core.Player.PLAYER_2.setAIPlayer();
            window.setPlayer2Ai(true);
        } else {
            Core.Player.PLAYER_2.setHumanPlayer();
            window.setPlayer2Ai(false);
        }
    }

    public void changeDepth(int depth) {
        core.setSearchDepth(depth);
    }
}
