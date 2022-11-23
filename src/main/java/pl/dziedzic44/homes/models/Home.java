package pl.dziedzic44.homes.models;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;

public class Home implements ConfigurationSerializable {
    private final String name;
    private final Location location;

    public Home(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.ofEntries(Map.entry("name", name), Map.entry("location", location));
    }

    public Home deserialize(Map<String, Object> map) {
        return new Home((String) map.get("name"), (Location) map.get("location"));
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}