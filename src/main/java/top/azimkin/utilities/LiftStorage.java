package top.azimkin.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import top.azimkin.ImmersiveElevators;
import top.azimkin.lift.XZW;

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

    public static FileConfiguration getDataFile() {
        return data;
    }

    public static void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStr(String route) {
        return data.getString(route) == null ? "0" : data.getString(route);
    }

    public static String getStr(String name, String route) {
        return data.getString(Lifts + name + "." + route);
    }

    public static int getInt(String name, String route) {
        return data.getInt(Lifts + name + "." + route);
    }

    public static List<String> getLifts() {
        List<String> temp = new ArrayList<>();
        if (data.isConfigurationSection("Lifts")) {
            ConfigurationSection liftsSelection = data.getConfigurationSection("Lifts");
            temp.addAll(liftsSelection.getKeys(false));
        }
        return temp;
    }

    public static Material getFloorMat(String name) {
        String temp = data.getString(Lifts + name + ".floorMaterial");
        if (temp.equals("default"))
            return VStorage.defaultFloor;
        else
            return Material.getMaterial(temp);
    }

    public static Material getButtonMat(String name) {
        String temp = data.getString(Lifts+name+".buttonMaterial");
        if (temp.equals("default"))
            return VStorage.defaultButton;
        else
            return Material.getMaterial(temp);
    }

    public static World getWorld(String name) {
        return Bukkit.getWorld(data.getString(Lifts + name + ".world"));
    }

    public static String getXZW(String name) {
        int x, z;
        World world;
        x = data.getInt(Lifts + name + ".posX");
        z = data.getInt(Lifts + name + ".posZ");
        world = Bukkit.getWorld(data.getString(Lifts + name + ".world"));
        StringBuilder sb = new StringBuilder();
        sb.append(x).append(";").append(z).append(";").append(world);
        return sb.toString();
    }
}
