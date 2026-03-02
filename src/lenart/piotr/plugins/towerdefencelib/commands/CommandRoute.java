package lenart.piotr.plugins.towerdefencelib.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommandRoute {
    private final List<Token> tokens = new ArrayList<>();
    private final Consumer<CommandData> callback;
    private final int minArgs;
    private final int maxArgs;

    public CommandRoute(String rootCommand, String pattern, Consumer<CommandData> callback) {
        this.callback = callback;
        String clean = pattern.startsWith("/") ? pattern.substring(1) : pattern;
        String[] parts = clean.split(" ");

        int start = parts[0].equalsIgnoreCase(rootCommand) ? 1 : 0;

        int requiredCount = 0;
        for (int i = start; i < parts.length; i++) {
            Token t = new Token(parts[i]);
            tokens.add(t);
            if (t.type != TokenType.OPTIONAL) requiredCount++;
        }
        this.minArgs = requiredCount;
        this.maxArgs = tokens.size();
    }

    public Map<String, String> tryMatch(String[] input) {
        if (input.length < minArgs || input.length > maxArgs) return null;

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);

            if (i >= input.length) {
                if (t.type == TokenType.OPTIONAL) continue;
                else return null;
            }

            String val = input[i];
            if (t.type == TokenType.STATIC) {
                if (!t.value.equalsIgnoreCase(val)) return null;
            } else {
                map.put(t.value, val);
            }
        }
        return map;
    }

    public Consumer<CommandData> getCallback() { return callback; }
    public List<Token> getTokens() { return tokens; }
}
