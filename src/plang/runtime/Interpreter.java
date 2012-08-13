package plang.runtime;

import plang.parser.ast.AssignmentNode;
import plang.parser.ast.CallNode;
import plang.parser.ast.ExpressionNode;
import plang.parser.ast.ExpressionsNode;
import plang.parser.ast.IdentifierNode;
import plang.parser.ast.IfNode;
import plang.parser.ast.Node;
import plang.parser.ast.NumberNode;
import plang.parser.ast.PrintNode;
import plang.parser.ast.ReadNode;
import plang.parser.ast.StatementsNode;

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

    Object interpretRead(ReadNode node);

    Object interpretPrint(PrintNode node);

    Object interpretExpressions(ExpressionsNode node);

    Object interpretCall(CallNode node);

    Object interpretIf(IfNode node);

    Object interpretStatements(StatementsNode node);

}
