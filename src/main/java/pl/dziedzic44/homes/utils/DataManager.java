package pl.dziedzic44.homes.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.dziedzic44.homes.Main;
import java.io.File;
import java.io.IOException;

public class DataManager {
    private final File file;
    private final FileConfiguration fileConfiguration;

    public DataManager(Main main, String fileName) {
        file = new File(main.getDataFolder() + File.separator + fileName);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}