package plang.runtime;

public interface StackFrame {
    /**
     * Do the stack frame contain <code>name</code>?
     * 
     * @param name
     * @return
     */
    boolean contains(String name);

    /**
     * Return <code>name</code>!
     * 
     * @param name
     * @return
     */
    Object lookup(String name);

    /**
     * Enter <code>name</code> with <code>value</code>
     * 
     * @param name
     * @param value
     */
    void enter(String name, Object value);
}
