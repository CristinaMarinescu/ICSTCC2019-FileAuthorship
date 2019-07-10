package entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ProcessAuthorsNumber {
	private String CodeMaatAuthorsNoFileName;
	private String repositoryLocation;
	
	private Entity processBlame;
	
	public ProcessAuthorsNumber(String repositoryLocation, String CodeMaatAuthorsNoFileName) {
		this.repositoryLocation = repositoryLocation;
		this.CodeMaatAuthorsNoFileName = CodeMaatAuthorsNoFileName;	
		this.processBlame = new Entity(repositoryLocation);
	}
	
	public String processFile() {
		String fileName = this.repositoryLocation + this.CodeMaatAuthorsNoFileName;
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			StringBuffer sb = new StringBuffer();
			String line;
		
			StringBuilder results = new StringBuilder("entity,CodeMaatAuthors,BlameAuthors,revisions\n"); 
			while ((line = br.readLine()) != null) {
				String[] fileData = line.split(",");
				if (Entity.isTextFile(fileData[0])) {
					this.processBlame.setFileName(fileData[0]);
					HashMap<String, Integer> authors = this.processBlame.retrieveInformation();
					Integer blameNoAuthors = (authors == null) ? -1 : authors.keySet().size();
					results.append(fileData[0] + ',' + fileData[1] + ',' + blameNoAuthors + ',' + fileData[2] + "\n");
				}
			}
			return results.toString();
		}
		catch (IOException e) {
			return "IOException";
		}
	}
	
}
