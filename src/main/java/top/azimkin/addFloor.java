package top.azimkin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static top.azimkin.configFile.*;

/*

    Adding floor for the lift

 */


public class addFloor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 3) {
            return false;
        }
        set("lifts." + args[0] + ".lvl_y." + args[1], args[2]);
        sender.sendMessage("Ok");

        return true;
    }
}
