package plang.parser.tokenizer.token;

public interface Token {

    /**
     * Get the value of this token
     * 
     * @return
     */
    Object getValue();

    /**
     * Get the value of this token casted to T (i.e.
     * <code>Number n = token.getValueSafe();</code> is valid as long as
     * {@link #getValue()} return {@link Number})
     * 
     * @return
     */
    <T> T getValueSafe();

    /**
     * Get the textual representation of this token (most often
     * {@link #getText()} == {@link #getValue()})
     * 
     * @return
     */
    String getText();

    /**
     * Get the line where this token is identified
     * 
     * @return
     */
    int getLine();

    /**
     * Get the (end) position of this token
     * 
     * Thus, start is:
     * <code>token.getPosition() - token.getText().length())</code>
     * 
     * Conveniently implemented in {@link DefaultToken} as
     * {@link DefaultToken#getStartPosition()}
     * 
     * @return
     */
    int getPosition();

    TokenType getType();

    String getCurrentLine();

    void setCurrentLine(String currentLine);

}