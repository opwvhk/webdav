package opwvhk.webdav;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlWriter {
	private static final DateTimeFormatter DATETIME_FORMAT_CREATION_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static final DateTimeFormatter DATETIME_FORMAT_LAST_MODIFIED = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss z");

	@SuppressWarnings("ConstantValue")
	public static void main(String[] args) throws ParserConfigurationException, TransformerException {

		String pathToEntry = "full/path/to/entry";
		String displayName = "entry";

		Instant creationDate = Instant.now().minus(Duration.ofDays(1));
		Instant lastModified = Instant.now();

		// If isFolder = true, these are ignored
		String contentType   = "text/plain"; // see isCollection
		String contentLength = "-1";
		String etag = ""; // Change with every change of the file

		boolean isFolder = false;

		DocumentBuilder builder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
		Document doc = builder.newDocument();
		doc.setXmlStandalone(true);

		String WEBDAV_NAMESPACE = "DAV:";

		Element response = doc.createElementNS(WEBDAV_NAMESPACE, "D:response");
		response.setAttributeNS(WEBDAV_NAMESPACE, "D:href", URI.create(pathToEntry).toString());

		Element propstat = doc.createElementNS(WEBDAV_NAMESPACE, "D:propstat");
		Element prop = doc.createElementNS(WEBDAV_NAMESPACE, "D:prop");
		Element element = doc.createElementNS(WEBDAV_NAMESPACE, "D:displayname");
		element.appendChild(doc.createCDATASection(displayName));
		prop.appendChild(element);
		if (creationDate != null) {
			prop.appendChild(createProperty(doc, WEBDAV_NAMESPACE, "D:creationdate", DATETIME_FORMAT_CREATION_DATE.format(creationDate)));
		}
		if (lastModified != null) {
			prop.appendChild(createProperty(doc, WEBDAV_NAMESPACE, "D:getlastmodified", DATETIME_FORMAT_LAST_MODIFIED.format(lastModified)));
		}

		if (isFolder) {
			Element resourceType = doc.createElementNS(WEBDAV_NAMESPACE, "D:resourcetype");
			resourceType.appendChild(doc.createElementNS(WEBDAV_NAMESPACE, "D:collection"));
			prop.appendChild(resourceType);
		} else {
			if (contentType != null) {
				prop.appendChild(createProperty(doc, WEBDAV_NAMESPACE, "D:getcontenttype", contentType));
			}
			if (contentLength != null) {
				prop.appendChild(createProperty(doc, WEBDAV_NAMESPACE, "D:getcontentlength", contentLength));
			}
			if (etag != null) {
				prop.appendChild(createProperty(doc, WEBDAV_NAMESPACE, "D:getetag", etag));
			}
		}
		propstat.appendChild(prop);
		propstat.appendChild(createProperty(doc, WEBDAV_NAMESPACE, "D:status", "HTTP/1.1 200 Ok"));

		response.appendChild(propstat);
		doc.appendChild(response);

		// Output the XML document
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(new DOMSource(doc), new StreamResult(System.out));
	}

	private static Node createProperty(Document doc, String namespace, String name, String value) {
		Element element = doc.createElementNS(namespace, name);
		element.setTextContent(value);
		return element;
	}
}
