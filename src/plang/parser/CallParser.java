package plang.parser;

import plang.parser.ast.CallNode;
import plang.parser.ast.IdentifierNode;
import plang.parser.ast.Node;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.util.Message;

/**
 * Parsing call expressions (or statements..)
 * 
 * <pre>
 * call ::= identifier '(' expressions ')'
 * </pre>
 * 
 * @author Isak Karlsson
 * 
 */
public class CallParser extends AbstractParser<CallNode> {

    public CallParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    protected CallNode executeParse(Token start) {
        CallNode node = getNodeFactory().callNode(start);

        Token next = getTokenizer().peek();
        if (start.getType() == DefaultTokenType.IDENTIFIER
                && next.getType() == DefaultTokenType.LEFT_PAREN) {

            next = getTokenizer().next();
            IdentifierNode id = getNodeFactory().identifierNode(start);
            id.setState(IdentifierNode.STORE);
            node.setIdentifier(id);

            // If the next token is ), then there are no arguments.
            if (getTokenizer().peek().getType() != DefaultTokenType.RIGHT_PAREN) {
                Parser parser = getParserFactory().expressionsParser(this);
                Node exprs = parser.parse();

                if (exprs != null) {
                    node.setExpressions(exprs);

                    next = getTokenizer().current();
                    if (next.getType() != DefaultTokenType.RIGHT_PAREN) {
                        flagError(Message.MISSING, ")");
                    } else {
                        consume();
                    }
                } else {
                    flagError();
                }
            } else {
                consume(2); // consume )
            }
        } else {
            flagError(Message.UNEXPECTED_CALL);
        }

        return node;
    }
}
