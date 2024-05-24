package main.kidneyAnalysesAgents.AgentsBehaviour;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.saver.InMemoryModelSaver;
import org.deeplearning4j.earlystopping.scorecalc.DataSetLossCalculator;
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition;
import org.deeplearning4j.earlystopping.termination.MaxTimeIterationTerminationCondition;
import org.deeplearning4j.earlystopping.termination.ScoreImprovementEpochTerminationCondition;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
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
import org.nd4j.linalg.dataset.ViewIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
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
	private static final int NUM_EPOCHS = 10000;
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

						// Set up chart for training and validation loss
						JFrame frame = new JFrame("Training and Validation Loss");
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setLayout(new BorderLayout());

						XYSeries trainingSeries = new XYSeries("Training Loss");
						XYSeries validationSeries = new XYSeries("Validation Loss");

						XYSeriesCollection dataset = new XYSeriesCollection();
						dataset.addSeries(trainingSeries);
						dataset.addSeries(validationSeries);

						JFreeChart chart = ChartFactory.createXYLineChart("Training and Validation Loss", "Epoch",
								"Loss", dataset, PlotOrientation.VERTICAL, true, true, false);

						ChartPanel panel = new ChartPanel(chart);
						panel.setPreferredSize(new Dimension(800, 600));
						frame.add(panel, BorderLayout.CENTER);
						frame.pack();
						frame.setVisible(true);

						// Add training listener to collect training and validation loss values
						model.setListeners(new TrainingListener() {
							private int epochCount = 0;

							@Override
							public void iterationDone(Model model, int iteration, int epoch) {
								// Calculate how many iterations per epoch
								int numIterations = (int) Math.ceil((double) trainingData.numExamples() / BATCH_SIZE);

								if (iteration % numIterations == 0) {
									double trainingLoss = model.score();
									double validationLoss = getValidationLoss((MultiLayerNetwork) model, testingData);

									System.out.println("Epoch " + epochCount + " Training Loss: " + trainingLoss
											+ ", Validation Loss: " + validationLoss);

									trainingLosses.add(trainingLoss);
									trainingSeries.add(epochCount, trainingLoss);
									validationSeries.add(epochCount, validationLoss);

									epochCount++;
								}
							}

							@Override
							public void onEpochStart(Model model) {
								// Not needed
							}

							@Override
							public void onEpochEnd(Model model) {
								// Not needed
							}

							@Override
							public void onForwardPass(Model model, List<INDArray> activations) {
								// Not needed
							}

							@Override
							public void onForwardPass(Model model, Map<String, INDArray> activations) {
								// Not needed
							}

							@Override
							public void onGradientCalculation(Model model) {
								// Not needed
							}

							@Override
							public void onBackwardPass(Model model) {
								// Not needed
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
						System.out.println("\nMessage sent to the analyses manager: dataset was not received yet!");
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

	private static double getValidationLoss(MultiLayerNetwork model, DataSet validationData) {
		INDArray features = validationData.getFeatures();
		INDArray labels = validationData.getLabels();
		INDArray output = model.output(features, false);
		double loss = model.score(new DataSet(features, labels));
		return loss;
	}

	private static MultiLayerNetwork createModel() {
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().seed(SEED)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(new Adam(0.0001))
				.weightInit(WeightInit.XAVIER).l2(0.001) // Adjust L2 regularization strength
				.list().layer(0, new DenseLayer.Builder().nIn(NUM_FEATURES).nOut(256) // Increase number of units
						.activation(Activation.RELU).dropOut(0.5).build())
				.layer(1, new DenseLayer.Builder().nIn(256).nOut(128) // Adjust number of units
						.activation(Activation.RELU).dropOut(0.5).build())
				.layer(2, new DenseLayer.Builder().nIn(128).nOut(64) // Adjust number of units
						.activation(Activation.RELU).dropOut(0.5).build())
				.layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.XENT).activation(Activation.SIGMOID)
						.nIn(64).nOut(NUM_CLASSES).build())
				.build();

		MultiLayerNetwork model = new MultiLayerNetwork(configuration);
		model.init();
		return model;
	}

	private static void trainModel(MultiLayerNetwork model, DataSet trainingData) {
		EarlyStoppingConfiguration<MultiLayerNetwork> esConf = new EarlyStoppingConfiguration.Builder<MultiLayerNetwork>()
				.epochTerminationConditions(new MaxEpochsTerminationCondition(NUM_EPOCHS),
						new ScoreImprovementEpochTerminationCondition(15)) // Stop if no improvement for 10 epochs
				.evaluateEveryNEpochs(1)
				.iterationTerminationConditions(new MaxTimeIterationTerminationCondition(1, TimeUnit.HOURS))
				.scoreCalculator(new DataSetLossCalculator(new ViewIterator(trainingData, BATCH_SIZE), true))
				.modelSaver(new InMemoryModelSaver<MultiLayerNetwork>()).build();

		EarlyStoppingTrainer trainer = new EarlyStoppingTrainer(esConf, model,
				new ViewIterator(trainingData, BATCH_SIZE));
		EarlyStoppingResult<MultiLayerNetwork> result = trainer.fit();

		System.out.println("Termination reason: " + result.getTerminationReason());
		System.out.println("Termination details: " + result.getTerminationDetails());
		System.out.println("Total epochs: " + result.getTotalEpochs());
		System.out.println("Best epoch number: " + result.getBestModelEpoch());
		System.out.println("Score at best epoch: " + result.getBestModelScore());
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
			if (frame instanceof JFrame && frame.getTitle().equals("Training and Validation Loss")) {
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
