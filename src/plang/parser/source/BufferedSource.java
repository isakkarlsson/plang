package plang.parser.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class BufferedSource implements Source {

    private BufferedReader in;
    private ArrayList<String> data;
    private int line = -1;
    private int index = -1;

    public BufferedSource(Reader stream) {
        in = new BufferedReader(stream);
        data = new ArrayList<String>();
        readLine();
        next();
    }

    private void readLine() {
        try {
            String str = in.readLine();
            index = -1;
            line++;
            data.add(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getLine() {
        return currentLine() == null ? line : line + 1;
    }

    @Override
    public int getPosition() {
        return currentLine() == null ? lineAt(getLine() - 1).length()
                : index + 1;
    }

    @Override
    public char current() {
        if (currentLine() == null) {
            // line -= 1;
            return EOF;
        } else if (index == -1 || index == currentLine().length()) {
            return EOL;
        } else if (index > currentLine().length()) {
            readLine();
            return next();
        } else {
            return currentLine().charAt(index);
        }
    }

    @Override
    public char peek() {
        return peek(1);
    }

    @Override
    public char peek(int n) {
        char c = current();
        if (c == EOF) {
            return c;
        }
        int next = index + n;
        return next < currentLine().length() ? currentLine().charAt(next) : EOL;
    }

    @Override
    public char next() {
        index++;
        return current();
    }

    @Override
    public boolean hasNext() {
        return peek() != EOF;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean hasNextOnLine() {
        return peek() != EOL;
    }

    @Override
    public String lineAt(int n) {
        if (n > data.size()) {
            throw new IndexOutOfBoundsException(String.format(
                    "Line %d not read", n));
        }
        return data.get(n);
    }

    @Override
    public String currentLine() {
        return lineAt(line);
    }

}
