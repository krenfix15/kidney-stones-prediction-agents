package kidneyAnalysesAgents.Helpers;

public class Analyses {
	private String gravityString;
	private String phString;
	private String osmoString;
	private String conductivityString;
	private String ureaString;
	private String calciumString;
	private String targetString;

	public String PARAMETER_SEPARATOR = ",";

	public Analyses() {
		setGravityString("");
		setPhString("");
		setOsmoString("");
		setConductivityString("");
		setUreaString("");
		setCalciumString("");
		setTargetString("");
	}

	public Analyses(String FileLine) {
		String[] fileData = FileLine.split(PARAMETER_SEPARATOR);
		setGravityString(fileData[0]);
		setPhString(fileData[1]);
		setOsmoString(fileData[2]);
		setConductivityString(fileData[3]);
		setUreaString(fileData[4]);
		setCalciumString(fileData[5]);
		setTargetString(fileData[6]);
	}

	public String getGravityString() {
		return gravityString;
	}

	public void setGravityString(String gravityString) {
		this.gravityString = gravityString;
	}

	public String getPhString() {
		return phString;
	}

	public void setPhString(String phString) {
		this.phString = phString;
	}

	public String getOsmoString() {
		return osmoString;
	}

	public void setOsmoString(String osmoString) {
		this.osmoString = osmoString;
	}

	public String getConductivityString() {
		return conductivityString;
	}

	public void setConductivityString(String conductivityString) {
		this.conductivityString = conductivityString;
	}

	public String getUreaString() {
		return ureaString;
	}

	public void setUreaString(String ureaString) {
		this.ureaString = ureaString;
	}

	public String getCalciumString() {
		return calciumString;
	}

	public void setCalciumString(String calciumString) {
		this.calciumString = calciumString;
	}

	public String getTargetString() {
		return targetString;
	}

	public void setTargetString(String targetString) {
		this.targetString = targetString;
	}

	public String getSeparator() {
		return this.PARAMETER_SEPARATOR;
	}

	public String convertStringForFile() {
		String s = String.format("%2$s%1$s%3$s%1$s%4$s%1$s%5$s%1$s%6$s%1$s%7$s%1$s\n", PARAMETER_SEPARATOR,
				gravityString, phString, osmoString, conductivityString, ureaString, calciumString, targetString);
		return s;
	}

	@Override
	public String toString() {
		return convertStringForFile();
	}
}
