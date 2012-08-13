package plang.parser;

import plang.parser.ast.AssignmentNode;
import plang.parser.ast.IdentifierNode;
import plang.parser.ast.Node;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.util.Message;

/**
 * Parses an assignment
 * 
 * <pre>
 * assignment ::= identifier ':=' expression
 * </pre>
 * 
 * @author Isak Karlsson
 * 
 */
public class AssignmentParser extends AbstractParser<AssignmentNode> {

    public AssignmentParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    public AssignmentNode executeParse(Token start) {
        AssignmentNode node = null;
        Token next = getTokenizer().next();
        if (start.getType() == DefaultTokenType.IDENTIFIER
                && next.getType() == DefaultTokenType.COLON_EQUAL) {
            node = getNodeFactory().assignmentNode(start);

            IdentifierNode id = getNodeFactory().identifierNode(start);
            id.setState(IdentifierNode.STORE);
            node.setIdentifier(id);

            Parser exprParser = getParserFactory().expressionParser(this);
            Node exprNode = exprParser.parse();

            if (exprNode != null) {
                node.setValue(exprNode);
            } else {
                flagError();
            }
        } else {
            flagError(Message.UNEXPECTED_ASSIGNMENT);
        }

        return node;
    }
}
