package plang.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import plang.parser.ast.DefaultOperator;
import plang.parser.ast.ExpressionNode;
import plang.parser.ast.Node;
import plang.parser.ast.Operator;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.Token;
import plang.parser.tokenizer.token.TokenType;

public class CompareParser extends ExpressionParser {

    public static final Set<TokenType> OPERATORS = Collections
            .unmodifiableSet(new HashSet<TokenType>(Arrays.asList(
                    DefaultTokenType.LESS_THAN,
                    DefaultTokenType.LESS_THAN_EQUAL,
                    DefaultTokenType.GREATER_THAN,
                    DefaultTokenType.GREATER_THAN_EQUAL)));

    public CompareParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    protected Set<TokenType> getValidOperators() {
        return OPERATORS;
    }

    @Override
    protected Parser leftParser() {
        return getParserFactory().expressionParser(this);
    }

    @Override
    protected Parser rightParser() {
        return getParserFactory().expressionParser(this);
    }

    @Override
    protected boolean isRightOptional() {
        return false;
    }

    @Override
    protected ExpressionNode buildNode(Token start) {
        return getNodeFactory().compareNode(start);
    }

    @Override
    protected Operator convert(TokenType t) {
        if (t == DefaultTokenType.LESS_THAN) {
            return DefaultOperator.LT;
        } else if (t == DefaultTokenType.GREATER_THAN) {
            return DefaultOperator.GT;
        } else if (t == DefaultTokenType.LESS_THAN_EQUAL) {
            return DefaultOperator.LTE;
        } else if (t == DefaultTokenType.GREATER_THAN_EQUAL) {
            return DefaultOperator.GTE;
        } else if (t == DefaultTokenType.EQUAL) {
            return DefaultOperator.EQ;
        } else if (t == DefaultTokenType.LESS_THAN_GREATER) {
            return DefaultOperator.NEQ;
        }

        return super.convert(t);
    }

    @Override
    protected Node optimize(ExpressionNode node) {
        return node;
    }

}
