package org.clic.gamestar.achievementrace;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Consumer;

public class RunCommandExecutor implements CommandExecutor {
    private static WorldCreator worldCreator(String playerName) {
        return new WorldCreator(playerName);
    }

    private static void generateWorld(String worldName, Consumer<World> callback) {
        AchievementRace.SCHEDULER.runTask(AchievementRace.INSTANCE, () -> {
            var worldManager = AchievementRace.MULTIVERSE.getMVWorldManager();
            if (worldManager.isMVWorld(worldName)) {
                worldManager.regenWorld(worldName, true, true, null);
            } else {
                worldManager.addWorld(worldName, World.Environment.NORMAL, null, WorldType.NORMAL, true, null);
            }
            callback.accept(worldManager.getMVWorld(worldName).getCBWorld());
        });
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
                generateWorld(player.getName(), (world) -> {
                    if (world == null) {
                        player.sendMessage(ChatColor.RED + "Could not generate world. Please contact an administrator.");
                        return;
                    }

                    var spawnLocation = new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
                    player.teleport(spawnLocation);
                    player.setBedSpawnLocation(spawnLocation);
                    State.setScore(player.getName(), 0);
                });
                break;
            }
            case "stop": {
                sender.sendMessage(ChatColor.GREEN + "Run stopped !");

                var spawnLocation = new Location(Bukkit.getWorlds().get(0), 0, Bukkit.getWorlds().get(0).getHighestBlockYAt(0, 0), 0);
                player.teleport(spawnLocation);
                player.setBedSpawnLocation(spawnLocation);

                Bukkit.unloadWorld(player.getName(), false);

                State.saveResult(player.getName());
                break;
            }
            default:
                return false;
        }

        return true;
    }
}
