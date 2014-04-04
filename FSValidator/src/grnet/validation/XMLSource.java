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

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * @author vogias
 * 
 */
public class XMLSource {

	/**
	 * @param args
	 */

	File source;

	public XMLSource(String source) {
		this.source = new File(source);
	}

	/**
	 * @return the source
	 */
	public File getSource() {
		return source;
	}

	public Collection<File> getXMLs() {

		String[] extensions = { "xml" };
		

		Collection<File> files = FileUtils.listFiles(getSource(), extensions, true);
		
		
		return files;

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLSource source = new XMLSource(
				"C:\\Users\\vogias\\Desktop\\ODS DATA\\data\\mdstore\\");

		System.out.println(source.getXMLs());
	}

}
