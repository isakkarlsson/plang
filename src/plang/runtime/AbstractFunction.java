package plang.runtime;

public abstract class AbstractFunction implements Function {

    /**
     * Overriden to call the methods below if args.length = 2, 3 or 4. Override
     * those if that's the arity you're looking for.
     */
    @Override
    public Object call(Object... args) {
        if (args.length == 2) {
            return call(args[0], args[1]);
        } else if (args.length == 3) {
            return call(args[0], args[1], args[2]);
        } else if (args.length == 4) {
            return call(args[0], args[1], args[2], args[3]);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Object call(Object a, Object b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object call(Object a, Object b, Object c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object call(Object a, Object b, Object c, Object d) {
        throw new UnsupportedOperationException();
    }

}
