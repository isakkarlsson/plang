package plang.parser;

import plang.parser.ast.Node;
import plang.parser.tokenizer.TokenIdentifier;
import plang.runtime.Interpreter;

/**
 * Supplied to a {@link AbstractParser} to allow for the use of different
 * implementation of parser without altering the other parsers.
 * 
 * For example, to extend the parser with a String type:
 * 
 * <pre>
 * public class StringFactorParser extends FactorParser {
 *     public Node executeParse(Token start) {
 *         if (start.getType() == StringTokenType.STRING) {
 *             getTokenizer().next(); // consume the string..
 *             return new StringNode(start.getLine(), start.getPosition(),
 *                     start.getValue());
 *         } else {
 *             // This was no string,
 *             return super.executeParse(start);
 *         }
 *     }
 * }
 * </pre>
 * 
 * Then, create a subclass of {@link ParserFactory} (or its subclass
 * {@link DefaultParserFactory})
 * 
 * <pre>
 * public StringParserFactory extends DefaultParserFactory {
 *    public FactorParser factorParser(AbstractParser<?> parent) {
 *      return new StringFactorParser(this);
 *    }
 * }
 * </pre>
 * 
 * Example implementation of a new String{@link Node}:
 * 
 * <pre>
 * public class StringNode extends AbstractNode {
 *     private String value;
 * 
 *     public StringNode(Token token) {
 *         super(token);
 *     }
 * 
 *     public String getValue() {
 *         return value;
 *     }
 * 
 *     public void setValue(String value) {
 *         this.value = value;
 *     }
 * 
 *     &#064;Override
 *     public String toTree() {
 *         return String.format(&quot;String(value=%s)&quot;, value6);
 *     }
 * 
 *     &#064;Override
 *     public List&lt;Node&gt; getChildrens() {
 *         return new LinkedList&lt;Node&gt;();
 *     }
 * 
 *     &#064;Override
 *     public Object interpret(Interpreter interpreter) {
 *         return interpreter.interpretString(this);
 *     }
 * }
 * </pre>
 * 
 * Change {@link Interpreter}, (and update any implementations of
 * {@link Interpreter})
 * 
 * <pre>
 * public interface Interpreter {
 *     // already present method
 * 
 *     Object interpretString(StringNode node);
 * }
 * </pre>
 * 
 * These changes, of course, require the tokenization process to be extended in
 * order to allow STRING tokens. See: {@link TokenIdentifier} for an example how
 * to extend the tokenizer.
 * 
 * 
 * @author Isak Karlsson
 */
public interface ParserFactory {

    /**
     * Construct an instance of {@link AssignmentParser}
     * 
     * @see AssignmentParser
     * @param parent
     * @return
     */
    AssignmentParser assignmentParser(AbstractParser<?> parent);

    /**
     * Construct an instance of {@link ExpressionParser}
     * 
     * @see ExpressionParser
     * @param parent
     * @return
     */
    ExpressionParser expressionParser(AbstractParser<?> parent);

    /**
     * Construct an instance of {@link TermParser}
     * 
     * @see TermParser
     * @param parent
     * @return
     */
    TermParser termParser(AbstractParser<?> parent);

    /**
     * Construct an instance of {@link FactorParser}
     * 
     * @see FactorParser
     * @param parent
     * @return
     */
    FactorParser factorParser(AbstractParser<?> parent);

    /**
     * Construct an instance of {@link StatementParser}
     * 
     * @see StatementParser
     * @param parent
     * @return
     */
    StatementParser statementParser(AbstractParser<?> parent);

    /**
     * Construct an instance of {@link ExpressionsParser}
     * 
     * @param parent
     * @return
     */
    ExpressionsParser expressionsParser(AbstractParser<?> parent);

    CallParser callParser(AbstractParser<?> parent);

    CompareParser compareParser(AbstractParser<?> parent);

    IfParser ifParser(AbstractParser<?> parent);

    StatementsParser statementsParser(AbstractParser<?> parent);

}
