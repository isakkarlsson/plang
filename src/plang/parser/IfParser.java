package plang.parser;

import plang.parser.ast.IfNode;
import plang.parser.ast.Node;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.util.Message;

public class IfParser extends AbstractParser<IfNode> {

    public IfParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    protected IfNode executeParse(Token start) {
        IfNode ifNode = null;

        if (start.getType() == DefaultTokenType.IF) {
            ifNode = getNodeFactory().ifNode(start);

            Parser parser = getParserFactory().compareParser(this);
            Node node = parser.parse();
            if (node != null) {
                ifNode.setCompare(node);

                parser = getParserFactory().statementsParser(this);
                node = parser.parse(getTokenizer().current());
                if (node != null) {
                    ifNode.setTrue(node);

                    start = getTokenizer().current();
                    if (start.getType() == DefaultTokenType.ELSE) {
                        node = parser.parse();
                        if (node != null) {
                            ifNode.setFalse(node);
                        } else {
                            flagError();
                        }
                    }
                } else {
                    flagError();
                }

            } else {
                flagError();
            }

        } else {
            flagError(Message.UNEXPECTED);
        }
        return ifNode;
    }
}
