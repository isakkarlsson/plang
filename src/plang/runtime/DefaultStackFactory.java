package plang.runtime;

public class DefaultStackFactory implements StackFactory {

    @Override
    public StackFrame getStackFrame() {
        return new DefaultStackFrame();
    }

    @Override
    public Stack getStack() {
        return new DefaultStack();
    }

}
