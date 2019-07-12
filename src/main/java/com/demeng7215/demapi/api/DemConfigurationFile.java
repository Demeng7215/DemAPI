package com.demeng7215.demapi.api;

import com.demeng7215.demapi.DemAPI;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Create multiple YAML configuration files for your plugin with ease!
 * Also contains methods to reload and save your DemConfigurationFile.
 * <p>
 * Looking for a log file and a plain text file (.txt)? See DemLogFile.
 *
 * @see DemLogFile
 */
public class DemConfigurationFile {

    /**
     * The configuration FILE.
     * This can be used if you have your own configuration util you would like to use with this.
     */
    @Getter
    public File configFile;

    /**
     * The actual configuration itself.
     * This can be used if you have your own configuration util you would like to use with this.
     */
    @Getter
    public FileConfiguration config;

    /**
     * Creates a configuration file for your plugin.
     *
     * @param configName The name of the config. Must have the file extension ".yml".
     */
    public DemConfigurationFile(String configName) throws Exception {

        JavaPlugin pl = DemAPI.getPlugin();
        configFile = new File(pl.getDataFolder(), configName);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            pl.saveResource(configName, false);
        }

        config = new YamlConfiguration();
        config.load(configFile);
    }

    /**
     * Reloads the configuration file.
     */
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Saves the configuration file.
     */
    public void saveConfig() throws IOException {
        config.save(configFile);
        reloadConfig();
    }
}
