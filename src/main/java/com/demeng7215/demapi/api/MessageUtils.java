package com.demeng7215.demapi.api;

import com.demeng7215.demapi.DemAPI;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Utils for sending colored and formatted messages to the console, a player, or command sender.
 */
public class MessageUtils {

    private static JavaPlugin pl = DemAPI.getPlugin();

    /**
     * The message prefix of the plugin.
     * Set the prefix some time during plugin enable, before utilizing functions in the API that require the prefix.
     */
    @NonNull
    @Getter
    @Setter
    private static String prefix;

    /**
     * Colors a string containing color codes.
     *
     * @param s The string you want to color.
     * @return A colored string.
     */
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * "Formats" the string- colors the message and adds the plugin's message prefix to it.
     * This requires a prefix to be set.
     *
     * @param s The string you want to format.
     * @return A colored string with the prefix- a formatted string.
     * @see #setPrefix(String)
     */
    public static String format(String s) {
        return color(prefix + s);
    }

    /**
     * Sends a colored message to the console. Contains prefix.
     *
     * @param message The message that you want to send.
     * @see #format(String)
     */
    public static void sendColoredConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(format(message));
    }

    /**
     * Sends the console a plain log message, with customizable level.
     *
     * @param message The plain log message you want to send.
     * @param level   The level of the log message.
     */
    public static void sendConsoleLogMessage(Level level, String message) {
        pl.getLogger().log(level, message);
    }

    /**
     * Sends the command sender a formatted message.
     *
     * @param message The message that you want formatted and sent.
     * @param sender  The command sender that will receive the message.
     * @see #format(String)
     */
    public static void sendMessageToCommandSender(CommandSender sender, String message) {
        sender.sendMessage(format(message));
    }

    public static void sendMessageToPlayer(Player player, String message) {
        player.sendMessage(format(message));
    }

    /**
     * Generate a fancy error message.
     *
     * @param ex          The exception.
     * @param id          An error ID to make it easier to track down the issue.
     * @param description A brief description of the error.
     * @param disable     true to disable the plugin, false to keep the plugin running.
     */
    public static void error(Exception ex, int id, String description, boolean disable) {

        pl.getLogger().warning("Error! Generating stack trace...");
        ex.printStackTrace();

        sendColoredConsoleMessage("&4====================== [ ERROR ] ======================");
        sendColoredConsoleMessage("&cUh oh! An error has occurred in " + Common.getName() + "!");
        sendColoredConsoleMessage("&cError ID: &8" + id);
        sendColoredConsoleMessage("&cError Description: &8" + description);
        sendColoredConsoleMessage("&cGet support using one of the support options.");
        sendColoredConsoleMessage("&4====================== [ ERROR ] ======================");

        if (disable) Bukkit.getPluginManager().disablePlugin(pl);
    }

    /**
     * Sends an "enabled" message.
     */
    public static void sendSuccessfulEnableMessage() {
        MessageUtils.sendColoredConsoleMessage("&2" + Common.getName() + " v" + Common.getVersion() +
                " by " + pl.getDescription().getAuthors()
                .toString().replace("[", "").replace("]", "") +
                " has been successfully enabled!");
    }

    /**
     * Sends a "disabled" message.
     */
    public static void sendSuccessfulDisableMessage() {
        MessageUtils.sendColoredConsoleMessage("&4" + Common.getName() + " v" + Common.getVersion() +
                " by " + pl.getDescription().getAuthors()
                .toString().replace("[", "").replace("]", "") +
                " has been successfully disabled.");
    }
}
