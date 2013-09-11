/**
 * 
 */
package grnet.validation;

import java.util.Vector;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * @author vogias
 * 
 */
public class ValidationErrorHandler implements ErrorHandler {

	String fullmessage = "";
	Vector<String> shortMessage = new Vector<>();

	public String getFullMessage() {
		return fullmessage;
	}

	private void appendShortMessage(String sMes) {
		if (!shortMessage.contains(sMes))
			shortMessage.add(sMes);
	}

	public Vector<String> getShortMessages() {
		return shortMessage;
	}

	public void appenedFullMessage(String mes) {
		fullmessage += mes + "\n";
	}

	public void warning(SAXParseException ex) {
		String shortMes = ex.getMessage();

		String message = "**Parsing Warning**" + "  Line:    "
				+ ex.getLineNumber() + " URI:" + ex.getSystemId()
				+ " Message: " + shortMes;

		appenedFullMessage(message);

		appendShortMessage(shortMes);
		// throw new SAXException(message);
	}

	public void error(SAXParseException ex) {
		String shortMes = ex.getMessage();

		String message = "**Parsing Error**" + " Line:" + ex.getLineNumber()
				+ " URI:" + ex.getSystemId() + " Message: " + shortMes;

		appenedFullMessage(message);

		appendShortMessage(shortMes);
		// throw new SAXException(message);
	}

	public void fatalError(SAXParseException ex) {
		String shortMes = ex.getMessage();

		String message = "**Fatal Error**" + " Line:" + ex.getLineNumber()
				+ " URI:" + ex.getSystemId() + " Message:" + shortMes;

		appenedFullMessage(message);

		appendShortMessage(shortMes);
		// throw new SAXException(message);
	}
}