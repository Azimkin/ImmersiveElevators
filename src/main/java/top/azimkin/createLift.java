package top.azimkin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static top.azimkin.configFile.*;

public class createLift implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 5) {
            return false;
        }
        Player player = (Player) sender;
        short buttonX, buttonY, buttonZ, levels;
        String elevatorName = args[0];
        try {
            buttonX = Short.parseShort(args[1]);
            buttonY = Short.parseShort(args[2]);
            buttonZ = Short.parseShort(args[3]);
            levels = Short.parseShort(args[4]);
        } catch (NumberFormatException exception) {
            player.sendMessage("Ошибка преобразования координат.");
            return true;
        }

        set("lifts." + elevatorName + ".x_button", String.valueOf(buttonX));
        set("lifts." + elevatorName + ".y_button", String.valueOf(buttonY));
        set("lifts." + elevatorName + ".z_button", String.valueOf(buttonZ));
        set("lifts." + elevatorName + ".levels", String.valueOf(levels));
        set("lifts." + elevatorName + ".lvl_y.1", String.valueOf(buttonY));
        player.sendMessage("Лифт был создан. Установлена кнопка первого этажа на " + buttonX + " " + buttonY + " " + buttonZ);
        return true;
    }
}
