package lenart.piotr.plugins.towerdefencelib.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtils {
    private static final char COLOR_CHAR = '&';

    public static String fix(String text) {
        if (text == null) return null;
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, text);
    }

    public static String fix(String text, char featureCharacter) {
        if (text == null) return null;
        return ChatColor.translateAlternateColorCodes(featureCharacter, text);
    }

    public static List<String> fix(List<String> list) {
        if (list == null) return null;
        return list.stream()
                .map(ColorUtils::fix)
                .collect(Collectors.toList());
    }

    public static List<String> fix(List<String> list, char featureCharacter) {
        if (list == null) return null;
        return list.stream()
                .map(str -> ColorUtils.fix(str, featureCharacter))
                .collect(Collectors.toList());
    }
}
