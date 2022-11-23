package pl.dziedzic44.homes.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.dziedzic44.homes.Main;
import pl.dziedzic44.homes.utils.DataManager;

public record PlayerQuitListener(Main main, DataManager homesYML) implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        homesYML.getConfig().set("homes." + event.getPlayer().getUniqueId(), main.homes.get(event.getPlayer().getUniqueId()));
        main.homes.remove(event.getPlayer().getUniqueId());
        homesYML.saveConfig();
    }
}