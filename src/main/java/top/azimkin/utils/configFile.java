package top.azimkin.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import top.azimkin.ImmersiveElevators;

import java.io.File;
import java.io.IOException;

public class configFile {
    private static Plugin plugin = ImmersiveElevators.getPlugin();
    private static File file = null;

    public static void setup() {
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists())
        {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
        }
    }

    public static String getValue(String selection)
    {
        if (plugin.getConfig().isConfigurationSection(selection))
        {
            return plugin.getConfig().getString(selection);
        }
        else
        {
            plugin.getConfig().set(selection, "0");
            return plugin.getConfig().getString(selection);
        }
    }
}
