/*
 * 
 */
package platformSpecific;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// TODO: Auto-generated Javadoc
/**
 * The Class SOLARIS.
 */
public class SOLARIS implements OSType 
{
	
	/** The OS type. */
	final String OSType = "SunOS";
	
	/* (non-Javadoc)
	 * @see platformSpecific.OSType#getCSVStartline()
	 */
	@Override
	public int getCSVStartline() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see platformSpecific.OSType#getCSVEndLine()
	 */
	@Override
	public int getCSVEndLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see platformSpecific.OSType#getLineToRemoveStartIndex()
	 */
	@Override
	public int getLineToRemoveStartIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
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
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));

			// repeat until all lines is read
			while ((line = reader.readLine()) != null)
			{
				if (line.toString().contains("USER"))
				{
					endIndex = line.toString().indexOf("ST") + 2;
					break;
				}
			}
		}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return endIndex;
	}

	/* (non-Javadoc)
	 * @see platformSpecific.OSType#firstLineCSVIndex()
	 */
	@Override
	public int firstLineCSVIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
