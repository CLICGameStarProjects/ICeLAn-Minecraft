package org.clic.gamestar.achievementrace;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RunCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        switch (args[0]) {
            case "start":
                State.setScore("Thechi2000", State.getScore("Thechi2000") + 10);
                sender.sendMessage(ChatColor.GREEN + "Current score: " + State.getScore("Thechi2000"));
                break;
            case "stop":
                sender.sendMessage(ChatColor.GREEN + "Run stopped !");
                State.saveResult("Thechi2000");
                break;
            default:
                return false;
        }

        return true;
    }
}
