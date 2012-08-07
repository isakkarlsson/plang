package plang.tokenizer.token;

public interface TokenFactory {

	Token number(String value, Number number, int line, int position);

	Token identifier(String value, int line, int position);

	Token operator(String value, int line, int position);

	Token error(int line, int position);

	Token eof(int line, int position);
}
