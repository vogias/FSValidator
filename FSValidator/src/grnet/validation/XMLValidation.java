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
import java.io.IOException;
import java.net.ConnectException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author vogias
 * 
 */
public class XMLValidation {

	private static final Logger slf4jLogger = LoggerFactory.getLogger(XMLValidation.class);
	private final static String QUEUE_NAME = "validation";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method ssstub

		Enviroment enviroment = new Enviroment(args[0]);

		if (enviroment.envCreation) {
			String schemaUrl = enviroment.getArguments().getSchemaURL();
			Core core = new Core(schemaUrl);

			XMLSource source = new XMLSource(args[0]);

			File sourceFile = source.getSource();

			if (sourceFile.exists()) {

				Collection<File> xmls = source.getXMLs();

				System.out.println("Validating repository:" + sourceFile.getName());

				System.out.println("Number of files to validate:" + xmls.size());

				Iterator<File> iterator = xmls.iterator();

				System.out.println("Validating against schema:" + schemaUrl + "...");

				ValidationReport report = null;
				if (enviroment.getArguments().createReport().equalsIgnoreCase("true")) {

					report = new ValidationReport(enviroment.getArguments().getDestFolderLocation(),
							enviroment.getDataProviderValid().getName());

				}

				ConnectionFactory factory = new ConnectionFactory();
				factory.setHost(enviroment.getArguments().getQueueHost());
				factory.setUsername(enviroment.getArguments().getQueueUserName());
				factory.setPassword(enviroment.getArguments().getQueuePassword());

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

						Connection connection = factory.newConnection();
						Channel channel = connection.createChannel();
						channel.queueDeclare(QUEUE_NAME, false, false, false, null);

						channel.basicPublish("", QUEUE_NAME, null, logString.toString().getBytes());
						channel.close();
						connection.close();
						try {
							if (report != null) {

								report.raiseValidFilesNum();
							}

							FileUtils.copyFileToDirectory(xmlFile, enviroment.getDataProviderValid());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						logString.append(" " + "Invalid");
						slf4jLogger.info(logString.toString());

						try {
							Connection connection = factory.newConnection();
							Channel channel = connection.createChannel();
							channel.queueDeclare(QUEUE_NAME, false, false, false, null);

							channel.basicPublish("", QUEUE_NAME, null, logString.toString().getBytes());
							channel.close();
							connection.close();
						} catch (ConnectException ex) {
							ex.printStackTrace();
						}

						try {
							if (report != null) {

								if (enviroment.getArguments().extendedReport().equalsIgnoreCase("true"))
									report.appendXMLFileNameNStatus(xmlFile.getPath(), Constants.invalidData,
											core.getReason());

								report.raiseInvalidFilesNum();
							}
							FileUtils.copyFileToDirectory(xmlFile, enviroment.getDataProviderInValid());
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
