package lenart.piotr.plugins.towerdefencelib.commands;

public class Token {
    TokenType type;
    String value;

    Token(String raw) {
        if (raw.startsWith("<") && raw.endsWith(">")) {
            type = TokenType.REQUIRED;
            value = raw.substring(1, raw.length() - 1);
        } else if (raw.startsWith("[") && raw.endsWith("]")) {
            type = TokenType.OPTIONAL;
            value = raw.substring(1, raw.length() - 1);
        } else {
            type = TokenType.STATIC;
            value = raw;
        }
    }

    public TokenType getType() { return type; }
    public String getValue() { return value; }
}
