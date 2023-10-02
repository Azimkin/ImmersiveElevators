package top.azimkin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import top.azimkin.commands.addFloor;
import top.azimkin.commands.addFloorTabCompleter;
import top.azimkin.commands.createLift;
import top.azimkin.commands.createLiftTabCompleter;
import top.azimkin.events.buttonClick;
import top.azimkin.utils.configFile;
import top.azimkin.utils.liftData;

public final class ImmersiveElevators extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        top.azimkin.utils.configFile.setup();

        liftData.setup();

        getCommand("createlift").setExecutor(new createLift());
        getCommand("addfloor").setExecutor(new addFloor());
        getCommand("addfloor").setTabCompleter(new addFloorTabCompleter());
        getCommand("createlift").setTabCompleter(new createLiftTabCompleter());

        Bukkit.getServer().getPluginManager().registerEvents(new buttonClick(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("ImmersiveElevators");
    }
}
