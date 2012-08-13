package plang.parser.ast;

import java.util.Arrays;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class ExpressionNode extends AbstractNode {

    private Node left, right;
    private Operator operator;

    public ExpressionNode(Token token) {
        super(token);
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
        this.left.setParent(this);
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        this.right.setParent(this);
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public String toTree() {
        return toTree("Expression");
    }

    protected String toTree(String name) {
        String left = this.left != null ? this.left.toTree() : "";
        String right = this.right != null ? this.right.toTree() : "";
        String operator = this.operator != null ? this.operator.getOperation()
                : "";

        return String.format("%s(left=%s, operator=%s, right=%s)", name, left,
                operator, right);

    }

    @Override
    public List<Node> getChildrens() {
        return Arrays.asList(left, right);
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretExpression(this);
    }

}
