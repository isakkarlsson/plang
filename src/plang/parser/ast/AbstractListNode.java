package plang.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import plang.parser.tokenizer.token.Token;

public abstract class AbstractListNode extends AbstractNode {

    private List<Node> nodes = new ArrayList<Node>();

    public AbstractListNode(Token token) {
        super(token);
    }

    public void addNode(Node n) {
        n.setParent(this);
        nodes.add(n);
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    protected String toTree(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Node n : nodes) {
            builder.append(n.toTree());
            builder.append(", ");
        }
        builder.replace(builder.length() - 2, builder.length(), "]");
        return String.format("%s(nodes=%s)", name, builder.toString());
    }

    @Override
    public List<Node> getChildrens() {
        return getNodes();
    }
}
