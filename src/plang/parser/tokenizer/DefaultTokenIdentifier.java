package plang.parser.tokenizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import plang.parser.source.Source;
import plang.parser.tokenizer.token.DefaultToken;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.parser.tokenizer.token.TokenFactory;
import plang.util.MessageHandler;

public class DefaultTokenIdentifier extends TokenIdentifier {

    public static Set<Character> OPERATORS = new HashSet<Character>(
            Arrays.asList('<', '>', '=', ':', '*', '/', '+', '-'));

    public static Set<Character> SINGLETON = new HashSet<Character>(
            Arrays.asList('(', ')', ',', ';'));

    public DefaultTokenIdentifier(TokenIdentifier next) {
        super(next);
    }

    public DefaultTokenIdentifier(TokenFactory factory, MessageHandler handler) {
        super(factory, handler);
    }

    @Override
    protected Token identify(Source source) {
        Token token = null;
        char ch = source.current();
        if (ch == Source.EOF) {
            token = getTokenFactory().eof(source.getLine(),
                    source.getPosition());
        } else if (Character.isDigit(ch)) {
            token = extractNumber(source);
        } else if (isValidIdentifier(ch, true)) {
            token = extractIdentifier(source);
        } else if (SINGLETON.contains(ch)) {
            token = new DefaultToken(String.valueOf(ch), String.valueOf(ch),
                    source.getLine(), source.getPosition(),
                    DefaultTokenType.getKeyword(String.valueOf(ch)));
            source.next();
        }
        return token;
    }

    /**
     * Redefine what a valid identifier consist of.
     * 
     * @param ch
     *            character to check
     * @param start
     *            if start is true, then ch is the _first_ char of an identifier
     * @return
     */
    protected boolean isValidIdentifier(char ch, boolean start) {
        if (Character.isLetter(ch)) {
            return true;
        } else if (Character.isDigit(ch) && !start) {
            return true;
        } else if (OPERATORS.contains(ch)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Extract an identifier
     * 
     * Override to redefine what is an identifier.
     * 
     * @return
     */
    protected Token extractIdentifier(Source source) {
        char ch = source.current();
        StringBuilder builder = new StringBuilder();
        while (isValidIdentifier(ch, false)) {
            builder.append(ch);
            ch = source.next();
        }

        return getTokenFactory().identifier(builder.toString(),
                source.getLine(), source.getPosition());
    }

    /**
     * Extract a number
     * 
     * Override to redefine what is a number.
     * 
     * @return
     */
    protected Token extractNumber(Source source) {
        StringBuilder builder = new StringBuilder();
        char c = source.current();
        boolean hasDot = false;
        while (Character.isDigit(c)
                || (c == '.' && Character.isDigit(source.peek()) && !hasDot)) {
            if (c == '.') {
                hasDot = true;
            }

            builder.append(c);
            c = source.next();
        }

        String value = builder.toString();
        return getTokenFactory().number(value, source.getLine(),
                source.getPosition());
    }

}
