package kidneyAnalysesAgents.AgentsBehaviour;

import java.io.IOException;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import kidneyAnalysesAgents.AgentsGUI.AgentAddAnalysesGUI;
import kidneyAnalysesAgents.Helpers.Analysis;
import kidneyAnalysesAgents.Helpers.FileAdministrator;

public class AgentAddAnalyses extends Agent {
	private static final long serialVersionUID = 1L;

	// Interfata pentru inregistrare
	private AgentAddAnalysesGUI interfaceAddAnalyses;

	private Analysis newAnalyses;

	private FileAdministrator fileAdmin;

	@Override
	protected void setup() {
		newAnalyses = new Analysis();

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
			final String conductivity, final String urea, final String calcium, final String target) {
		addBehaviour(new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				newAnalyses.setGravityString(gravity);
				newAnalyses.setPhString(pH);
				newAnalyses.setOsmoString(osmo);
				newAnalyses.setConductivityString(conductivity);
				newAnalyses.setUreaString(urea);
				newAnalyses.setCalciumString(calcium);
				newAnalyses.setkidneyStonesPresenceString(target);

				try {
					fileAdmin = new FileAdministrator("urineAnalyses.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				fileAdmin.AddNewAnalyses(newAnalyses);

				System.out.println("The analyses were successfully added!\n");
			}
		});
	}

}
