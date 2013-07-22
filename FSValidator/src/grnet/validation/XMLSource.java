/**
 * 
 */
package grnet.validation;

import java.io.File;
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
		FileUtils utils = new FileUtils();

		Collection<File> files = utils.listFiles(getSource(), extensions, true);

		return files;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLSource source = new XMLSource(
				"C:\\Users\\vogias\\Desktop\\ODS DATA\\data\\mdstore\\");

		System.out.println(source.getXMLs());
	}

}
