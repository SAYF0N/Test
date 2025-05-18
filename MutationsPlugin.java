package me.tuusuario.mutations;

import me.tuusuario.mutations.data.PlayerData;
import me.tuusuario.mutations.gui.SkillTreeGUI;
import me.tuusuario.mutations.utils.MutationManager;
import me.tuusuario.mutations.zones.RadiationZoneListener;
import org.bukkit.Bukkit;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;

import java.io.File;
import java.util.*;

public class MutationsPlugin extends JavaPlugin {

    private final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    private final HashMap<UUID, BossBar> radiationBars = new HashMap<>();
    private File dataFolder;

    @Override
    public void onEnable() {
        this.dataFolder = new File(getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();

        getCommand("mutate").setExecutor(new me.tuusuario.mutations.commands.MutateCommand(this));
        getCommand("mutations").setExecutor((sender, command, label, args) -> {
            if (!(sender instanceof Player player)) return true;
            new SkillTreeGUI(this).openSkillTree(player);
            return true;
        });

        getServer().getPluginManager().registerEvents(new RadiationZoneListener(), this);
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                playerDataMap.put(e.getPlayer().getUniqueId(), new PlayerData(e.getPlayer(), dataFolder));
            }

            @EventHandler
            public void onQuit(PlayerQuitEvent e) {
                PlayerData data = playerDataMap.remove(e.getPlayer().getUniqueId());
                if (data != null) data.save();
            }
        }, this);

        startRadiationBarTask();
    }

    private void startRadiationBarTask() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData data = getPlayerData(player.getUniqueId());
                BossBar bar = radiationBars.computeIfAbsent(player.getUniqueId(), uuid ->
                        Bukkit.createBossBar("Radiación", BarColor.GREEN, BarStyle.SEGMENTED_10)
                );
                double progress = Math.min(data.getRadiationLevel() / 100.0, 1.0);
                bar.setProgress(progress);
                bar.setTitle("Radiación: " + data.getRadiationLevel());
                bar.addPlayer(player);
            }
        }, 0L, 60L);
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }
}