package plang.runtime;

public interface StackFactory {

    /**
     * Construct a {@link StackFrame}
     * 
     * @return
     */
    StackFrame getStackFrame();

    /**
     * Construct a {@link Stack}
     * 
     * @return
     */
    Stack getStack();

}
