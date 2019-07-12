package com.demeng7215.demapi.listeners;

import com.demeng7215.demapi.api.DemGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Important GUI managing listeners.
 */
public class DemGUIListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = DemGUI.openInventories.get(playerUUID);

        if (inventoryUUID != null) {
            e.setCancelled(true);
            DemGUI gui = DemGUI.getInventoriesByUUID().get(inventoryUUID);
            DemGUI.InvAction action = gui.getActions().get(e.getSlot());

            if (action != null) {
                action.click(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        DemGUI.openInventories.remove(playerUUID);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent e) {

        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        DemGUI.openInventories.remove(playerUUID);
    }
}
