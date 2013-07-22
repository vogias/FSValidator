/**
 * 
 */
package grnet.validation;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

/**
 * @author vogias
 * 
 */
public class XMLValidation {

	public static void main(String[] args) throws IOException {// "http://83.212.101.124/odsAP/lomODS.xsd"
		// TODO Auto-generated method stub

		Enviroment enviroment = new Enviroment();

		if (enviroment.envCreation) {
			Core core = new Core();

			XMLSource source = new XMLSource(enviroment.getArguments()
					.getSourceFolderLocation());
			File sourceFile = source.getSource();

			if (sourceFile.exists()) {

				Collection<File> xmls = source.getXMLs();
				System.out
						.println("Number of files to validate:" + xmls.size());

				Iterator<File> iterator = xmls.iterator();

				System.out.println("Validating...");

				ValidationReport report = new ValidationReport(enviroment
						.getArguments().getDestFolderLocation(), enviroment
						.getDataProviderValid().getName());
				while (iterator.hasNext()) {

					File xmlFile = iterator.next();
					boolean xmlIsValid = core.validateXMLSchema(enviroment
							.getArguments().getSchemaURL(), xmlFile);

					if (xmlIsValid) {
						try {
							report.appendXMLFileNameNStatus(xmlFile.getPath(),
									Constants.validData, core.getReason());
							report.raiseValidFilesNum();

							FileUtils.copyFileToDirectory(xmlFile,
									enviroment.getDataProviderValid());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {

						try {
							report.appendXMLFileNameNStatus(xmlFile.getPath(),
									Constants.invalidData, core.getReason());
							report.raiseValidFilesNum();
							FileUtils.copyFileToDirectory(xmlFile,
									enviroment.getDataProviderInValid());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				report.appendGeneralInfo();
				System.out.println("Validation is done.");
			}

		}
	}
}
