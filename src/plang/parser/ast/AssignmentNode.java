package plang.parser.ast;

import java.util.Arrays;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class AssignmentNode extends AbstractNode {

    private Node identifier, value;

    public AssignmentNode(Token token) {
        super(token);
    }

    public Node getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Node identifier) {
        this.identifier = identifier;
        this.identifier.setParent(this);
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
        this.value.setParent(this);
    }

    @Override
    public String toTree() {
        return String.format("Assignment(identifier=%s, value=%s)",
                identifier.toTree(), value.toTree());
    }

    @Override
    public List<Node> getChildrens() {
        return Arrays.asList(new Node[] { identifier, value });
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretAssignment(this);
    }

}
