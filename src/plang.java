import java.io.File;
import java.io.FileReader;

import plang.source.BufferedSource;
import plang.source.Source;
import plang.tokenizer.DefaultTokenizer;
import plang.tokenizer.Tokenizer;
import plang.tokenizer.token.Token;
import plang.tokenizer.token.TokenFactory;
import plang.util.MessageHandler;
import plang.util.PrintStreamMessageListener;

public class plang {

	public static void main(String[] args) throws Exception {

		MessageHandler messageHandler = new MessageHandler();
		messageHandler.add(new PrintStreamMessageListener(System.out));
		Source source = new BufferedSource(new FileReader(
				new File("test.plang")));
		Tokenizer tokenizer = new DefaultTokenizer(source, new TokenFactory() {

			@Override
			public Token number(String value, Number number, int line,
					int position) {
				System.out.println("NU: " + value);
				return null;
			}

			@Override
			public Token identifier(String value, int line, int position) {
				System.out.println("ID: " + value);
				return null;
			}

			@Override
			public Token operator(String value, int line, int position) {
				System.out.println("OP: " + value);
				return null;
			}

			@Override
			public Token error(int line, int position) {
				System.out.println("ER: " + line);
				return null;
			}

			@Override
			public Token eof(int line, int position) {
				System.out.println("EOF");
				return null;
			}
		}, messageHandler, '#');
		tokenizer.next();

	}
}
