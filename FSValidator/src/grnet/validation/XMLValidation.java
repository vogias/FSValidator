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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method ssstub

		Enviroment enviroment = new Enviroment();

		if (enviroment.envCreation) {
			String schemaUrl = enviroment.getArguments().getSchemaURL();
			Core core = new Core(schemaUrl);

			// XMLSource source = new XMLSource(enviroment.getArguments()
			// .getSourceFolderLocation());
			
			XMLSource source = new XMLSource(args[0]);
			
			File sourceFile = source.getSource();

			if (sourceFile.exists()) {

				Collection<File> xmls = source.getXMLs();

				System.out
						.println("Number of files to validate:" + xmls.size());

				Iterator<File> iterator = xmls.iterator();

				System.out.println("Validating against schema:" + schemaUrl
						+ "...");

				ValidationReport report = null;
				if (enviroment.getArguments().createReport()
						.equalsIgnoreCase("true")) {

					report = new ValidationReport(enviroment.getArguments()
							.getDestFolderLocation(), enviroment
							.getDataProviderValid().getName());

				}

				while (iterator.hasNext()) {

					File xmlFile = iterator.next();

					boolean xmlIsValid = core.validateXMLSchema(xmlFile);

					if (xmlIsValid) {
						try {
							if (report != null) {
								// report.appendXMLFileNameNStatus(
								// xmlFile.getPath(), Constants.validData,
								// core.getReason());
								report.raiseValidFilesNum();
							}

							FileUtils.copyFileToDirectory(xmlFile,
									enviroment.getDataProviderValid());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {

						try {
							if (report != null) {

								if (enviroment.getArguments().extendedReport()
										.equalsIgnoreCase("true"))
									report.appendXMLFileNameNStatus(
											xmlFile.getPath(),
											Constants.invalidData,
											core.getReason());

								report.raiseInvalidFilesNum();
							}
							FileUtils.copyFileToDirectory(xmlFile,
									enviroment.getDataProviderInValid());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (report != null) {
					report.writeErrorBank(core.getErrorBank());
					report.appendGeneralInfo();
				}
				System.out.println("Validation is done.");
			}

		}
	}
}
