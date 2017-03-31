package de.vetemi.wikiprocessor;

import java.io.File;

public class Main {

	public static void main(String[] args) {

		File filesInput = new File("C:/Users/vetemi/Downloads/WikiOneFilePerArticle/WikiOneFilePerArticle");
		File fileOutput = new File("C:/Users/vetemi/Desktop/WikiArticles.tsv");

		Processor processor = new Processor(filesInput, fileOutput);
		processor.proccess();
	}
}
