package platformSpecific;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SOLARIS implements OSType 
{
	
	final String OSType = "SunOS";
	
	@Override
	public int getCSVStartline() {
		return 0;
	}

	@Override
	public int getCSVEndLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLineToRemoveStartIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLineToRemoveEndIndex() {
		// TODO Auto-generated method stub
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
					endIndex = line.toString().indexOf("ST") + 2;
					break;
				}
				count++;	
			}
		}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return endIndex;
	}

	@Override
	public int firstLineCSVIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
