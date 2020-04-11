package ui;

import common.CommonReturnType;
import core.Core;
import core.GameControl;
import presenter.Presenter;
import ui.components.*;

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

    private Presenter presenter;

    public MainWindow() {
        super("Connect4");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        initPresenter();
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

        // 玩家信息组件
        players = new PlayerPanel[2];
        players[0] = new PlayerPanel("Player 1");
        players[1] = new PlayerPanel("Player 2");
        //玩家1先手
        players[0].reset();
        players[1].reset();
        players[0].toggle();

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
        timeDisplay.setHandler(presenter);
        menuBar.setHandler(presenter);
        panelButtons.setHandler(presenter);
        settings.setHandler(presenter);
    }

    public void onLoadArchiveFinish(CommonReturnType result, int playerNum) {
        stopClock();
        if (result.getStatus() == CommonReturnType.FAIL) {
            JOptionPane.showMessageDialog(null, result.getMessage(), "load", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, result.getMessage());
            timeDisplay.restartCountdown();
            panelChessBoard.repaint();
            players[0].reset();
            players[1].reset();
            players[playerNum].toggle();
        }
        continueClock();
    }

    /**
     * 初始化 Presenter
     */
    private void initPresenter() {
        this.presenter = new Presenter(this, new GameControl());
    }

    public void onTimeout(String playerName) {
        JOptionPane.showMessageDialog(null,
                playerName + " 超时了!\n请重新开始游戏");
        panelButtons.disableBtns();
    }

    public void onNewGame() {
        panelButtons.enableBtns();
        players[1].switchRole(Core.Player.PLAYER_2.isAi());
        players[0].reset();
        players[1].reset();
        players[0].toggle();
        timeDisplay.restartCountdown();
    }

    public void onSaveFinish(CommonReturnType result) {
        stopClock();
        if (result.getStatus() == CommonReturnType.FAIL) {
            JOptionPane.showMessageDialog(null, result.getMessage(), "save", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, result.getMessage());
        }
        continueClock();
    }

    public void enableComponents() {
        panelButtons.enableBtns();
        timeDisplay.restartCountdown();
    }

    public void disableComponents() {
        panelButtons.disableBtns();
        timeDisplay.stopCountdown();
    }

    public void refreshBoard(int[][] board) {
        this.panelChessBoard.setBoard(board);
        this.panelChessBoard.repaint();
    }

    public void onPlayerSwitch(int playerNum) {
        players[0].reset();
        players[1].reset();
        players[playerNum].toggle();
        timeDisplay.restartCountdown();
    }

    public void onGameOver(String playerName, boolean isDraw) {
        if (!isDraw) {
            JOptionPane.showMessageDialog(null, playerName + " wins!");
        } else {
            JOptionPane.showMessageDialog(null, "It's a Draw!");
        }
        disableComponents();
    }

    public void setPlayer2Ai(boolean isAi) {
        players[1].switchRole(isAi);
    }

    public void openSettings() {
        stopClock();
        settings.setVisible(true);
    }

    public void stopClock(){
        timeDisplay.stopCountdown();
    }

    public void continueClock(){
        timeDisplay.continueCountdown();
    }
}