package de.vetemi.test.wikiprocessor;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import de.vetemi.wikiprocessor.TextProcessor;
import de.vetemi.wikiprocessor.WikiIO;

public class TextProcessorTest {
	
	private static WikiIO wikiIO;
	private static TextProcessor textProcessor;

	@BeforeClass
	public static void setUp() throws Exception {
		File input = new File("resources/test/test_input.txt");
		File output = new File("resources/test/test_output.txt");
		wikiIO = new WikiIO(input, output);
		textProcessor = new TextProcessor(wikiIO.getStopWordList());
	}

	@Test
	public void testFeatureName() {
		String testContent = "lesen/machen. bücher! der- hat? gemacht.";

		List<String> contentReference = new ArrayList<>();
		contentReference.add("les");
		contentReference.add("mach");
		contentReference.add("buch");
		contentReference.add("der");
		contentReference.add("haben");
		contentReference.add("machen");
		contentReference.add("lesen");
		contentReference.add("gemacht");
		contentReference.add("hat");
		
		Set<String> result = textProcessor.featureName(testContent);
		
		for (String word : result) {
			assertTrue("Contains in reference", contentReference.contains(word));
		}
		assertTrue("Same size", contentReference.size() == result.size());
	}

	@Test
	public void testStem() {
		List<String> testContent = new ArrayList<>();
		testContent.add("lesen");
		testContent.add("machen");
		testContent.add("bücher");

		List<String> contentReference = new ArrayList<>();
		contentReference.add("les");
		contentReference.add("mach");
		contentReference.add("buch");
		
		List<String> result = textProcessor.stem(testContent);
		
		for (String word : result) {
			assertTrue("Contains in reference", contentReference.contains(word));
		}
		assertTrue("Same size", contentReference.size() == result.size());
	}
	
	@Test
	public void testLemmatize() {
		List<String> testContent = new ArrayList<>();
		testContent.add("der");
		testContent.add("hat");
		testContent.add("gemacht");

		List<String> contentReference = new ArrayList<>();
		contentReference.add("der");
		contentReference.add("haben");
		contentReference.add("machen");
		
		List<String> result = textProcessor.lemmatize(testContent);
		
		for (String word : result) {
			assertTrue("Contains in reference", contentReference.contains(word));
		}
		assertTrue("Same size", contentReference.size() == result.size());
	}

	@Test
	public void testPrepareContent() {
		String testContent = "Satz"+" \t"+ "enthält" + " \t" + "tabs.";
		String resultReference = "satz enthält tabs";
		
		String result = textProcessor.prepareContent(testContent);
		
		assertTrue("Same String", resultReference.equals(result));		
	}

	@Test
	public void testFilterStopWords() {
		String testContent = "Das sind fast nur Stopwörter";
		String resultReference = "fast stopwörter";
		
		String result = textProcessor.filterStopWords(testContent);
		
		assertTrue("Same String", resultReference.equals(result));
	}

}
