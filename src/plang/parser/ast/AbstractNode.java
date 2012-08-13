package plang.parser.ast;

import plang.parser.tokenizer.token.Token;

public abstract class AbstractNode implements Node {

    private Node parent;
    private int position, line;

    public AbstractNode(Token token) {
        this.position = token.getPosition();
        this.line = token.getLine();
    }

    @Override
    public void setParent(Node node) {
        this.parent = node;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Node getParent() {
        return parent;
    }
}
