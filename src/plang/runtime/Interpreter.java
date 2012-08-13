package plang.runtime;

import plang.parser.ast.AssignmentNode;
import plang.parser.ast.CallNode;
import plang.parser.ast.CompareNode;
import plang.parser.ast.ExpressionNode;
import plang.parser.ast.ExpressionsNode;
import plang.parser.ast.IdentifierNode;
import plang.parser.ast.IfNode;
import plang.parser.ast.Node;
import plang.parser.ast.NumberNode;
import plang.parser.ast.PrintNode;
import plang.parser.ast.ReadNode;
import plang.parser.ast.StatementsNode;

/**
 * Read: http://en.wikipedia.org/wiki/Visitor_pattern
 * 
 * @author Isak Karlsson
 * 
 */
public interface Interpreter {

    /**
     * Interpret a node. This method delegates to the nodes
     * {@link Node#interpret(Interpreter)} method to invoke the correct method
     * (from instances of this class)
     * 
     * @param node
     * @return
     */
    Object interpret(Node node);

    /**
     * Interpret an assignment node
     * 
     * @param assignmentNode
     * @return
     */
    Object interpretAssignment(AssignmentNode node);

    /**
     * Interpret an identifier node
     * 
     * @param identifierNode
     * @return
     */
    Object interpretIdentifier(IdentifierNode node);

    /**
     * Interpret a number
     * 
     * @param numberNode
     * @return
     */
    Object interpretNumber(NumberNode node);

    /**
     * Interpret an expression, most likely +,-,*,/ since the node is often
     * optimized away for call()
     * 
     * @param node
     * @return
     */
    Object interpretExpression(ExpressionNode node);

    /**
     * Interpret the read keyword
     * 
     * @param node
     * @return
     */
    Object interpretRead(ReadNode node);

    /**
     * Interpret the print keyword
     * 
     * @param node
     * @return
     */
    Object interpretPrint(PrintNode node);

    /**
     * Interpret a list of expressions
     * 
     * @param node
     * @return
     */
    Object interpretExpressions(ExpressionsNode node);

    /**
     * Interpret a call
     * 
     * @param node
     * @return
     */
    Object interpretCall(CallNode node);

    /**
     * Interpret an if statement
     * 
     * @param node
     * @return
     */
    Object interpretIf(IfNode node);

    /**
     * Interpret a list of statements
     * 
     * @param node
     * @return
     */
    Object interpretStatements(StatementsNode node);

    /**
     * Interpret a comparison
     * 
     * @param node
     * @return
     */
    Object interpretCompare(CompareNode node);

}
