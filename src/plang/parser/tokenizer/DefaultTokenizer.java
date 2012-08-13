package plang.parser.tokenizer;

import java.util.LinkedList;

import plang.parser.source.Source;
import plang.parser.tokenizer.token.Token;

public class DefaultTokenizer implements Tokenizer {

    private LinkedList<Token> tokenStack = new LinkedList<Token>();
    private Source source;
    private char comment;
    private TokenIdentifier identifier;
    private Token current;

    /**
     * Construct a new default tokenizer
     * 
     * @param source
     *            to be tokenized
     * @param factory
     *            create tokens using this factory
     * @param comment
     *            the character which denotes a line comment
     */
    public DefaultTokenizer(Source source, TokenIdentifier idenfifier,
            char comment) {
        this.identifier = idenfifier;
        this.source = source;
        this.comment = comment;
    }

    @Override
    public Token current() {
        if (current == null) {
            current = extract();
        }

        return current;
    }

    @Override
    public Token next() {
        if (tokenStack.isEmpty()) {
            current = extract();
        } else {
            current = tokenStack.poll();
        }

        return current;
    }

    @Override
    public Token peek() {
        return peek(1);
    }

    @Override
    public Token peek(int n) {
        if (tokenStack.size() >= n) {
            return tokenStack.get(n - 1);
        } else {
            Token token = extract();
            tokenStack.push(token);
            return token;
        }
    }

    @Override
    public Source getSource() {
        return source;
    }

    /**
     * Calls: {@link TokenIdentifier#extract(Source)}
     * 
     * Don't override. To add a new token type:
     * 
     * <pre>
     * {@code
     *  public class RegexTokenIdentifier extends TokenIdentifier {
     *    public RegexTokenIdentifier(...) {}
     *    
     *    protected Token identify(Source source) {
     *      char ch = source.current();
     *      if(ch == '/') {
     *        return extractRegexToken(source);
     *      } else {
     *        return null;
     *      }
     *    }
     *  }
     * }
     * </pre>
     * 
     * And construct the tokenizer:
     * 
     * <pre>
     * {@code
     *   TokenIdentifier ti = new DefaultTokenIdentifier(new RegexTokenIdentifier(), factory, messageHandler));
     *   Tokenizer t = new DefaultTokenizer(source, ti, '#');
     * }
     * </pre>
     * 
     * @return
     */
    protected Token extract() {
        consumeWhitespace();
        return identifier.extract(getSource());
    }

    /**
     * Method to consume whitespaces between tokens.
     * 
     * In this version they are defined as <spaces> and anything (of a line)
     * after <code>this.comment</code>
     * 
     * Subclass to redefine whitespace
     */
    protected void consumeWhitespace() {
        char current = getSource().current();
        while (Character.isWhitespace(current) || current == this.comment) {
            if (current == this.comment) {
                do {
                    current = getSource().next();
                } while (current != Source.EOL && current != Source.EOF);
                if (current == Source.EOL) {
                    current = getSource().next();
                }

            } else {
                current = getSource().next();
            }
        }
    }

}
