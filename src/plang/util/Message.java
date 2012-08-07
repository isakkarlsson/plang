package plang.util;

public class Message {
	public static final String TOO_MANY_ERRORS = "To many errors encountered";
	public static final String UNEXPECTED_TOKEN = "Unexpected token '%s'";
	public static final String UNEXPECTED_CHAR = "Unexpected char '%s' (this is not a character?)";
	public static final String UNEXPECTED_NUMBER = "Unexpected number '%s' (this is not a number?)";
	public static final String UNEXPECTED_STATEMENT = "Unexpected statement '%s'";
	public static final String UNEXPECTED_ASSIGNMENT = "Unexpected assignment '%s'";
	public static final String UNEXPECTED_EXPRESSION = "Unexpected expression '%s'";

	private int line, position;
	private String currentLine, message;
	private MessageType type;
	private Object[] args;

	public Message(String currentLine, MessageType type, int line,
			int position, String message, Object[] args) {
		this.currentLine = currentLine;
		this.type = type;
		this.line = line;
		this.position = position;
		this.message = message;
		this.args = args;
	}

	public int getLine() {
		return line;
	}

	public int getPosition() {
		return position;
	}

	public String currentLine() {
		return currentLine;
	}

	public String getMessage() {
		return message;
	}

	public MessageType getType() {
		return type;
	}

	public Object[] getArgs() {
		return args;
	}

}
