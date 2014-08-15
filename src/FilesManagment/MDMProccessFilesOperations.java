/*
 * 
 */
package FilesManagment;

// TODO: Auto-generated Javadoc
/**
 * The Class MDMProccessFilesOperations.
 */
public class MDMProccessFilesOperations extends FileManagmentOperations
{

	/** The first indexfor date isertion. */
//	private final int firstIndexforDateIsertion = 2;

	/**
	 * Instantiates a new MDM proccess files operations.
	 */
	public MDMProccessFilesOperations() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Gets the datalines number.
	 * 
	 * @param inputFileName
	 *            the input file name
	 * @return the datalines number
	 */
	public int getDatalinesNumber(String inputFileName)
	{
		int datalinesNum = this.getTotalLinesNum(inputFileName) - 2;	
		return datalinesNum;		
	}

	
}
