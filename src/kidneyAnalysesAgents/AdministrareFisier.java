package kidneyAnalysesAgents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AdministrareFisier {
	private String numeFisierString;

	public AdministrareFisier() {
		numeFisierString = "clienti.txt";
	}

	public AdministrareFisier(String numeFisier) throws IOException {
		this.numeFisierString = numeFisier;
		try {
			File fisierSalvare = new File(numeFisier);
			if (fisierSalvare.createNewFile()) {
				System.out.println("Fisier creat: " + fisierSalvare.getName());
			}
		} catch (IOException e) {
			System.out.println("A aparut o eroare la deschiderea fisierului.");
			e.printStackTrace();
		}
	}

	public void AdaugaClient(Analize c) {
		try {
			FileWriter myWriter = new FileWriter(numeFisierString, true);
			myWriter.write(c.ConversieLaSirPentruFisier());
			myWriter.close();
			System.out.println("Am scris datele in fisierul csv.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Analize> GetClienti() {
		ArrayList<Analize> listaClienti = new ArrayList<Analize>();

		try {
			BufferedReader myReader = new BufferedReader(new FileReader(numeFisierString));
			String lineString;

			try {
				while ((lineString = myReader.readLine()) != null) {
					Analize c = new Analize(lineString);
					listaClienti.add(c);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaClienti;
	}

	public Analize GetClient(String cnp) throws IOException {
		try {
			@SuppressWarnings("resource")
			BufferedReader myReader = new BufferedReader(new FileReader(numeFisierString));
			String lineString;

			while ((lineString = myReader.readLine()) != null) {
				Analize c = new Analize(lineString);
				if (c.getCNP().equals(cnp)) {
					return c;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Boolean UpdateClient(Analize clientActualizat) {
		Boolean actualizareCuSucces = false;
		try {
			ArrayList<Analize> clienti = GetClienti();
			FileWriter myWriter = new FileWriter(numeFisierString);

			for (Analize client : clienti) {
				if (!client.getCNP().equals(clientActualizat.getCNP())) {
					myWriter.write(client.ConversieLaSirPentruFisier());
				} else {
					myWriter.write(clientActualizat.ConversieLaSirPentruFisier());
				}

			}
			myWriter.close();
			actualizareCuSucces = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actualizareCuSucces;
	}

}
