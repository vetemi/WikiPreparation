package de.vetemi.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Stream;

public class TSVReader {

	public  HashMap<String, String> readTSV(File fileInput) {

		int count = 0;
		long start = System.currentTimeMillis();
		System.out.println("Start");
		String line;
		String[] lineSplit;
		HashMap<String, String> wikiMap = new HashMap<>();
		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileInput), "UTF-8"));
			while (bReader.ready()) {
				line = bReader.readLine();
				if (!line.isEmpty()) {
					lineSplit = line.split("\t");
					if (lineSplit.length == 2) {
						wikiMap.put(lineSplit[0], lineSplit[1]);
					}
				}
			}
			bReader.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		long end = System.currentTimeMillis();

		System.out.println("Size: " + 	wikiMap.size());
		System.out.println("Runtime: " + (end - start));
		return wikiMap;
	}

}
