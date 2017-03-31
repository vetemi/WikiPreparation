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
		System.out.println("---- Start reading ----");

		String content;
		Set<String> featuredNames;
		for (File f : filewriter.getFilesInputArray()) {
			content = "";
			content = getContentFromFile(f, 1);

			content = textprocessor.prepareContent(content);
			featuredNames = textprocessor.featureName(f.getName());
			putInWikimap(f.getName(), content);

			for (String name : featuredNames) {
				if(!name.equals(f.getName())) {
					putInWikimap(name, content);
				}
			}
		}
		System.out.println("--- Start writing ---");
		for (Map.Entry<String, String> entry : wikiMap.entrySet())
		{
			filewriter.writeContentInFile(entry.getKey(), entry.getValue());
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
