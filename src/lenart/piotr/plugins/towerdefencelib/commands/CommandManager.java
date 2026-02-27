package lenart.piotr.plugins.towerdefencelib.commands;

import lenart.piotr.plugins.towerdefencelib.exceptions.CommandException;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;
import java.util.function.Consumer;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final String rootCommand;
    private final List<CommandRoute> routes = new ArrayList<>();

    public CommandManager(JavaPlugin plugin, String label) {
        this.rootCommand = label.replace("/", "").toLowerCase();
        PluginCommand cmd = plugin.getCommand(rootCommand);
        if (cmd != null) {
            cmd.setExecutor(this);
            cmd.setTabCompleter(this);
        }
    }

    public void on(String pattern, Consumer<CommandData> callback) {
        routes.add(new CommandRoute(rootCommand, pattern, callback));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            for (CommandRoute route : routes) {
                Map<String, String> capturedArgs = route.tryMatch(args);
                if (capturedArgs != null) {
                    route.getCallback().accept(new CommandData(sender, capturedArgs));
                    return true;
                }
            }
            sender.sendMessage("§cWrong command use");
        } catch (CommandException e) {
            sender.sendMessage("§cWrong command use: " + e.getMessage());
        } catch (Exception e) {
            sender.sendMessage("§4Internal error");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Set<String> suggestions = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        String currentToken = args[args.length - 1].toLowerCase();
        int currentIndex = args.length - 1;

        for (CommandRoute route : routes) {
            List<Token> routeTokens = route.getTokens();

            if (isPartialMatch(routeTokens, args, currentIndex)) {
                if (currentIndex < routeTokens.size()) {
                    Token targetToken = routeTokens.get(currentIndex);

                    if (targetToken.getType() == TokenType.STATIC) {
                        if (targetToken.getValue().toLowerCase().startsWith(currentToken)) {
                            suggestions.add(targetToken.getValue());
                        }
                    } else {
                        suggestions.add(currentToken);
                    }
                }
            }
        }

        return new ArrayList<>(suggestions);
    }

    private boolean isPartialMatch(List<Token> routeTokens, String[] args, int currentIndex) {
        for (int i = 0; i < currentIndex; i++) {
            if (i >= routeTokens.size()) return false;

            Token t = routeTokens.get(i);
            String input = args[i];

            if (t.getType() == TokenType.STATIC) {
                if (!t.getValue().equalsIgnoreCase(input)) return false;
            }
        }
        return true;
    }
}
