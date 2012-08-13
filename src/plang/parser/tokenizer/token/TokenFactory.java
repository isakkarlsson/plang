package plang.parser.tokenizer.token;

public interface TokenFactory {

    /**
     * Construct a number token from value
     * 
     * @param value
     * @param number
     * @param line
     * @param position
     * @return
     */
    Token number(String value, int line, int position);

    /**
     * Construct an identifier token from value
     * 
     * @param value
     * @param line
     * @param position
     * @return
     */
    Token identifier(String value, int line, int position);

    /**
     * Construct an error token (just to denote a syntactic error)
     * 
     * @param line
     * @param position
     * @return
     */
    Token error(int line, int position);

    /**
     * Construct the EOF token
     * 
     * @param line
     * @param position
     * @return
     */
    Token eof(int line, int position);
}
