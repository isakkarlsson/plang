package plang.runtime;

/**
 * Represent a callable function
 * 
 * @author Isak Karlsson
 * 
 */
public interface Function {

    /**
     * Call with an array of objects as arguments.
     * 
     * @param args
     * @return
     */
    Object call(Object... args);

    Object call(Object a, Object b);

    Object call(Object a, Object b, Object c);

    Object call(Object a, Object b, Object c, Object d);

}
