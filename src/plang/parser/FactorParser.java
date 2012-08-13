package plang.parser;

import plang.parser.ast.IdentifierNode;
import plang.parser.ast.Node;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.util.Message;

/**
 * Parses a Factor (the leaf of a expression)
 * 
 * <pre>
 * factor := number |
 *           identifier |
 *           call |
 *           read |
 *           '(' expression ')'
 * </pre>
 * 
 * @author Isak Karlsson
 * 
 */
public class FactorParser extends AbstractParser<Node> {

    public FactorParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    public Node executeParse(Token start) {
        Node node = null;
        if (start.getType() == DefaultTokenType.NUMBER) {
            node = parseNumber(start);
        } else if (start.getType() == DefaultTokenType.IDENTIFIER) {
            if (getTokenizer().peek().getType() != DefaultTokenType.LEFT_PAREN) {
                node = parseIdentifier(start);
            } else {
                node = parseCall(start);
            }
        } else if (start.getType() == DefaultTokenType.LEFT_PAREN) {
            node = parseExpression(start);
        } else if (start.getType() == DefaultTokenType.READ) {
            node = parseRead(start);
        } else {
            flagError(Message.UNEXPECTED);
        }

        return node;
    }

    /**
     * Parse an expression (encapsulated between '(' and ')')
     * 
     * @param start
     * @return
     */
    protected Node parseExpression(Token start) {
        Parser parser = getParserFactory().expressionParser(this);
        Node exprNode = parser.parse();
        if (exprNode != null) {
            Token paren = getTokenizer().current();
            if (paren.getType() == DefaultTokenType.RIGHT_PAREN) {
                consume();
                return exprNode;
            } else {
                flagError(Message.MISSING, ")");
            }
        } else {
            flagError();
        }

        return null;
    }

    /**
     * Parse a call
     * 
     * @param start
     * @return
     */
    protected Node parseCall(Token start) {
        Parser parser = getParserFactory().callParser(this);
        Node call = parser.parse(start);
        if (call != null) {
            return call;
        } else {
            flagError();
        }

        return null;
    }

    /**
     * Parse the read keyword
     * 
     * @param start
     * @return
     */
    protected Node parseRead(Token start) {
        consume();
        return getNodeFactory().readNode(start);
    }

    /**
     * Parse an identifier (as {@link IdentifierNode#LOAD})
     * 
     * @param start
     * @return
     */
    protected Node parseIdentifier(Token start) {
        consume();
        return getNodeFactory().identifierNode(start);
    }

    /**
     * Parse a number
     * 
     * @param start
     * @return
     */
    protected Node parseNumber(Token start) {
        consume();
        return getNodeFactory().numberNode(start);
    }

}
