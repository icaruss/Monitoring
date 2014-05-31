package FilesManagment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public abstract class FilesManagment 
{
	protected static String fileName;
	protected static String filePath;
	protected static List<String> fileNames = new ArrayList<String>();
	
	public FilesManagment(String _fileName) 
	{
		super();
		FilesManagment.fileName = _fileName;
	}




	public FilesManagment() 
	{
		
	}




	public String getFileName() {
		return fileName;
	}



	public String getFilePath() {
		return filePath;
	}

	public List<String> getFileNames() {
		return fileNames;
	}



	public int getTotalLinesNum(String inputFileName) 
	{
		File inputFile = new File(inputFileName);
		int linesNum = 0;
		BufferedReader reader = null;
		try 
		{
			reader = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			while (reader.readLine() != null)
				linesNum++;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return linesNum;
	}
	
	public void renameFile(String oldFileName, String newFileName) throws IOException
	{
		File file = new File(oldFileName);
	
	    // File (or directory) with new name
	    File file2 = new File(newFileName);
	    if(file2.exists()) throw new java.io.IOException("file exists");
	
	    // Rename file (or directory)
	    boolean success = file.renameTo(file2);
	    if (!success) {
	    }
	        // File was not successfully renamed
    }
	
	
	// Insert at the beggining of the line
	public void insertStringToLine(String[] dates, String fileName) throws IOException
	{
		BufferedReader reader = null;
		BufferedWriter writer = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
				reader = new BufferedReader(new FileReader(fileName));
				String tmp;
				while ((tmp = reader.readLine()) != null)
					list.add(tmp);
				reader.close();

			for (int i = 0; i < dates.length + 1 ; i++) 
			{
				String tmp2 = null;
				if (i == 0)
				{
					if (fileName.startsWith("vmstat"))
					{
						tmp2 =list.get(i);
						list.remove(i);
						list.add(i, tmp2);
						
					}
					else
					{
						tmp2 = "," + list.get(i);
						list.remove(i);
						list.add(i, tmp2);
					}
				}
				else
				{
					if (fileName.startsWith("vmstat"))
					{
						tmp2 = dates[i - 1] + list.get(i);
						list.remove(i);
						list.add(i, tmp2);
					}
					else
					{
						tmp2 = dates[i - 1] + "," + list.get(i);
						list.remove(i);
						list.add(i, tmp2);
					}
				}

			}

			writer = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < list.size(); i++)
				writer.write(list.get(i) + "\r\n");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			reader.close();
			writer.close();
		}
	}
	
	public void removeFirstLine(String fileName) throws IOException 
	{  
	    RandomAccessFile raf = new RandomAccessFile(fileName, "rw");          
	     //Initial write position                                             
	    long writePosition = raf.getFilePointer();                            
	    raf.readLine();                                                       
	    // Shift the next lines upwards.                                      
	    long readPosition = raf.getFilePointer();                             

	    byte[] buff = new byte[1024];                                         
	    int n;                                                                
	    while (-1 != (n = raf.read(buff))) {                                  
	        raf.seek(writePosition);                                          
	        raf.write(buff, 0, n);                                            
	        readPosition += n;                                                
	        writePosition += n;                                               
	        raf.seek(readPosition);                                           
	    }                                                                     
	    raf.setLength(writePosition);                                         
	    raf.close();                                                          
	}      
	
	public String extractFileNameFromPath(String FullFileName) 
	{
		String[] stringArr = FullFileName.split("\\\\");
		String fileName = stringArr[stringArr.length - 1];
		return fileName;
		
	}
	
	public void removeLineFromFile(String file, String lineToRemove) throws IOException
	{

		File inputFile = new File(file);
		File tempFile = new File(file + ".tmp");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while ((currentLine = reader.readLine()) != null) 
		{
			// trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			if (trimmedLine.equals(lineToRemove))
				continue;
			writer.write(currentLine);
			writer.newLine();
			writer.flush();
		}
		writer.close();
		reader.close();
		inputFile.delete();
		boolean successful = tempFile.renameTo(inputFile);
		if (successful)
			System.out.println(lineToRemove + " has been removed");
		else
			System.out.println(lineToRemove + " has NOT been removed");
	}
	
	
	public void insertTextToFile(int index, String forInsert, String fileName)
			throws IOException {

		BufferedReader reader = null;
		BufferedWriter writer = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			reader = new BufferedReader(new FileReader(fileName));
			String tmp;
			while ((tmp = reader.readLine()) != null)
				list.add(tmp);
			reader.close();

			list.add(index, forInsert);
			// list.add(0, "Start Text");
			// list.add("End Text");

			writer = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < list.size(); i++)
				writer.write(list.get(i) + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
			writer.close();
		}
	}
	
	public void deleteFile(String FileName, String FilePath)
	{
		File file = new File(FilePath + "\\\\" + FilePath);
		file.delete();

	}
	
	public void createFile(String FileName)
	{
		File file = new File(FileName);
		file.delete();

	}
}

