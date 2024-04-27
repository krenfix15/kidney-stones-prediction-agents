package kidneyAnalysesAgents;

import java.io.IOException;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentAnalysesSelector extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Analyses client;

	private FileAdministrator adminFisier = new FileAdministrator();

	private AnalysesSelectorGUI interfata;

	@Override
	protected void setup() {
		// Crearea si afisarea interfetei
		interfata = new AnalysesSelectorGUI(this);
		interfata.afiseazaInterfata();

		System.out.println("Agentul pentru banca din " + getAID().getName() + " este pregãtit.\n");

		// Inregistrare agent in DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setType("filiala");
		sd.setName("JADE-Bancã");

		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new ServiciuSold());

		addBehaviour(new ServiciuTranzactie());
	}

	// Stergerea agentului banca
	@Override
	protected void takeDown() {
		// Deinregistrare
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Inchidere interfata
		interfata.dispose();

		doDelete();

		// Printarea mesajului de inchidere
		System.out.println("Agentul " + getAID().getName() + " se închide.\n");
	}

	private class ServiciuSold extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				// CFP Message received. Process it
				String mesaj = msg.getContent();

				ACLMessage reply = msg.createReply();

				try {
					client = adminFisier.GetClient(mesaj);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Integer soldVechiClient = Integer.valueOf(client.getSold());

				if (soldVechiClient != null) {
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent("Sold curent: " + soldVechiClient.toString());
				} else {
					// CNP ul n a fost gasit in baza de date.
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-available");
				}

				myAgent.send(reply);
			} else {
				block();
			}
		}
	} // Sfarsitul clasei ServiciuSold

	private class ServiciuTranzactie extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);

			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				// CFP Message received. Process it
				String mesaj = msg.getContent();

				ACLMessage reply = msg.createReply();

				String informatii[] = mesaj.split(" ");

				String cnp = informatii[0];

				Integer suma = Integer.parseInt(informatii[1]);

				String operatie = informatii[2];

				Integer soldNou;

				try {
					client = adminFisier.GetClient(cnp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Integer soldVechiClient = Integer.valueOf(client.getSold());

				if (soldVechiClient != null && operatie.equals("adaugare")) {
					soldNou = soldVechiClient + suma;
					client.setSold(soldNou.toString());

					System.out.println("Adãugare " + suma + " LEI în cont. Sold cont: " + soldNou + " LEI");

					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Sold actualizat: " + String.valueOf(soldNou) + "\n");
				} else if (soldVechiClient != null && operatie.equals("extragere") && (soldVechiClient > suma)) {
					soldNou = soldVechiClient - suma;
					client.setSold(soldNou.toString());

					System.out.println("Extragere " + suma + " LEI din cont. Sold cont: " + soldNou + " LEI");

					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Sold actualizat: " + String.valueOf(soldNou) + "\n");
				} else {
					System.out.println("Operatia nu poate fi efectuatã.\n");
					// Clientul cu acel CNP nu exista
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-possible");
				}

				if (client != null && !client.getCNP().equals("")) {
					adminFisier.UpdateClient(client);
				}

				myAgent.send(reply);
			} else {
				block();
			}
		}
	} // Sfarsitul clasei ServiciuTranzactie

}
