/*
 * 
 */
package platformSpecific;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// TODO: Auto-generated Javadoc
/**
 * The Class AIX.
 */
public class AIX implements OSType {

	/** The OS type. */
	final String OSType = "AIX";

	/*
	 * (non-Javadoc)
	 * 
	 * @see platformSpecific.OSType#firstLineCSVIndex()
	 */
	@Override
	public int firstLineCSVIndex() {
		String line = null;
		File inputFile = new File("");
		int startIndex = 0;

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(
					new FileReader(inputFile));

			// repeat until all lines is read
			while ((line = reader.readLine()) != null) {
				if (line.toString().contains("USER")) {
					startIndex = line.toString().indexOf("TT") - 1;
					break;
				}
			}
		} catch (Exception e) {

		}
		return startIndex;
	}

	/**
	 * Gets the numberof chars to remove.
	 * 
	 * @return the numberof chars to remove
	 */
	public int getNumberofCharsToRemove() {
		return 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platformSpecific.OSType#getCSVStartline()
	 */
	public int getCSVStartline() {

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platformSpecific.OSType#getCSVEndLine()
	 */
	@Override
	public int getCSVEndLine() {
		// TODO Auto-generated method stub
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platformSpecific.OSType#getLineToRemoveStartIndex()
	 */
	@Override
	public int getLineToRemoveStartIndex() {
		String line = null;
		File inputFile = new File("");
		int startIndex = 0;

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(
					new FileReader(inputFile));
			// repeat until all lines is read
			while ((line = reader.readLine()) != null) {
				if (line.toString().contains("USER")) {
					startIndex = line.toString().indexOf("TT") - 1;
					break;
				}
			}
		} catch (Exception e) {

		}
		return startIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platformSpecific.OSType#getLineToRemoveEndIndex()
	 */
	@Override
	public int getLineToRemoveEndIndex() {
		// TODO Auto-generated method stub
		String line = null;
		File inputFile = new File("");
		int endIndex = 0;

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(
					new FileReader(inputFile));

			// repeat until all lines is read
			while ((line = reader.readLine()) != null) {
				if (line.toString().contains("USER")) {
					endIndex = line.toString().indexOf("ST") + 2;
					break;
				}
			}
		} catch (Exception e) {

		}
		return endIndex;
	}

}
