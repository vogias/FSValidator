/**
 * 
 */
package grnet.validation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author vogias
 * 
 */
public class ValidationErrorHandler implements ErrorHandler {

	String message = "";

	public String getMessage() {
		return message;
	}

	public void appenedMessage(String mes) {
		message += mes + "\n";
	}

	public void warning(SAXParseException ex) {
		String message = "**Parsing Warning**" + "  Line:    "
				+ ex.getLineNumber() + " URI:" + ex.getSystemId()
				+ " Message: " + ex.getMessage();

		appenedMessage(message);
		// throw new SAXException(message);
	}

	public void error(SAXParseException ex) {
		String message = "**Parsing Error**" + " Line:" + ex.getLineNumber()
				+ " URI:" + ex.getSystemId() + " Message: " + ex.getMessage();

		appenedMessage(message);
		// throw new SAXException(message);
	}

	public void fatalError(SAXParseException ex) {
		String message = "**Fatal Error**" + " Line:" + ex.getLineNumber()
				+ " URI:" + ex.getSystemId() + " Message:" + ex.getMessage();

		appenedMessage(message);
		// throw new SAXException(message);
	}
}