/*
 * 
 */
package FilesManagment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Folder.
 */
public class Folder extends File
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The folder. */
	File folder;
	
	/** The max files. */
	static int maxFiles = 10;
	
	/** The file names. */
	List<String> fileNames = new ArrayList<String>();
	
	/** The full file names. */
	List<String> fullFileNames = new ArrayList<String>();
	
	/** The folder path. */
	String folderPath;
	
	/** The files. */
	public List<File> files =  new ArrayList<File>();
	
	
	/**
	 * Instantiates a new folder.
	 * 
	 * @param folderPath
	 *            the folder path
	 */
	public Folder(String folderPath) 
	{
		super(folderPath);
		this.folder = new File(folderPath);
		folder.mkdir();
		this.folderPath = folderPath;
	}
	
	/**
	 * Check folder size.
	 */
	public void checkFolderSize()
	{
		while (folder.listFiles().length  >= maxFiles)
		{			
			File[] tmp = folder.listFiles();
			removeDirectory(tmp[0]);
		
		}
	}
	
	
	/**
	 * Removes the directory.
	 * 
	 * @param directory
	 *            the directory
	 * @return true, if successful
	 */
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


	/**
	 * Adds the file to folder.
	 * 
	 * @param fileName
	 *            the file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void addFileToFolder(String fileName) throws IOException
	{
		File tmpFile = new File(folder.getAbsolutePath() + "\\" + fileName);
		fileNames.add(fileName);
		fullFileNames.add(folder.getAbsolutePath() + "\\" + fileName);
		files.add(tmpFile);
		FilesManagment fm = new FileManagmentOperations();
		fm.renameFile(fileName, folder.getAbsolutePath() + "\\" + fileName);
	}
	
	

	/**
	 * Gets the full file names.
	 * 
	 * @return the full file names
	 */
	public List<String> getFullFileNames() {
		return fullFileNames;
	}


	/**
	 * Sets the full file names.
	 * 
	 * @param fullFileNames
	 *            the new full file names
	 */
	public void setFullFileNames(List<String> fullFileNames) {
		this.fullFileNames = fullFileNames;
	}


	/**
	 * Gets the folder path.
	 * 
	 * @return the folder path
	 */
	public String getFolderPath() {
		return folderPath;
	}


	/**
	 * Sets the folder path.
	 * 
	 * @param folderPath
	 *            the new folder path
	 */
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}


	/**
	 * Gets the file names.
	 * 
	 * @return the file names
	 */
	public List<String> getFileNames() {
		return fileNames;
	}


	/**
	 * Sets the file names.
	 * 
	 * @param fileNames
	 *            the new file names
	 */
	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}


	/**
	 * Gets the max files.
	 * 
	 * @return the max files
	 */
	public int getMaxFiles() {
		return maxFiles;
	}


	/**
	 * Sets the max files.
	 * 
	 * @param maxFiles
	 *            the new max files
	 */
	public void setMaxFiles(int maxFiles) {
		Folder.maxFiles = maxFiles;
	}
	
	
	/**
	 * Creates the full file names.
	 * 
	 * @return the list
	 */
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
