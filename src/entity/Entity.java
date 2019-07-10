package entity;

import java.io.IOException;
import java.io.*;
import java.util.*;

public class Entity {
	private String fileName;
	private String repository;
	private HashMap<String, Integer> authors = new HashMap<String, Integer>();
	
	public Entity(String repository, String fileName) {
		this.fileName = fileName;
		this.repository = repository;
	}
	
	public Entity(String repository) { 
		this.repository = repository;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public HashMap<String, Integer> retrieveInformation() {
		this.authors = new HashMap<String, Integer>();
		String[] arguments = new String[] {"git", "blame", "--date=format:*", repository + fileName};
		boolean blameHasContent = false;
		try {
			ProcessBuilder proc = new ProcessBuilder(arguments);
			proc.directory(new File(repository));
			Process p = proc.start();
		
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			
			while ((line = br.readLine()) != null) {
				blameHasContent = true;
				try {
					int beginIndex = line.indexOf("(");
					int endIndex = line.indexOf(")");
					if (beginIndex >= 0 && endIndex > 0) {
						String stringContainingAuthor = line.substring(beginIndex + 1, endIndex);
			
						endIndex = stringContainingAuthor.indexOf("*");
						if (endIndex > 0) {
							String author = stringContainingAuthor.substring(0, endIndex).trim();
							this.addLine(author);
						}	
					}
				}
				catch (Exception e) {
					System.err.println("GIT BLAME process line error: " + repository + fileName);
					e.printStackTrace();
					return null;
				}
			}
		}
		catch(IOException e) {
			System.err.println("GIT BLAME file not found: " + repository + fileName);
			e.printStackTrace();
			return null;
		}
		if(!blameHasContent) {
			return null;
		}
		return this.authors;
	}
	
	private void addLine(String author) {
		if (this.authors.containsKey(author)) {
			int noLines = this.authors.get(author);
			noLines++;
			this.authors.put(author, noLines);
		}
		else {
			this.authors.put(author, 1);
		}
	}
	
	public static boolean isTextFile(String fileName) {
		fileName = fileName.toLowerCase();
		return !(fileName.endsWith(".jpg") || fileName.endsWith(".png") ||
			fileName.endsWith(".gif") || fileName.endsWith(".ico") ||
			fileName.endsWith(".jpeg") || fileName.endsWith(".mp4") || 
			fileName.endsWith(".jar") || fileName.endsWith(".ttf") || 
			fileName.endsWith(".woff") || fileName.endsWith(".woff2") || 
			fileName.endsWith(".eot") || fileName.endsWith(".pfx") || 
			fileName.endsWith(".dll") || fileName.endsWith(".otf") || 
			fileName.endsWith(".gz") || fileName.endsWith(".pdf") || 
			fileName.endsWith(".psd") || fileName.endsWith(".tif") || 
			fileName.endsWith(".tgz") || fileName.endsWith(".zip") || 
			fileName.endsWith(".zlib") || fileName.endsWith(".fm5") || 
			fileName.endsWith(".beam") || fileName.endsWith(".bz2") || 
			fileName.endsWith(".jks") || fileName.endsWith(".p12") || 
			fileName.endsWith(".epub") || fileName.endsWith(".vsdk") || 
			fileName.endsWith(".docx") || fileName.endsWith(".doc") || 
			fileName.endsWith(".exe") || 
			fileName.endsWith(".mov")|| fileName.endsWith(".dia"));
	}
}
