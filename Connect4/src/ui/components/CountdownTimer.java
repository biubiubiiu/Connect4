package ui.components;//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountdownTimer extends JPanel {

    public interface timeoutCallback {
        public void run();
    }

    // 计时时长：15000ms
    private static final int COUNTDOWN = 15000;
    private static final int ONE_SEC = 1000;
    private int current;

    private final JLabel display;
    private final Timer timer;

    private timeoutCallback callback;

    public CountdownTimer() {
        super();
        this.setPreferredSize(new Dimension(50, 50));

        display = new JLabel(timeFormat(COUNTDOWN));
        timer = new Timer(ONE_SEC, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current -= ONE_SEC;
                display.setText(timeFormat(current));
                if (current <= 0) {
                    timer.stop();
                    callback.run();
                }
            }
        });
        timer.setRepeats(true);
        display.setOpaque(false);

        display.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(display);
    }

    private String timeFormat(int time) {
        return (time / 1000 + "s");
    }

    public void restartCountdown() {
        // 重新装入倒计时
        current = COUNTDOWN;
        display.setText(timeFormat(current));
        timer.start();
    }

    public void stopCountdown() {
        timer.stop();
    }

    public void setTimeoutCallback(timeoutCallback callback) {
        this.callback = callback;
    }

    //绘制背景图片
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon(".\\res\\clock.png");
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}