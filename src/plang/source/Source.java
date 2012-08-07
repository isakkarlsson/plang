package plang.source;

public interface Source {

	/**
	 * Character denoting end of line
	 */
	char EOL = '\n';

	/**
	 * Character denoting end of file
	 */
	char EOF = (char) 0;

	/**
	 * Get current line number (in source file)
	 * 
	 * @return
	 */
	int getLine();

	/**
	 * Get line at n from source file (can't be used before line is read)
	 * 
	 * @param n
	 * @throws IndexOutOfBoundsException
	 *             If n > number of read lines
	 * @return
	 */
	String lineAt(int n);

	/**
	 * Get the currently read line
	 * 
	 * Same as: <code>s.lineAt(s.getLine())</code>
	 * 
	 * @return
	 */
	String currentLine();

	/**
	 * Get position on line
	 * 
	 * @return
	 */
	int getPosition();

	/**
	 * Get current character on stream
	 * 
	 * @return
	 */
	char current();

	/**
	 * Peek at the next character of stream (without moving)
	 * 
	 * @return
	 */
	char peek();

	/**
	 * Peek n characters ahead (or until new line)
	 * 
	 * @param n
	 * @return
	 */
	char peek(int n);

	/**
	 * Get the next character (and move the current char)
	 * 
	 * @return
	 */
	char next();

	/**
	 * Check if there are more characters on the stream (peek() == Scanner.EOF)
	 * 
	 * @return
	 */
	boolean hasNext();

	/**
	 * Check if there are more characters on the current line (peek() ==
	 * Scanner.EOL)
	 * 
	 * @return
	 */
	boolean hasNextOnLine();
}
