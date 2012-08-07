package plang.tokenizer;

import plang.source.Source;
import plang.tokenizer.token.Token;

public interface Tokenizer {

	/**
	 * Return the latest extracted token
	 * 
	 * @return
	 */
	Token current();

	/**
	 * Get the next token in the source
	 * 
	 * @return
	 */
	Token next();

	/**
	 * "Peek" at the next token
	 * 
	 * @return
	 */
	Token peek();

	/**
	 * "Peek" n tokens ahead
	 * 
	 * @param n
	 *            number of tokens ahead
	 * @return
	 */
	Token peek(int n);

	/**
	 * Get the source from which the tokens are extracted
	 * 
	 * @return
	 */
	Source getSource();
}
