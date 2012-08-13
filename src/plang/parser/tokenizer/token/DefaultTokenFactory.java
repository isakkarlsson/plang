package plang.parser.tokenizer.token;

public class DefaultTokenFactory implements TokenFactory {
    @Override
    public DefaultToken number(String value, int line, int position) {
        Number number = null;
        try {
            number = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                number = Long.parseLong(value);
            } catch (NumberFormatException e2) {
                try {
                    number = Double.parseDouble(value);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
        return new DefaultToken(value, number, line, position,
                DefaultTokenType.NUMBER);
    }

    @Override
    public DefaultToken identifier(String value, int line, int position) {
        TokenType type = DefaultTokenType.getKeyword(value);
        if (type == null) {
            type = DefaultTokenType.IDENTIFIER;
        }

        return new DefaultToken(value, value, line, position, type);
    }

    @Override
    public DefaultToken error(int line, int position) {
        return new DefaultToken("", "<ERROR>", line, position,
                DefaultTokenType.ERROR);
    }

    @Override
    public DefaultToken eof(int line, int position) {
        return new DefaultToken("", "<EOF>", line, position,
                DefaultTokenType.EOF);
    }
}
