package pl.dziedzic44.homes;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dziedzic44.homes.commands.DelhomeCommand;
import pl.dziedzic44.homes.commands.HomeCommand;
import pl.dziedzic44.homes.commands.SethomeCommand;
import pl.dziedzic44.homes.listeners.PlayerJoinListener;
import pl.dziedzic44.homes.listeners.PlayerQuitListener;
import pl.dziedzic44.homes.models.Home;
import pl.dziedzic44.homes.utils.DataManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Main extends JavaPlugin {
    public HashMap<UUID, ArrayList<Home>> homes = new HashMap<>();
    private final DataManager homesYML = new DataManager(this, "homes.yml");

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        this.saveDefaultConfig();
        this.saveResource("messages.yml", false);
        DataManager messagesYML = new DataManager(this, "messages.yml");
        ConfigurationSerialization.registerClass(Home.class);
        for (Player player : this.getServer().getOnlinePlayers())
            if (this.homesYML.getConfig().get("homes." + player.getUniqueId()) == null) this.homes.put(player.getUniqueId(), new ArrayList<>());
            else this.homes.put(player.getUniqueId(), (ArrayList<Home>) this.homesYML.getConfig().get("homes." + player.getUniqueId()));
        Objects.requireNonNull(getCommand("delhome")).setExecutor(new DelhomeCommand(this, messagesYML));
        Objects.requireNonNull(getCommand("home")).setExecutor(new HomeCommand(this, messagesYML));
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new SethomeCommand(this, messagesYML));
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, this.homesYML), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this, this.homesYML), this);
    }

    @Override
    public void onDisable() {
        for (Player player : this.getServer().getOnlinePlayers()) {
            this.homesYML.getConfig().set("homes." + player.getUniqueId(), this.homes.get(player.getUniqueId()));
            this.homes.remove(player.getUniqueId());
            this.homesYML.saveConfig();
        }
    }
}