package utils;

import javax.swing.*;

public class MenuFactory {

    /**
     * 通过字符串形式，快速构造菜单项
     * @param base 菜单项
     * @param items 子菜单项
     * @return 构造好的JMenu
     * @TODO 为菜单项设置点击行为
     */
    public static JMenu createJMenu(String base, String[] items) {
        JMenu baseMenu = new JMenu(base);
        for(String item: items) {
            baseMenu.add(new JMenuItem(item));
        }
        return baseMenu;
    }

}
