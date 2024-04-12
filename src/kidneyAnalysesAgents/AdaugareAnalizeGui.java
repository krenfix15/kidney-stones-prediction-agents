package kidneyAnalysesAgents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class AdaugareAnalizeGui extends JFrame {

	private static final long serialVersionUID = 1L;

	private AgentAdaugareAnalize adaugareAnalizeAgent;

	private JFrame frmnAdaugareAnalize;

	private JTextField txtGravity;
	private JTextField txtPh;
	private JTextField txtOsmo;
	private JTextField txtCond;
	private JTextField txtUrea;
	private JTextField txtCalc;

	// Creaza aplicatia agentului de adaugare analize
	public AdaugareAnalizeGui(AgentAdaugareAnalize agentAdaugareAnalize) {
		super(agentAdaugareAnalize.getLocalName());
		adaugareAnalizeAgent = agentAdaugareAnalize;
		initialize();
	}

	// Initializeaza continutul interfetei
	private void initialize() {
		frmnAdaugareAnalize = new JFrame();
		frmnAdaugareAnalize.setTitle("AdaugareAnalize");
		frmnAdaugareAnalize.getContentPane().setBackground(Color.DARK_GRAY);
		frmnAdaugareAnalize.setBounds(100, 100, 309, 237);
		frmnAdaugareAnalize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmnAdaugareAnalize.getContentPane().setLayout(null);

		// Inchide agentul la inchiderea interfetei
		frmnAdaugareAnalize.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				adaugareAnalizeAgent.doDelete();
			}
		});

		txtGravity = new JTextField();
		txtGravity.setBackground(Color.BLACK);
		txtGravity.setForeground(Color.LIGHT_GRAY);
		txtGravity.setText("Nume");
		txtGravity.setBounds(10, 11, 273, 20);
		txtGravity.setColumns(10);
		frmnAdaugareAnalize.getContentPane().add(txtGravity);

		txtGravity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtGravity.getText().equals("Nume")) {
					txtGravity.setText("");
					txtGravity.setForeground(Color.white);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtGravity.getText().equals("")) {
					txtGravity.setText("Nume");
					txtGravity.setForeground(Color.lightGray);
				}
			}
		});

		txtGravity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtGravity.getText().equals("Nume")) {
					txtGravity.setText("");
					txtGravity.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtGravity.getText().equals("")) {
					txtGravity.setText("Nume");
					txtGravity.setForeground(Color.lightGray);
				}
			}
		});

		txtPh = new JTextField();
		txtPh.setText("Prenume");
		txtPh.setForeground(Color.LIGHT_GRAY);
		txtPh.setColumns(10);
		txtPh.setBackground(Color.BLACK);
		txtPh.setBounds(10, 42, 273, 20);
		frmnAdaugareAnalize.getContentPane().add(txtPh);

		txtPh.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtPh.getText().equals("Prenume")) {
					txtPh.setText("");
					txtPh.setForeground(Color.white);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtPh.getText().equals("")) {
					txtPh.setText("Prenume");
					txtPh.setForeground(Color.lightGray);
				}
			}
		});

		txtPh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtPh.getText().equals("Prenume")) {
					txtPh.setText("");
					txtPh.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtPh.getText().equals("")) {
					txtPh.setText("Prenume");
					txtPh.setForeground(Color.lightGray);
				}
			}
		});

		txtOsmo = new JTextField();
		txtOsmo.setText("CNP");
		txtOsmo.setForeground(Color.LIGHT_GRAY);
		txtOsmo.setColumns(10);
		txtOsmo.setBackground(Color.BLACK);
		txtOsmo.setBounds(10, 73, 273, 20);
		frmnAdaugareAnalize.getContentPane().add(txtOsmo);

		txtOsmo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtOsmo.getText().equals("CNP")) {
					txtOsmo.setText("");
					txtOsmo.setForeground(Color.white);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtOsmo.getText().equals("")) {
					txtOsmo.setText("CNP");
					txtOsmo.setForeground(Color.lightGray);
				}
			}
		});

		txtOsmo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtOsmo.getText().equals("CNP")) {
					txtOsmo.setText("");
					txtOsmo.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtOsmo.getText().equals("")) {
					txtOsmo.setText("CNP");
					txtOsmo.setForeground(Color.lightGray);
				}
			}
		});

		txtCond = new JTextField();
		txtCond.setText("SOLD [LEI]");
		txtCond.setForeground(Color.LIGHT_GRAY);
		txtCond.setColumns(10);
		txtCond.setBackground(Color.BLACK);
		txtCond.setBounds(10, 104, 273, 20);
		frmnAdaugareAnalize.getContentPane().add(txtCond);

		txtCond.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtCond.getText().equals("SOLD [LEI]")) {
					txtCond.setText("");
					txtCond.setForeground(Color.white);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtCond.getText().equals("")) {
					txtCond.setText("SOLD [LEI]");
					txtCond.setForeground(Color.lightGray);
				}
			}
		});

		txtCond.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtCond.getText().equals("SOLD [LEI]")) {
					txtCond.setText("");
					txtCond.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtCond.getText().equals("")) {
					txtCond.setText("SOLD [LEI]");
					txtCond.setForeground(Color.lightGray);
				}
			}
		});

		JButton btnInregistrare = new JButton("\u00CEnregistrare");
		btnInregistrare.setForeground(Color.WHITE);
		btnInregistrare.setBackground(Color.BLACK);
		btnInregistrare.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnInregistrare.setBounds(10, 158, 273, 27);
		frmnAdaugareAnalize.getContentPane().add(btnInregistrare);

		btnInregistrare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtGravity.getText().matches("[a-zA-Z]+") && !txtGravity.getText().equals("Nume")
						&& txtPh.getText().matches("[a-zA-Z]+") && !txtPh.getText().equals("Prenume")
						&& txtOsmo.getText().matches(
								"^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$")
						&& txtCond.getText().matches("[1-9][0-9]*")) {
					adaugareAnalizeAgent.inregistreazaDateleClientului(txtGravity.getText(), txtPh.getText(),
							txtOsmo.getText(), txtCond.getText());

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
