package org.clic.gamestar.achievementrace;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class RunCommandExecutor implements CommandExecutor {
    private static WorldCreator worldCreator(String playerName) {
        return new WorldCreator(playerName);
    }

    private static boolean deleteWorld(String worldName) {
        var path = Path.of(Bukkit.getWorldContainer().getAbsolutePath(), worldName);
        try {
            Files.walk(path).sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException ignored) {
                }
            });
        } catch (IOException ignored) {
        }

        return !new File(String.valueOf(path)).exists();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command may only be used by a player");
            return true;
        }

        switch (args[0]) {
            case "start": {
                var world = Bukkit.getServer().createWorld(worldCreator(player.getName()));

                if (world == null) {
                    player.sendMessage(ChatColor.RED + "Could not generate world. Please contact an administrator.");
                    return true;
                }

                var spawnLocation = new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
                player.teleport(spawnLocation);
                player.setBedSpawnLocation(spawnLocation);
                State.setScore(player.getName(), 0);

                break;
            }
            case "stop": {
                sender.sendMessage(ChatColor.GREEN + "Run stopped !");

                var spawnLocation = new Location(Bukkit.getWorlds().get(0), 0, Bukkit.getWorlds().get(0).getHighestBlockYAt(0, 0), 0);
                player.teleport(spawnLocation);
                player.setBedSpawnLocation(spawnLocation);

                Bukkit.unloadWorld(player.getName(), false);

                if (!deleteWorld(player.getName())) {
                    player.sendMessage(ChatColor.RED + "An error occurred while deleting the world. Please contact an administrator.");
                }

                State.saveResult(player.getName());
                break;
            }
            default:
                return false;
        }

        return true;
    }
}
