package top.azimkin.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.azimkin.lift.Elevator;
import top.azimkin.lift.XZW;
import top.azimkin.utilities.Lang;

public class ImmersiveElevatorsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1)
            return false;

        if (args[0].equals("help")) {
            sender.sendMessage(Lang.getLang("help"));
            return true;
        }
        if (!(sender instanceof Player)){
            sender.sendMessage("Command can be executed only by player!");
            return true;
        }
        Player player = (Player) sender;

        if(args[0].equals("create")) {
            if (args.length < 2)
                return false;
            Block block = player.getTargetBlockExact(5);
            XZW pos = new XZW(block.getX(), block.getZ(), block.getWorld());
            Elevator.add(args[1], pos);
            player.sendMessage(Lang.getLang("success-create-lift")
                    .replace("%name%", args[1]));
        }

        return true;
    }
}
