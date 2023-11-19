package top.azimkin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.azimkin.commands.ImmersiveElevatorsCommand;
import top.azimkin.events.ButtonClick;
import top.azimkin.lift.Elevator;
import top.azimkin.utilities.ConfigManager;
import top.azimkin.utilities.Lang;
import top.azimkin.utilities.LiftStorage;

public final class ImmersiveElevators extends JavaPlugin {
    private static ImmersiveElevators IE;
    private static final String GC = "\u001B[32m";
    private static final String CC = "\u001B[0m";

    @Override
    public void onEnable() {
        long strt = System.currentTimeMillis();
        IE = this;
        ConfigManager.setup();
        Lang.setup();
        LiftStorage.setup();
        Elevator.register();
        getCommand("elevators").setExecutor(new ImmersiveElevatorsCommand());
        getCommand("elevators").setTabCompleter(new ImmersiveElevatorsCommand());
        getServer().getPluginManager().registerEvents(new ButtonClick(), this);

        getLogger().info(GC + Lang.getClearLang("Enabled")
                .replace("%ms%", String.valueOf(System.currentTimeMillis()-strt))+ CC);
    }

    @Override
    public void onDisable() {

    }

    public static ImmersiveElevators getInstance()
    {
        return IE;
    }

}
