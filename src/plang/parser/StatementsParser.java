package plang.parser;

import plang.parser.ast.Node;
import plang.parser.ast.NodeFactory;
import plang.parser.ast.StatementsNode;
import plang.parser.tokenizer.Tokenizer;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.util.Message;
import plang.util.MessageHandler;

public class StatementsParser extends AbstractParser<StatementsNode> {

    public StatementsParser(Tokenizer tokenizer, NodeFactory nodeFactory,
            ParserFactory parserFactory, MessageHandler messageHandler) {
        super(tokenizer, nodeFactory, parserFactory, messageHandler);
    }

    public StatementsParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    protected StatementsNode executeParse(Token start) {
        StatementsNode node = getNodeFactory().statementsNode(start);
        if (start.getType() == DefaultTokenType.DO) {
            Parser parser = getParserFactory().statementParser(this);
            Node child = parser.parse();
            if (child != null) {
                node.addNode(child);

                start = getTokenizer().current();
                while (start.getType() != DefaultTokenType.END) {
                    child = parser.parse(start);
                    if (child != null) {
                        node.addNode(child);
                    } else {
                        flagError();
                    }

                    start = getTokenizer().current();
                }
                if (start.getType() == DefaultTokenType.END) {
                    consume();
                }

            } else {
                flagError();
            }
        } else {
            flagError(Message.UNEXPECTED);
        }

        return node;
    }

    @Override
    protected Node optimize(StatementsNode node) {
        if (node.getNodes().size() > 1) {
            return node;
        } else {
            return node.getNodes().get(0);
        }
    }

}
