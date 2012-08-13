package plang.parser;

import plang.parser.ast.ExpressionNode;
import plang.parser.ast.Node;
import plang.parser.ast.NodeFactory;
import plang.parser.tokenizer.Tokenizer;
import plang.parser.tokenizer.token.Token;
import plang.util.MessageHandler;
import plang.util.MessageType;

public abstract class AbstractParser<T extends Node> implements Parser {

    private Tokenizer tokenizer;
    private MessageHandler messageHandler;
    private NodeFactory nodeFactory;
    private ParserFactory parserFactory;
    private boolean failed;

    /**
     * Construct a parser using a tokenizer and a message handler
     * 
     * @param tokenizer
     * @param nodeFactory
     * @param messageHandler
     */
    public AbstractParser(Tokenizer tokenizer, NodeFactory nodeFactory,
            ParserFactory parserFactory, MessageHandler messageHandler) {
        this.tokenizer = tokenizer;
        this.nodeFactory = nodeFactory;
        this.messageHandler = messageHandler;
        this.parserFactory = parserFactory;
    }

    /**
     * Construct a parser from another {@link AbstractParser} and use its
     * tokenizer and message handler
     * 
     * @param parser
     */
    public AbstractParser(AbstractParser<?> parser) {
        this(parser.tokenizer, parser.nodeFactory, parser.parserFactory,
                parser.messageHandler);
    }

    /**
     * Get the tokenizer
     * 
     * @return
     */
    protected Tokenizer getTokenizer() {
        return tokenizer;
    }

    /**
     * Get the node factory
     * 
     * @return
     */
    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    /**
     * Get the parser factory
     * 
     * @return
     */
    public ParserFactory getParserFactory() {
        return parserFactory;
    }

    /**
     * Flag a syntax error using the current message handler.
     * 
     * Same as:
     * <code>getMessageHandler().error(token, MessageType.SYNTAX_ERROR, message, args)</code>
     * 
     * Use sparsely (we don't want C++ error propagation)
     * 
     * @see MessageHandler#error(Token, MessageType, String, Object...)
     * @param token
     * @param message
     * @param args
     */
    protected void flagError(Token token, String message, Object... args) {
        getMessageHandler().error(token, MessageType.SYNTAX_ERROR, message,
                args);
        setFailed(true);
    }

    /**
     * Flag syntax error for current token
     * 
     * @see #flagError(Token, String, Object...)
     * @param message
     * @param args
     */
    protected void flagError(String message) {
        flagError(message, getTokenizer().current().getValue().toString());
    }

    /**
     * 
     * @param message
     * @param args
     */
    protected void flagError(String message, Object... args) {
        flagError(getTokenizer().current(), message, args);
    }

    /**
     * Flag an error but don't emit an error message (if it's not needed)
     */
    protected void flagError() {
        setFailed(true);
    }

    /**
     * Return <code>true</code> if the parser has encountered an error
     * 
     * If {@link #hasFailed()} return true then {@link #parse(Token)} return
     * <code>null</code>
     * 
     * @return
     */
    public boolean hasFailed() {
        return failed;
    }

    /**
     * Notify that this parser has failed
     * 
     * {@link #flagError()} do this automatically
     * 
     * @param failed
     */
    protected void setFailed(boolean failed) {
        this.failed = failed;
    }

    /**
     * Get the message handler
     * 
     * @return
     */
    protected MessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * Most likely return a node of type <code>T</code>, but
     * {@link #optimize(Node)} might change that (to any suitable type)
     * 
     * Don't rely on casting (which is most likely not useful)
     * 
     * Don't override! Use {@link #executeParse(Token)} instead to gain
     * optimization using {@link #optimize(Node)}
     * 
     * @return a node if {@link #hasFailed()} == false otherwise null
     */
    @Override
    public Node parse(Token start) {
        T node = executeParse(start);

        if (!hasFailed() && node != null) {
            return optimize(node);
        } else {
            node = null;
        }
        return node;
    }

    @Override
    public Node parse() {
        return parse(getTokenizer().next());
    }

    protected void consume(int n) {
        for (int i = 0; i < n; i++) {
            getTokenizer().next();
        }
    }

    protected void consume() {
        getTokenizer().next();
    }

    /**
     * Optimize by pruning unnecessary nodes.
     * 
     * E.g. is {@link ExpressionParser} optimized to return an instance of
     * {@link TermNode} if {@link ExpressionNode#getRight()} is
     * <code>null</code>
     * 
     * This pattern should be used to improve interpretation speed.
     * 
     * @param node
     * @return
     */
    protected Node optimize(T node) {
        return node;
    }

    /**
     * Override with the actual parsing.
     * 
     * Is called by {@link #parse(Token)} before {@link #optimize(Node)}
     * 
     * @param start
     * @return
     */
    abstract protected T executeParse(Token start);
}
