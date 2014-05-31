package FilesManagment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Folder extends File
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File folder;
	static int maxFiles = 10;
	List<String> fileNames = new ArrayList<String>();
	List<String> fullFileNames = new ArrayList<String>();
	String folderPath;
	public List<File> files =  new ArrayList<File>();
	
	
	public Folder(String folderPath) 
	{
		super(folderPath);
		this.folder = new File(folderPath);
		folder.mkdir();
		this.folderPath = folderPath;
	}
	
	public void checkFolderSize()
	{
		while (folder.listFiles().length  >= maxFiles)
		{			
			File[] tmp = folder.listFiles();
			removeDirectory(tmp[0]);
		
		}
	}
	
	
	public static boolean removeDirectory(File directory) {

		  // System.out.println("removeDirectory " + directory);
		
		

		  if (directory == null)
		    return false;
		  if (!directory.exists())
		    return true;
		  if (!directory.isDirectory())
		    return false;

		  String[] list = directory.list();

		  // Some JVMs return null for File.list() when the
		  // directory is empty.
		  if (list != null) {
		    for (int i = 0; i < list.length; i++) {
		      File entry = new File(directory, list[i]);

		      //        System.out.println("\tremoving entry " + entry);

		      if (entry.isDirectory())
		      {
		        if (!removeDirectory(entry))
		          return false;
		      }
		      else
		      {
		        if (!entry.delete())
		          return false;
		      }
		    }
		  }

		  return directory.delete();
		}


	public void addFileToFolder(String fileName) throws IOException
	{
		File tmpFile = new File(folder.getAbsolutePath() + "\\" + fileName);
		fileNames.add(fileName);
		fullFileNames.add(folder.getAbsolutePath() + "\\" + fileName);
		files.add(tmpFile);
		FilesManagment fm = new FileManagmentOperations();
		fm.renameFile(fileName, folder.getAbsolutePath() + "\\" + fileName);
	}
	
	

	public List<String> getFullFileNames() {
		return fullFileNames;
	}


	public void setFullFileNames(List<String> fullFileNames) {
		this.fullFileNames = fullFileNames;
	}


	public String getFolderPath() {
		return folderPath;
	}


	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}


	public List<String> getFileNames() {
		return fileNames;
	}


	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}


	public int getMaxFiles() {
		return maxFiles;
	}


	public void setMaxFiles(int maxFiles) {
		this.maxFiles = maxFiles;
	}
	
	
	public List<String> createFullFileNames()
	{
		for (int i = 0 ; i < fileNames.size() ; i++)
		{
			fullFileNames.add(folderPath + "\\" + fileNames.get(i));
			files.add(new File(fullFileNames.get(i)));
		}
		return fullFileNames;
	}
	
}
