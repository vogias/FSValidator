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

	public void warning(SAXParseException ex) {
		System.err.println(ex.getMessage());
	}

	public void error(SAXParseException ex) {
		System.err.println(ex.getMessage());
	}

	public void fatalError(SAXParseException ex) throws SAXException {
		throw ex;
	}
}