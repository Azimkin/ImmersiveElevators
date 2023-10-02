package top.azimkin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static top.azimkin.utils.liftData.*;
import static top.azimkin.utils.utils.setColor;

/*

    Adding floor for the lift

 */


public class addFloor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2) {
            return false;
        }
        short lift_lvls = 0;
        if (!getData("lifts." + args[0] + ".levels").equals("0"))
        {
            try {
                lift_lvls = Short.parseShort(getData("lifts." + args[0] + ".levels"));
            } catch (NumberFormatException exception) {
                sender.sendMessage("Error!");
                exception.printStackTrace();
                return true;
            }
        } else {
            sender.sendMessage("Lift don't exist!");
            return true;
        }

        lift_lvls++;
        set("lifts." + args[0] + ".levels", String.valueOf(lift_lvls));
        set("lifts." + args[0] + ".lvl_y." + lift_lvls, args[1]);
        sender.sendMessage("Added " + lift_lvls + " floor on " + args[1]);

        return true;
    }
}
