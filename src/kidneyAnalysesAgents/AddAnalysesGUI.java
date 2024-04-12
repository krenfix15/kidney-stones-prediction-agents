package kidneyAnalysesAgents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddAnalysesGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private AgentAddAnalyses agentAddAnalyses;

	private JFrame frmnAdaugareAnalize;

	private JTextField txtGravity;
	private JTextField txtPh;
	private JTextField txtOsmo;
	private JTextField txtCond;
	private JTextField txtUreaConcentration;
	private JTextField txtCalciumConcentration;

	// Creaza aplicatia agentului de adaugare analize
	public AddAnalysesGUI(AgentAddAnalyses agentAdaugareAnalize) {
		super(agentAdaugareAnalize.getLocalName());
		agentAddAnalyses = agentAdaugareAnalize;
		initialize();
	}

	// Initializeaza continutul interfetei
	private void initialize() {
		frmnAdaugareAnalize = new JFrame();
		frmnAdaugareAnalize.setTitle("AdaugareAnalize");
		frmnAdaugareAnalize.getContentPane().setBackground(Color.DARK_GRAY);
		frmnAdaugareAnalize.setBounds(100, 100, 172, 373);
		frmnAdaugareAnalize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmnAdaugareAnalize.getContentPane().setLayout(null);

		// Inchide agentul la inchiderea interfetei
		frmnAdaugareAnalize.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				agentAddAnalyses.doDelete();
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
		txtGravity.setBounds(10, 11, 137, 20);
		txtGravity.setColumns(10);
		frmnAdaugareAnalize.getContentPane().add(txtGravity);

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
		txtPh.setBounds(10, 42, 137, 20);
		frmnAdaugareAnalize.getContentPane().add(txtPh);

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
		txtOsmo.setBounds(10, 73, 137, 20);
		frmnAdaugareAnalize.getContentPane().add(txtOsmo);

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
		txtCond.setBounds(10, 104, 137, 20);
		frmnAdaugareAnalize.getContentPane().add(txtCond);

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
		txtUreaConcentration.setBounds(10, 135, 137, 20);
		frmnAdaugareAnalize.getContentPane().add(txtUreaConcentration);

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
		txtCalciumConcentration.setBounds(10, 166, 137, 20);
		frmnAdaugareAnalize.getContentPane().add(txtCalciumConcentration);

		JComboBox<String> cbKidneyStones = new JComboBox<String>();
		cbKidneyStones.setToolTipText("");
		cbKidneyStones.setForeground(new Color(192, 192, 192));
		cbKidneyStones.setBackground(new Color(0, 0, 0));
		cbKidneyStones.setBounds(10, 210, 137, 22);
		cbKidneyStones.addItem("Present");
		cbKidneyStones.addItem("Not Present");
		cbKidneyStones.addItem("Unknown yet");
		frmnAdaugareAnalize.getContentPane().add(cbKidneyStones);

		JLabel lblKidneyStonesPresence = new JLabel("Kidney stones presence");
		lblKidneyStonesPresence.setForeground(new Color(192, 192, 192));
		lblKidneyStonesPresence.setBounds(10, 195, 137, 14);
		frmnAdaugareAnalize.getContentPane().add(lblKidneyStonesPresence);

		JButton btnInregistrare = new JButton("Add analyses");
		btnInregistrare.setForeground(Color.WHITE);
		btnInregistrare.setBackground(Color.BLACK);
		btnInregistrare.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnInregistrare.setBounds(10, 299, 137, 27);
		frmnAdaugareAnalize.getContentPane().add(btnInregistrare);

		btnInregistrare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtGravity.getText().matches("[a-zA-Z]+") && !txtGravity.getText().equals("Nume")
						&& txtPh.getText().matches("[a-zA-Z]+") && !txtPh.getText().equals("Prenume")
						&& txtOsmo.getText().matches(
								"^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$")
						&& txtCond.getText().matches("[1-9][0-9]*")) {
					agentAddAnalyses.AddNewUrineAnalyses(txtGravity.getText(), txtPh.getText(), txtOsmo.getText(),
							txtCond.getText(), txtUreaConcentration.getText(), txtCalciumConcentration.getText(),
							(String) cbKidneyStones.getSelectedItem());

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
		txtGravity.setText("Nume");
		txtPh.setText("Prenume");
		txtOsmo.setText("CNP");
		txtCond.setText("SOLD [LEI]");
	}

	// Setarea culorii implicite a textului din textboxuri
	private void setCuloareImplicitControale(Color fg) {
		txtGravity.setForeground(fg);
		txtPh.setForeground(fg);
		txtOsmo.setForeground(fg);
		txtCond.setForeground(fg);
	}

	public void afiseazaInterfata() {
		frmnAdaugareAnalize.setVisible(true);
	}
}
