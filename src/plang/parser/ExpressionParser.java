package plang.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import plang.parser.ast.DefaultOperator;
import plang.parser.ast.ExpressionNode;
import plang.parser.ast.Node;
import plang.parser.ast.Operator;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.parser.tokenizer.token.TokenType;
import plang.util.Message;

/**
 * Parses an expression
 * 
 * <pre>
 * expression ::= term '+' expression |
 *                term '-' expression
 * </pre>
 * 
 * @author Isak Karlsson
 * 
 */
public class ExpressionParser extends AbstractParser<ExpressionNode> {

    /**
     * Valid {@link TokenType} to start an expression with
     * 
     * Default: {@link DefaultTokenType#NUMBER} and
     * {@link DefaultTokenType#IDENTIFIER}
     */
    protected static final Set<TokenType> START = Collections
            .unmodifiableSet(new HashSet<TokenType>(Arrays.asList(
                    DefaultTokenType.NUMBER, DefaultTokenType.IDENTIFIER,
                    DefaultTokenType.LEFT_PAREN, DefaultTokenType.READ)));

    protected static final Set<TokenType> OPERATORS = Collections
            .unmodifiableSet(new HashSet<TokenType>(Arrays.asList(
                    DefaultTokenType.PLUS, DefaultTokenType.MINUS)));

    public ExpressionParser(AbstractParser<?> parser) {
        super(parser);
    }

    /**
     * The tokens denoting valid operators for an expression (i.e. + and - in
     * the default implementation)
     * 
     * If getValidOperators() is overriden, override {@link #convert(TokenType)}
     * to reflect this.
     * 
     * @see #OPERATORS
     * @return
     */
    protected Set<TokenType> getValidOperators() {
        return OPERATORS;
    }

    /**
     * Valid expression initialization tokens
     * 
     * {@link DefaultTokenType#IDENTIFIER}, {@link DefaultTokenType#NUMBER} and
     * {@link DefaultTokenType#LEFT_PAREN} in the default implementation.
     * 
     * @return
     */
    protected Set<TokenType> getValidIntializers() {
        return START;
    }

    /**
     * Return true if right hand side is optional
     * 
     * @return
     */
    protected boolean isRightOptional() {
        return true;
    }

    /**
     * Get the parser needed to parse the left hand side of an expression
     * 
     * {@link ParserFactory#termParser(AbstractParser)} in the default
     * implementation
     * 
     * If the implementation is needed to be changed consider overriding either
     * this method _or_ if a global change is needed subclass the
     * {@link DefaultParserFactory}
     * 
     * @return
     */
    protected Parser leftParser() {
        return getParserFactory().termParser(this);
    }

    /**
     * @see #leftParser()
     * @return
     */
    protected Parser rightParser() {
        return getParserFactory().expressionParser(this);
    }

    @Override
    public ExpressionNode executeParse(Token start) {
        ExpressionNode node = null;

        if (getValidIntializers().contains(start.getType())) {
            node = buildNode(start);

            Parser leftParser = leftParser();
            Node left = leftParser.parse(start);

            if (left != null) {
                node.setLeft(left);
                parseRight(node);
            } else {
                flagError();
            }
        } else {
            flagError(Message.UNEXPECTED_EXPRESSION);
        }

        return node;
    }

    /**
     * Override to build another {@link Node} subclass of ExpressionNode
     * 
     * @param start
     * @return
     */
    protected ExpressionNode buildNode(Token start) {
        return getNodeFactory().expressionNode(start);
    }

    /**
     * Parses the right hand side of an expression (or other "two hand"
     * construct)
     * 
     * @param node
     */
    protected void parseRight(ExpressionNode node) {
        Token operator = getTokenizer().current();
        if (getValidOperators().contains(operator.getType())) {
            Parser rightParser = rightParser();
            Node right = rightParser.parse();

            if (right != null) {
                node.setRight(right);
                Operator op = convert(operator.getType());
                if (op != null) {
                    node.setOperator(op);
                } else {
                    flagError(operator, "Invalid operator '%s'",
                            operator.getValue());
                }

            } else {
                flagError();
            }
        } else if (!isRightOptional()) {
            flagError(operator, "Invalid operator '%s'", operator.getValue());
        }
    }

    /**
     * Convert a {@link TokenType} to an {@link Operator}
     * 
     * @param t
     * @return
     */
    protected Operator convert(TokenType t) {
        if (t == DefaultTokenType.PLUS) {
            return DefaultOperator.ADD;
        } else if (t == DefaultTokenType.MINUS) {
            return DefaultOperator.SUB;
        } else {
            return null;
        }
    }

    @Override
    protected Node optimize(ExpressionNode node) {
        if (node.getRight() == null) {
            return node.getLeft();
        } else {
            return node;
        }
    }
}
