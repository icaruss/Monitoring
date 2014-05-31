package Clix;


import java.io.IOException;
import java.util.logging.Level;

import log.MonLogger;

import FilesManagment.Converter;

public class ParseClix extends Converter
{
	
	
	
	public String parseClixOut(String fileName) 
	{
		String xls = null;
		try 
		{
			String csvFileName = fileName.split("\\.")[0] + ".csv";
			String lineToRemove = "Server Time, Thread Id, State, User, Protocol, Command, Locks, Wait Time ms, Run Time ms, Start Time, Connection, Repository, Remote Host";
			removeLineFromFile(fileName, lineToRemove);
			removeLineFromFile(fileName, "");
			removeFirstLine(fileName);
			removeFirstLine(fileName);	
			insertTextToFile(0, lineToRemove, fileName);
			
			renameFile(fileName, csvFileName);
			xls = convertCSVToExcel(csvFileName);
		}
		catch(IOException e)
		{
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		
		return xls;
		
	}

}


