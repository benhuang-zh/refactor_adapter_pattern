package refactor.adapter.xml;

public class XMLBuilder extends AbstractBuilder {
	public XMLBuilder(String rootName) {
		super(rootName);
	}

	protected Adapter createRootNode(String rootName) {
		return new TagNodeAdapter(new TagNode(rootName));
	}

}
