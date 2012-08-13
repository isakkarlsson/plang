package plang.parser.ast;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class StatementsNode extends AbstractListNode {

    public StatementsNode(Token token) {
        super(token);
    }

    @Override
    public String toTree() {
        return toTree("Statements");
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretStatements(this);
    }
}
