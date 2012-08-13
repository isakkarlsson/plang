package plang.parser.tokenizer;

import plang.parser.ParserFactory;
import plang.parser.source.Source;
import plang.parser.tokenizer.token.Token;
import plang.parser.tokenizer.token.TokenFactory;
import plang.util.Message;
import plang.util.MessageHandler;
import plang.util.MessageType;

/**
 * 
 * Example:
 * 
 * <pre>
 * public enum StringTokenType implements TokenType {
 *     STRING;
 * 
 *     public String getValue() {
 *         return &quot;string&quot;;
 *     }
 * }
 * 
 * public class StringTokenIdentifier extends TokenIdentifier {
 *     public StringTokenIdentifier(TokenFactory factory, MessageHandler handler) {
 *         super(factory, handler);
 *     }
 * 
 *     protected Token identify(Source source) {
 *         if (source.current() == '\&quot;') {
 *             // extract until &quot; (on same line?)
 *             return new Token(theString, theString, source.getLine(),
 *                     source.getPosition(), StringTokenType.STRING);
 *         }
 *         return null; // not a String
 *     }
 * }
 * </pre>
 * 
 * Where the tokenizer is constructed:
 * 
 * <pre>
 * TokenizerIdentifier id = new DefaultTokenIdentifier(new StringTokenIdentifier(factory, handler));
 * Tokenizer tok = new DefaultTokenizer(..., id,..);
 * </pre>
 * 
 * The tokenizer is now extended to recognize String tokens. See:
 * {@link ParserFactory} for example how to extend the parser to parse the new
 * String token
 * 
 * @author Isak Karlsson
 * 
 */
public abstract class TokenIdentifier {

    private TokenIdentifier next;
    private TokenFactory factory;
    private MessageHandler messageHandler;

    public TokenIdentifier(TokenIdentifier next) {
        this.next = next;
        this.messageHandler = next.messageHandler;
        this.factory = next.factory;
    }

    public TokenIdentifier(TokenFactory factory, MessageHandler handler) {
        this.messageHandler = handler;
        this.factory = factory;
    }

    /**
     * Don't override. It handles delegation. Override {@link #identify(Source)}
     * 
     * @param source
     * @return
     */
    public Token extract(Source source) {
        char ch = source.current();
        int line = source.getLine();
        int pos = source.getPosition();

        Token token = identify(source);
        if (token == null && next != null) {
            token = next.extract(source);
        } else if (token == null) {
            messageHandler.error(source, MessageType.SYNTAX_ERROR,
                    Message.UNEXPECTED_TOKEN, String.valueOf(ch));
            source.next(); // Skip bad character, to scan the rest..

            token = factory.error(line, pos);
        }
        token.setCurrentLine(source.lineAt(line - 1));
        return token;
    }

    /**
     * See {@link DefaultTokenizer#extract()} for example
     * 
     * @param source
     * @return
     */
    protected abstract Token identify(Source source);

    protected TokenFactory getTokenFactory() {
        return factory;
    }

}
