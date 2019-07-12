package com.demeng7215.demapi.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Some NMS reflection utils you can use.
 * This class is not explained in detail- use only if you know what you are doing.
 */
public class NMSStuff {

    public static void sendPacket(Player player, Object packet) throws Exception {
        Object handle = player.getClass().getMethod("getHandle").invoke(player);
        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
        playerConnection.getClass().getMethod("sendPacket",
                getNMSClass("Packet")).invoke(playerConnection, packet);
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (final ClassNotFoundException ex) {
            return null;
        }
    }
}
