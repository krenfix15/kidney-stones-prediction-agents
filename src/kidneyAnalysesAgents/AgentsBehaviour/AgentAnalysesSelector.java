package kidneyAnalysesAgents.AgentsBehaviour;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import kidneyAnalysesAgents.AgentsGUI.AgentAnalysesSelectorGUI;
import kidneyAnalysesAgents.Helpers.Analysis;
import kidneyAnalysesAgents.Helpers.FileAdministrator;

public class AgentAnalysesSelector extends Agent {
	private static final long serialVersionUID = 1L;

	public ArrayList<Analysis> selectedAnalyses;

	private AgentAnalysesSelectorGUI agentInterface;

	private FileAdministrator fileAdmin = new FileAdministrator();

	private String selectedAnalysesFilename = "selectedUrineAnalyses.csv";

	private AID[] agentsAnalysesManagers;

	@Override
	protected void setup() {
		agentInterface = new AgentAnalysesSelectorGUI(this);
		agentInterface.showInterface();

		System.out.println("The analyses selector agent " + getAID().getName() + " is ready.\n");

		// Register in DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setType("selector");
		sd.setName("JADE-Selector");

		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
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

	// Invoked by interface when adding new analysis sample
	public void SelectUrineAnalyses(final Double gravity_min, final Double gravity_max, final Double pH_min,
			final Double pH_max, final Double osmo_min, final Double osmo_max, final Double conductivity_min,
			final Double conductivity_max, final Double urea_min, final Double urea_max, final Double calcium_min,
			final Double calcium_max) {
		addBehaviour(new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {

				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("manager");

				template.addServices(sd);

				try {
					DFAgentDescription[] result = DFService.search(myAgent, template);
					System.out.println("Found the analyses agent:");
					agentsAnalysesManagers = new AID[result.length];

					for (int i = 0; i < result.length; ++i) {
						agentsAnalysesManagers[i] = result[i].getName();
						System.out.println(agentsAnalysesManagers[i].getName());
					}

				} catch (FIPAException fe) {
					fe.printStackTrace();
				}

				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				for (AID agent : agentsAnalysesManagers) {
					message.addReceiver(agent);
				}

				fileAdmin.writeSelectedAnalysesToFile(
						fileAdmin.selectUrineAnalyses(gravity_min, gravity_max, pH_min, pH_max, osmo_min, osmo_max,
								conductivity_min, conductivity_max, urea_min, urea_max, calcium_min, calcium_max),
						selectedAnalysesFilename);

				message.setContent("The selected analyses were written to " + selectedAnalysesFilename + ".");

				// Send the message
				myAgent.send(message);
				System.out.println("\nInform message sent to the analyses manager!");
			}
		});
	}
}
