package main.kidneyAnalysesAgents.AgentsBehaviour;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.kidneyAnalysesAgents.AgentsGUI.AgentPredictGUI;
import main.kidneyAnalysesAgents.Helpers.Analysis;

public class AgentPredict extends Agent {
	private static final long serialVersionUID = 1L;

	private AgentPredictGUI interfacePredict;

	private Analysis analysisToPredict;

	private String filename = null;

	private AID[] agentsAnalysesManagers;

	String predictionString;

	private static final int BATCH_SIZE = 64;
	private static final int NUM_FEATURES = 6;
	private static final int NUM_CLASSES = 2;
	private static final int SEED = 123;
	private static final int NUM_EPOCHS = 1000;
	private static final double[] NEW_ANALYSIS_DATA = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	private static List<Double> trainingLosses = new ArrayList<>();

	@Override
	protected void setup() {
		analysisToPredict = new Analysis();

		interfacePredict = new AgentPredictGUI(this);
		interfacePredict.showInterface();

		System.out.println("\nThe agent " + getAID().getName() + " is ready.\n");

		// Register in DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setType("predict");
		sd.setName("JADE-predict");

		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
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

	// Invoked by interface when adding new analysis sample
	public void RequestPredictNewAnalysis(final String gravity, final String pH, final String osmo,
			final String conductivity, final String urea, final String calcium, final String dataset) {
		addBehaviour(new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				analysisToPredict.setGravityString(gravity);
				analysisToPredict.setPhString(pH);
				analysisToPredict.setOsmoString(osmo);
				analysisToPredict.setConductivityString(conductivity);
				analysisToPredict.setUreaString(urea);
				analysisToPredict.setCalciumString(calcium);

				NEW_ANALYSIS_DATA[0] = Double.parseDouble(analysisToPredict.getGravityString());
				NEW_ANALYSIS_DATA[1] = Double.parseDouble(analysisToPredict.getGravityString());
				NEW_ANALYSIS_DATA[2] = Double.parseDouble(analysisToPredict.getGravityString());
				NEW_ANALYSIS_DATA[3] = Double.parseDouble(analysisToPredict.getGravityString());
				NEW_ANALYSIS_DATA[4] = Double.parseDouble(analysisToPredict.getGravityString());
				NEW_ANALYSIS_DATA[5] = Double.parseDouble(analysisToPredict.getGravityString());

				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("manager");

				template.addServices(sd);

				try {
					DFAgentDescription[] result = DFService.search(myAgent, template);
					System.out.println("Found the analyses manager agent:");
					agentsAnalysesManagers = new AID[result.length];

					for (int i = 0; i < result.length; ++i) {
						agentsAnalysesManagers[i] = result[i].getName();
						System.out.println(agentsAnalysesManagers[i].getName());
					}

				} catch (FIPAException fe) {
					fe.printStackTrace();
				}

				ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
				for (AID agent : agentsAnalysesManagers) {
					message.addReceiver(agent);
				}
				message.setContent(dataset.toLowerCase());

				// Send the message
				myAgent.send(message);
				System.out
						.println("\nRequest sent to manager to give the file path for " + dataset.toLowerCase() + ".");
			}
		});
	}

	public void ReceiveDataset() {
		addBehaviour(new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);

				ACLMessage msg = myAgent.receive(mt);

				if (msg != null) {
					// Message received
					String message = msg.getContent();

					ACLMessage reply = msg.createReply();

					if (!message.isEmpty()) {
						setFilename(message);
						reply.setPerformative(ACLMessage.CONFIRM);
						reply.setContent("I received the dataset from " + getFilename());
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
		});
	}

	public void PredictKidneyStones(String filename) {
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
					System.out.println("Found the analyses manager agent:");
					agentsAnalysesManagers = new AID[result.length];

					for (int i = 0; i < result.length; ++i) {
						agentsAnalysesManagers[i] = result[i].getName();
						System.out.println(agentsAnalysesManagers[i].getName());
					}

				} catch (FIPAException fe) {
					fe.printStackTrace();
				}

				ACLMessage message = new ACLMessage(ACLMessage.INFORM_REF);
				for (AID agent : agentsAnalysesManagers) {
					message.addReceiver(agent);
				}

				try {
					if (filename != null) {
						RecordReader recordReader = new CSVRecordReader(1);
						recordReader.initialize(new FileSplit(new ClassPathResource(filename).getFile()));

						DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, BATCH_SIZE,
								NUM_FEATURES, NUM_CLASSES);

						DataSet allData = iterator.next();
						allData.shuffle(42);

						// Split the data into training (80%) and testing (20%) sets
						SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.8);
						DataSet trainingData = testAndTrain.getTrain();
						DataSet testingData = testAndTrain.getTest();

						// Normalize the training and testing data
						DataNormalization normalizer = new NormalizerStandardize();
						normalizer.fit(trainingData); // Collect statistics about the data (mean, standard deviation)
						normalizer.transform(trainingData); // Apply normalization to the training data
						normalizer.transform(testingData); // Apply normalization to the testing data

						MultiLayerNetwork model = createModel();

						// Set up chart
						JFrame frame = new JFrame("Training Loss");
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setLayout(new BorderLayout());

						XYSeries series = new XYSeries("Training Loss");

						JFreeChart chart = ChartFactory.createXYLineChart("Training Loss", "Epoch", "Loss",
								new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);

						ChartPanel panel = new ChartPanel(chart);
						panel.setPreferredSize(new Dimension(800, 600));
						frame.add(panel, BorderLayout.CENTER);
						frame.pack();
						frame.setVisible(true);

						// Add training listener to collect training loss values
						model.setListeners(new TrainingListener() {
							private int epochCount = 0;

							@Override
							public void iterationDone(Model model, int iteration, int epoch) {
								double score = model.score();
								System.out.println("Training Loss at epoch " + epochCount + ": " + score);
								trainingLosses.add(score);
								series.add(epochCount++, score);
							}

							@Override
							public void onEpochStart(Model model) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onEpochEnd(Model model) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onForwardPass(Model model, List<INDArray> activations) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onForwardPass(Model model, Map<String, INDArray> activations) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onGradientCalculation(Model model) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onBackwardPass(Model model) {
								// TODO Auto-generated method stub

							}
						});

						trainModel(model, trainingData);

						// Evaluate model
						evaluateModel(model, testingData);

						// Make predictions
						predictionString = makePredictions(model);

						closeChartFrame();

						message.setContent(predictionString);

						// Send the message
						myAgent.send(message);
						System.out
								.println("\nMessage sent with the prediction to analyses manager: " + predictionString);
					} else {
						message.setContent("Dataset was not received yet!");
						// Send the message
						myAgent.send(message);
						System.out
								.println("\nMessage sent to the analyses manager: " + "dataset was not received yet!");
					}
				} catch (Exception e) {
					e.printStackTrace();
					message.setContent("Dataset was not received yet!");
					// Send the message
					myAgent.send(message);
				}
			}
		});
	}

	private static MultiLayerNetwork createModel() {
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().seed(SEED)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).weightInit(WeightInit.XAVIER)
				.list()
				.layer(0, new DenseLayer.Builder().nIn(NUM_FEATURES).nOut(64).activation(Activation.RELU).build())
				.layer(1, new DenseLayer.Builder().nIn(64).nOut(32).activation(Activation.RELU).build())
				.layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.XENT).activation(Activation.SIGMOID)
						.nIn(32).nOut(NUM_CLASSES).build())
				.build();

		MultiLayerNetwork model = new MultiLayerNetwork(configuration);
		model.init();
		return model;
	}

	private static void trainModel(MultiLayerNetwork model, DataSet trainingData) {
		for (int epoch = 0; epoch < NUM_EPOCHS; epoch++) {
			model.fit(trainingData);
		}
	}

	private static Evaluation evaluateModel(MultiLayerNetwork model, DataSet testingData) {
		Evaluation evaluation = new Evaluation(NUM_CLASSES);
		INDArray features = testingData.getFeatures();
		INDArray labels = testingData.getLabels();
		INDArray predicted = model.output(features, false);
		evaluation.eval(labels, predicted);
		double accuracy = evaluation.accuracy();
		System.out.println("Accuracy on testing data: " + (accuracy * 100) + "%");
		return evaluation;
	}

	private static String makePredictions(MultiLayerNetwork model) {
		String predictionString;
		INDArray input = Nd4j.create(NEW_ANALYSIS_DATA).reshape(1, NEW_ANALYSIS_DATA.length);
		INDArray prediction = model.output(input);
		if (prediction.getDouble(0, 1) == 1.0) {
			System.out.println("Predicted probability of kidney stones presence: PRESENT!");
			predictionString = "KIDNEY STONES ARE PRESENT";
		} else {
			System.out.println("Predicted probability of kidney stones presence: NOT PRESENT!");
			predictionString = "KIDNEY STONES ARE NOT PRESENT";
		}

		return predictionString;
	}

	private void closeChartFrame() {
		// Get all frames owned by this agent
		Frame[] frames = Frame.getFrames();
		for (Frame frame : frames) {
			if (frame instanceof JFrame && frame.getTitle().equals("Training Loss")) {
				frame.dispose(); // Close the frame
			}
		}
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public String getPredictionResult() {
		return predictionString;
	}

	public void setPredictionResult(String predictionResult) {
		this.predictionString = predictionResult;
	}
}
