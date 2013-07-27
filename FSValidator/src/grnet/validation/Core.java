/**
 * 
 */
package grnet.validation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
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
	Boolean flag;

	public Core() {
		reason = "";
		flag = true;
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

		// try {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// Schema schema = factory.newSchema(new File(xsdPath));
		Schema schema;
		ValidationErrorHandler lenient = null;
		try {
			schema = factory.newSchema(new URL(xsdPath));

			Validator validator = schema.newValidator();
			lenient = new ValidationErrorHandler();
			validator.setErrorHandler(lenient);
			validator.validate(new StreamSource(xml));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.err.println("Malformed URL.");
			System.err.println("Please correct the input XSD URL");
			System.err.println("Exiting...");
			System.exit(-1);
		} catch (SAXException e) {
			// TODO Auto-generated catch block

			// String message = e.getLocalizedMessage();
			// setReason(message);
			// if (message.contains("**Parsing Warning**")) {
			// appendtReason(message);
			//
			// // System.out.println(getReason());
			// } else if (message.contains("**Parsing Error**")) {
			// appendtReason(message);
			// flag = flag && false;
			// // System.out.println(getReason());
			// } else if (message.contains("**Fatal Error**")) {
			// appendtReason(message);
			//
			// flag = flag && false;
			// // System.out.println(getReason());
			// }

			// return flag;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Source XML File not found ");
			System.err.println("Please correct the input XML Folder location.");
			System.err.println("Exiting...");
			System.exit(-1);

		}
		String message = lenient.getMessage();
		setReason(message);

		if (message.contains("**Parsing Error**"))

			flag = flag && false;

		else if (message.contains("**Fatal Error**"))

			flag = flag && false;

		return flag;
	}

}
