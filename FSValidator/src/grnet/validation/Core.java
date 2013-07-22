/**
 * 
 */
package grnet.validation;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * @author vogias
 * 
 */
public class Core {

	/**
	 * @param args
	 */
	String reason;

	public Core() {
		reason = "";
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean validateXMLSchema(String xsdPath, File xml) {

		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			// Schema schema = factory.newSchema(new File(xsdPath));
			Schema schema = factory.newSchema(new URL(xsdPath));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xml));
		} catch (IOException | SAXException e) {
			setReason(e.getMessage());
			return false;
		}
		return true;
	}

}
