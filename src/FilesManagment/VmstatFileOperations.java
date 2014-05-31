package FilesManagment;

public class VmstatFileOperations extends FileManagmentOperations
{

	private final int firstIndexforDateIsertion = 1;

	
	
	public VmstatFileOperations() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public int getDatalinesNumber(String inputFileName)
	{
		int datalinesNum = this.getTotalLinesNum(inputFileName) - 6;	
		//System.out.println("Total number of lines in a vmstat file is: " + datalinesNum);
		return datalinesNum;		
	}



	public int getFirstIndexforDateIsertion() {
		return firstIndexforDateIsertion;
	}

}
