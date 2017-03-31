package de.vetemi.wikiprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Tokenizer {

	private SentenceDetectorME sentencer;
	private TokenizerME tokenizer;

	public Tokenizer() {
		try {
			this.sentencer = new SentenceDetectorME(new SentenceModel(new File("resources/models/opennlp/de-sentence.bin")));
			this.tokenizer = new TokenizerME(new TokenizerModel(new File("resources/models/opennlp/de-token.bin")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> tokenize(String string) {
		List<String> result = new ArrayList<String>();		
		Pattern punctuation = Pattern.compile("[,.;:!?&\\)(]");

		for (String sentence : sentencer.sentDetect(string)) {
			for (String token : tokenizer.tokenize(sentence)) {
				 token = token.toLowerCase();
				// strip punctuation
				if (!punctuation.matcher(token).find()) {

					Set<String> zwischenresult = new HashSet<>();
					zwischenresult.add(token);
					if (token.contains("/") && !token.equals("/") && !token.equals("//")) {
						Set<String> tmpresult = new HashSet<>();
						for (String tok : zwischenresult) {
							String[] split = tok.split("/");
							int len = split.length;
							// e.g. start/amadeus/merlin
							for (int i = 0; i < len; i++) {
								// e.g. Lehrer/in no own Token
								if (!split[i].startsWith("in") && (split[i].length() > 1)) {
									tmpresult.add(split[i]);
								}
							}
						}
						zwischenresult.clear();
						zwischenresult = tmpresult;
					}
					
					if (token.contains("-") && !token.equals("-")) {
						Set<String> tmpresult2 = new HashSet<>();
						for (String tok : zwischenresult) {
							String[] split = tok.split("-");
							int len = split.length;
							for (int i = 0; i < len; i++) {
								if (split[i].length() > 1) {
									tmpresult2.add(split[i]);
								}
							}
						}
						zwischenresult.clear();
						zwischenresult = tmpresult2;
					}
					
					for (String tok : zwischenresult) {
						if (!tok.isEmpty()) {
							result.add(tok);
						}
					}

				}
			}
		}

		return result;

	}
}

