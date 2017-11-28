package refactor.adapter.xml;

import java.io.StringReader;
import java.util.StringTokenizer;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class PrettyPrinter extends DefaultHandler {
	private StringBuilder result;
	private int count;
	private boolean startElement;
	private boolean charFlags = false;
	private boolean firstItem = false;

	public PrettyPrinter() {
	}

	public void characters(char[] buf, int start, int length) {
		String resultData = new String(buf, start, length);
		if (firstItem) {
			insertTabs();
			charFlags = true;
			firstItem = false;
		}
		resultData = resultData.replaceAll("&", "&amp;");
		result.append(resultData);
	}

	public void endElement(String uri, String localName, String qName) {
		adjustWithPreviousText();
		if (!startElement)
			count--;

		insertTabs();
		startElement = false;
		result.append("</").append(localName).append(">\n");
	}

	public String format(String inputXML) {
		result = new StringBuilder("\n");
		count = 0;
		startElement = false;
		inputXML = stripTabs(inputXML).replaceAll("& ", "&amp; ");
		try {
			XMLReader reader = new SAXParser();
			InputSource source = new InputSource(new StringReader(inputXML));
			reader.setContentHandler(this);
			reader.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	private void insertTabs() {
		for (int x = 0; x < count; x++)
			result.append("\t");
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		firstItem = true;
		adjustWithPreviousText();
		if (startElement)
			count++;
		insertTabs();
		result.append("<").append(localName);
		int len;
		if ((len = attributes.getLength()) > 0) {
			result.append(" ");
			for (int i = 0; i < len; i++) {
				String value = attributes.getValue(i);
				value = value.replaceAll("& ", "&amp; ");
				result.append(attributes.getLocalName(i)).append("='").append(value).append("'");
				if (i < (len - 1))
					result.append(" ");
			}
		}
		startElement = true;
		result.append(">\n");
	}

	public void adjustWithPreviousText() {
		if (charFlags == true) {
			charFlags = false;
			result.append("\n");
		}
	}

	private String stripTabs(String rawText) {
		StringTokenizer tok = new StringTokenizer(rawText, "\t");
		StringBuilder result = new StringBuilder();
		while (tok.hasMoreElements())
			result.append(tok.nextToken());
		return result.toString();
	}
}
