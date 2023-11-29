package org.clic.gamestar.achievementrace;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AchievementRace extends JavaPlugin {
    public static Plugin INSTANCE;
    public static Logger LOGGER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        LOGGER = this.getLogger();

        State.load();
        Config.load();

        this.getCommand("run").setExecutor(new RunCommandExecutor());
        this.getServer().getPluginManager().registerEvents(new AchievementListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
