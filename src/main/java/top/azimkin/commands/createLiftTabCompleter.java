package top.azimkin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class createLiftTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of(
                    "<Lift name>"
            );
        } else if(args.length == 2) {
            return List.of(
                    "<Lift button X>"
            );
        } else if (args.length == 3) {
            return List.of(
                    "<Lift button Y>"
            );
        } else if (args.length == 4) {
            return List.of(
                    "<Lift button Z>"
            );
        }

        return null;
    }
}
