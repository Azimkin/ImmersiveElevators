package top.azimkin.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import top.azimkin.lift.Elevator;
import top.azimkin.lift.XZW;

public class ButtonClick implements Listener {
    @EventHandler
    private static void onButtonClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        XZW pos = new XZW(event.getClickedBlock().getX(), event.getClickedBlock().getZ(), Bukkit.getWorld("world"));
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(pos.getX());
        strbldr.append(pos.getZ());
        strbldr.append(pos.getWorld());
        Elevator el = Elevator.elevators.get(strbldr.toString());
        event.getPlayer().sendMessage(String.valueOf(el));
        if (event.getClickedBlock().getType() != el.getButton())
            return;
        Location fLoc = event.getPlayer().getLocation();
        fLoc.setY(event.getPlayer().getLocation().getBlockY()-1);
        if(fLoc.getBlock().getType() != el.getFloor())
            return;
        event.getPlayer().sendMessage("yessir");
    }
}