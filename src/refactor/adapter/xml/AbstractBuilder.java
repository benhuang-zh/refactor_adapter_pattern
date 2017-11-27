package refactor.adapter.xml;

import java.util.Stack;

public abstract class AbstractBuilder implements OutputBuilder {
    static final private String CANNOT_ADD_ABOVE_ROOT = "Cannot add node above the root node.";
    static final private String CANNOT_ADD_BESIDE_ROOT = "Cannot add node beside the root node.";
    private Stack history = new Stack();
    private Adapter root;
    private Adapter parent;
    private Adapter current;

    public AbstractBuilder(String rootName) {
        init(rootName);
    }

    public void addAbove(String uncle) {
        if (current == root)
            throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
        history.pop();
        boolean atRootNode = (history.size() == 1);
        if (atRootNode)
            throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
        history.pop();
        current = (Adapter) history.peek();
        addBelow(uncle);
    }

    public void addGrandfather(String grandfather) {
        if (current == root)
            throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
        history.pop();
        boolean atRootNode = (history.size() == 1);
        if (atRootNode)
            throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
        history.pop();
        history.pop();
        current = (Adapter) history.peek();
        addBelow(grandfather);
    }

    public void addAttribute(String name, String value) {
        current.addAttribute(name, value);
    }

    public void addBelow(String child) {
        Adapter childNode = current.add(child);
        parent = current;
        current = childNode;
        history.push(current);
    }

    public void addBeside(String sibling) {
        if (current == root)
            throw new RuntimeException(CANNOT_ADD_BESIDE_ROOT);
        current = parent.add(sibling);
        history.pop();
        history.push(current);
    }

    public void addValue(String value) {
        current.addValue(value);
    }

    public void startNewBuild(String rootName) {
        init(rootName);
    }

    public String toString() {
        return root.toString();
    }

    protected void init(String rootName) {
        root = createRootNode(rootName);
        current = root;
        parent = root;
        history = new Stack();
        history.push(current);
    }

    abstract protected Adapter createRootNode(String rootName);

}
