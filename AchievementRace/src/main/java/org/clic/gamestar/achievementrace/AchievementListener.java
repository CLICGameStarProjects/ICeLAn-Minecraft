package org.clic.gamestar.achievementrace;

import org.bukkit.ChatColor;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AchievementListener implements Listener {
    @EventHandler
    public void onAchievementGrant(PlayerAdvancementDoneEvent event) {
        var achievementValue = Config.getAchievementValue(event.getAdvancement().getKey().toString());
        if (achievementValue != 0) {
            State.setScore(
                    event.getPlayer().getName(),
                    State.getScore(event.getPlayer().getName()) + achievementValue
            );
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your score now is " + State.getScore(event.getPlayer().getName()));
        }
    }
}
