package plang.runtime;

import java.util.HashMap;
import java.util.Map;

public class DefaultStackFrame implements StackFrame {

    private Map<String, Object> variables = new HashMap<String, Object>();

    @Override
    public boolean contains(String name) {
        return variables.containsKey(name);
    }

    @Override
    public Object lookup(String name) {
        return variables.get(name);
    }

    @Override
    public void enter(String name, Object value) {
        variables.put(name, value);
    }

}
