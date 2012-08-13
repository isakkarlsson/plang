import java.io.File;
import java.io.FileReader;

import plang.parser.DefaultParserFactory;
import plang.parser.Parser;
import plang.parser.StatementsParser;
import plang.parser.ast.DefaultNodeFactory;
import plang.parser.ast.Node;
import plang.parser.source.BufferedSource;
import plang.parser.source.Source;
import plang.parser.tokenizer.DefaultTokenIdentifier;
import plang.parser.tokenizer.DefaultTokenizer;
import plang.parser.tokenizer.TokenIdentifier;
import plang.parser.tokenizer.Tokenizer;
import plang.parser.tokenizer.token.DefaultTokenFactory;
import plang.runtime.DefaultInterpreter;
import plang.runtime.Interpreter;
import plang.util.MessageHandler;
import plang.util.PrintStreamMessageListener;

public class plang {

    public static void main(String[] args) throws Exception {
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.add(new PrintStreamMessageListener(System.out));

        Source source = new BufferedSource(new FileReader(
                new File("test.plang")));
        TokenIdentifier tokenIdentifier = new DefaultTokenIdentifier(
                new DefaultTokenFactory(), messageHandler);

        Tokenizer tokenizer = new DefaultTokenizer(source, tokenIdentifier, '#');

        Parser parser = new StatementsParser(tokenizer,
                new DefaultNodeFactory(), new DefaultParserFactory(),
                messageHandler);

        Node node = parser.parse();
        Interpreter inter = new DefaultInterpreter();
        inter.interpret(node);

        if (node != null) {
            System.out.println("      AST \n================");
            System.out.println(node.toTree());
        }

    }
}
