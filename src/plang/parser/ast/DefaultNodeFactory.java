package plang.parser.ast;

import plang.parser.tokenizer.token.Token;

public class DefaultNodeFactory implements NodeFactory {

    @Override
    public AssignmentNode assignmentNode(Token token) {
        return new AssignmentNode(token);
    }

    @Override
    public IdentifierNode identifierNode(Token token) {
        IdentifierNode node = new IdentifierNode(token);
        node.setValue(token.getText());
        return node;
    }

    @Override
    public NumberNode numberNode(Token token) {
        NumberNode node = new NumberNode(token);
        node.setValue((Number) token.getValue());
        return node;
    }

    @Override
    public ExpressionNode expressionNode(Token token) {
        return new ExpressionNode(token);
    }

    @Override
    public ReadNode readNode(Token token) {
        return new ReadNode(token);
    }

    @Override
    public PrintNode printNode(Token token) {
        return new PrintNode(token);
    }

    @Override
    public ExpressionsNode expressionsNode(Token token) {
        return new ExpressionsNode(token);
    }

    @Override
    public CallNode callNode(Token token) {
        return new CallNode(token);
    }

    @Override
    public CompareNode compareNode(Token token) {
        return new CompareNode(token);
    }

    @Override
    public IfNode ifNode(Token start) {
        return new IfNode(start);
    }

    @Override
    public StatementsNode statementsNode(Token start) {
        return new StatementsNode(start);
    }

}
