package lenart.piotr.plugins.towerdefencelib.commands;

import lenart.piotr.plugins.towerdefencelib.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import java.util.Map;

public class CommandData {
    private final CommandSender sender;
    private final Map<String, String> args;

    public CommandData(CommandSender sender, Map<String, String> args) {
        this.sender = sender;
        this.args = args;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Player requirePlayer() {
        if (!(sender instanceof Player)) {
            throw new CommandException("Require player sender");
        }
        return (Player) sender;
    }

    public ConsoleCommandSender requireConsole() {
        if (!(sender instanceof ConsoleCommandSender)) {
            throw new CommandException("Require console sender");
        }
        return (ConsoleCommandSender) sender;
    }

    public boolean has(String key) {
        return args.containsKey(key);
    }

    public String getStr(String key) {
        if (!args.containsKey(key)) {
            throw new CommandException("Missing parameter: " + key);
        }
        return args.get(key);
    }

    public String getStr(String key, String default_value) {
        if (!args.containsKey(key)) {
            return default_value;
        }
        return args.get(key);
    }

    public int getInt(String key) {
        try {
            return Integer.parseInt(getStr(key));
        } catch (NumberFormatException e) {
            throw new CommandException("Parameter '" + key + "' must be an integer");
        }
    }

    public int getInt(String key, int default_value) {
        if (!args.containsKey(key)) return default_value;
        return getInt(key);
    }

    public double getDouble(String key) {
        try {
            return Double.parseDouble(getStr(key));
        } catch (NumberFormatException e) {
            throw new CommandException("Parameter '" + key + "' must be a number");
        }
    }

    public double getDouble(String key, double default_value) {
        if (!args.containsKey(key)) return default_value;
        return getDouble(key);
    }

    public boolean getBool(String key) {
        String val = getStr(key).toLowerCase();
        if (val.equals("true") || val.equals("1") || val.equals("yes") || val.equals("y") || val.equals("t")) return true;
        if (val.equals("false") || val.equals("0") || val.equals("no") || val.equals("n") || val.equals("f")) return false;
        throw new CommandException("Parameter '" + key + "' must be true/false value");
    }

    public boolean getBool(String key, boolean default_value) {
        if (!args.containsKey(key)) return default_value;
        return getBool(key);
    }
}