package pl.dziedzic44.homes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pl.dziedzic44.homes.Main;
import pl.dziedzic44.homes.models.Home;
import pl.dziedzic44.homes.utils.DataManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record SethomeCommand(Main main, DataManager messagesYML) implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String index = "success";
        if (!(commandSender instanceof Player player)) index = "not-player";
        else if (strings.length != 1) index = "incorrect-usage";
        else if (main.homes.get(player.getUniqueId()).stream().anyMatch(home -> home.getName().equals(strings[0]))) index = "home-already-exists";
        else if (main.homes.get(player.getUniqueId()).size() >= main.getConfig().getInt("homes-limit")) index = "home-limit-reached";
        else main.homes.get(player.getUniqueId()).add(new Home(strings[0], player.getLocation()));
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesYML.getConfig().getString("sethome." + index))));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}