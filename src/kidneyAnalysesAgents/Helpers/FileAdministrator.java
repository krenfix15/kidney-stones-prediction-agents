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

	public void AddNewAnalyses(Analyses a) {
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

	public ArrayList<Analyses> GetAllAnalyses() {
		ArrayList<Analyses> analysesList = new ArrayList<Analyses>();

		try {
			BufferedReader myReader = new BufferedReader(new FileReader(fileNameString));
			String lineString;

			try {
				while ((lineString = myReader.readLine()) != null) {
					Analyses c = new Analyses(lineString);
					analysesList.add(c);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return analysesList;
	}

	public Analyses GetClient(String cnp) throws IOException {
		try {
			@SuppressWarnings("resource")
			BufferedReader myReader = new BufferedReader(new FileReader(fileNameString));
			String lineString;

			while ((lineString = myReader.readLine()) != null) {
				Analyses c = new Analyses(lineString);
				// if (c.getCNP().equals(cnp)) {
				// return c;
				// }
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * public Boolean UpdateClient(Analyses clientActualizat) { Boolean
	 * actualizareCuSucces = false; try { ArrayList<Analyses> clienti =
	 * GetAllAnalyses(); FileWriter myWriter = new FileWriter(fileNameString);
	 * 
	 * for (Analyses client : clienti) { if
	 * (!client.getCNP().equals(clientActualizat.getCNP())) {
	 * myWriter.write(client.ConversieLaSirPentruFisier()); } else {
	 * myWriter.write(clientActualizat.ConversieLaSirPentruFisier()); }
	 * 
	 * } myWriter.close(); actualizareCuSucces = true; } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } return
	 * actualizareCuSucces; }
	 */
}
