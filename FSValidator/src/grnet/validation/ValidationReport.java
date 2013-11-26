/**
 * 
 */
package grnet.validation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vogias
 * 
 */
public class ValidationReport {

	/**
	 * @param args
	 */

	String validationReportpath;
	int validFilesNum, invalidFilesNum;
	File validationReport, summaryReport;
	BufferedWriter writer, csvWriter;
	long start;
	String name;

	public ValidationReport(String path, String name) throws IOException {
		validationReportpath = path;
		this.name = name;
		validationReport = new File(path, "report_" + name + ".txt");

		validFilesNum = 0;
		invalidFilesNum = 0;
		start = System.currentTimeMillis();
		writer = new BufferedWriter(new FileWriter(validationReport));
		String date = new Date().toString();
		writer.append("Report date:" + date);
		writer.newLine();

	}

	/**
	 * @return the validFilesNum
	 */
	public int getValidFilesNum() {
		return validFilesNum;
	}

	/**
	 * @return the invalidFilesNum
	 */
	public int getInvalidFilesNum() {
		return invalidFilesNum;
	}

	/**
	 * @param validFilesNum
	 *            the validFilesNum to set
	 */
	public void raiseValidFilesNum() {
		this.validFilesNum++;
	}

	/**
	 * @param invalidFilesNum
	 *            the invalidFilesNum to set
	 */
	public void raiseInvalidFilesNum() {
		this.invalidFilesNum++;
	}

	/**
	 * @return the validationReportpath
	 */
	public String getValidationReportpath() {
		return validationReportpath;
	}

	/**
	 * @param validationReportpath
	 *            the validationReportpath to set
	 */
	public void setValidationReportpath(String validationReportpath) {
		this.validationReportpath = validationReportpath;
	}

	private String appendTotalParsedFiles() throws IOException {
		int total = getInvalidFilesNum() + getValidFilesNum();
		writer.append("Total parsed files:" + total);
		writer.newLine();

		// slf4jLogger.info("Total parsed files:" + total);

		return String.valueOf(total);
	}

	private String appendValidFilesNum() throws IOException {
		int validFilesNum = getValidFilesNum();
		writer.append("Number of valid records:" + validFilesNum);
		writer.newLine();
		// slf4jLogger.info("Number of valid records:" + getValidFilesNum());

		return String.valueOf(validFilesNum);

	}

	private String appendInValidFilesNum() throws IOException {
		int invalidFilesNum = getInvalidFilesNum();
		writer.append("Number of invalid records:" + invalidFilesNum);
		writer.newLine();
		// slf4jLogger.info("Number of invalid records:" +
		// getInvalidFilesNum());

		return String.valueOf(invalidFilesNum);
	}

	private String appendDuration() throws IOException {
		long end = System.currentTimeMillis();
		long diff = end - start;
		writer.append("Total time (ms):" + diff);
		writer.newLine();

		// slf4jLogger.info("Total time (ms):" + diff);

		return String.valueOf(diff);
	}

	public void appendGeneralInfo() throws IOException {
		writer.append("=========Validation general info===========");
		writer.newLine();
		appendTotalParsedFiles();
		appendDuration();

		appendValidFilesNum();

		appendInValidFilesNum();

		writer.append("===========================================");
		writer.newLine();
		writer.close();

	}

	public void writeErrorBank(HashMap<String, Integer> errorBank)
			throws IOException {
		writer.append("=========Validation errors summary=========");
		writer.newLine();

		Iterator<String> iterator = errorBank.keySet().iterator();

		while (iterator.hasNext()) {
			String err = iterator.next();
			Integer counter = errorBank.get(err);

			writer.append("Validation error:" + err + "\nTimes found:"
					+ counter);
			writer.newLine();
		}

		writer.append("===========================================");
		writer.newLine();

		writeSummary2CSV(errorBank);

	}

	private void writeSummary2CSV(HashMap<String, Integer> errorBank)
			throws IOException {
		summaryReport = new File(validationReportpath, "report_" + this.name
				+ ".csv");
		csvWriter = new BufferedWriter(new FileWriter(summaryReport));
		csvWriter.append("Error,Times_Found");
		csvWriter.newLine();

		Iterator<String> iterator = errorBank.keySet().iterator();

		while (iterator.hasNext()) {
			String err = iterator.next();
			Integer counter = errorBank.get(err);

			if (err.contains(","))
				err = err.replace(",", "/");

			csvWriter.append(err + "," + +counter);
			csvWriter.newLine();
		}
		csvWriter.close();

	}

	public void appendXMLFileNameNStatus(String name, String status,
			String reason) throws IOException {
		writer.append("Validating file:" + name);
		writer.newLine();
		writer.append("Validating Status:" + status);
		writer.newLine();
		if (status.equals(Constants.invalidData)) {
			writer.append("Reason:\n" + reason);
			writer.newLine();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
