package platformSpecific;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class HPUX implements OSType 
{
	final String OSType = "HP-UX";

	@Override
	public int getCSVStartline() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCSVEndLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLineToRemoveStartIndex() {
		String line = null;
		File inputFile = new File("");
		int startIndex = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			FileWriter fstream = new FileWriter("", true);
			int count = 0;

			// repeat until all lines is read
			while ((line = reader.readLine()) != null)
			{
				if (line.toString().contains("USER"))
				{
					startIndex = line.toString().indexOf("TT") - 2;
					break;
				}
				count++;	
			}
		}
			catch (Exception e)
			{
				
			}
			return startIndex;
	}

	@Override
	public int getLineToRemoveEndIndex() {
		String line = null;
		File inputFile = new File("");
		int endIndex = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			FileWriter fstream = new FileWriter("", true);
			int count = 0;

			// repeat until all lines is read
			while ((line = reader.readLine()) != null)
			{
				if (line.toString().contains("USER"))
				{
					endIndex = line.toString().indexOf(" S ") + 3;
					break;
				}
				count++;	
			}
		}
			catch (Exception e)
			{
				
			}
			return endIndex;
	}

	@Override
	public int firstLineCSVIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
