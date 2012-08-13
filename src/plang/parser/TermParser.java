package plang.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import plang.parser.ast.DefaultOperator;
import plang.parser.ast.ExpressionNode;
import plang.parser.ast.Operator;
import plang.parser.tokenizer.token.DefaultTokenType;
import plang.parser.tokenizer.token.TokenType;

/**
 * Parses a term and returns an {@link ExpressionNode} (if not optimized) but
 * allow for precedence rules to apply. A {@link TermParser} parses the "higher"
 * precedence operators * and /
 * 
 * <pre>
 * term ::= factor '*' term |
 *          factor '/' term
 * </pre>
 * 
 * @author Isak Karlsson
 * 
 */
public class TermParser extends ExpressionParser {

    protected static final Set<TokenType> OPERATORS = Collections
            .unmodifiableSet(new HashSet<TokenType>(Arrays.asList(
                    DefaultTokenType.STAR, DefaultTokenType.SLASH)));

    public TermParser(AbstractParser<?> parser) {
        super(parser);
    }

    @Override
    protected Set<TokenType> getValidOperators() {
        return TermParser.OPERATORS;
    }

    @Override
    protected Parser leftParser() {
        return getParserFactory().factorParser(this);
    }

    @Override
    protected Parser rightParser() {
        return getParserFactory().termParser(this);
    }

    @Override
    protected Operator convert(TokenType t) {
        if (t == DefaultTokenType.STAR) {
            return DefaultOperator.MUL;
        } else if (t == DefaultTokenType.SLASH) {
            return DefaultOperator.DIV;
        } else {
            return null;
        }
    }
}
