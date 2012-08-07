package plang.tokenizer.token;

import java.util.HashMap;
import java.util.Map;

public enum TokenType {

	/*
	 * Keywords
	 */
	DO, END, FUNCTION,

	/*
	 * Identifier, number
	 */
	IDENTIFIER, NUMBER,

	/*
	 * Operators
	 */
	EQUAL("="), EQUAL_COLON(":="), PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), LEFT_PAREN(
			"("), RIGHT_PAREN(")");

	private static Map<String, TokenType> keywords = new HashMap<String, TokenType>();
	private static Map<String, TokenType> operators = new HashMap<String, TokenType>();

	static {
		TokenType[] tokens = TokenType.values();
		for (int n = DO.ordinal(); n <= FUNCTION.ordinal(); n++) {
			keywords.put(tokens[n].getValue(), tokens[n]);
		}

		for (int n = EQUAL.ordinal(); n <= RIGHT_PAREN.ordinal(); n++) {
			operators.put(tokens[n].getValue(), tokens[n]);
		}
	}

	public static boolean isKeyword(String str) {
		return keywords.containsKey(str);
	}

	public static boolean isOperator(String str) {
		return operators.containsKey(str);
	}

	public static TokenType getOperator(String str) {
		return operators.get(str);
	}

	public static TokenType getKeyword(String str) {
		return keywords.get(str);
	}

	private String value;

	private TokenType(String value) {
		this.value = value;
	}

	private TokenType() {
		this.value = name().toLowerCase();
	}

	public String getValue() {
		return this.value;
	}

}
