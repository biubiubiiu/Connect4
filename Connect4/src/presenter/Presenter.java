package presenter;

import common.CommonReturnType;
import core.Core;
import ui.MainWindow;
import ui.components.BtnGroup;
import ui.components.CountdownTimer;
import ui.components.MenuBar;
import ui.components.Settings;
import utils.ArchiveManager;

import java.util.concurrent.*;

/**
 * @author Raymond
 */
public class Presenter implements CountdownTimer.TimeoutCallback, MenuBar.MenuBarEvent, BtnGroup.BtnEvent, Settings.SettingsEvent {

    private final MainWindow window;
    private final Core core;
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            (ThreadFactory) Thread::new);

    public Presenter(MainWindow window, Core core) {
        this.window = window;
        this.core = core;
    }

    @Override
    public void newGame() {
        core.reset();
        window.onNewGame();
        window.refreshBoard(core.getBoard());
    }

    @Override
    public void saveGame() {
        CommonReturnType result = ArchiveManager.saveArchive(core);
        window.onSaveFinish(result);
    }

    @Override
    public void loadArchive() {
        CommonReturnType result = ArchiveManager.loadArchive(core);
        window.onLoadArchiveFinish(result, core.getCurrPlayer() == Core.Player.PLAYER_1 ? 0 : 1);
        if (core.getGameStatus() == Core.Status.CONTINUE) {
            window.enableComponents();
        }
        window.refreshBoard(core.getBoard());
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void viewSettings() {
        window.openSettings();
    }

    private void checkAiMove() {
        if(!core.getCurrPlayer().isAi()) {
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

    @Override
    public void timeout() {
        core.setGameState(Core.Status.FAIL);
        window.onTimeout(core.getCurrPlayer().toString());
    }

    @Override
    public void changeGameMode(int mode) {
        if (mode == Core.GAME_MODE.HUMAN_VS_AI.value) {
            Core.Player.PLAYER_2.setAiPlayer();
            window.setPlayer2Ai(true);
        } else {
            Core.Player.PLAYER_2.setHumanPlayer();
            window.setPlayer2Ai(false);
        }
    }

    @Override
    public void changeDepth(int depth) {
        core.setSearchDepth(depth);
    }

    @Override
    public void buttonClick(int i) {
        boolean succeed = core.dropAt(i);
        if(succeed) {
            window.refreshBoard(core.getBoard());
            checkStatus();
            checkAiMove();
        }
    }
}
