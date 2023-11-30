package org.clic.gamestar.achievementrace;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Logger;

public final class AchievementRace extends JavaPlugin {
    public static AchievementRace INSTANCE;
    public static MultiverseCore MULTIVERSE;
    public static Logger LOGGER;
    public static BukkitScheduler SCHEDULER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        MULTIVERSE = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        LOGGER = this.getLogger();
        SCHEDULER = Bukkit.getScheduler();

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
