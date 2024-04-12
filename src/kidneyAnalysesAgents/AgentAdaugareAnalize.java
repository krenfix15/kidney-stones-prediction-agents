package kidneyAnalysesAgents;

import java.io.IOException;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class AgentAdaugareAnalize extends Agent {
	private static final long serialVersionUID = 1L;

	// Interfata pentru inregistrare
	private AdaugareAnalizeGui interfata;

	private Analize clientNou;

	private AdministrareFisier adminFisier;

	@Override
	protected void setup() {
		clientNou = new Analize();

		// Crearea si afisarea interfetei
		interfata = new AdaugareAnalizeGui(this);
		interfata.afiseazaInterfata();

		System.out.println("\nAgentul " + getAID().getName() + " este pregãtit.\n");
	}

	// Stergerea agentului client
	@Override
	protected void takeDown() {
		// Inchidere interfata
		interfata.dispose();

		doDelete();

		// Printarea mesajului de inchidere
		System.out.println("Agentul " + getAID().getName() + " se închide.\n");
	}

	// Invocata de interfata atunci cand se inregistreaza un nou client
	public void inregistreazaDateleClientului(final String nume, final String prenume, final String CNP,
			final String sold) {
		addBehaviour(new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				clientNou.setNume(nume);
				clientNou.setPrenume(prenume);
				clientNou.setCNP(CNP);
				clientNou.setSold(sold);

				try {
					adminFisier = new AdministrareFisier("clienti.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				adminFisier.AdaugaClient(clientNou);

				System.out.println(
						"Informatiile clientului " + nume + " " + prenume + " au fost înregistrate cu succes!\n");
			}
		});
	}

}
