package kidneyAnalysesAgents.AgentsStart;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class StartAgentAnalysesSelector {

	public static void main(String[] args) {

		String host = "localhost"; // JADE environment Main Container host
		String port = "1099"; // JADE environment Main Container port

		String agentName = "AnalysesSelector";

		// Instance of the runtime env
		Runtime runtime = Runtime.instance();

		// Container creation
		Profile p = new ProfileImpl();

		p.setParameter(Profile.MAIN_HOST, host);
		p.setParameter(Profile.MAIN_PORT, port);

		ContainerController cc = runtime.createAgentContainer(p);

		if (cc != null) {
			// Creation of the agent
			try {
				AgentController ac = cc.createNewAgent(agentName, "kidneyAnalysesAgents.AgentsBehaviour.AgentAnalysesSelector", null);
				ac.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
