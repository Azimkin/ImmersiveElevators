package top.azimkin.lift;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import top.azimkin.utilities.LiftStorage;
import top.azimkin.utilities.VStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elevator {
    private int posX;
    private int posZ;
    private int minFloor;
    private Material customFloor;
    private Material customButton;
    private World world;
    private String name;
    public static Map<String, Elevator> elevators = new HashMap<>();

    Elevator() {}

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

    public int getX() {
        return posX;
    }

    public int getZ() {
        return posZ;
    }

    public World getWorld() {
        return world;
    }

    public Material getFloor () {
        return customFloor;
    }

    public Material getButton() {
        return customButton;
    }

    public static void register() {
        int posX;
        int posZ;
        World world;
        int firstFloor;
        Material buttonMaterial;
        Material floorMaterial;
        for (String name : LiftStorage.getLifts())
        {
            posX = LiftStorage.getInt(name, "posX");
            posZ = LiftStorage.getInt(name, "posZ");
            world = LiftStorage.getWorld(name);
            firstFloor = LiftStorage.getInt(name, "firstFloor");
            if (firstFloor == 2147483647)
                firstFloor = VStorage.defaultFirstFloor;
            buttonMaterial = LiftStorage.getButtonMat(name);
            floorMaterial = LiftStorage.getFloorMat(name);
            Elevator elevator = new Elevator()
                    .setName(name)
                    .setPosX(posX)
                    .setPosZ(posZ)
                    .setWorld(world)
                    .setFloor(firstFloor)
                    .setFloorMaterial(floorMaterial)
                    .setButtonMaterial(buttonMaterial);
            StringBuilder strbldr = new StringBuilder()
                    .append(posX)
                    .append(";")
                    .append(posZ)
                    .append(";")
                    .append(world);
            elevators.put(strbldr.toString(), elevator);
        }
    }

    public static void add(String name, XZW pos) {
        FileConfiguration data = LiftStorage.getDataFile();
        String ln = "Lifts." + name + ".";
        data.set(ln + "world", pos.getWorld().getName());
        data.set(ln + "posX", pos.getX());
        data.set(ln + "posZ", pos.getZ());
        data.set(ln + "firstFloor", 2147483647);
        data.set(ln + "buttonMaterial", "default");
        data.set(ln + "floorMaterial", "default");
        LiftStorage.save();
        Elevator elevator = new Elevator()
                .setName(name)
                .setWorld(pos.getWorld())
                .setPosX(pos.getX())
                .setPosZ(pos.getZ())
                .setFloor(VStorage.defaultFirstFloor)
                .setFloorMaterial(VStorage.defaultFloor)
                .setButtonMaterial(VStorage.defaultButton);
        StringBuilder strbldr = new StringBuilder()
                .append(pos.getX())
                .append(";")
                .append(pos.getZ())
                .append(";")
                .append(pos.getWorld());
        elevators.put(strbldr.toString(), elevator);
    }

    public static void editFloorMaterial(String name, String material) {
        Elevator elevator = elevators.get(LiftStorage.getXZW(name));
        if (Material.getMaterial(material) == null) {
            elevator.setFloorMaterial(VStorage.defaultFloor);
            LiftStorage.getDataFile().set("Lifts." + name + ".floorMaterial", VStorage.defaultFloor);
            LiftStorage.save();
        }
        else {
            elevator.setFloorMaterial(Material.getMaterial(material));
            LiftStorage.getDataFile().set("Lifts." + name + ".floorMaterial", Material.getMaterial(material));
            LiftStorage.save();
        }
        elevators.put(LiftStorage.getXZW(name), elevator);
    }

    public static void editButtonMat(String name, String material) {
        Elevator elevator = elevators.get(LiftStorage.getXZW(name));
        if (Material.getMaterial(material) == null) {
            elevator.setButtonMaterial(VStorage.defaultButton);
            LiftStorage.getDataFile().set("Lifts." + name + ".buttonMaterial", VStorage.defaultButton);
            LiftStorage.save();
        }
        else {
            elevator.setButtonMaterial(Material.getMaterial(material));
            LiftStorage.getDataFile().set("Lifts." + name + ".buttonMaterial", Material.getMaterial(material));
            LiftStorage.save();
        }
        elevators.put(LiftStorage.getXZW(name), elevator);
    }
}