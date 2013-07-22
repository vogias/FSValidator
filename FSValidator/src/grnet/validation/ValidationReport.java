/**
 * 
 */
package grnet.validation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

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
	File validationReport;
	BufferedWriter writer;
	long start;

	public ValidationReport(String path, String name) throws IOException {
		validationReportpath = path;
		validationReport = new File(path, "report_" + name + ".txt");

		validFilesNum = 0;
		invalidFilesNum = 0;
		start = System.currentTimeMillis();
		writer = new BufferedWriter(new FileWriter(validationReport));
		writer.append("Report date:" + new Date().toString());
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

	private void appendTotalParsedFiles() throws IOException {
		writer.append("Total parsed files:" + getInvalidFilesNum()
				+ getValidFilesNum());
		writer.newLine();
	}

	private void appendValidFilesNum() throws IOException {
		writer.append("Number of valid records:" + getValidFilesNum());
		writer.newLine();
	}

	private void appendInValidFilesNum() throws IOException {
		writer.append("Number of valid records:" + getInvalidFilesNum());
		writer.newLine();
	}

	private void appendDuration() throws IOException {
		long end = System.currentTimeMillis();
		long diff = end - start;
		writer.append("Total time (ms):" + diff);
		writer.newLine();
	}

	public void appendGeneralInfo() throws IOException {
		appendTotalParsedFiles();
		appendDuration();
		appendValidFilesNum();
		appendInValidFilesNum();
		writer.close();

	}

	public void appendXMLFileNameNStatus(String name, String status,
			String reason) throws IOException {
		writer.append("Validating file:" + name);
		writer.newLine();
		writer.append("Validating Status:" + status);
		writer.newLine();
		if (status.equals(Constants.invalidData)) {
			writer.append("Reason:" + reason);
			writer.newLine();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
