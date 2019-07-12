package com.demeng7215.demapi.api;

import com.demeng7215.demapi.DemAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This listener notifies the player with the specified UUID that the server is running the plugin.
 */
public class DeveloperNotifications implements Listener {

    // If developer notifications are enabled.
    private static boolean enabled;

    private static String uuid;

    /**
     * Enables the developer notification listener.
     *
     * @param devUUID Your Minecraft UUID.
     */
    public static void enableNotifications(String devUUID) {

        // Lightly checks if the UUID is valid by checking if it contains dashes.
        if (!devUUID.contains("-")) {
            return;
        }

        uuid = devUUID;
        Registerer.registerListeners(new DeveloperNotifications());
        enabled = true;
    }

    // The actual listener.
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeveloperJoin(PlayerJoinEvent e) {

        if (enabled && e.getPlayer().getUniqueId().toString().equals(uuid)) {

            Player p = e.getPlayer();

            Bukkit.getScheduler().runTaskLaterAsynchronously(DemAPI.getPlugin(), () -> {
                p.sendMessage("");
                p.sendMessage(MessageUtils.color("&4&lThis server is running " + Common.getName() + "!"));
                p.sendMessage("");
            }, 60L);
        }
    }
}
