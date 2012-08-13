package plang.parser.ast;

import java.util.Arrays;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class CallNode extends AbstractNode {

    private Node identifier, expressions;

    public CallNode(Token token) {
        super(token);
    }

    public Node getExpressions() {
        return expressions;
    }

    public void setExpressions(Node expressions) {
        this.expressions = expressions;
        this.expressions.setParent(this);
    }

    public Node getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Node identifier) {
        this.identifier = identifier;
        this.identifier.setParent(this);
    }

    @Override
    public String toTree() {
        return String.format("Call(identifier=%s, expressions=%s)", identifier
                .toTree(), expressions != null ? expressions.toTree() : "");
    }

    @Override
    public List<Node> getChildrens() {
        return Arrays.asList(identifier, expressions);
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretCall(this);
    }

}
