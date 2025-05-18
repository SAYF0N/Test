package me.tuusuario.mutations.gui;

import me.tuusuario.mutations.utils.MutationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillTreeGUI implements Listener {

    private final JavaPlugin plugin;

    public SkillTreeGUI(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openSkillTree(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Árbol de Mutaciones");

        ItemStack gamma = new ItemStack(Material.ENDER_EYE);
        ItemMeta meta = gamma.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Visión Gamma");
        meta.setLore(java.util.List.of(ChatColor.GRAY + "Visión nocturna eterna."));
        gamma.setItemMeta(meta);
        gui.setItem(11, gamma);

        ItemStack piel = new ItemStack(Material.IRON_CHESTPLATE);
        meta = piel.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Piel Queloide");
        meta.setLore(java.util.List.of(ChatColor.GRAY + "Resistencia + Lentitud"));
        piel.setItemMeta(meta);
        gui.setItem(13, piel);

        player.openInventory(gui);
    }

    @org.bukkit.event.EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getView().getTitle()).equals("Árbol de Mutaciones")) return;

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        String mutationKey = switch (name) {
            case "Visión Gamma" -> "vision_gamma";
            case "Piel Queloide" -> "piel_queloide";
            default -> null;
        };

        if (mutationKey != null) {
            MutationManager.applyMutation(player, mutationKey);
            player.sendMessage(ChatColor.AQUA + "¡Has desbloqueado " + name + "!");
            player.closeInventory();
        }
    }
}