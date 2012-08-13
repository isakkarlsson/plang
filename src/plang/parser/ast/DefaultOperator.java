package plang.parser.ast;

public enum DefaultOperator implements Operator {
    ADD("__add__"), SUB("__sub__"), MUL("__mul__"), DIV("__div__"), LT("__lt__"), GT(
            "__gt__"), LTE("__lte__"), GTE("__gte__"), EQ("__eq__"), NEQ(
            "__neq__");

    private String operator;

    private DefaultOperator(String op) {
        this.operator = op;
    }

    @Override
    public String getOperation() {
        return operator;
    }
}
