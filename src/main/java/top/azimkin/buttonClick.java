package top.azimkin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static top.azimkin.configFile.*;
import static top.azimkin.utils.*;

/*

    Open lift GUI

 */

public class buttonClick implements Listener {
    static HashMap<Player, String> currentLift = new HashMap<>();
    //Говно которое, Я надеюсь, кто-то когда-то перепишет. - Азимкин
    private static int[] position_2 = {3, 5 , 12, 14, 21, 23, 30, 32, 39, 41, 48, 50};
    private static int[] position_1 = {4, 13, 22, 31, 40, 49};

    @EventHandler
    private static void onPlayerClicksButton(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        //player.sendMessage("Event!");
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            //player.sendMessage("return 1");
            return;
        }
        if (event.getClickedBlock().getType() != Material.POLISHED_BLACKSTONE_BUTTON) {
            //player.sendMessage("return 2");
            return;
        }
        List<String> list = configFile.getLifts();
       // player.sendMessage("list ok");
        for (String key : list) {
            int buttonX = event.getClickedBlock().getLocation().getBlockX();
            int buttonZ = event.getClickedBlock().getLocation().getBlockZ();
            Inventory inv;
            //player.sendMessage("in for");
            if ((buttonX == getInt("lifts." + key + ".x_button") && buttonZ == getInt("lifts." + key + ".z_button"))) {
               int levels = getInt("lifts." + key + ".levels");
               short rows = 0;
              // player.sendMessage("if in for");
               if (levels > 6) {
                   //player.sendMessage("IF > 6");
                   rows = (short) Math.ceil(levels / 2.0);
                   inv = Bukkit.createInventory(null, rows*9, "Лифт");
                   currentLift.put(player, key);
                   for (int i = 0; i < levels; i++) {
                       //player.sendMessage("for (levels)");
                       if (getInt("lifts." + key + ".lvl_y." + (i+1))-1 != event.getPlayer().getLocation().getBlockY()) {
                           ItemStack item = new ItemStack(Material.LIGHT_GRAY_WOOL);
                           ItemMeta itemmeta = item.getItemMeta();
                           itemmeta.setDisplayName(setColor("&f" + (i+1) ) );
                           item.setItemMeta(itemmeta);
                           inv.setItem(position_2[i], item);
                       } else {
                           ItemStack item = new ItemStack(Material.GRAY_WOOL);
                           ItemMeta itemmeta = item.getItemMeta();
                           itemmeta.setDisplayName(setColor("&6" + (i+1) ) );
                           item.setItemMeta(itemmeta);
                           inv.setItem(position_2[i], item);
                       }
                   }
               } else {
                   //player.sendMessage("else >6");
                   rows = (short) levels;
                   inv = Bukkit.createInventory(null, rows*9, "Лифт");
                   currentLift.put(player, key);
                   for (int i = 0; i < levels; i++) {
                       //player.sendMessage("for (levels)");
                       if (getInt("lifts." + key + ".lvl_y." + (i+1))-1 != event.getPlayer().getLocation().getBlockY()) {
                           //player.sendMessage("IF");
                           ItemStack item = new ItemStack(Material.LIGHT_GRAY_WOOL);
                           ItemMeta itemmeta = item.getItemMeta();
                           itemmeta.setDisplayName(setColor("&f" + (i+1) ) );
                           item.setItemMeta(itemmeta);
                           //player.sendMessage("INv set item");
                           inv.setItem(position_1[i], item);
                           //player.sendMessage("item" + i + "setted");
                       } else {
                           //player.sendMessage("ELSE");
                           ItemStack item = new ItemStack(Material.GRAY_WOOL);
                           ItemMeta itemmeta = item.getItemMeta();
                           itemmeta.setDisplayName(setColor("&6" + (i+1) ) );
                           item.setItemMeta(itemmeta);
                          // player.sendMessage("INv set item");
                           inv.setItem(position_1[i], item);
                          // player.sendMessage("item " + i + " setted");
                       }
                   }
               }
                //player.sendMessage("open inv");
                event.getPlayer().openInventory(inv);
            }
        }
    }

    @EventHandler
    private static void onPlayerClickButton(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Лифт")) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if(event.getCurrentItem() == null)
            return;

        if (event.getCurrentItem().getType() == Material.GRAY_WOOL) {
            player.sendMessage(setColor("&cВы уже находитесь на этом этаже!"));
            return;
        }

        ItemMeta meta = event.getCurrentItem().getItemMeta();
        String lvl = meta.getDisplayName().replaceAll("[^0-9]", "");

        Location playerLocation = player.getLocation();
        double playerX = playerLocation.getX();
        double playerZ = playerLocation.getZ();
        float playerYaw = playerLocation.getYaw();
        float playerPitch = playerLocation.getPitch();
        int newPlayerY = getInt("lifts." + currentLift.get(player) + ".lvl_y." + lvl);
        if (newPlayerY == 0) {
            player.sendMessage(setColor("&cДанный этаж не настроен."));
            return;
        }
        player.teleport(new Location(Bukkit.getWorld("world"), playerX, (newPlayerY - 1), playerZ, playerYaw, playerPitch));
        player.closeInventory();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(setColor("Вы сейчас на &6" + lvl + "&f этаже") ) );

    }

}
/*
lifts:
    lift_name:
        x_button:
        y_button:
        z_button:
        levels:
        lvl_y:
            '1':
            '2':
            '3':
            ...

ex:

lifts:
    meria:
        x_button: 20
        y_button: 65
        z_button: 20
        levels: 2
        lvl_y:
            '1': 60
            '2': 65
            ...
 */