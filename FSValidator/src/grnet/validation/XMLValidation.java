/**
 * 
 */
package grnet.validation;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vogias
 * 
 */
public class XMLValidation {

	private static final Logger slf4jLogger = LoggerFactory
			.getLogger(XMLValidation.class);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method ssstub

		Enviroment enviroment = new Enviroment(args[0]);
		// StringBuffer logString = new StringBuffer();

		if (enviroment.envCreation) {
			String schemaUrl = enviroment.getArguments().getSchemaURL();
			Core core = new Core(schemaUrl);

			// XMLSource source = new XMLSource(enviroment.getArguments()
			// .getSourceFolderLocation());

			XMLSource source = new XMLSource(args[0]);

			File sourceFile = source.getSource();

			if (sourceFile.exists()) {

				Collection<File> xmls = source.getXMLs();

				// System.out
				// .println("Number of files to validate:" + xmls.size());

				System.out.println("Validating repository:"
						+ sourceFile.getName());

				// logString.append(sourceFile.getName());
				System.out
						.println("Number of files to validate:" + xmls.size());

				// logString.append(" "+xmls.size());
				Iterator<File> iterator = xmls.iterator();

				System.out.println("Validating against schema:" + schemaUrl
						+ "...");

				// logString.append(" "+schemaUrl);

				ValidationReport report = null;
				if (enviroment.getArguments().createReport()
						.equalsIgnoreCase("true")) {

					report = new ValidationReport(enviroment.getArguments()
							.getDestFolderLocation(), enviroment
							.getDataProviderValid().getName());

				}

				while (iterator.hasNext()) {

					StringBuffer logString = new StringBuffer();
					logString.append(sourceFile.getName());
					logString.append(" " + schemaUrl);

					File xmlFile = iterator.next();
					String name = xmlFile.getName();
					name = name.substring(0, name.indexOf(".xml"));

					logString.append(" " + name);

					boolean xmlIsValid = core.validateXMLSchema(xmlFile);

					if (xmlIsValid) {
						logString.append(" " + "Valid");
						slf4jLogger.info(logString.toString());
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
						logString.append(" " + "Invalid");
						slf4jLogger.info(logString.toString());
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
				// System.out.println("Validation is done.");
				
			}

		}
	}
}
