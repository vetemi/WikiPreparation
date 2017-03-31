package de.vetemi.test.wikiprocessor;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;

import de.vetemi.filereader.TSVReader;
import de.vetemi.wikiprocessor.WikiIO;

public class WikiIOTest {

	@Test
	public void testImportStopwords() {
		WikiIO wikiIO = new WikiIO(new File("resources/test/test_input.txt"),
				new File("resources/test/test_input.txt"));
		
		assertTrue("stopword list contains entries", wikiIO.getStopWordList().size() > 0);
	}

	@Test
	public void testWriteContentInFile() {
		File input = new File("resources/test/test_input.txt");
		File output = new File("resources/test/test_output.txt");
		WikiIO wikiIO = new WikiIO(input,output);
		
		String testName = "test";
		String testContent = "das ist ein testinhalt";		
		wikiIO.writeContentInFile(testName, testContent);
		wikiIO.close();

		TSVReader tsvReader = new TSVReader();
		HashMap<String,String> result = tsvReader.readTSV(output);
		
		assertTrue("Written key is read", result.containsKey(testName));
		assertTrue("Content is as defined", result.get(testName).equals(testContent));
		
		
		
		
	}

}
