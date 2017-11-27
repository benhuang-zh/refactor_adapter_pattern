package refactor.adapter.xml;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Stack;

public class DOMBuilder extends AbstractBuilder {
	private Document doc;
	private Adapter root;
	private Adapter parent;
	private Adapter current;

	public DOMBuilder(String rootName) {
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

	public Document getDocument() {
		return doc;
	}

	protected void init(String rootName) {
		doc = new DocumentImpl();
		Element rootElement = doc.createElement(rootName);
		doc.appendChild(rootElement);

		root = new ElementAdapter(doc, rootElement);
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
