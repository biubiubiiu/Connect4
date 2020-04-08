package ui.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private static final int WIDTH = 180;
    private static final int HEIGHT = 80;

    private JLabel icon;
    private JLabel isTurn;

    public PlayerPanel(String name) {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Border border = BorderFactory.createBevelBorder(1);
        //1对应凹陷效果
        Border border1 = BorderFactory.createTitledBorder(border, name);
        setBorder(border1);

        ImageIcon img_1 = new ImageIcon(".\\res\\player.png");
        img_1.setImage(img_1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
        icon = new JLabel(img_1);

        ImageIcon img_2 = new ImageIcon(".\\res\\chess.png");
        img_2.setImage(img_2.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        isTurn = new JLabel(img_2);
        isTurn.setVisible(false);

        this.setLayout(new BorderLayout());
        this.add(icon, BorderLayout.WEST);
        this.add(isTurn, BorderLayout.EAST);
    }


    public void switchStatus() {
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
