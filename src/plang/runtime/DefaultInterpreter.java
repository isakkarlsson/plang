package plang.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    private abstract class Func {
        Object call(Object l, Object r) {
            return call(l, r);
        }

        Object call(Object... args) {
            return null;
        }
    }

    private Map<String, Object> variables = new HashMap<String, Object>();
    private Map<String, Func> builtins = new HashMap<String, Func>();
    Scanner sc = new Scanner(System.in);

    public DefaultInterpreter() {
        builtins.put("__add__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() + ((Number) r).intValue();
            }
        });
        builtins.put("__sub__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() - ((Number) r).intValue();
            }
        });
        builtins.put("__mul__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() * ((Number) r).intValue();
            }
        });
        builtins.put("__div__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() / ((Number) r).intValue();
            }
        });

        builtins.put("__lt__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() < ((Number) r).intValue();
            }
        });

        builtins.put("__gt__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() > ((Number) r).intValue();
            }
        });

        builtins.put("__lte__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() <= ((Number) r).intValue();
            }
        });

        builtins.put("__gte__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() >= ((Number) r).intValue();
            }
        });

        builtins.put("__eq__", new Func() {

            @Override
            public Object call(Object l, Object r) {
                return ((Number) l).intValue() == ((Number) r).intValue();
            }
        });

        builtins.put("int", new Func() {
            @Override
            Object call(Object... args) {
                return Integer.parseInt((String) args[0]);
            }
        });
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

        variables.put(name, value);

        return null;
    }

    @Override
    public Object interpretIdentifier(IdentifierNode node) {
        if (node.getState() == IdentifierNode.STORE) {
            return node.getValue();
        }

        return variables.get(node.getValue());
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

        Func f = builtins.get(op.getOperation());

        return f.call(lhs, rhs);
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
        return builtins.get(name).call(args.toArray(new Object[0]));
    }

    @Override
    public Object interpretIf(IfNode node) {
        boolean compare = (Boolean) interpret(node.getCompare());
        if (compare) {
            return interpret(node.getTrue());
        } else {
            return interpret(node.getFalse());
        }
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

}
