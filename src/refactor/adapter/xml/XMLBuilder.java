package refactor.adapter.xml;

import java.util.Stack;

public class XMLBuilder extends AbstractBuilder {
	private Adapter root;
	private Adapter parent;
	private Adapter current;

	public XMLBuilder(String rootName) {
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

	public void addGrandfather(String uncle) {
		if (current == root)
			throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
		history.pop();
		boolean atRootNode = (history.size() == 1);
		if (atRootNode)
			throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
		history.pop();
		history.pop();
		current = (Adapter) history.peek();
		addBelow(uncle);
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

	protected void init(String rootName) {
		root = new TagNodeAdapter(new TagNode(rootName));
		current = root;
		parent = root;
		history = new Stack();
		history.push(current);
	}

	public void startNewBuild(String rootName) {
		init(rootName);
	}

	public String toString() {
		return root.toString();
	}
}
