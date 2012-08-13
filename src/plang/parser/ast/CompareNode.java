package plang.parser.ast;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class CompareNode extends ExpressionNode {

    public CompareNode(Token token) {
        super(token);
    }

    @Override
    public String toTree() {
        return toTree("Compare");
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretCompare(this);
    }

}
