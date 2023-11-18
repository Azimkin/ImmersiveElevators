package top.azimkin.utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import top.azimkin.lift.Elevator;

import java.util.HashMap;

public class VStorage {
    public static final Material defaultFloor = ConfigManager.getMat("FloorMaterial");
    public static final Material defaultButton = ConfigManager.getMat("ButtonMaterial");
    public static final int defaultFirstFloor = ConfigManager.getInt("DefaultFirstFloor");
    public static HashMap<Player, Elevator> currentElevator = new HashMap<>();
}
