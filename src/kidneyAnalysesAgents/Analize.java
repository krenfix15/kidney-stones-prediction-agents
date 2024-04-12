package kidneyAnalysesAgents;

public class Analize {
	private String numeString;
	private String prenumeString;
	private String cnpString;
	private String soldString;

	public String SEPARATOR_PRINCIPAL_FISIER = ",";

	public Analize() {
		numeString = "";
		prenumeString = "";
		cnpString = "";
		soldString = "";
	}

	public Analize(String linieFisier) {
		String[] dateFisier = linieFisier.split(SEPARATOR_PRINCIPAL_FISIER);
		numeString = dateFisier[0]; // (Integer)CampuriClient.NUME
		prenumeString = dateFisier[1]; // (Integer)CampuriClient.PRENUME
		cnpString = dateFisier[2]; // (Integer)CampuriClient.CNP
		soldString = dateFisier[3]; // (Integer)CampuriClient.SOLD
	}

	public String getNume() {
		return this.numeString;
	}

	public void setNume(String nume) {
		this.numeString = nume;
	}

	public String getPrenume() {
		return this.prenumeString;
	}

	public void setPrenume(String prenume) {
		this.prenumeString = prenume;
	}

	public String getCNP() {
		return this.cnpString;
	}

	public void setCNP(String cnp) {
		this.cnpString = cnp;
	}

	public String getSold() {
		return this.soldString;
	}

	public void setSold(String sold) {
		this.soldString = sold;
	}

	public String getSeparator() {
		return this.SEPARATOR_PRINCIPAL_FISIER;
	}

	public String ConversieLaSirPentruFisier() {
		String s = String.format("%2$s%1$s%3$s%1$s%4$s%1$s%5$s%1$s\n", SEPARATOR_PRINCIPAL_FISIER, numeString,
				prenumeString, cnpString, soldString);
		return s;
	}

	@Override
	public String toString() {
		return ConversieLaSirPentruFisier();
	}

}
