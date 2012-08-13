package plang.parser.ast;

import plang.parser.tokenizer.token.Token;

/**
 * Used to allow for different subclass implementations without altering any
 * code. <br>
 * For example:
 * 
 * <pre>
 * public class DoubleNode extends NumberNode {
 *     // ...
 * }
 * 
 * public class IntegerNode extends NumberNode {
 *     // ...
 * }
 * 
 * public class MyNodeFactory extends DefaultNodeFactory {
 *     public NumberNode numberNode(Token t) {
 *      if(t.getValue() instanceof Double) {
 *         return new DoubleNode(...);
 *      } else {
 *        return new IntegerNode(...);
 *      }
 *   }
 * }
 * </pre>
 * 
 * @author Isak Karlsson
 * 
 */
public interface NodeFactory {

    /**
     * Construct an assignment node
     * 
     * @param token
     * @return
     */
    AssignmentNode assignmentNode(Token token);

    /**
     * Construct an identifier node
     * 
     * @param token
     * @return
     */
    IdentifierNode identifierNode(Token token);

    /**
     * 
     * @param token
     * @return
     */
    NumberNode numberNode(Token token);

    /**
     * 
     * @param token
     * @return
     */
    ExpressionNode expressionNode(Token token);

    /**
     * 
     * @param token
     * @return
     */
    ReadNode readNode(Token token);

    /**
     * 
     * @param start
     * @return
     */
    PrintNode printNode(Token token);

    /**
     * 
     * @param start
     * @return
     */
    ExpressionsNode expressionsNode(Token token);

    /**
     * 
     * @param token
     * @return
     */
    CallNode callNode(Token token);

    /**
     * 
     * @param start
     * @return
     */
    CompareNode compareNode(Token start);

    IfNode ifNode(Token start);

    StatementsNode statementsNode(Token start);

}
