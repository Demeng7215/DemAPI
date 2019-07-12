package com.demeng7215.demapi.api;

import com.demeng7215.demapi.DemAPI;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

/**
 * Fast and efficient update checker.
 */
public class UpdateChecker {

    private static JavaPlugin pl = DemAPI.getPlugin();

    /**
     * The latest plugin version, the version on Spigot.
     */
    @Getter
    private static String spigotPluginVersion;

    /**
     * Checks if the plugin is updated. You can use this to send update notification messages.
     * Returns true if the plugin is up-to-date, and false if there is an update available.
     * <p>
     * Will also return true (up-to-date) is there was an error during the update check.
     * Avoid this by checking if #checkForUpdates(int) returns false.
     */
    @Getter
    private static boolean isUpdated;

    /**
     * Checks for updates (compares the local plugin version and the Spigot plugin version).
     * If there is an update, send a notification message, register a notification event
     * Use #isUpdated() for the update result.
     *
     * @param resourceID The ID of your resource.
     * @see #isUpdated()
     */
    public static boolean checkForUpdates(int resourceID) {

        MessageUtils.sendConsoleLogMessage(Level.INFO, "Checking for updates...");

        String localPluginVersion = Common.getVersion();

            try {

                // Request the current version of your plugin on SpigotMC.
                final URL spigot = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID);
                final BufferedReader in = new BufferedReader(new InputStreamReader(spigot.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    spigotPluginVersion = inputLine;
                }

            } catch (final IOException ex) {
                isUpdated = true;
            }

            // If there was an error, return false.
            if (isUpdated) {
                // There was an error while checking for updates.
                return false;
            }

            // The update result- true for is up-to-date, false for update available.
            isUpdated = localPluginVersion.equals(spigotPluginVersion);

            // There is a new update available.
            if (!isUpdated()) {
                // Send the update available message.
                MessageUtils.sendColoredConsoleMessage("&2====================== [ UPDATE ] ======================");
                MessageUtils.sendColoredConsoleMessage("&aA newer version of " + Common.getName() + " is available!");
                MessageUtils.sendColoredConsoleMessage("&aCurrent version: &f" + Common.getVersion());
                MessageUtils.sendColoredConsoleMessage("&aLatest version: &f" + spigotPluginVersion);
                MessageUtils.sendColoredConsoleMessage("&aGet the update: &fhttps://spigotmc.org/resources/" + resourceID);
                MessageUtils.sendColoredConsoleMessage("&2====================== [ UPDATE ] ======================");
            } else {
                // There is not a new update available. Send the up-to-date message.
                MessageUtils.sendConsoleLogMessage(Level.INFO, "Plugin is up-to-date.");
            }

            // Update check successful!
            return true;
    }
}
