package top.azimkin.utilities;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import top.azimkin.ImmersiveElevators;

import java.io.File;
import java.io.IOException;

import static top.azimkin.utilities.ConfigManager.getStr;

public class Lang {
    private static final ImmersiveElevators PLUGIN = ImmersiveElevators.getInstance();
    private static YamlDocument lang;
    private static String PREFIX;
    private static final String DefaultPrefix = "[IE] ";

    public static void setup() {
        PLUGIN.saveResource("lang/lang_EN.yml", false);
        try {
            lang = YamlDocument.create(
                    new File(PLUGIN.getDataFolder().getPath()+"/lang","lang_"+getStr("Lang")+".yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("lang-version")).build());
        } catch (IOException exception) {
            PLUGIN.getLogger().warning("What a hell");
        }
        PREFIX = getClearLang("prefix");
        PLUGIN.getLogger().info(getClearLang("TranslationLoaded"));
    }

    public static String getLang(String translation) {
        return
                lang.getString(translation) == null ?
                        DefaultPrefix + "Invalid Translation" :
                        TextUtilities.format(PREFIX + lang.getString(translation));
    }

    public static String getClearLang(String translation) {
        return lang.getString(translation) == null ? "unknown translation" : lang.getString(translation);
    }
}
