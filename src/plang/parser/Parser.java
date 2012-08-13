package plang.parser;

import plang.parser.ast.Node;
import plang.parser.tokenizer.token.Token;

public interface Parser {

    /**
     * Generate a node that is part of the abstract syntax tree
     * 
     * @see #parse(Token)
     * @return
     */
    Node parse();

    /**
     * Generate a node that is part of the abstract syntax tree with knowledge
     * of token <code>start</code>
     * 
     * If a {@link #parse()} return null, don't emit an error message. The
     * parser that failed is supposed to handle error reporting.
     * 
     * A call to parse should consume _all_ tokens required so that a the
     * current token is the last _not_ used token. This is vital for the
     * process.
     * 
     * 
     * @param start
     * @return a {@link Node} on success or <code>null</code> if parsing fail
     */
    Node parse(Token start);
}
