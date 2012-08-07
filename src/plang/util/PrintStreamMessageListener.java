package plang.util;

import java.io.PrintStream;

public class PrintStreamMessageListener implements MessageListener {

	private PrintStream out;

	public PrintStreamMessageListener(PrintStream out) {
		this.out = out;
	}

	@Override
	public void fatal(Throwable t) {
		out.println(t.getMessage());
		System.exit(-1);
	}

	@Override
	public void fatal(Message message) {
		out.format(message + "\n", message.getArgs());
		System.exit(-1);
	}

	@Override
	public void error(Message message) {
		out.format("   File \"%s\", line %d:%d\n", "<stdin>", message.getLine(), message.getPosition());
		out.println("     " + message.currentLine());
		for (int n = 0; n < message.getPosition() + 4; n++) {
			out.print(" ");
		}
		out.println("^");
		out.format(message.getType().toString() + ": " + message.getMessage()
				+ "\n\n", message.getArgs());
	}

}
