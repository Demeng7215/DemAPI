package com.demeng7215.demapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class DemAPIPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.sendWarning();
    }

    @Override
    public void onDisable() {
        this.sendWarning();
    }

    private void sendWarning() {
            System.out.println("CAUTION: DemAPI is being ran as a plugin.");
    }
}
