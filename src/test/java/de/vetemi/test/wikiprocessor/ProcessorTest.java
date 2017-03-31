package de.vetemi.test.wikiprocessor;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import de.vetemi.filereader.TSVReader;
import de.vetemi.wikiprocessor.Processor;

public class ProcessorTest {

	private static Processor processor;
	private static File input;
	private static File output;

	@Before
	public void setUp() throws Exception {
		input = new File("resources/test/WikiFileTest");
		output = new File("resources/test/test_output.txt");
		processor = new Processor(input, output);
	}

	@Test
	public void testProccess() {
		processor.proccess();
		TSVReader tsvReader = new TSVReader();
		HashMap<String,String> result = tsvReader.readTSV(output);
		
		assertTrue("contains as elements as files defined", result.size() == 6);
		assertTrue("Check content hase", result.get("hase").equals("3 4"));
		assertTrue("Check content fuchs", result.get("fuchs").equals("1"));
		assertTrue("Check content maus", result.get("maus").equals("2"));
	}

	@Test
	public void testPutInWikimap() {

		String name1 = "name1";
		String content1 = "content1";

		String name2 = "name2";
		String content2 = "content2";

		String name3 = "name1";
		String content3 = "content3";

		String referenceContent = content3 + " " + content1;

		processor.putInWikimap(name1, content1);
		processor.putInWikimap(name2, content2);
		processor.putInWikimap(name3, content3);

		HashMap<String, String> result = processor.getWikiMap();

		assertTrue("Does not contain more than 2 elements", result.size() == 2);
		assertTrue("Content of name1 is defined as", result.get(name1).equals(referenceContent));
	}

	@Test
	public void testGetContentFromFile() {
		String reference = "1 3 4 2 ";
		String result = processor.getContentFromFile(new File("resources/test/WikiFileTest"), 1);

		assertTrue("Same content", reference.equals(result));

	}

}
