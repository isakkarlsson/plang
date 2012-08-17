import java.io.File;
import java.io.FileReader;

import plang.parser.DefaultParserFactory;
import plang.parser.Parser;
import plang.parser.ParserFactory;
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
import plang.runtime.DefaultStackFactory;
import plang.runtime.Interpreter;
import plang.util.MessageHandler;
import plang.util.PrintStreamMessageListener;

/**
 * Main class
 * 
 * @author Isak Karlsson
 * 
 */
public class plang {

    /**
     * To extend the programming language see: {@link TokenIdentifier}, and
     * {@link ParserFactory} for examples. Otherwise, read the code and try to
     * understand how it functions.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java -jar plang.jar file.plang");
            return;
        }

        // Construct a message handler
        MessageHandler messageHandler = new MessageHandler();

        // Add a listener that prints to STDOUT
        // Add additional listeners if needed
        messageHandler.add(new PrintStreamMessageListener(System.out));

        // Define source file to read code from
        Source source = new BufferedSource(new FileReader(new File(args[0])));

        // Construct a token identifier
        // Add additional TokenIdentifiers to extend the tokenization process
        TokenIdentifier tokenIdentifier = new DefaultTokenIdentifier(
                new DefaultTokenFactory(), messageHandler);

        // Construct a tokenizer from the token identifier and the source
        // Define # as a comment character (# this is a comment)
        Tokenizer tokenizer = new DefaultTokenizer(source, tokenIdentifier, '#');

        // Construct a statements parser to parse the file
        // That is: the file should start with 'do' at least one statement and
        // end with 'end'
        //
        // Change, the node factory and the parser factory if extending
        Parser parser = new StatementsParser(tokenizer,
                new DefaultNodeFactory(), new DefaultParserFactory(),
                messageHandler);

        // Parse the file and construct a node
        // Most likely a StatementsNode
        Node node = parser.parse();

        // Construct an interpreter
        // Interpret the constructed node
        // Change or edit the interpreter to change how things are handled (i.e.
        // some of the semantics)
        Interpreter interpreter = new DefaultInterpreter(new DefaultStackFactory());
        interpreter.interpret(node);

        // If the parsing was successful print the string representation of the
        // abstract syntax tree (for debugging)
        if (node != null) {
            System.out.println("      AST \n================");
            System.out.println(node.toTree());
        }

    }
}
