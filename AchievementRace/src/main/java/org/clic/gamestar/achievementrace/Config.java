package org.clic.gamestar.achievementrace;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.*;

public class Config {
    private static FileConfiguration configuration;

    public static void load() {
        configuration = AchievementRace.INSTANCE.getConfig();
        AchievementRace.INSTANCE.saveDefaultConfig();
    }

    public static int getAchievementValue(String achievementName) {
        var section = configuration.getConfigurationSection("achievements");
        return section != null ? section.getInt(achievementName, 0) : 0;
    }
}
