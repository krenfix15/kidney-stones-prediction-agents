package main.kidneyAnalysesAgents.AgentsBehaviour;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.kidneyAnalysesAgents.AgentsGUI.AgentAnalysesManagerGUI;
import main.kidneyAnalysesAgents.Helpers.Analysis;
import main.kidneyAnalysesAgents.Helpers.FileAdministrator;

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

		addBehaviour(new MessageDatasetService());
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

				if (!message.isEmpty()) {
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
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				// Message received
				String message = msg.getContent();

				ACLMessage reply = msg.createReply();

				if (!message.isEmpty()) {
					// Content is not empty
					try {
						reply.setPerformative(ACLMessage.CONFIRM);
						reply.setContent("I checked that the file exists. It is okay.");
					} catch (IllegalArgumentException e) {
						reply.setPerformative(ACLMessage.FAILURE);
						reply.setContent("The file does not exist.");
					}
				} else {
					// No content or empty content
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("The received message is empty.");
				}

				// Send reply
				myAgent.send(reply);
			} else {
				// No message received, block
				block();
			}
		}
	}

	private class MessageDatasetService extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				// Message received
				String message = msg.getContent();

				ACLMessage reply = msg.createReply();

				if (!message.isEmpty()) {
					if (message.equals("entire dataset")) {
						try {
							reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
							reply.setContent("urineAnalyses.csv");
						} catch (IllegalArgumentException e) {
							reply.setPerformative(ACLMessage.FAILURE);
							reply.setContent("The message was not received.");
						}
					}
					if (message.equals("selected dataset")) {
						try {
							reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
							reply.setContent("selectedUrineAnalyses.csv");
						} catch (IllegalArgumentException e) {
							reply.setPerformative(ACLMessage.FAILURE);
							reply.setContent("The message was not received.");
						}
					}
				} else {
					// No content or empty content
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("The received message is empty.");
				}

				// Send reply
				myAgent.send(reply);
			} else {
				// No message received, block
				block();
			}
		}
	}

}
