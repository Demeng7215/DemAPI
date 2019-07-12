package com.demeng7215.demapi.api;

import com.demeng7215.demapi.DemAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Just some common methods that are useful or used frequently.
 */
public class Common {

    private static final JavaPlugin pl = DemAPI.getPlugin();

    /**
     * Gets the plugin name, as stated in the plugin description file (plugin.yml).
     *
     * @return The plugin name, stated in plugin.yml.
     */
    public static String getName() {
        return pl.getDescription().getName();
    }

    /**
     * Gets the plugin version, as stated in the plugin description file (plugin.yml).
     *
     * @return The plugin version, stated in plugin.yml.
     */
    public static String getVersion() {
        return pl.getDescription().getVersion();
    }

    /**
     * Checks if the version stated in the plugin.yml is different from the actual and accurate version.
     * This can be used to detect plugin.yml tampering.
     *
     * @param actualVersion The actual and accurate version, the version that the plugin should be.
     * @return true if versions match, false is versions mismatch.
     */
    public static boolean checkVersionsMatch(String actualVersion) {
        return actualVersion.equals(getVersion());
    }

    /**
     * Checks if the plugin or server was reloaded simply by checking if there are any online players.
     * This check should be called on plugin startup to prevent errors and glitches in the plugin.
     *
     * @param takeAction Should DemAPI send a message to console, disable the plugin, and kick all players with a message?
     * @return true if reload detected, false if reload was not detected
     */
    public static boolean simpleReloadCheck(boolean takeAction) {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {

            if (takeAction) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.kickPlayer("&c&lA plugin has been reloaded instead of being restarted.\n" +
                            "&cThis kick is to prevent errors and notify admins about this " +
                            "dangerous loading approach.\n" +
                            "\n" +
                            "&6The plugin has been disabled.");
                }
                MessageUtils.sendColoredConsoleMessage("&cPlugin has been disabled and all players " +
                        "have been kicked due to reload.");
                pl.getPluginLoader().disablePlugin(pl);
            }
            return true;
        }
        return false;
    }
}
