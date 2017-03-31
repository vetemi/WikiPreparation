package de.vetemi.wikiprocessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class WikiIO {

	private BufferedWriter bWriterOutput;
	private File[] filesInputArray;
	private List<String> stopWordList;
	private File stopWordFile;

	public WikiIO(File fileInput, File fileOutput) {
		// prepare output
		try {
			bWriterOutput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), "UTF-8"));
			if (fileOutput.exists()) {
				fileOutput.delete();
			}
			fileOutput.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// list all files within input folder
		filesInputArray = fileInput.listFiles();

		stopWordFile = new File("resources/stopwords_de.txt");
		importStopwords();

	}

	public void importStopwords() {
		System.out.println("Start importing word stopword file:" + stopWordFile.getName());

		try {
			BufferedReader bReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(stopWordFile), "UTF-8"));

			if (stopWordList == null) {
				stopWordList = new ArrayList<String>();
			}
			while (bReader.ready()) {
				String line = bReader.readLine();
				if (!line.isEmpty()) {
					stopWordList.add(line);
				}
			}
			bReader.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Finished importing word stopword file. Size:" + stopWordList.size());

	}

	public void writeContentInFile(String name, String content) {
		try {
			bWriterOutput.write(name);
			bWriterOutput.write("\t");
			bWriterOutput.write(content);
			bWriterOutput.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			bWriterOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public List<String> getStopWordList() {
		return stopWordList;
	}

	public void setStopWordList(List<String> stopWordList) {
		this.stopWordList = stopWordList;
	}

	public File getStopWordFile() {
		return stopWordFile;
	}

	public void setStopWordFile(File stopWordFile) {
		this.stopWordFile = stopWordFile;
	}

	public BufferedWriter getbWriterOutput() {
		return bWriterOutput;
	}

	public void setbWriterOutput(BufferedWriter bWriterOutput) {
		this.bWriterOutput = bWriterOutput;
	}

	public File[] getFilesInputArray() {
		return filesInputArray;
	}

	public void setFilesInputArray(File[] filesInputArray) {
		this.filesInputArray = filesInputArray;
	}
}
