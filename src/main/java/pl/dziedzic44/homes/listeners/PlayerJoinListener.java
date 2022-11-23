package pl.dziedzic44.homes.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.dziedzic44.homes.Main;
import pl.dziedzic44.homes.models.Home;
import pl.dziedzic44.homes.utils.DataManager;
import java.util.ArrayList;

public record PlayerJoinListener(Main main, DataManager homesYML) implements Listener {
    @EventHandler
    @SuppressWarnings("unchecked")
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (homesYML.getConfig().get("homes." + event.getPlayer().getUniqueId()) == null) main.homes.put(event.getPlayer().getUniqueId(), new ArrayList<>());
        else main.homes.put(event.getPlayer().getUniqueId(), (ArrayList<Home>) homesYML.getConfig().get("homes." + event.getPlayer().getUniqueId()));
    }
}