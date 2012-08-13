package plang.parser;

import plang.parser.ast.ExpressionsNode;
import plang.parser.ast.Node;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;

/**
 * Parse a list of expressions
 * 
 * <pre>
 * expressions ::= expr {, [ expr ] }
 * </pre>
 * 
 * @author isak
 * 
 */
public class ExpressionsParser extends AbstractParser<ExpressionsNode> {

    public ExpressionsParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    protected ExpressionsNode executeParse(Token start) {
        ExpressionsNode node = getNodeFactory().expressionsNode(start);

        Parser parser = getParserFactory().expressionParser(this);
        Node expr = parser.parse(start);
        if (expr != null) {
            node.addNode(expr);

            start = getTokenizer().current();
            while (start.getType() == DefaultTokenType.COMMA) {
                expr = parser.parse();
                if (expr != null) {
                    node.addNode(expr);
                    start = getTokenizer().current();
                } else {
                    flagError();
                    break;
                }
            }
        } else {
            flagError();
        }
        return node;
    }

    @Override
    protected Node optimize(ExpressionsNode node) {
        if (node.getNodes().size() == 1) {
            return node.getNodes().get(0);
        }

        return node;
    }

}
