package plang.tokenizer;

import java.util.LinkedList;

import plang.source.Source;
import plang.tokenizer.token.Token;
import plang.tokenizer.token.TokenFactory;
import plang.tokenizer.token.TokenType;
import plang.util.Message;
import plang.util.MessageHandler;
import plang.util.MessageType;

public class DefaultTokenizer implements Tokenizer {

	private LinkedList<Token> tokenStack = new LinkedList<Token>();

	private TokenFactory tokenFactory;
	private Source source;
	private char comment;

	private MessageHandler messageHandler;

	/**
	 * Construct a new default tokenizer
	 * 
	 * @param source
	 *            to be tokenized
	 * @param factory
	 *            create tokens using this factory
	 * @param comment
	 *            the character which denotes a line comment
	 */
	public DefaultTokenizer(Source source, TokenFactory factory,
			MessageHandler messageHandler, char comment) {
		this.source = source;
		this.comment = comment;
		this.tokenFactory = factory;
		this.messageHandler = messageHandler;
	}

	@Override
	public Token current() {
		return tokenStack.getFirst();
	}

	@Override
	public Token next() {
		if (tokenStack.isEmpty()) {
			return extract();
		} else {
			return tokenStack.poll();
		}
	}

	@Override
	public Token peek() {
		return peek(1);
	}

	@Override
	public Token peek(int n) {
		if (tokenStack.size() > n) {
			return tokenStack.get(n - 1);
		} else {
			Token token = extract();
			tokenStack.push(token);
			return token;
		}
	}

	@Override
	public Source getSource() {
		return source;
	}

	protected MessageHandler getMessageHandler() {
		return messageHandler;
	}

	protected Token extract() {
		consumeWhitespace();
		Token token = null;
		char ch = getSource().current();
		if (Character.isDigit(ch)) {
			token = extractNumber();
		} else if (Character.isLetter(ch)) {
			token = extractIdentifier();
		} else if (TokenType.isOperator(String.valueOf(ch))) {
			token = extractOperator();
		} else {
			getMessageHandler().error(getSource(), MessageType.SYNTAX_ERROR,
					Message.UNEXPECTED_TOKEN, String.valueOf(ch));
			token = getTokenFactory().error(getSource().getLine(),
					getSource().getLine());
		}

		return token;
	}

	/**
	 * Extract an operator
	 * 
	 * @return
	 */
	protected Token extractOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Extract an identifier
	 * 
	 * @return
	 */
	private Token extractIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Extract a number
	 * 
	 * @return
	 */
	private Token extractNumber() {
		StringBuilder builder = new StringBuilder();
		char c = getSource().current();
		boolean hasDot = false;
		while (Character.isDigit(c)
				|| (c == '.' && Character.isDigit(getSource().peek()) && !hasDot)) {
			if (c == '.') {
				hasDot = true;
			}

			builder.append(c);
			c = getSource().next();
		}

		String value = builder.toString();
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
					getMessageHandler().error(getSource(),
							MessageType.SYNTAX_ERROR,
							Message.UNEXPECTED_NUMBER, value);
					return tokenFactory.error(getSource().getLine(),
							getSource().getPosition());
				}
			}
		}

		return tokenFactory.number(value, number, getSource().getLine(),
				getSource().getPosition());
	}

	protected TokenFactory getTokenFactory() {
		return tokenFactory;
	}

	protected void consumeWhitespace() {
		char current = getSource().current();
		while (Character.isWhitespace(current) || current == this.comment) {
			if (current == this.comment) {
				do {
					current = getSource().next();
				} while (current != Source.EOL && current != Source.EOF);
				if (current == Source.EOL) {
					current = getSource().next();
				}

			} else {
				current = getSource().next();
			}
		}
	}

}
