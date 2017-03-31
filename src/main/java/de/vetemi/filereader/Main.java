package de.vetemi.filereader;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		File fileInput = new File("C:/Users/vetemi/Desktop/WikiArticles.tsv");
		
		TSVReader tsvReader = new TSVReader();
		
		tsvReader.readTSV(fileInput);

	}

}
