package de.vetemi.test.wikiprocessor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.vetemi.wikiprocessor.Tokenizer;

public class TokenizerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTokenize() {
		Tokenizer tokenizer = new Tokenizer();
		String testSentence = "Bitte tokenisieren/aufsplitten. Wörter? Satz!"+"\n"+"Zeilenumbruch";
		List<String> tokenizedReference = new ArrayList<>();
		tokenizedReference.add("bitte");
		tokenizedReference.add("tokenisieren");
		tokenizedReference.add("aufsplitten");
		tokenizedReference.add("wörter");
		tokenizedReference.add("satz");
		tokenizedReference.add("zeilenumbruch");
		
		List<String> tokenizedWords = tokenizer.tokenize(testSentence);
		
		for (String word : tokenizedWords) {
			assertTrue("Contains in reference", tokenizedReference.contains(word));
		}
		assertTrue("Same size", tokenizedReference.size() == tokenizedWords.size());
		
	}

}
