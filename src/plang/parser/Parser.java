package plang.parser;

import plang.parser.ast.Node;
import plang.tokenizer.Tokenizer;
import plang.tokenizer.token.Token;

public interface Parser {

	Node parse();

	Node parse(Token start);
	
	Tokenizer getTokenizer();
	
	
}
