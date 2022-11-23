package pl.dziedzic44.homes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pl.dziedzic44.homes.Main;
import pl.dziedzic44.homes.models.Home;
import pl.dziedzic44.homes.runnables.TeleportationRunnable;
import pl.dziedzic44.homes.utils.DataManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record HomeCommand(Main main, DataManager messagesYML) implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String index = "success";
        if (!(commandSender instanceof Player player)) index = "not-player";
        else if (strings.length != 1) index = "incorrect-usage";
        else if (main.homes.get(player.getUniqueId()).size() == 0) index = "no-homes";
        else if (main.homes.get(player.getUniqueId()).stream().noneMatch(home -> home.getName().equalsIgnoreCase(strings[0]))) index = "home-not-found";
        else new TeleportationRunnable(main, player, strings[0], messagesYML).runTaskTimer(main, 0L, 1L);
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesYML.getConfig().getString("home." + index))));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return new ArrayList<>();
        return main.homes.get(player.getUniqueId()).stream().map(Home::getName).collect(Collectors.toList());
    }
}