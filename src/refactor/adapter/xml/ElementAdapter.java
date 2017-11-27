package refactor.adapter.xml;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.StringWriter;

public class ElementAdapter implements Adapter {


    private Document doc;
    private Element node;

    public ElementAdapter(Document doc, Element node) {
        this.doc = doc;
        this.node = node;
    }

    @Override
    public void addValue(String value) {
        node.appendChild(doc.createTextNode(value));
    }

    @Override
    public Adapter add(String nodeName) {
        Element node = doc.createElement(nodeName);
        this.node.appendChild(node);
        return new ElementAdapter(doc, node);
    }

    @Override
    public void addAttribute(String name, String value) {
        node.setAttribute(name, value);
    }

    public String toString() {
        OutputFormat format = new OutputFormat(doc);
        StringWriter stringOut = new StringWriter();
        XMLSerializer serial = new XMLSerializer(stringOut, format);
        try {
            serial.asDOMSerializer();
            serial.serialize(doc.getDocumentElement());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return ioe.getMessage();
        }
        return stringOut.toString();
    }
}
