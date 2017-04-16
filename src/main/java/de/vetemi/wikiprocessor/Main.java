package de.vetemi.wikiprocessor;

import java.io.File;

public class Main {

	public static void main(String[] args) {

		File filesInput = new File("C:/Users/vetemi/Downloads/WikiOneFilePerArticle");
		File fileOutput = new File("resources/GermanWikiArticles.tsv");

		Processor processor = new Processor(filesInput, fileOutput);
		processor.proccess();
	}
}
