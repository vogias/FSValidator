/**
 * 
 */
package grnet.validation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

/**
 * @author vogias
 * 
 */
public class Arguments {

	Properties props;

	public Arguments() throws FileNotFoundException, IOException {
		props = new Properties();
		props.load(new FileInputStream("configure.properties"));
	}

	/**
	 * @return the props
	 */
	public Properties getProps() {
		return props;
	}

	/**
	 * @param props
	 *            the props to set
	 */
	public void setProps(Properties props) {
		this.props = props;
	}

	public String getSchemaURL() {
		return props.getProperty(Constants.xsd);
	}

	public String getSourceFolderLocation() {
		return props.getProperty(Constants.sourceFolder);
	}

	public String getDestFolderLocation() {
		return props.getProperty(Constants.destFolder);
	}

	public String createReport() {
		return props.getProperty(Constants.createReport);
	}

	public String extendedReport() {
		String extended = props.getProperty(Constants.extendedReport)
				.toLowerCase();

		return extended;

	}
}
