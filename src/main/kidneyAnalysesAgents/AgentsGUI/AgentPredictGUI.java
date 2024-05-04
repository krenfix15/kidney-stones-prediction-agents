package main.kidneyAnalysesAgents.AgentsGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.kidneyAnalysesAgents.AgentsBehaviour.AgentPredict;

public class AgentPredictGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private AgentPredict agentPredict;

	private JFrame frmPredict;

	private JTextField txtGravity;
	private JTextField txtPh;
	private JTextField txtOsmo;
	private JTextField txtCond;
	private JTextField txtUreaConcentration;
	private JTextField txtCalciumConcentration;
	private JTextField txtKidneyStonesPresence;

	// Creaza aplicatia agentului de adaugare analize
	public AgentPredictGUI(AgentPredict aPredict) {
		super(aPredict.getLocalName());
		agentPredict = aPredict;
		initialize();
	}

	// Initializeaza continutul interfetei
	private void initialize() {
		frmPredict = new JFrame();
		frmPredict.setTitle("Predict");
		frmPredict.getContentPane().setBackground(Color.DARK_GRAY);
		frmPredict.setBounds(100, 100, 252, 418);
		frmPredict.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPredict.getContentPane().setLayout(null);

		// Inchide agentul la inchiderea interfetei
		frmPredict.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				agentPredict.doDelete();
			}
		});

		txtGravity = new JTextField();
		txtGravity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtGravity.getText().toLowerCase().contains("gravity")) {
					txtGravity.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtGravity.getText().isEmpty()) {
					txtGravity.setText("Gravity");
				}
			}
		});
		txtGravity.setBackground(Color.BLACK);
		txtGravity.setForeground(Color.LIGHT_GRAY);
		txtGravity.setText("Gravity");
		txtGravity.setBounds(10, 11, 218, 20);
		txtGravity.setColumns(10);
		frmPredict.getContentPane().add(txtGravity);

		txtPh = new JTextField();
		txtPh.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtPh.getText().toLowerCase().contains("ph")) {
					txtPh.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtPh.getText().isEmpty()) {
					txtPh.setText("pH");
				}
			}
		});
		txtPh.setText("pH");
		txtPh.setForeground(Color.LIGHT_GRAY);
		txtPh.setColumns(10);
		txtPh.setBackground(Color.BLACK);
		txtPh.setBounds(10, 42, 218, 20);
		frmPredict.getContentPane().add(txtPh);

		txtOsmo = new JTextField();
		txtOsmo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtOsmo.getText().toLowerCase().contains("osmolarity")) {
					txtOsmo.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtOsmo.getText().isEmpty()) {
					txtOsmo.setText("Osmolarity");
				}
			}
		});
		txtOsmo.setText("Osmolarity");
		txtOsmo.setForeground(Color.LIGHT_GRAY);
		txtOsmo.setColumns(10);
		txtOsmo.setBackground(Color.BLACK);
		txtOsmo.setBounds(10, 73, 218, 20);
		frmPredict.getContentPane().add(txtOsmo);

		txtCond = new JTextField();
		txtCond.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtCond.getText().toLowerCase().contains("conductivity")) {
					txtCond.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtCond.getText().isEmpty()) {
					txtCond.setText("Conductivity");
				}
			}
		});
		txtCond.setText("Conductivity");
		txtCond.setForeground(Color.LIGHT_GRAY);
		txtCond.setColumns(10);
		txtCond.setBackground(Color.BLACK);
		txtCond.setBounds(10, 104, 218, 20);
		frmPredict.getContentPane().add(txtCond);

		txtUreaConcentration = new JTextField();
		txtUreaConcentration.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtUreaConcentration.getText().toLowerCase().contains("urea concentration")) {
					txtUreaConcentration.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtUreaConcentration.getText().isEmpty()) {
					txtUreaConcentration.setText("Urea concentration");
				}
			}
		});
		txtUreaConcentration.setText("Urea concentration");
		txtUreaConcentration.setForeground(Color.LIGHT_GRAY);
		txtUreaConcentration.setColumns(10);
		txtUreaConcentration.setBackground(Color.BLACK);
		txtUreaConcentration.setBounds(10, 135, 218, 20);
		frmPredict.getContentPane().add(txtUreaConcentration);

		txtCalciumConcentration = new JTextField();
		txtCalciumConcentration.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtCalciumConcentration.getText().toLowerCase().contains("calcium concentration")) {
					txtCalciumConcentration.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtCalciumConcentration.getText().isEmpty()) {
					txtCalciumConcentration.setText("Calcium concentration");
				}
			}
		});
		txtCalciumConcentration.setText("Calcium concentration");
		txtCalciumConcentration.setForeground(Color.LIGHT_GRAY);
		txtCalciumConcentration.setColumns(10);
		txtCalciumConcentration.setBackground(Color.BLACK);
		txtCalciumConcentration.setBounds(10, 166, 218, 20);
		frmPredict.getContentPane().add(txtCalciumConcentration);

		JButton btnPredict = new JButton("Predict");
		btnPredict.setForeground(Color.WHITE);
		btnPredict.setBackground(Color.BLACK);
		btnPredict.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnPredict.setBounds(10, 268, 218, 27);
		frmPredict.getContentPane().add(btnPredict);

		txtKidneyStonesPresence = new JTextField();
		txtKidneyStonesPresence.setEditable(false);
		txtKidneyStonesPresence.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (null != agentPredict.getPredictionResult()) {
					if (agentPredict.getPredictionResult().equals("KIDNEY STONES ARE PRESENT")) {
						txtKidneyStonesPresence.setBackground(new Color(139, 0, 0));
						txtKidneyStonesPresence.setText("       " + agentPredict.getPredictionResult());
					}
					if (agentPredict.getPredictionResult().equals("KIDNEY STONES ARE NOT PRESENT")) {
						txtKidneyStonesPresence.setBackground(new Color(1, 100, 0));
						txtKidneyStonesPresence.setText("     " + agentPredict.getPredictionResult());
					}
				}
			}
		});
		txtKidneyStonesPresence.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtKidneyStonesPresence.setText("            HOVER OVER WITH MOUSE");
		txtKidneyStonesPresence.setForeground(Color.WHITE);
		txtKidneyStonesPresence.setColumns(10);
		txtKidneyStonesPresence.setBackground(Color.BLACK);
		txtKidneyStonesPresence.setBounds(10, 331, 218, 37);
		frmPredict.getContentPane().add(txtKidneyStonesPresence);

		JLabel lblSelectDataset = new JLabel("Select dataset to train on");
		lblSelectDataset.setForeground(Color.WHITE);
		lblSelectDataset.setBackground(SystemColor.desktop);
		lblSelectDataset.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectDataset.setBounds(10, 208, 216, 14);
		frmPredict.getContentPane().add(lblSelectDataset);

		JComboBox<String> cmbBoxSelectDataset = new JComboBox<String>();
		cmbBoxSelectDataset.setToolTipText("");
		cmbBoxSelectDataset.setForeground(new Color(192, 192, 192));
		cmbBoxSelectDataset.setBackground(new Color(0, 0, 0));
		cmbBoxSelectDataset.setBounds(10, 222, 218, 22);
		cmbBoxSelectDataset.addItem("Entire dataset");
		cmbBoxSelectDataset.addItem("Selected dataset");
		frmPredict.getContentPane().add(cmbBoxSelectDataset);

		JLabel lblNewLabel = new JLabel("Predicted kidney stones presence");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 316, 218, 14);
		frmPredict.getContentPane().add(lblNewLabel);

		btnPredict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isNumber(txtGravity.getText()) && isNumber(txtPh.getText()) && isNumber(txtOsmo.getText())
						&& isNumber(txtCond.getText()) && isNumber(txtUreaConcentration.getText())
						&& isNumber(txtCalciumConcentration.getText())) {
					// Start the one shot behaviour to request the prediction
					agentPredict.RequestPredictNewAnalysis(txtGravity.getText(), txtPh.getText(), txtOsmo.getText(),
							txtCond.getText(), txtUreaConcentration.getText(), txtCalciumConcentration.getText(),
							((String) cmbBoxSelectDataset.getSelectedItem()));

					agentPredict.ReceiveDataset();

					String selectedString = (String) cmbBoxSelectDataset.getSelectedItem();
					if (selectedString != null && selectedString.equals("Entire dataset")) {
						agentPredict.PredictKidneyStones("urineAnalyses.csv");
					}
					if (selectedString != null && selectedString.equals("Selected dataset")) {
						agentPredict.PredictKidneyStones("selectedUrineAnalyses.csv");
					}

					setDefaultTextForTexboxes();
					setDefaultColorForTexboxes(Color.LIGHT_GRAY);
				} else {
					System.out.print("Predicting was not possible, check the validity of the inputs!\n");
				}
			}
		});

		setResizable(false);
	}

	// Set default text for textboxes
	private void setDefaultTextForTexboxes() {
		txtGravity.setText("Gravity");
		txtPh.setText("pH");
		txtOsmo.setText("Osmolarity");
		txtCond.setText("Conductivity");
		txtUreaConcentration.setText("Urea concentration");
		txtCalciumConcentration.setText("Calcium concentration");
		txtKidneyStonesPresence.setText("            HOVER OVER WITH MOUSE");
	}

	// Set default color for textboxes
	private void setDefaultColorForTexboxes(Color fg) {
		txtGravity.setForeground(fg);
		txtPh.setForeground(fg);
		txtOsmo.setForeground(fg);
		txtCond.setForeground(fg);
		txtUreaConcentration.setForeground(fg);
		txtCalciumConcentration.setForeground(fg);
		txtKidneyStonesPresence.setBackground(Color.BLACK);
	}

	public void showInterface() {
		frmPredict.setVisible(true);
	}

	public static boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
