/*
 * 
 */
package FilesManagment;

// TODO: Auto-generated Javadoc
/**
 * The Class VmstatFileOperations.
 */
public class VmstatFileOperations extends FileManagmentOperations {

	/** The first indexfor date isertion. */
	private final int firstIndexforDateIsertion = 1;

	/**
	 * Instantiates a new vmstat file operations.
	 */
	public VmstatFileOperations() {
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
	public int getDatalinesNumber(String inputFileName) {
		int datalinesNum = this.getTotalLinesNum(inputFileName) - 6;
		// System.out.println("Total number of lines in a vmstat file is: " +
		// datalinesNum);
		return datalinesNum;
	}

	/**
	 * Gets the first indexfor date isertion.
	 * 
	 * @return the first indexfor date isertion
	 */
	public int getFirstIndexforDateIsertion() {
		return firstIndexforDateIsertion;
	}

}
