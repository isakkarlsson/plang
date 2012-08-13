package plang.parser;

/**
 * Default implementation of {@link ParserFactory}.
 * 
 * @author Isak Karlsson
 * 
 */
public class DefaultParserFactory implements ParserFactory {

    @Override
    public AssignmentParser assignmentParser(AbstractParser<?> parent) {
        return new AssignmentParser(parent);
    }

    @Override
    public ExpressionParser expressionParser(AbstractParser<?> parent) {
        return new ExpressionParser(parent);
    }

    @Override
    public TermParser termParser(AbstractParser<?> parent) {
        return new TermParser(parent);
    }

    @Override
    public FactorParser factorParser(AbstractParser<?> parent) {
        return new FactorParser(parent);
    }

    @Override
    public StatementParser statementParser(AbstractParser<?> parent) {
        return new StatementParser(parent);
    }

    @Override
    public ExpressionsParser expressionsParser(AbstractParser<?> parent) {
        return new ExpressionsParser(parent);
    }

    @Override
    public CallParser callParser(AbstractParser<?> parent) {
        return new CallParser(parent);
    }

    @Override
    public CompareParser compareParser(AbstractParser<?> parent) {
        return new CompareParser(parent);
    }

    @Override
    public IfParser ifParser(AbstractParser<?> parent) {
        return new IfParser(parent);
    }

    @Override
    public StatementsParser statementsParser(AbstractParser<?> parent) {
        return new StatementsParser(parent);
    }

}
