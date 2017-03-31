package de.vetemi.wikiprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.tartarus.snowball.ext.germanStemmer;

import is2.data.SentenceData09;
import is2.lemmatizer.Lemmatizer;

public class TextProcessor {

	private Tokenizer tokenizer;
	private germanStemmer stemmer;
	private Lemmatizer lemmatizer;
	private List<String> removalCharacters;
	private List<String> stopWords;

	public TextProcessor(List<String> stopWords) {
		super();
		this.stopWords = stopWords;
		tokenizer = new Tokenizer();
		stemmer = new germanStemmer();
		lemmatizer = new Lemmatizer("resources/models/matetools/lemma-ger-3.6.model");

		// prepare Characters which should be removed from content
		removalCharacters = new ArrayList<>();
		removalCharacters.add("\t");

	}

	public Set<String> featureName(String name) {
		List<String> tokenizedName = tokenizer.tokenize(name);

		Set<String> featuredList = new HashSet<>();

		featuredList.addAll(stem(tokenizedName));
		featuredList.addAll(lemmatize(tokenizedName));

		return featuredList;
	}

	public List<String> stem(List<String> wordTokens) {
		List<String> stemmedWords = new ArrayList<>();
		for (String word : wordTokens) {
			try {
				this.stemmer.setCurrent(word);
				if (this.stemmer.stem()) {
					word = this.stemmer.getCurrent();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			stemmedWords.add(word);
		}
		return stemmedWords;
	}

	public List<String> lemmatize(List<String> wordTokens) {
		List<String> sentenceAsList = new ArrayList<>();
		if (!wordTokens.isEmpty()) {
			if ((wordTokens.size() == 1) && (wordTokens.get(0).isEmpty())) {

			} else {
				SentenceData09 sentence = new SentenceData09();
				wordTokens.add(0, "<root>");
				sentence.init(wordTokens.toArray(new String[0]));
				sentence = lemmatizer.apply(sentence);

				sentenceAsList = Arrays.asList(Arrays.copyOfRange(sentence.plemmas, 1, sentence.plemmas.length))
						.stream().filter(e -> !e.equals("--")).collect(Collectors.toList());

			}
		}
		return sentenceAsList;
	}

	public String prepareContent(String content) {
		for (String removalChar : removalCharacters) {
			content = content.replace(removalChar, " ");
		}
		content = filterStopWords(content);
		return content;
	}

	public String filterStopWords(String content) {
		List<String> tokenizedContent = tokenizer.tokenize(content);
		tokenizedContent.removeAll(stopWords);
		StringBuilder sb = new StringBuilder();
		if (!tokenizedContent.isEmpty()) {
			for (String word : tokenizedContent) {
				sb.append(word);
				sb.append(" ");
			}
			return sb.toString().trim();
		}
		return "";
	}

}
