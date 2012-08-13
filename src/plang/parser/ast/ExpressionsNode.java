package plang.parser.ast;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class ExpressionsNode extends AbstractListNode {

    public ExpressionsNode(Token token) {
        super(token);
    }

    @Override
    public String toTree() {
        return toTree("Expressions");
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretExpressions(this);
    }

}
