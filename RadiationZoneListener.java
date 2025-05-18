package me.tuusuario.mutations.zones;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class RadiationZoneListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();

        if (block.getType() == Material.SOUL_SAND || block.getType() == Material.MAGMA_BLOCK) {
            Bukkit.getLogger().info("Jugador " + player.getName() + " en zona radiactiva.");
            player.sendMessage("Estás entrando en una zona radiactiva...");
            JavaPlugin plugin = JavaPlugin.getProvidingPlugin(getClass());
            me.tuusuario.mutations.MutationsPlugin mutationsPlugin = (me.tuusuario.mutations.MutationsPlugin) plugin;
            mutationsPlugin.getPlayerData(player.getUniqueId()).addRadiation(1.0);
        }
    }
}