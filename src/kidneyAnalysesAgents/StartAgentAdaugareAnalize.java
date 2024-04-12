package kidneyAnalysesAgents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class StartAgentAdaugareAnalize {

	public static void main(String[] args) {

		String host = "localhost"; // JADE Bank environment Main Container host
		String port = "1099"; // JADE Bank environment Main Container port
		String agentName = "AdaugareAnalize"; // Nume agent

		// Preluarea instanþei mediului de execuþie JADE
		Runtime runtime = Runtime.instance();

		// Crearea unui container în care sã ruleze agentul
		Profile p = new ProfileImpl();

		p.setParameter(Profile.MAIN_HOST, host);
		p.setParameter(Profile.MAIN_PORT, port);

		ContainerController cc = runtime.createAgentContainer(p);

		if (cc != null) {
			// crearea unui agent pentru adaugare analize si lansarea lui in executie
			try {
				AgentController ac = cc.createNewAgent(agentName, "bankagents.AgentAdaugareAnalize", null);
				ac.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
