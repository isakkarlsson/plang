package plang.parser.ast;

import java.util.LinkedList;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class ReadNode extends AbstractNode {

    public ReadNode(Token token) {
        super(token);
    }

    @Override
    public String toTree() {
        return String.format("Read()");
    }

    @Override
    public List<Node> getChildrens() {
        return new LinkedList<Node>();
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretRead(this);
    }

}
