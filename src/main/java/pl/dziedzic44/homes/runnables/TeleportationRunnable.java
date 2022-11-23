package pl.dziedzic44.homes.runnables;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.dziedzic44.homes.Main;
import pl.dziedzic44.homes.utils.DataManager;
import java.util.Objects;

public class TeleportationRunnable extends BukkitRunnable {
    private final Main main;
    private final Player player;
    private final Location initialLocation, homeLocation;
    private final DataManager messagesYML;
    private int ticks = 0;

    public TeleportationRunnable(Main main, Player player, String homeName, DataManager messagesYML) {
        this.main = main;
        this.player = player;
        this.initialLocation = player.getLocation();
        this.homeLocation = main.homes.get(player.getUniqueId()).stream().filter(home -> home.getName().equalsIgnoreCase(homeName)).toList().get(0).getLocation();
        this.messagesYML = messagesYML;
    }

    @Override
    public void run() {
        for (String key : main.getConfig().getStringList("teleportation-effects")) player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(key)), Integer.MAX_VALUE, 0));
        if (ticks >= main.getConfig().getInt("teleportation-delay") * 20) {
            player.teleport(homeLocation);
            if (main.getConfig().getBoolean("play-teleportation-sound")) player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 10, 1);
            for (String key : main.getConfig().getStringList("teleportation-effects")) player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(key)));
            this.cancel();
        } else if (!main.getConfig().getBoolean("allow-movement-while-teleporting") && initialLocation.getBlock().getLocation().distance(player.getLocation().getBlock().getLocation()) != 0d) {
            for (String key : main.getConfig().getStringList("teleportation-effects")) player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(key)));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesYML.getConfig().getString("home.teleportation-cancelled"))));
            this.cancel();
        } else {
            if (ticks % 20 == 0) player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesYML.getConfig().getString("home.time-left")).replace("{timeLeft}", String.valueOf(main.getConfig().getInt("teleportation-delay") - (ticks / 20)))));
            ticks++;
        }
    }
}