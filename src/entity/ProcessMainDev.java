package entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ProcessMainDev {
	private String CodeMaatMainDevFileName;
	private String repositoryLocation;
	
	private Entity processBlame;
	
	public ProcessMainDev(String repositoryLocation, String CodeMaatMainDevFileName) {
		this.repositoryLocation = repositoryLocation;
		this.CodeMaatMainDevFileName = CodeMaatMainDevFileName;	
		this.processBlame = new Entity(repositoryLocation);
	}
	
	public String processFile() {
		String fileName = this.repositoryLocation + this.CodeMaatMainDevFileName;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			StringBuffer sb = new StringBuffer();
			String line;
		
			StringBuilder results = new StringBuilder("entity,mainDev,added,totalAdded,ownership,blameMainDev,blameAdded,blameTotalAdded,blameOwnership\n"); 
			while ((line = br.readLine()) != null) {
				String[] fileData = line.split(",");
				if (Entity.isTextFile(fileData[0])) {
					if (fileData.length == 5) {
						this.processBlame.setFileName(fileData[0]);
						HashMap<String, Integer> authors = this.processBlame.retrieveInformation();
						String blame_data = this.processMainDev(authors);
							results.append(fileData[0] + ',' + fileData[1] + ',' + fileData[2] + ',' + fileData[3] + ',' + fileData[4] + ",");
							results.append(blame_data);
							results.append("\n");
					}
				}
			}
			return results.toString();
		}
		catch (IOException e) {
			return "IOException";
		}
	}
	
	private String processMainDev(HashMap<String, Integer> authors) {
		if (authors == null) {
			return "-1,-1,-1,-1";
		}
		String result = "";
		String mainDev = "";
		int mainDevAddedLines = -1, totalLines = 0;
		double ownership;
		if (authors.keySet().size() == 0) {
			return "-1,-1,-1,-1";
		}
		for (String author: authors.keySet()) {
			int addedLines = authors.get(author);
			totalLines += addedLines;
			if (mainDevAddedLines < addedLines) {
				mainDev = author;
				mainDevAddedLines = addedLines;
			}
		}
		ownership = (mainDevAddedLines * 1.0) / totalLines;
		return mainDev + ',' + mainDevAddedLines + ',' + totalLines + ',' + ownership;
	}
}
