package plang.tokenizer.token;

public interface Token {

	TokenType getTokenType();

	String getValue();

	String getText();

	int getLine();

	int getPosition();

	String getCurrentLine();
}
