package plang.parser.ast;

import java.util.LinkedList;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class NumberNode extends AbstractNode {

    private Number value;

    public NumberNode(Token token) {
        super(token);
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    @Override
    public String toTree() {
        return String.format("Number(value=%s)", value.toString());
    }

    @Override
    public List<Node> getChildrens() {
        return new LinkedList<Node>();
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretNumber(this);
    }

}
