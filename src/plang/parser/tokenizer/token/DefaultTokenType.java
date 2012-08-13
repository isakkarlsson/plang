package plang.parser.tokenizer.token;

import java.util.HashMap;
import java.util.Map;

public enum DefaultTokenType implements TokenType {

    /*
     * Keywords
     */
    DO, END, FUNCTION, IF, WHILE, ELSE, PRINT, READ,

    /*
     * Operators
     */
    EQUAL("="), COLON_EQUAL(":="), PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), LESS_THAN(
            "<"), GREATER_THAN(">"), LESS_THAN_EQUAL("<="), GREATER_THAN_EQUAL(
            ">="), LESS_THAN_GREATER("<>"), LEFT_PAREN("("), COMMA(","), SEMI_COLON(
            ";"), AND("&&"), OR("||"), RIGHT_PAREN(")"),

    /*
     * Identifier, number
     */
    IDENTIFIER, NUMBER, ERROR, EOF, STRING, ;

    private static Map<String, TokenType> keywords = new HashMap<String, TokenType>();

    static {
        DefaultTokenType[] tokens = DefaultTokenType.values();
        for (int n = DO.ordinal(); n <= RIGHT_PAREN.ordinal(); n++) {
            keywords.put(tokens[n].getValue(), tokens[n]);
        }
    }

    public static boolean isKeyword(String str) {
        return keywords.containsKey(str.toLowerCase());
    }

    public static TokenType getKeyword(String str) {
        return keywords.get(str.toLowerCase());
    }

    private String value;

    private DefaultTokenType(String value) {
        this.value = value;
    }

    private DefaultTokenType() {
        this.value = name().toLowerCase();
    }

    @Override
    public String getValue() {
        return this.value;
    }

}
