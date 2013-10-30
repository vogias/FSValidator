/**
 * 
 */
package grnet.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vogias
 * 
 */
public class Enviroment {

	boolean envCreation = false;
	File validData, invalidData, dataProviderValid, dataProviderInValid;
	Arguments arguments;

	private static final Logger slf4jLogger = LoggerFactory
			.getLogger(Enviroment.class);

	public Enviroment(String source) {
		envCreation = createEnviroment(source);
	}

	/**
	 * @return the arguments
	 */
	public Arguments getArguments() {
		return arguments;
	}

	/**
	 * @param arguments
	 *            the arguments to set
	 */
	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}

	/**
	 * @return the dataProvider
	 */

	/**
	 * @return the envCreation
	 */
	public boolean EnvCreation() {
		return envCreation;
	}

	/**
	 * @return the dataProviderValid
	 */
	public File getDataProviderValid() {
		return dataProviderValid;
	}

	/**
	 * @return the dataProviderInValid
	 */
	public File getDataProviderInValid() {
		return dataProviderInValid;
	}

	/**
	 * @param dataProviderValid
	 *            the dataProviderValid to set
	 */
	public void setDataProviderValid(File dataProviderValid) {
		this.dataProviderValid = dataProviderValid;
	}

	/**
	 * @param dataProviderInValid
	 *            the dataProviderInValid to set
	 */
	public void setDataProviderInValid(File dataProviderInValid) {
		this.dataProviderInValid = dataProviderInValid;
	}

	/**
	 * @return the validData
	 */
	public File getValidDataFolder() {
		return validData;
	}

	/**
	 * @return the invalidData
	 */
	public File getInvalidDataFolder() {
		return invalidData;
	}

	private boolean createEnviroment(String src) {

		try {
			arguments = new Arguments();

			File destStructure = new File(arguments.getDestFolderLocation());

			if (destStructure.exists()) {
				validData = new File(destStructure, "VALID");
				invalidData = new File(destStructure, "INVALID");
				validData.mkdir();
				invalidData.mkdir();

				// File source = new File(arguments.getSourceFolderLocation());

				File source = new File(src);
				if (source.exists()) {
					dataProviderValid = new File(validData, source.getName());
					dataProviderValid.mkdir();
					dataProviderInValid = new File(invalidData,
							source.getName());
					dataProviderInValid.mkdir();

				} else {
					System.err.println("Wrong source folder location.");
					System.err.println("Exiting...");
					System.exit(-1);
				}

				return true;
			} else
				return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			slf4jLogger.error(e.getMessage());
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			slf4jLogger.error(e.getMessage());
			return false;
		}

	}

	// public static void main(String[] args) {//
	// "http://83.212.101.124/odsAP/lomODS.xsd"
	// // TODO Auto-generated method stub
	// Enviroment enviroment = new Enviroment();
	//
	// if (enviroment.envCreation) {
	// System.out.println(enviroment.getInvalidDataFolder().getPath());
	// System.out.println(enviroment.getValidDataFolder().getPath());
	// }
	// }
}
