package main.kidneyAnalysesAgents.Helpers;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

public class KidneyStonesPrediction {
	private static final int BATCH_SIZE = 64;
	private static final int NUM_FEATURES = 6;
	private static final int NUM_CLASSES = 2;
	private static final int SEED = 123;
	private static final int NUM_EPOCHS = 10000;
	private static final double[] NEW_ANALYSIS_DATA = { 1.026, 6.29, 833, 22.2, 457, 4.45 };
	private static List<Double> trainingLosses = new ArrayList<>();

	public static void main(String[] args) {
		try {
			RecordReader recordReader = new CSVRecordReader(1);
			recordReader.initialize(new FileSplit(new ClassPathResource("urineAnalyses.csv").getFile()));

			DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, BATCH_SIZE, NUM_FEATURES,
					NUM_CLASSES);

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
			makePredictions(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private static void makePredictions(MultiLayerNetwork model) {
		INDArray input = Nd4j.create(NEW_ANALYSIS_DATA).reshape(1, NEW_ANALYSIS_DATA.length);
		INDArray prediction = model.output(input);
		if (prediction.getDouble(0, 1) == 1.0) {
			System.out.println("Predicted probability of kidney stones presence: PRESENT!");
		} else {
			System.out.println("Predicted probability of kidney stones presence: NOT PRESENT!");
		}
	}
}