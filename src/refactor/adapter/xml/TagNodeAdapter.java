package refactor.adapter.xml;

public class TagNodeAdapter implements Adapter {


    private final TagNode tagNode;

    public TagNodeAdapter(TagNode tagNode) {
        this.tagNode = tagNode;
    }

    @Override
    public void addValue(String value) {
        tagNode.addValue(value);
    }

    @Override
    public Adapter add(String nodeName) {
        TagNode node = new TagNode(nodeName);
        tagNode.add(node);
        return new TagNodeAdapter(node);
    }

    @Override
    public void addAttribute(String name, String value) {
        tagNode.addAttribute(name, value);
    }

    @Override
    public String toString() {
        return tagNode.toString();
    }
}
