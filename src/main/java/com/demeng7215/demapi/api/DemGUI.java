package com.demeng7215.demapi.api;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class DemGUI {

    @Getter(AccessLevel.PRIVATE)
    private final UUID uuid;

    private final Inventory inv;
    private final Map<Integer, InvAction> actions;
    private static final Map<UUID, DemGUI> inventoriesByUUID = new HashMap<>();
    public static final Map<UUID, UUID> openInventories = new HashMap<>();

    protected DemGUI(int size, String title) {

        this.uuid = UUID.randomUUID();

        this.inv = Bukkit.createInventory(null, size, MessageUtils.color(title));

        this.actions = new HashMap<>();
        inventoriesByUUID.put(getUuid(), this);
    }

    public interface InvAction {
        void click(Player player);
    }

    protected void setItem(int slot, ItemStack stack, String name, List<String> lore, InvAction action) {

        ItemMeta meta = stack.getItemMeta();
        List<String> loreList = new ArrayList<>();

        for (String s : lore) {
            loreList.add(MessageUtils.color(s));
        }

        meta.setDisplayName(MessageUtils.color(name));
        meta.setLore(loreList);

        stack.setItemMeta(meta);

        inv.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void open(Player p) {
        p.openInventory(inv);
        openInventories.put(p.getUniqueId(), getUuid());
    }

    public static Map<UUID, DemGUI> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public Map<Integer, InvAction> getActions() {
        return actions;
    }

    public Inventory getInventory() {
        return inv;
    }
}
