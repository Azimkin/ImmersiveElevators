package top.azimkin.utilities;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.Material;
import top.azimkin.ImmersiveElevators;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static final ImmersiveElevators PLUGIN = ImmersiveElevators.getInstance();
    private static YamlDocument config;

    public static void setup() {
        try {
            config = YamlDocument.create(new File(PLUGIN.getDataFolder(), "config.yml"),
                    PLUGIN.getResource("config.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException exception) {
            PLUGIN.getLogger().warning("What a hell");
        }
        PLUGIN.getLogger().info("Config loaded!");
    }

    public static String getStr(String path) {
        return config.getString(path);
    }

    public static Material getMat(String selection) {
        return Material.getMaterial(config.getString(selection)) == null ? Material.STONE :
                Material.getMaterial(config.getString(selection));
    }

    public static int getInt(String selection) {
        return config.getInt(selection);
    }
}