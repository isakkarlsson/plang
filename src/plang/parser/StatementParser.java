package plang.parser;

import plang.parser.ast.Node;
import plang.parser.ast.PrintNode;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.util.Message;

/**
 * Parses a statement
 * 
 * <pre>
 * statement ::= assignment | print | while | if
 * </pre>
 * 
 * 
 * Example:<br>
 * 
 * <pre>
 * print 10;
 * a := 10 + int(read);
 * </pre>
 * 
 * @author Isak Karlsson
 * 
 */
public class StatementParser extends AbstractParser<Node> {

    public StatementParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    protected Node executeParse(Token start) {
        Node node = null;
        boolean requireEnd = true;

        if (start.getType() == DefaultTokenType.IDENTIFIER) {
            Token peek = getTokenizer().peek();
            if (peek.getType() == DefaultTokenType.COLON_EQUAL) {
                node = parseAssignment(start);
            } else if (peek.getType() == DefaultTokenType.LEFT_PAREN) {
                node = parseCall(start);
            }
        } else if (start.getType() == DefaultTokenType.PRINT) {
            node = parsePrint(start);
        } else if (start.getType() == DefaultTokenType.WHILE) {
            node = parseWhile(start);
        } else if (start.getType() == DefaultTokenType.IF) {
            node = parseIf(start);
            requireEnd = false;
        } else {
            flagError(Message.UNEXPECTED_STATEMENT);
        }

        start = getTokenizer().current();
        if (start.getType() != DefaultTokenType.SEMI_COLON) {
            if (requireEnd) {
                flagError(Message.MISSING, ";");
            }
        } else {
            consume();
        }

        return node;
    }

    protected Node parsePrint(Token start) {
        PrintNode node = getNodeFactory().printNode(start);

        Parser parser = getParserFactory().expressionParser(this);
        Node expr = parser.parse();
        if (expr != null) {
            node.setExpression(expr);
        } else {
            flagError();
        }

        return node;
    }

    protected Node parseWhile(Token start) {
        flagError("Not implemented '%s'");
        return null;
    }

    protected Node parseIf(Token start) {
        Parser parser = getParserFactory().ifParser(this);
        return parser.parse(start);

    }

    protected Node parseCall(Token start) {
        Parser parser = getParserFactory().callParser(this);
        return parser.parse(start);
    }

    protected Node parseAssignment(Token start) {
        Parser parser = getParserFactory().assignmentParser(this);
        Node assignment = parser.parse(start);
        if (assignment != null) {
            return assignment;
        } else {
            return null;
        }
    }
}
