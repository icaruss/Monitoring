package FilesManagment;

public class MDMProccessFilesOperations extends FileManagmentOperations
{

	private final int firstIndexforDateIsertion = 2;

	public MDMProccessFilesOperations() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getDatalinesNumber(String inputFileName)
	{
		int datalinesNum = this.getTotalLinesNum(inputFileName) - 2;	
		return datalinesNum;		
	}

	
}
