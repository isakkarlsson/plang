package plang.runtime;

import java.util.ArrayList;

public class DefaultStack implements Stack {

    private ArrayList<StackFrame> frames = new ArrayList<StackFrame>();
    private int current = -1;

    @Override
    public void push(StackFrame frame) {
        frames.add(frame);
        current++;
    }

    @Override
    public Object lookup(String name) {
        for (int n = current; n >= 0; n--) {
            StackFrame frame = frames.get(n);
            Object value = frame.lookup(name);
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    @Override
    public void enter(String name, Object value) {
        local().enter(name, value);
    }

    @Override
    public StackFrame local() {
        return frames.get(current);
    }

    @Override
    public StackFrame pop() {
        StackFrame pop = frames.remove(current--);
        return pop;
    }

    @Override
    public int getLevel() {
        return current;
    }

}
