package top.azimkin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ImmersiveElevators extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        top.azimkin.configFile.setup();

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
}
