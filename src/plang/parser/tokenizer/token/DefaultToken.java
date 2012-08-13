package plang.parser.tokenizer.token;

public class DefaultToken implements Token {

    private Object value;
    private String text, currentLine;
    private int line, position;
    private TokenType type;

    public DefaultToken(String text, Object value, int line, int position,
            TokenType type) {
        super();
        this.value = value;
        this.text = text;
        this.line = line;
        this.position = position;
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see plang.tokenizer.token.IToekn#getValue()
     */
    @Override
    public Object getValue() {
        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see plang.tokenizer.token.IToekn#getText()
     */
    @Override
    public String getText() {
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see plang.tokenizer.token.IToekn#getLine()
     */
    @Override
    public int getLine() {
        return line;
    }

    /*
     * (non-Javadoc)
     * 
     * @see plang.tokenizer.token.IToekn#getPosition()
     */
    @Override
    public int getPosition() {
        return position;
    }

    /**
     * Get the start of this token
     * 
     * @return
     */
    public int getStartPosition() {
        return getPosition() - getText().length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see plang.tokenizer.token.IToekn#getType()
     */
    @Override
    public TokenType getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see plang.tokenizer.token.IToekn#getCurrentLine()
     */
    @Override
    public String getCurrentLine() {
        return currentLine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see plang.tokenizer.token.IToekn#setCurrentLine(java.lang.String)
     */
    @Override
    public void setCurrentLine(String currentLine) {
        this.currentLine = currentLine;
    }

    @Override
    public String toString() {
        return "Token [value=" + value + ", text=" + text + ", currentLine="
                + currentLine + ", line=" + line + ", position=" + position
                + ", type=" + type + "]";
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValueSafe() {
        return (T) value;
    }

}
