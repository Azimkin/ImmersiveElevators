package top.azimkin.lift;

import dev.dejvokep.boostedyaml.route.Route;
import org.bukkit.Material;
import org.bukkit.World;
import top.azimkin.utilities.LiftStorage;
import top.azimkin.utilities.VStorage;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private int posX;
    private int posZ;
    private int minFloor;
    private Material customFloor;
    private Material customButton;
    private World world;
    private String name;
    public static List<Elevator> elevators = new ArrayList<>();

    Elevator() {}

    Elevator(int posX, int posZ, int minFloor) {
        this.posX = posX;
        this.posZ = posZ;
        this.minFloor = minFloor;
    }
    Elevator(int posX, int posZ, int minFloor, Material customFloor) {
        this.posX = posX;
        this.posZ = posZ;
        this.minFloor = minFloor;
        this.customFloor = customFloor;
    }

    Elevator(int posX, int posZ, int minFloor, Material customButton, boolean button) {
        this.posX = posX;
        this.posZ = posZ;
        this.minFloor = minFloor;
        this.customButton = customButton;
    }

    Elevator(int posX, int posZ, int minFloor, Material customFloor, Material customButton) {
        this.posX = posX;
        this.posZ = posZ;
        this.minFloor = minFloor;
        this.customButton = customButton;
        this.customFloor = customFloor;
    }

    public Elevator setPosX (int X) {
        this.posX = X;
        return this;
    }

    public Elevator setPosZ (int Z) {
        this.posZ = Z;
        return this;
    }

    public Elevator setName(String name) {
        this.name = name;
        return this;
    }

    public Elevator setFloorMaterial(Material material) {
        this.customFloor = material;
        return this;
    }

    public Elevator setFloor(int floor) {
        this.minFloor = floor;
        return this;
    }

    public Elevator setButtonMaterial(Material material) {
        this.customButton = material;
        return this;
    }

    public Elevator setWorld(World world) {
        this.world = world;
        return this;
    }

    public static void register() {
        List<String> lifts = LiftStorage.getLifts();

        for (String name : lifts) {
            Elevator el = new Elevator()
                    .setName(name)
                    .setPosX(LiftStorage.getInt(name, "posX"))
                    .setPosZ(LiftStorage.getInt(name, "posZ"))
                    .setWorld(LiftStorage.getWorld(name));
            if (LiftStorage.getInt(name, "minFloor") == -2147483648)
                el.setFloor(VStorage.defaultFirstFloor);
            else
                el.setFloor(LiftStorage.getInt(name, "minFloor"));

            if (LiftStorage.getMat(name, "customFloor") != null) {
                el.setFloorMaterial(LiftStorage.getMat(name, "customFloor"));
            } else {
                el.setFloorMaterial(VStorage.defaultFloor);
            }

            if (LiftStorage.getMat(name, "customButton") == null)
                el.setButtonMaterial(VStorage.defaultButton);
            else
                el.setButtonMaterial(LiftStorage.getMat(name, "customButton"));
        }
    }
}