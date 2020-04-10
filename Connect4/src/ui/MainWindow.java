package ui;

import common.CommonReturnType;
import core.Core;
import core.GameControl;
import ui.components.*;
import utils.ArchiveManager;

import javax.swing.*;

/**
 * @author Fimon
 */
public class MainWindow extends JFrame {

    /**
     * 窗口大小参数
     */
    static final int WINDOW_WIDTH = 850;
    static final int WINDOW_HEIGHT = 670;

    /**
     * 窗口四周间隙参数
     */
    static final int GAP_HORIZONTAL = 5;
    static final int GAP_VERTICAL = 4;

    private JPanel panelMain;
    private Board panelChessBoard;
    private PlayerPanel[] players;
    private CountdownTimer timeDisplay;
    private BtnGroup panelButtons;
    private MenuBar menuBar;
    private Settings settings;

    public MainWindow() {
        super("Connect4");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        initComponents();
        initLayout();
        initEventHandler();

        //开始计时
        timeDisplay.restartCountdown();
    }

    /**
     * 初始化各组件
     */
    private void initComponents() {
        //主体选用SpringLayout布局
        panelMain = new JPanel(new SpringLayout());
        this.setContentPane(panelMain);

        // 棋盘组件
        panelChessBoard = new Board();
        panelChessBoard.setCore(new GameControl());

        // 玩家信息组件
        players = new PlayerPanel[2];
        players[0] = new PlayerPanel("Player 1");
        players[1] = new PlayerPanel("Player 2");
        //玩家1先手
        players[0].switchStatus();

        //计时器组件
        timeDisplay = new CountdownTimer();

        // 按键组
        panelButtons = new BtnGroup(Core.COL);

        // 添加菜单栏
        menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        // 设置页
        settings = new Settings();
        settings.setVisible(false);
    }

    /**
     * 设置各组件位置
     */
    private void initLayout() {
        SpringLayout springLayout = (SpringLayout) panelMain.getLayout();
        //将棋盘加到panelMain中并设置其格式
        panelMain.add(panelChessBoard);
        SpringLayout.Constraints panelChessBoardCons = springLayout.getConstraints(panelChessBoard);
        panelChessBoardCons.setX(Spring.constant(GAP_HORIZONTAL));
        panelChessBoardCons.setY(Spring.constant(GAP_VERTICAL));

        // 将按钮组加到panelMain并设置其格式
        panelMain.add(panelButtons);
        SpringLayout.Constraints panelButtonsCons = springLayout.getConstraints(panelButtons);
        panelButtonsCons.setX(Spring.constant(GAP_HORIZONTAL));
        panelButtonsCons.setY(
                // padding-top: 20
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(20))
        );

        //将计时器组件加入到panelMain
        panelMain.add(timeDisplay);
        SpringLayout.Constraints timeDisplayCons = springLayout.getConstraints(timeDisplay);
        timeDisplayCons.setX(
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.EAST),
                        Spring.constant(80)
                )
        );
        timeDisplayCons.setY(Spring.constant(50));

        // 将玩家信息组加到panelMain并设置其格式
        panelMain.add(players[0]);
        SpringLayout.Constraints player1Cons = springLayout.getConstraints(players[0]);
        player1Cons.setX(
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.EAST),
                        Spring.constant(10)
                )
        );
        player1Cons.setY(
                Spring.sum(
                        timeDisplayCons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(50)
                )
        );

        panelMain.add(players[1]);
        SpringLayout.Constraints player2Cons = springLayout.getConstraints(players[1]);
        player2Cons.setX(
                Spring.sum(
                        panelChessBoardCons.getConstraint(SpringLayout.EAST),
                        Spring.constant(10)
                )
        );
        player2Cons.setY(
                Spring.sum(
                        player1Cons.getConstraint(SpringLayout.SOUTH),
                        Spring.constant(20)
                )
        );
    }

    /**
     * 设置各组件的事件处理方法
     */
    private void initEventHandler() {
        timeDisplay.setTimeoutCallback(() -> {
                    JOptionPane.showMessageDialog(null,
                            panelChessBoard.getCore().getCurrPlayer() + " 超时了!");
                    panelChessBoard.getCore().setGameState(Core.Status.FAIL);
                }
        );

        menuBar.setHandler(new MenuBar.MenuBarEvent() {
            @Override
            public void newGame() {
                panelChessBoard.getCore().reset();
                panelChessBoard.repaint();
                panelButtons.enableBtns();
                players[0].reset();
                players[1].reset();
                players[0].switchStatus();
                timeDisplay.restartCountdown();
            }

            @Override
            public void saveGame() {
                CommonReturnType result = ArchiveManager.saveArchive(panelChessBoard.getCore());
                if (result.getStatus() == CommonReturnType.FAIL) {
                    JOptionPane.showMessageDialog(null, result.getMessage(), "save", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                }
            }

            @Override
            public void loadArchive() {
                CommonReturnType result = ArchiveManager.loadArchive(panelChessBoard.getCore());
                if (result.getStatus() == CommonReturnType.FAIL) {
                    JOptionPane.showMessageDialog(null, result.getMessage(), "load", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                    timeDisplay.restartCountdown();
                    panelChessBoard.repaint();
                    if (panelChessBoard.getCore().getGameStatus() == Core.Status.CONTINUE) {
                        panelButtons.enableBtns();
                    }
                    players[0].reset();
                    players[1].reset();
                    players[panelChessBoard.getCore().getCurrPlayer() == Core.Player.PLAYER_1 ? 0 : 1].switchStatus();
                }
            }

            @Override
            public void exit() {
                System.exit(0);
            }

            @Override
            public void viewSettings() {
                settings.setVisible(true);
            }
        });

        panelButtons.setHandler(i -> {
            if (panelChessBoard.getCore().getCurrPlayer().isAI()) {
                return;
            }
            if (panelChessBoard.getCore().getGameStatus() != Core.Status.CONTINUE) {
                return;
            }
            panelChessBoard.getCore().dropAt(i);
            panelChessBoard.repaint();
            panelChessBoard.getCore().checkStatus();
            panelChessBoard.repaint();

            switch (panelChessBoard.getCore().getGameStatus()) {
                case WIN:
                    timeDisplay.stopCountdown();
                    JOptionPane.showMessageDialog(null, panelChessBoard.getCore().getCurrPlayer() + " wins!");
                    panelButtons.disableBtns();
                    break;
                case FAIL:
                    timeDisplay.stopCountdown();
                    JOptionPane.showMessageDialog(null, "It's a Draw!");
                    panelButtons.disableBtns();
                    break;
                case CONTINUE:
                    //交换玩家
                    players[0].switchStatus();
                    players[1].switchStatus();
                    timeDisplay.restartCountdown();
                    break;
                default:
                    break;
            }
        });

        settings.setHandler(new Settings.SettingsEvent() {
            @Override
            public void changeGameMode(int mode) {
                if (mode == Core.GAME_MODE.HUMAN_VS_AI.value) {
                    Core.Player.PLAYER_2.setAIPlayer();
                } else {
                    Core.Player.PLAYER_2.setHumanPlayer();
                }
            }

            @Override
            public void changeDepth(int depth) {
                panelChessBoard.getCore().setSearchDepth(depth);
            }
        });
    }
}