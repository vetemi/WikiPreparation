package de.vetemi.wikiprocessor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Processor {

	private static final int MAX_DEPTHS = 10;
	private WikiIO filewriter;
	private TextProcessor textprocessor;
	private HashMap<String, String> wikiMap;

	public Processor(File filesInput, File fileOutput) {
		wikiMap = new HashMap<>();
		filewriter = new WikiIO(filesInput, fileOutput);
		textprocessor = new TextProcessor(filewriter.getStopWordList());
	}

	public void proccess() {
		System.out.println("---- Start reading/wirting ----");

		String content;
		String name;
		Set<String> featuredNames;
		for (File f : filewriter.getFilesInputArray()) {
			content = getContentFromFile(f, 1);
			name = f.getName().replace(".txt", "");

			content = textprocessor.prepareContent(content);
			featuredNames = textprocessor.featureName(name);
			filewriter.writeContentInFile(name, content);

			for (String featuredName : featuredNames) {
				if(!featuredName.equals(name)) {
					filewriter.writeContentInFile(featuredName, content);
				}
			}
		}
		filewriter.close();
		System.out.println("--- END OF PROCESS ---");
	}

	public void putInWikimap(String name, String content) {
		if (wikiMap.containsKey(name)) {
			content += " ";
			content += wikiMap.get(name);
		}
		wikiMap.put(name, content);
	}

	public String getContentFromFile(File file, int depth) {

		if (depth == MAX_DEPTHS) {
			return "";
		}

		StringBuilder contentSB = new StringBuilder();

		if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			for (File f : subFiles) {
				contentSB.append(getContentFromFile(f, depth++));
			}
		} else {

			try {
				String currentContent = FileUtils.readFileToString(file, "UTF-8");
				if (!currentContent.isEmpty()) {
					contentSB = new StringBuilder(currentContent);
					contentSB.append(" ");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return contentSB.toString();
	}
	
	public HashMap<String, String> getWikiMap() {
		return wikiMap;
	}

	public void setWikiMap(HashMap<String, String> wikiMap) {
		this.wikiMap = wikiMap;
	}

}
