/*
 * 
 */
package platformSpecific;

// TODO: Auto-generated Javadoc
/**
 * The Interface OSType.
 */
interface OSType 
{
	
	/**
	 * Gets the CSV startline.
	 * 
	 * @return the CSV startline
	 */
	int getCSVStartline();
	
	/**
	 * Gets the CSV end line.
	 * 
	 * @return the CSV end line
	 */
	int getCSVEndLine();
	
	/**
	 * Gets the line to remove start index.
	 * 
	 * @return the line to remove start index
	 */
	int getLineToRemoveStartIndex();
	
	/**
	 * Gets the line to remove end index.
	 * 
	 * @return the line to remove end index
	 */
	int getLineToRemoveEndIndex();
	
	/**
	 * First line csv index.
	 * 
	 * @return the int
	 */
	int firstLineCSVIndex();

	
}
