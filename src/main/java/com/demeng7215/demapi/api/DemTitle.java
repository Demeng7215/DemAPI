package com.demeng7215.demapi.api;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Easy title sending for Minecraft versions!
 * May be a little bit slow, because of multi-version support & reflection (about 150ms).
 */
public class DemTitle {

    /**
     * Send a fancy title!
     *
     * @param p The player that will see the title.
     * @param title The title. Supports color codes!
     * @param subtitle The subtitle. Supports color codes!
     * @param fadeIn The number of ticks the title/subtitle will take to fade in.
     * @param stay The number of ticks the title/subtitle will stay on the screen.
     * @param fadeOut The number of ticks the title/subtitle will take to fade out.
     * @throws Exception
     */
    public static void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut)
            throws Exception {

        String titleText = MessageUtils.color(title);
        String subtitleText = MessageUtils.color(subtitle);

        Object enumTitle = NMSStuff.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0]
                .getField("TITLE").get(null);
        Object titleChat = NMSStuff.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                .getMethod("a", String.class).invoke(null, "{\"text\":\"" + titleText + "\"}");

        Object enumSubtitle = NMSStuff.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0]
                .getField("SUBTITLE").get(null);
        Object subtitleChat = NMSStuff.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                .getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitleText + "\"}");

        Constructor<?> titleConstructor = NMSStuff.getNMSClass("PacketPlayOutTitle")
                .getConstructor(NMSStuff.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                        NMSStuff.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);

        Object titlePacket = titleConstructor.newInstance(
                enumTitle, titleChat, fadeIn, stay, fadeOut);
        Object subtitlePacket = titleConstructor.newInstance(
                enumSubtitle, subtitleChat, fadeIn, stay, fadeOut);

        NMSStuff.sendPacket(p, titlePacket);
        NMSStuff.sendPacket(p, subtitlePacket);
    }
}
