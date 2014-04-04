/*******************************************************************************
 * Copyright (c) 2014 Kostas Vogias.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Kostas Vogias - initial API and implementation
 ******************************************************************************/
/**
 * 
 */
package grnet.validation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	// public String getSourceFolderLocation() {
	// return props.getProperty(Constants.sourceFolder);
	// }

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
