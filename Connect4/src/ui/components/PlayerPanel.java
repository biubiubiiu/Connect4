package ui.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author Fimon
 */
public class PlayerPanel extends JPanel {
    private static final int WIDTH = 180;
    private static final int HEIGHT = 80;

    private final JLabel isTurn;
    private final JLabel icon;


    public PlayerPanel(String name) {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Border border1 = BorderFactory.createBevelBorder(1);
        //1对应凹陷效果
        Border border = BorderFactory.createTitledBorder(border1, name);
        setBorder(border);

        java.net.URL url1 = this.getClass().getResource("/player.png");
        ImageIcon img1 = new ImageIcon(url1);
        img1.setImage(img1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
        icon = new JLabel(img1);

        java.net.URL url2 = this.getClass().getResource("/chess.png");
        ImageIcon img2 = new ImageIcon(url2);
        img2.setImage(img2.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        isTurn = new JLabel(img2);

        this.setLayout(new BorderLayout());
        this.add(icon, BorderLayout.WEST);
        this.add(isTurn, BorderLayout.EAST);
    }


    public void switchRole(boolean isAi){
        Border border1 = BorderFactory.createBevelBorder(1);
        java.net.URL url;
        Border border;

        if (isAi){
            url = this.getClass().getResource("/robot2.png");
            border = BorderFactory.createTitledBorder(border1, "Ai");
        }
        else {
            url = this.getClass().getResource("/player.png");
            border = BorderFactory.createTitledBorder(border1, "Player 2");
        }

        ImageIcon img = new ImageIcon(url);
        img.setImage(img.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
        icon.setIcon(img);

        this.setBorder(border);
    }


    public void toggle() {
        if (isTurn.isVisible()) {
            isTurn.setVisible(false);
        } else {
            isTurn.setVisible(true);
        }
    }

    public void reset() {
        isTurn.setVisible(false);
    }
}
