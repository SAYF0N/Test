package me.tuusuario.mutations.commands;

import me.tuusuario.mutations.utils.MutationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.tuusuario.mutations.MutationsPlugin;

public class MutateCommand implements CommandExecutor {

    private final MutationsPlugin plugin;

    public MutateCommand(MutationsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo jugadores pueden usar este comando.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Uso: /mutate <nombre>");
            return true;
        }

        String mutation = args[0];
        MutationManager.applyMutation(player, mutation);
        return true;
    }
}