package kidneyAnalysesAgents.Helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileAdministrator {
	private String fileNameString;

	public FileAdministrator() {
		fileNameString = "urineAnalyses.csv";
	}

	public FileAdministrator(String fileName) throws IOException {
		this.fileNameString = fileName;
		try {
			File saveFile = new File(fileName);
			if (saveFile.createNewFile()) {
				System.out.println("File created: " + saveFile.getName());
			}
		} catch (IOException e) {
			System.out.println("Error while opening the file.");
			e.printStackTrace();
		}
	}

	public void AddNewAnalysis(Analysis a) {
		try {
			FileWriter myWriter = new FileWriter(fileNameString, true);
			myWriter.write(a.convertStringForFile());
			myWriter.close();
			System.out.println("I wrote the new analyses into urineAnalyses.csv.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Analysis> GetAllAnalyses() {
		ArrayList<Analysis> analysesList = new ArrayList<Analysis>();

		try (BufferedReader myReader = new BufferedReader(new FileReader(fileNameString))) {
			String lineString;

			try {
				// Skip header line
				myReader.readLine();
				while ((lineString = myReader.readLine()) != null) {
					Analysis c = new Analysis(lineString);
					analysesList.add(c);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return analysesList;
	}
}
