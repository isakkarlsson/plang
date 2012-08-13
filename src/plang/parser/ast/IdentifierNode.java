package plang.parser.ast;

import java.util.LinkedList;
import java.util.List;

import plang.parser.tokenizer.token.Token;
import plang.runtime.Interpreter;

public class IdentifierNode extends AbstractNode {

    /**
     * This is a storage identifier (i.e. the lhs in an assignment)
     */
    public static final int STORE = 0;

    /**
     * This is a loading identifier (i.e. look up the value in an expression)
     */
    public static final int LOAD = 1;

    private String value;
    private int state;

    public IdentifierNode(Token token) {
        super(token);
        state = LOAD;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getState() {
        return state;
    }

    /**
     * Either {@link #STORE} or {@link #LOAD}
     * 
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toTree() {
        return String.format("Identifier(value=%s, state=%s)", value,
                state == 0 ? "STORE" : "LOAD");
    }

    @Override
    public List<Node> getChildrens() {
        return new LinkedList<Node>();
    }

    @Override
    public Object interpret(Interpreter interpreter) {
        return interpreter.interpretIdentifier(this);
    }

}
