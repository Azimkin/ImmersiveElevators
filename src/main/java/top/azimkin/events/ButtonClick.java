package top.azimkin.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import top.azimkin.ImmersiveElevators;
import top.azimkin.lift.Direction;
import top.azimkin.lift.Elevator;
import top.azimkin.lift.XZW;
import top.azimkin.utilities.Lang;
import top.azimkin.utilities.LiftGUIHolder;
import top.azimkin.utilities.TextUtilities;
import top.azimkin.utilities.VStorage;

public class ButtonClick implements Listener {
    private static final ImmersiveElevators PLUGIN = ImmersiveElevators.getInstance();
    private static final int[] rows1 = {49, 40, 31, 22, 13, 4};
    private static final int[] rows2 = {50, 48, 41, 39, 32, 30, 23, 21, 14, 12, 5, 3};
    private static final int[] rows3 = {50, 49, 48, 41, 40, 39, 32, 31, 30, 23, 22, 21, 14, 13, 12, 5, 4, 3};
    @EventHandler
    private static void onButtonClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        XZW pos = new XZW(event.getClickedBlock().getX(), event.getClickedBlock().getZ(), Bukkit.getWorld("world"));
        StringBuilder strbldr = new StringBuilder()
                .append(pos.getX())
                .append(";")
                .append(pos.getZ())
                .append(";")
                .append(pos.getWorld());
        Elevator el = Elevator.elevators.get(strbldr.toString());
        if (el == null)
            return;
        if(!(event.getPlayer().hasPermission("elevators.use"))) {
            event.getPlayer().sendMessage(Lang.getLang("no-permission"));
            return;
        }
        if (event.getClickedBlock().getType() != el.getButton())
            return;
        Location fLoc = event.getPlayer().getLocation();
        fLoc.setY(event.getPlayer().getLocation().getBlockY()-1);
        if(fLoc.getBlock().getType() != el.getFloor())
            return;
        int levels = 0;
        int currentButton = 0;
        Location loc = event.getClickedBlock().getLocation();
        for(int i = pos.getWorld().getMinHeight(); i < pos.getWorld().getMaxHeight(); i++){
            loc.setY(i);
            if (loc.getBlock().getType() == el.getButton()) {
                levels++;
                if(i == event.getClickedBlock().getLocation().getBlockY())
                    currentButton = levels;
            }
        }
        VStorage.currentElevator.put(event.getPlayer(), el);
        Inventory inv = Bukkit.createInventory(new LiftGUIHolder(), 54, Lang.getClearLang("elevator-name"));
        int[] tempRows = {};
        if (levels < 7)
            tempRows = rows1;
        else if (levels < 13)
            tempRows = rows2;
        else if (levels < 19)
            tempRows = rows3;
        for(int i = levels; i > 0; i--) {
            ItemStack item = new ItemStack(Material.LIGHT_GRAY_CONCRETE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(TextUtilities.format("&f" + i));
            if (i == currentButton) {
                meta.setDisplayName(TextUtilities.format("&e" + i));
                item = new ItemStack(Material.GRAY_CONCRETE);
                VStorage.currentFloor.put(event.getPlayer(), i);
            }
            item.setItemMeta(meta);
            inv.setItem(tempRows[i-1], item);
        }
        event.getPlayer().openInventory(inv);
    }

    @EventHandler
    private static void onPlayerClickGuiButton(InventoryClickEvent event) {
        if(!(event.getInventory().getHolder() instanceof LiftGUIHolder))
            return;
        event.setCancelled(true);
        if(event.getCurrentItem().getType() == Material.GRAY_CONCRETE)
            return;
        Player player = (Player) event.getWhoClicked();
        Elevator el = VStorage.currentElevator.get(player);
        StringBuilder sb = new StringBuilder();
        String button = event.getCurrentItem().getItemMeta().getDisplayName();
        for (int i = 0; i < button.length(); i++) {
            if (Character.isDigit(button.charAt(i))) {
                sb.append(button.charAt(i));
            }
        }
        int floor = 0;
        try {
            floor = Integer.parseInt(sb.toString());
        } catch (NumberFormatException exception) {
            return;
        }
        Direction dir;
        if (VStorage.currentFloor.get(player) > floor)
            dir = Direction.Down;
        else
            dir = Direction.Up;
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,1 ,1);
        int mh = player.getWorld().getMinHeight();
        int mxh = player.getWorld().getMaxHeight();
        Location location = new Location(player.getWorld(), el.getX(), 0, el.getZ());
        int height = 0;
        int lvl = 0;
        int[] floorsA = new int[18];
        int j = 0;
        for (int i = mh; i < mxh; i++) {
            location.setY(i);
            if (location.getBlock().getType() == el.getButton()) {
                lvl++;
                floorsA[j] = i;
                j++;
                if (lvl == floor) {
                    height = i;
                    break;
                }
            }
        }
        Location pnloc = player.getLocation();
//        if (dir == Direction.Up) {
//            int i = 0;
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//
//                }
//            }.runTaskTimer(PLUGIN, 0L, 1L);
//        }
        pnloc.setY(height-1);
        player.teleport(pnloc);
        player.closeInventory();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(TextUtilities.format(Lang.getClearLang("floor-destination")
                        .replace("%floor%", String.valueOf(floor)))));
    }
}