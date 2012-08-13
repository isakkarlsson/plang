package plang.parser.ast;

import java.util.Arrays;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class PrintNode extends AbstractNode {

    private Node expression;

    public PrintNode(Token token) {
        super(token);
    }

    public Node getExpression() {
        return expression;
    }

    public void setExpression(Node expression) {
        this.expression = expression;
        this.expression.setParent(this);
    }

    @Override
    public String toTree() {
        return String.format("Print(expression=%s)", expression.toTree());
    }

    @Override
    public List<Node> getChildrens() {
        return Arrays.asList(expression);
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretPrint(this);
    }

}
