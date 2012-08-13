package plang.parser.ast;

import java.util.Arrays;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class IfNode extends AbstractNode {

    private Node compare, _true, _false;

    public IfNode(Token token) {
        super(token);
    }

    public Node getCompare() {
        return compare;
    }

    public void setCompare(Node compare) {
        this.compare = compare;
    }

    public Node getTrue() {
        return _true;
    }

    public void setTrue(Node _true) {
        this._true = _true;
    }

    public Node getFalse() {
        return _false;
    }

    public void setFalse(Node _false) {
        this._false = _false;
    }

    @Override
    public String toTree() {
        return String.format("If(compare=%s, true=%s, false=%s",
                compare.toTree(), _true.toTree(),
                _false != null ? _false.toTree() : "");
    }

    @Override
    public List<Node> getChildrens() {
        return Arrays.asList(compare, _true, _false);
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretIf(this);
    }

}
