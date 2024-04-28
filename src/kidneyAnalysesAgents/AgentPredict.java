package kidneyAnalysesAgents;

import java.io.IOException;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class AgentPredict extends Agent {
	private static final long serialVersionUID = 1L;

	private AgentPredictGUI interfacePredict;

	private Analyses newAnalyses;

	private FileAdministrator fileAdmin;

	@Override
	protected void setup() {
		newAnalyses = new Analyses();

		interfacePredict = new AgentPredictGUI(this);
		interfacePredict.showInterface();

		System.out.println("\nThe agent " + getAID().getName() + " is ready.\n");
	}

	// Delete the agent that adds the analyses
	@Override
	protected void takeDown() {
		// Close interface
		interfacePredict.dispose();

		doDelete();

		// Print closing message
		System.out.println("The agent " + getAID().getName() + " is closing.\n");
	}

	// Invocata de interfata atunci cand se inregistreaza un nou client
	public void AddNewUrineAnalyses(final String gravity, final String pH, final String osmo,
			final String conductivity, final String urea, final String calcium) {
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
