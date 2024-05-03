package main.kidneyAnalysesAgents.AgentsGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
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
		frmPredict.setBounds(100, 100, 252, 353);
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
		btnPredict.setBounds(10, 196, 218, 27);
		frmPredict.getContentPane().add(btnPredict);

		txtKidneyStonesPresence = new JTextField();
		txtKidneyStonesPresence.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtKidneyStonesPresence.setEditable(false);
		txtKidneyStonesPresence.setEnabled(false);
		txtKidneyStonesPresence.setText("Predicted kidney stones presence chance");
		txtKidneyStonesPresence.setForeground(Color.LIGHT_GRAY);
		txtKidneyStonesPresence.setColumns(10);
		txtKidneyStonesPresence.setBackground(Color.BLACK);
		txtKidneyStonesPresence.setBounds(10, 248, 218, 58);
		frmPredict.getContentPane().add(txtKidneyStonesPresence);

		btnPredict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtGravity.getText().matches("[a-zA-Z]+") && !txtGravity.getText().equals("Nume")
						&& txtPh.getText().matches("[a-zA-Z]+") && !txtPh.getText().equals("Prenume")
						&& txtOsmo.getText().matches(
								"^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$")
						&& txtCond.getText().matches("[1-9][0-9]*")) {
					agentPredict.AddNewUrineAnalyses(txtGravity.getText(), txtPh.getText(), txtOsmo.getText(),
							txtCond.getText(), txtUreaConcentration.getText(), txtCalciumConcentration.getText());

					setTextImplicitControale();
					setCuloareImplicitControale(Color.LIGHT_GRAY);
				} else {
					System.out.print("Înregistrarea nu a putut fi efectuată. Verificați ca datele să fie valide!\n");
				}
			}
		});

		setResizable(false);
	}

	// Setarea textului implicit al textboxurilor
	private void setTextImplicitControale() {
		txtGravity.setText("Gravity");
		txtPh.setText("pH");
		txtOsmo.setText("Osmolarity");
		txtCond.setText("Conductivity");
	}

	// Setarea culorii implicite a textului din textboxuri
	private void setCuloareImplicitControale(Color fg) {
		txtGravity.setForeground(fg);
		txtPh.setForeground(fg);
		txtOsmo.setForeground(fg);
		txtCond.setForeground(fg);
	}

	public void showInterface() {
		frmPredict.setVisible(true);
	}
}
