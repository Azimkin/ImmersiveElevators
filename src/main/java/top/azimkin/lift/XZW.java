package top.azimkin.lift;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class XZW {
    private int X;
    private int Z;
    private String world;

    public XZW(int X, int Z, World world) {
        this.X = X;
        this.Z = Z;
        this.world = world.getName();
    }

    public int getX() {
        return X;
    }

    public int getZ() {
        return Z;
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }
}
