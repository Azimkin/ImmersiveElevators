package top.azimkin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static top.azimkin.utils.utils.setColor;

public class immersiveElevators implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(setColor("Plugin commands: \n/el lifts - show all lifts. \n/createlift - create lift " +
                "\n/addfloor - add floor for lift \n/editfloor - edit exist floor"));


        return true;
    }
}
