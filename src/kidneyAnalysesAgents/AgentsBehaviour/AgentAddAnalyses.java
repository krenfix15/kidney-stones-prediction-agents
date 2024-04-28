package kidneyAnalysesAgents.AgentsBehaviour;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import kidneyAnalysesAgents.AgentsGUI.AgentAddAnalysesGUI;
import kidneyAnalysesAgents.Helpers.Analysis;
import kidneyAnalysesAgents.Helpers.FileAdministrator;

public class AgentAddAnalyses extends Agent {
	private static final long serialVersionUID = 1L;

	// Interfata pentru inregistrare
	private AgentAddAnalysesGUI interfaceAddAnalyses;

	private Analysis newAnalysis;

	private AID[] agentsAnalysesManagers;
	
	@Override
	protected void setup() {
		newAnalysis = new Analysis();

		// Creation
		interfaceAddAnalyses = new AgentAddAnalysesGUI(this);
		interfaceAddAnalyses.showInterface();

		System.out.println("\nThe agent " + getAID().getName() + " is ready.\n");
	}

	// Delete the agent that adds the analyses
	@Override
	protected void takeDown() {
		// Closing interface
		interfaceAddAnalyses.dispose();

		doDelete();

		// Closing message
		System.out.println("The agent " + getAID().getName() + " is closing.\n");
	}

	// Invoked by interface when adding new analysis sample
	public void AddNewUrineAnalyses(final String gravity, final String pH, final String osmo,
			final String conductivity, final String urea, final String calcium, final String kidneyStonesPresence) {
		addBehaviour(new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				newAnalysis.setGravityString(gravity);
				newAnalysis.setPhString(pH);
				newAnalysis.setOsmoString(osmo);
				newAnalysis.setConductivityString(conductivity);
				newAnalysis.setUreaString(urea);
				newAnalysis.setCalciumString(calcium);
				newAnalysis.setkidneyStonesPresenceString(kidneyStonesPresence);

				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("manager");

				template.addServices(sd);

				try {
					DFAgentDescription[] result = DFService.search(myAgent, template);
					System.out.println("Found the next analyses agent");
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
	            message.setContent(newAnalysis.toString());

	            // Send the message
	            myAgent.send(message);
	            System.out.println("Message sent with the new analysis to analyses manager: " + newAnalysis.toString());
			}
		});
	}
}
