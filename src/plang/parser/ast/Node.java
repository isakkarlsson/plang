package plang.parser.ast;

import java.util.List;

import plang.runtime.Interpreter;

public interface Node {

    void setParent(Node node);

    /**
     * Return the line on which this node start (i.e. the first token that
     * represent it)
     * 
     * @return
     */
    int getLine();

    /**
     * Return the position on which this node start
     * 
     * @return
     */
    int getPosition();

    /**
     * Display this node as a part of a textual tree. Should be called
     * recursively for all branches of the current node.
     * 
     * E.g.: Assign(lhs=Identifier(value=helloworld), rhs=Number(10))
     * 
     * That is also the preferred syntax (to be easy to extend and read). Lists
     * can be encapsulated by [] (brackets).
     * 
     * @return
     */
    String toTree();

    /**
     * The parent of this node (that is, the tree should be traversable
     * "two-way")
     * 
     * @return
     */
    Node getParent();

    /**
     * Get a list of childens for this node.
     * 
     * Order should be left to right
     * 
     * @return
     */
    List<Node> getChildrens();

    /**
     * Allow this node to be interpreted by the {@link Interpreter}. When adding
     * a new node, {@link Interpreter} must be extended as well.
     * 
     * For example:
     * 
     * <pre>
     * // New node
     * public class ForNode implements Node {
     * ... // other all else
     * 
     *   public Object interpret(Interpreter interpreter) {
     *      interpreter.interpretFor(this);
     *   }
     * }
     * 
     * // Edit Interpreter
     * public interface Interpreter {
     *    Object interpretFor(ForNode node);
     * }
     * </pre>
     * 
     * Also, update any implementations of {@link Interpreter}
     * 
     * @param interpreter
     * @return
     */
    Object interpret(Interpreter interpreter);

}
