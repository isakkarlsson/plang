package plang.runtime;

public interface Stack {

    /**
     * Push a stack frame to the stack
     * 
     * See: {@link DefaultInterpreter#interpretIf(plang.parser.ast.IfNode)}
     * 
     * @param frame
     */
    void push(StackFrame frame);

    /**
     * Lookup a variable <code>name</code> recursively
     * 
     * @param name
     * @return
     */
    Object lookup(String name);

    /**
     * Enter a variable to the current stack frame
     * 
     * @param name
     * @param value
     */
    void enter(String name, Object value);

    /**
     * Return the local stack frame
     * 
     * @return
     */
    StackFrame local();

    /**
     * Pop the local stack frame
     * 
     * @return
     */
    StackFrame pop();

    /**
     * Get the stack depth
     * 
     * @return
     */
    int getLevel();

}
