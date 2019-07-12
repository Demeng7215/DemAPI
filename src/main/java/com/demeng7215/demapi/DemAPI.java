package com.demeng7215.demapi;

import com.demeng7215.demapi.api.MessageUtils;
import com.demeng7215.demapi.api.Registerer;
import com.demeng7215.demapi.listeners.DemGUIListeners;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * A useful library, util, and API.
 * You must set the plugin and prefix for full API usage.
 *
 * @author Demeng7215
 * @see #setPlugin(JavaPlugin)
 * @see MessageUtils#setPrefix(String)
 */
public class DemAPI {

    /**
     * The plugin that DemAPI is hooking into.
     */
    @NonNull
    @Getter
    private static JavaPlugin plugin;

    /**
     * Sets the plugin that will be using this API.
     * This should be called before API usage, preferably on plugin enable.
     *
     * @param plugin The main class of your plugin.
     */
    public static void setPlugin(JavaPlugin plugin) {

        // Set the plugin using DemAPI to the plugin that is using DemAPI...
        DemAPI.plugin = plugin;

        // Register stuff...
        Registerer.registerListeners(new DemGUIListeners());
    }

    // Checks if the plugin has been set.
    static {
        new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10L);
                } catch (final InterruptedException ignored) {
                }

                Objects.requireNonNull(plugin,
                        "A plugin using DemAPI did not set the using plugin as their plugin.");
            }
        }.start();
    }
}