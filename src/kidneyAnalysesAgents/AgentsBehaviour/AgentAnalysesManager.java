package kidneyAnalysesAgents.AgentsBehaviour;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import kidneyAnalysesAgents.AgentsGUI.AgentAnalysesManagerGUI;
import kidneyAnalysesAgents.Helpers.Analysis;
import kidneyAnalysesAgents.Helpers.FileAdministrator;

public class AgentAnalysesManager extends Agent {

	private static final long serialVersionUID = 1L;

	public Analysis analyses;

	private FileAdministrator fileAdmin = new FileAdministrator();

	private AgentAnalysesManagerGUI agentInterface;

	@Override
	protected void setup() {
		agentInterface = new AgentAnalysesManagerGUI(this);
		agentInterface.showInterface();

		System.out.println("The analyses manager agent " + getAID().getName() + " is ready.\n");

		// Register in DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setType("manager");
		sd.setName("JADE-Manager");

		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new AddAnalysesService());

		addBehaviour(new SelectAnalysesService());
	}

	// Delete the agent
	@Override
	protected void takeDown() {
		// Unregister from DF
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Close the interface
		agentInterface.dispose();

		doDelete();

		System.out.println("The agent " + getAID().getName() + " is closing.\n");
	}

	private class AddAnalysesService extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);

			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				// Message received
				String message = msg.getContent();

				ACLMessage reply = msg.createReply();

				if (message != null && !message.isEmpty()) {
					// Content is not empty, try to create Analysis object
					try {
						Analysis newAnalysis = new Analysis(message);
						// Successfully created Analysis object
						reply.setPerformative(ACLMessage.CONFIRM);
						reply.setContent("I added the new analysis.");
						fileAdmin.AddNewAnalysis(newAnalysis);
					} catch (IllegalArgumentException e) {
						// Failed to create Analysis object (invalid content)
						reply.setPerformative(ACLMessage.FAILURE);
						reply.setContent("Invalid analysis data.");
					}
				} else {
					// No content or empty content
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("No analysis data provided.");
				}

				// Send reply
				myAgent.send(reply);
			} else {
				// No message received, block
				block();
			}
		}
	}

	private class SelectAnalysesService extends CyclicBehaviour {
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

				Integer soldVechiClient = 1; // Integer.valueOf(client.getSold());

				if (soldVechiClient != null && operatie.equals("adaugare")) {
					soldNou = soldVechiClient + suma;
					// client.setSold(soldNou.toString());

					System.out.println("Adãugare " + suma + " LEI în cont. Sold cont: " + soldNou + " LEI");

					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Sold actualizat: " + String.valueOf(soldNou) + "\n");
				} else if (soldVechiClient != null && operatie.equals("extragere") && (soldVechiClient > suma)) {
					soldNou = soldVechiClient - suma;
					// client.setSold(soldNou.toString());

					System.out.println("Extragere " + suma + " LEI din cont. Sold cont: " + soldNou + " LEI");

					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Sold actualizat: " + String.valueOf(soldNou) + "\n");
				} else {
					System.out.println("Operatia nu poate fi efectuatã.\n");
					// Clientul cu acel CNP nu exista
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-possible");
				}

				// if (client != null && !client.getCNP().equals("")) {
				// adminFisier.UpdateClient(client);
				// }

				myAgent.send(reply);
			} else {
				block();
			}
		}
	}

}
