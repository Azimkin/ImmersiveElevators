package top.azimkin.utilities;

import org.bukkit.ChatColor;

public class TextUtilities {

    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
