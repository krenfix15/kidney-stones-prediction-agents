package kidneyAnalysesAgents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class StartAgentAnalysesDataSet {

	public static void main(String[] args) {

		String host = "localhost"; // JADE Book Trading environment Main Container host
		String port = "1099"; // JADE Dataset environment Main Container port

		String agentName = "AnalysesDataSet"; // The name of the agent

		// Instance of the runtime env
		Runtime runtime = Runtime.instance();

		// Container creation
		Profile p = new ProfileImpl();

		p.setParameter(Profile.MAIN_HOST, host);
		p.setParameter(Profile.MAIN_PORT, port);

		ContainerController cc = runtime.createAgentContainer(p);

		if (cc != null) {
			// Creating an agent for the analyses dataset
			try {
				AgentController ac = cc.createNewAgent(agentName, "kidneyAnalysesAgents.AnalysesDataSet", null);
				ac.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
