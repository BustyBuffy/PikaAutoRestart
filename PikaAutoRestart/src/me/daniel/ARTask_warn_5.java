package me.daniel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ARTask_warn_5 extends BukkitRunnable {

    private final JavaPlugin plugin;

    public ARTask_warn_5(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    

    @Override
    public void run() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        plugin.getServer().broadcastMessage(ChatColor.RED + "[Pika-Restart] Restarting server in 3 seconds.");
    }

}