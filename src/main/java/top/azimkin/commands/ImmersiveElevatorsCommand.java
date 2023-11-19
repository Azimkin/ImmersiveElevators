package top.azimkin.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.azimkin.lift.Elevator;
import top.azimkin.lift.XZW;
import top.azimkin.utilities.Lang;
import top.azimkin.utilities.LiftStorage;
import top.azimkin.utilities.TextUtilities;

import java.util.ArrayList;
import java.util.List;

public class ImmersiveElevatorsCommand implements CommandExecutor, TabCompleter {
    private static final List<String> materials = getMaterials();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1)
            return false;

        if (args[0].equals("help")) {
            sender.sendMessage(Lang.getLang("help"));
            return true;
        }
        else if (args[0].equals("list")) {
            StringBuilder sb = new StringBuilder();
            sb.append(TextUtilities.format("&eElevators list:\n"));
            for (String name : LiftStorage.getLifts()) {
                sb.append(TextUtilities.format(" &6&l- &r" + name + "\n"));
            }
            sender.sendMessage(sb.toString());
            return true;
        }
        else if (args[0].equals("remove")) {
            if (args.length < 2)
                return false;
            LiftStorage.getDataFile().set("Lifts." + args[1], null);
            LiftStorage.save();
            sender.sendMessage(Lang.getLang("elevator-removed")
                    .replace("%name%", args[1]));
        }
        else if (args[0].equals("setbutton")) {
            if (args.length < 3)
                return false;
            Elevator.editButtonMat(args[1], args[2]);
            sender.sendMessage(Lang.getLang("button-changed")
                    .replace("%name%", args[1])
                    .replace("%material%", args[2]));
            return true;
        }
        else if (args[0].equals("setfloor")) {
            if (args.length < 3)
                return false;
            Elevator.editFloorMaterial(args[1], args[2]);
            sender.sendMessage(Lang.getLang("floor-changed")
                    .replace("%name%", args[1])
                    .replace("%material%", args[2]));
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

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("elevator.commands")) {
            if (args.length == 1)
                return List.of(
                        "create",
                        "list",
                        "remove",
                        "setfloor",
                        "setbutton"
                );
            else if (args.length == 2) {
                if (args[0].equals("create")) {
                    return List.of("name");
                } else if (args[0].equals("remove") || args[0].equals("setfloor") || args[0].equals("setbutton")) {
                    return LiftStorage.getLifts();
                }
            }
            else if (args.length == 3) {
                if (args[0].equals("setbutton"))
                    return List.of(
                            "STONE_BUTTON",
                            "OAK_BUTTON",
                            "SPRUCE_BUTTON",
                            "BIRCH_BUTTON",
                            "JUNGLE_BUTTON",
                            "ACACIA_BUTTON",
                            "DARK_OAK_BUTTON",
                            "CRIMSON_BUTTON",
                            "WRAPPED_BUTTON",
                            "POLISHED_BLACKSTONE_BUTTON",
                            "default"
                    );
                else if (args[0].equals("setfloor"))
                    return materials;
            }
        }
        return null;
    }

    private static List<String> getMaterials() {
        List<String> mats = new ArrayList<>();
        for (Material mat : Material.values()) {
            mats.add(mat.toString());
        }
        return mats;
    }
}
