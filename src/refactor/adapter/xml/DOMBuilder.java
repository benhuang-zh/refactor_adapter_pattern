package refactor.adapter.xml;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOMBuilder extends AbstractBuilder {
	private Document doc;

	public DOMBuilder(String rootName) {
		super(rootName);
	}

	public Document getDocument() {
		return doc;
	}

	protected Adapter createRootNode(String rootName) {
		doc = new DocumentImpl();
		Element rootElement = doc.createElement(rootName);
		doc.appendChild(rootElement);
		return new ElementAdapter(doc, rootElement);
	}

}
