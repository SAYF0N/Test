package me.tuusuario.mutations.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MutationManager {

    public static void applyMutation(Player player, String mutation) {
        switch (mutation.toLowerCase()) {
            case "vision_gamma":
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
                player.sendMessage(ChatColor.GREEN + "Mutación 'Visión Gamma' aplicada.");
                break;
            case "piel_queloide":
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
                player.sendMessage(ChatColor.GREEN + "Mutación 'Piel Queloide' aplicada.");
                break;
            default:
                player.sendMessage(ChatColor.RED + "Mutación desconocida.");
                break;
        }
    }
}