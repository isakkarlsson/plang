package plang.parser.tokenizer.token;

/**
 * Marker interface
 * 
 * @author isak
 * 
 */
public interface TokenType {

    /**
     * Get the value for this token type (i.e. the string representation. E.g:
     * EQUAL = "=")
     * 
     * @return
     */
    String getValue();

}
