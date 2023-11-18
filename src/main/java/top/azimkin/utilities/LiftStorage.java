package top.azimkin.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import top.azimkin.ImmersiveElevators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LiftStorage {
    private static ImmersiveElevators PLUGIN = ImmersiveElevators.getInstance();
    private static File file;
    private static FileConfiguration data;
    private static final String Lifts = "Lifts.";

    public static void setup() {
        file = new File(PLUGIN.getDataFolder(), "LiftData.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                PLUGIN.getLogger().info("Can't create new file!");
                PLUGIN.getServer().getPluginManager().disablePlugin(PLUGIN);
            }
        }
        data = YamlConfiguration.loadConfiguration(file);
        PLUGIN.getLogger().info(Lang.getClearLang("LiftDataLoaded"));
    }

    public static String getStr(String route) {
        return data.getString(route) == null ? "0" : data.getString(route);
    }

    public static String getStr(String name, String route) {
        return data.getString(Lifts + name + "." + route);
    }

    public static int getInt(String name, String route) {
        if (data.contains(route)) {
            return data.getInt(Lifts + name + "." + route);
        } else {
            return -2147483648; // error code lol
        }
    }

    public static List<String> getLifts() {
        List<String> temp = new ArrayList<>();
        if (data.isConfigurationSection("Lifts")) {
            ConfigurationSection liftsSelection = data.getConfigurationSection("Lifts");
            temp.addAll(liftsSelection.getKeys(false));
        }
        return temp;
    }

    public static Material getMat(String name, String route) {
        return Material.getMaterial(data.getString(Lifts + name + "." + route));
    }

    public static World getWorld(String name) {
        return Bukkit.getWorld(data.getString(Lifts + name + ".world"));
    }
}
