/**
 * 
 */
package grnet.validation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	public String getSchemaURL() {
		return props.getProperty(Constants.xsd);
	}

	public String getSourceFolderLocation() {
		return props.getProperty(Constants.sourceFolder);
	}

	public String getDestFolderLocation() {
		return props.getProperty(Constants.destFolder);
	}
}
