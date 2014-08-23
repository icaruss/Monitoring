/*
 * 
 */
package platformSpecific;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// TODO: Auto-generated Javadoc
/**
 * The Class LINUX.
 */
public class LINUX implements OSType {

	/** The OS type. */
	final String OSType = "Linux";

	/*
	 * (non-Javadoc)
	 * 
	 * @see platformSpecific.OSType#getCSVStartline()
	 */
	@Override
	public int getCSVStartline() {
		// TODO Auto-generated method stub
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
		return 0;
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
					startIndex = line.toString().indexOf("TTY") - 1;
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
					endIndex = line.toString().indexOf("STAT") + 4;
					break;
				}
			}
		} catch (Exception e) {

		}
		return endIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platformSpecific.OSType#firstLineCSVIndex()
	 */
	@Override
	public int firstLineCSVIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
