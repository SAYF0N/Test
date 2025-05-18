package me.tuusuario.mutations.data;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {
    private final Player player;
    private final File file;
    private final YamlConfiguration config;
    private double radiation = 0;

    public PlayerData(Player player, File folder) {
        this.player = player;
        this.file = new File(folder, player.getUniqueId() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.radiation = config.getDouble("radiation", 0);
    }

    public void save() {
        config.set("radiation", radiation);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getRadiationLevel() {
        return radiation;
    }

    public void addRadiation(double amount) {
        this.radiation = Math.min(100.0, radiation + amount);
    }
}