package top.azimkin.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class liftData {
    private static File fileLiftData;
    private static FileConfiguration dataFileLifts;

    public static void setup() {
        fileLiftData = new File(Bukkit.getServer().getPluginManager().getPlugin("ImmersiveElevators").getDataFolder(), "LiftsData.yml");

        if (!fileLiftData.exists()){
            try{
                fileLiftData.createNewFile();
            } catch(IOException exception){

            }
        }
        dataFileLifts = YamlConfiguration.loadConfiguration(fileLiftData);
    }

    public static void save(){
        try{
            dataFileLifts.save(fileLiftData);
        } catch(IOException exception){
            System.out.println("Couldn't save PlayerData file!");
        }
    }

    public static String getData(String selection){
        if (dataFileLifts.contains(selection)){
            return dataFileLifts.getString(selection);
        } else {
            dataFileLifts.set(selection, "0");
            save();
            return dataFileLifts.getString(selection);
        }
    }

    public static List<String> getLifts() {
        List<String> contacts = new ArrayList<>();

        if (dataFileLifts.isConfigurationSection("lifts")) {
            ConfigurationSection liftsSelection = dataFileLifts.getConfigurationSection("lifts");
            for (String key : liftsSelection.getKeys(false)) {
                contacts.add(key);
            }
        }
        return contacts;
    }

    public static int getInt(String selection) {
        if (dataFileLifts.contains(selection)){
            int value = 0;
            try {
                value = Integer.parseInt(getData(selection));
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
            return value;
        } else {
            dataFileLifts.set(selection, 0);
            save();
            return 0;
        }
    }

    public static void set(String selection, String value){
        dataFileLifts.set(selection, value);
        save();
    }
}
