package de.vetemi.filereader;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		File fileInput = new File("C:/Users/vetemi/Documents/tmp_workspace/WikiPreparation/resources/GermanWikiArticles.tsv");
		
		TSVReader tsvReader = new TSVReader();
		
		tsvReader.readTSV(fileInput);
		
//		StringBuilder sb = new StringBuilder();
//		if (sb.toString() == null) {
//			System.out.println("is null");
//		} else if (sb.toString().isEmpty()) {
//			System.out.println("empty string");
//		}

	}

}
