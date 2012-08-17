package plang.runtime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import plang.parser.ast.AssignmentNode;
import plang.parser.ast.CallNode;
import plang.parser.ast.CompareNode;
import plang.parser.ast.ExpressionNode;
import plang.parser.ast.ExpressionsNode;
import plang.parser.ast.IdentifierNode;
import plang.parser.ast.IfNode;
import plang.parser.ast.Node;
import plang.parser.ast.NumberNode;
import plang.parser.ast.Operator;
import plang.parser.ast.PrintNode;
import plang.parser.ast.ReadNode;
import plang.parser.ast.StatementsNode;

/**
 * Very lazy implementation. Just a stub. Don't handle scopes, error messages
 * etc.
 * 
 * TODO: Implement those
 * 
 * @author Isak Karlsson
 * 
 */
public class DefaultInterpreter implements Interpreter {

    private Scanner sc = new Scanner(System.in);
    private Stack stack;
    private StackFactory stackFactory;

    public DefaultInterpreter(StackFactory factory) {
        stackFactory = factory;
        stack = getStackFactory().getStack();
        StackFrame builtins = getStackFactory().getStackFrame();

        builtins.enter("__add__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() + ((Number) r).intValue();
            }
        });
        builtins.enter("__sub__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() - ((Number) r).intValue();
            }
        });
        builtins.enter("__mul__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() * ((Number) r).intValue();
            }
        });
        builtins.enter("__div__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() / ((Number) r).intValue();
            }
        });

        builtins.enter("__lt__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() < ((Number) r).intValue();
            }
        });

        builtins.enter("__gt__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() > ((Number) r).intValue();
            }
        });

        builtins.enter("__lte__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() <= ((Number) r).intValue();
            }
        });

        builtins.enter("__gte__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() >= ((Number) r).intValue();
            }
        });

        builtins.enter("__eq__", new AbstractFunction() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() == ((Number) r).intValue();
            }
        });

        builtins.enter("int", new AbstractFunction() {
            @Override
            public Object call(Object... args) {
                return Integer.parseInt((String) args[0]);
            }
        });

        getStack().push(builtins);
    }

    protected Stack getStack() {
        return stack;
    }

    protected StackFactory getStackFactory() {
        return stackFactory;
    }

    @Override
    public Object interpret(Node node) {
        if (node != null) {
            return node.interpret(this);
        }
        return null;
    }

    @Override
    public Object interpretAssignment(AssignmentNode node) {
        String name = (String) interpret(node.getIdentifier());
        Object value = interpret(node.getValue());

        getStack().enter(name, value);

        return null;
    }

    @Override
    public Object interpretIdentifier(IdentifierNode node) {
        if (node.getState() == IdentifierNode.STORE) {
            return node.getValue();
        }

        return getStack().lookup(node.getValue());
    }

    @Override
    public Object interpretNumber(NumberNode node) {
        return node.getValue();
    }

    @Override
    public Object interpretExpression(ExpressionNode node) {
        Object lhs = interpret(node.getLeft());
        Object rhs = interpret(node.getRight());
        Operator op = node.getOperator();

        return lookupFunction(op.getOperation()).call(lhs, rhs);
    }

    @Override
    public Object interpretRead(ReadNode node) {
        return sc.nextLine();
    }

    @Override
    public Object interpretPrint(PrintNode node) {
        System.out.println(interpret(node.getExpression()));
        return null;
    }

    @Override
    public Object interpretExpressions(ExpressionsNode node) {
        List<Object> objs = new LinkedList<Object>();
        for (Node n : node.getNodes()) {
            objs.add(interpret(n));
        }

        return objs;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object interpretCall(CallNode node) {
        String name = (String) interpret(node.getIdentifier());
        List<Object> args = new ArrayList<Object>();

        Object obj = interpret(node.getExpressions());

        if (obj instanceof List) {
            args = (List<Object>) obj;
        } else {
            args.add(obj);
        }

        return lookupFunction(name).call(args.toArray(new Object[0]));
    }

    @Override
    public Object interpretIf(IfNode node) {
        boolean compare = (Boolean) interpret(node.getCompare());
        getStack().push(getStackFactory().getStackFrame());

        if (compare) {
            interpret(node.getTrue());
        } else {
            interpret(node.getFalse());
        }
        getStack().pop();

        return null;
    }

    @Override
    public Object interpretStatements(StatementsNode node) {
        for (Node n : node.getChildrens()) {
            interpret(n);
        }

        return null;
    }

    @Override
    public Object interpretCompare(CompareNode node) {
        return interpretExpression(node);
    }

    protected Function lookupFunction(String name) {
        Object f = getStack().lookup(name);
        if (!(f instanceof Function)) {
            throw new RuntimeException(String.format("%s is not a function!",
                    name));
        }
        return (Function) f;
    }

}
