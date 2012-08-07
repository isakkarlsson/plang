package plang.util;

public interface MessageListener {

	void error(Message message);

	void fatal(Message message);

	void fatal(Throwable t);
}
