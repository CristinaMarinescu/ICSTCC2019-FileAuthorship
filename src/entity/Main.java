package entity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) throws IOException {
		String repositoryLocation = "/Users/cristina/Desktop/PAPER_CMAAT/SYSTEMS/react/";
		String CodeMaatAuthorsNoFileName = "react-authors.csv";
		String CodeMaatMainDevFileName = "react-authors-mainDev.csv";
		String blameAuthorsNoFileName = "react-authors-blame.csv";
		String blameMainDevFileName = "react-authors-mainDev-blame.csv";

		ProcessAuthorsNumber processAuthors = new ProcessAuthorsNumber(repositoryLocation, CodeMaatAuthorsNoFileName);
		String resultPA = processAuthors.processFile();
		PrintStream psPA = new PrintStream(new FileOutputStream(repositoryLocation + blameAuthorsNoFileName));
		psPA.print(resultPA);
		psPA.close();
		
		ProcessMainDev processMainDev = new ProcessMainDev(repositoryLocation, CodeMaatMainDevFileName);
		String resultPMD = processMainDev.processFile();
		PrintStream psPMD = new PrintStream(new FileOutputStream(repositoryLocation + blameMainDevFileName));
		psPMD.print(resultPMD);
		psPMD.close();
	}
}
