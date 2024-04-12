package kidneyAnalysesAgents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class AnalizeGui extends JFrame {

	private static final long serialVersionUID = 1L;

	// Handle pentru operatiile cu fisiere
	private AdministrareFisier adminFisier;

	private AgentClient agentClient;

	// Regex pentru CNP
	private String regexCNP = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";

	private String operatie;

	private JFrame frame;
	private JTextField txtSuma;
	private JTextField txtSold;
	private JTextField txtCNP;

	/**
	 * Create the application.
	 */
	public AnalizeGui(AgentClient aC) {
		super(aC.getLocalName());
		agentClient = aC;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 309, 237);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Inchide agentul la inchiderea interfetei
		// din butonul din dreapta sus
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				agentClient.doDelete();
			}
		});

		txtCNP = new JTextField();
		txtCNP.setText("CNP");
		txtCNP.setForeground(Color.LIGHT_GRAY);
		txtCNP.setColumns(10);
		txtCNP.setBackground(Color.BLACK);
		txtCNP.setBounds(10, 11, 273, 20);
		frame.getContentPane().add(txtCNP);

		txtCNP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtCNP.getText().equals("CNP")) {
					txtCNP.setText("");
					txtCNP.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtCNP.getText().equals("")) {
					txtCNP.setText("CNP");
					txtCNP.setForeground(Color.lightGray);
				}
			}
		});

		JButton btnCauta = new JButton("Caut\u0103");
		btnCauta.setForeground(Color.WHITE);
		btnCauta.setBackground(Color.BLACK);
		btnCauta.setBounds(10, 29, 273, 23);
		frame.getContentPane().add(btnCauta);

		btnCauta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtCNP.getText().matches(regexCNP)) {
					String cnpCautat = txtCNP.getText();

					try {
						adminFisier = new AdministrareFisier("clienti.txt");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ArrayList<Analize> clienti = adminFisier.GetClienti();

					for (Analize c : clienti) {
						if (c.getCNP().equals(cnpCautat)) {
							txtSold.setText(c.getSold());
						}
					}
				}
			}
		});

		txtSold = new JTextField();
		txtSold.setText("SOLD [LEI]");
		txtSold.setForeground(Color.LIGHT_GRAY);
		txtSold.setEnabled(false);
		txtSold.setColumns(10);
		txtSold.setBackground(Color.BLACK);
		txtSold.setBounds(10, 78, 273, 20);
		frame.getContentPane().add(txtSold);

		txtSold.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtSuma.getText().equals("Suma [LEI]")) {
					txtSuma.setText("");
					txtSuma.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtSuma.getText().equals("")) {
					txtSuma.setText("Suma [LEI]");
					txtSuma.setForeground(Color.lightGray);
				}
			}
		});

		JButton btnAdaugaSold = new JButton("Adaug\u0103");
		btnAdaugaSold.setForeground(Color.GREEN);
		btnAdaugaSold.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdaugaSold.setBackground(Color.BLACK);
		btnAdaugaSold.setBounds(10, 123, 80, 65);
		frame.getContentPane().add(btnAdaugaSold);

		btnAdaugaSold.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtSuma.getText().matches("[1-9][0-9]*") && txtCNP.getText().matches(regexCNP)) {
					operatie = "adaugare";
					agentClient.preluareOperatiuneClient(txtCNP.getText(), Integer.parseInt(txtSuma.getText()),
							operatie);

					System.out.print(
							"CNP: " + txtCNP.getText() + "; Suma: " + txtSuma.getText() + "; Operatie: Adãugare\n");
				}
			}
		});

		txtSuma = new JTextField();
		txtSuma.setText("Suma [LEI]");
		txtSuma.setForeground(Color.LIGHT_GRAY);
		txtSuma.setColumns(10);
		txtSuma.setBackground(Color.BLACK);
		txtSuma.setBounds(100, 145, 93, 20);
		frame.getContentPane().add(txtSuma);

		txtSuma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtSuma.getText().equals("Suma [LEI]")) {
					txtSuma.setText("");
					txtSuma.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtSuma.getText().equals("")) {
					txtSuma.setText("Suma [LEI]");
					txtSuma.setForeground(Color.lightGray);
				}
			}
		});

		JButton btnExtrageSold = new JButton("Extrage");
		btnExtrageSold.setForeground(Color.RED);
		btnExtrageSold.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExtrageSold.setBackground(Color.BLACK);
		btnExtrageSold.setBounds(203, 123, 80, 65);
		frame.getContentPane().add(btnExtrageSold);

		btnExtrageSold.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtSuma.getText().matches("[1-9][0-9]*") && txtCNP.getText().matches(regexCNP)) {
					operatie = "extragere";
					agentClient.preluareOperatiuneClient(txtCNP.getText(), Integer.parseInt(txtSuma.getText()),
							operatie);

					System.out.print(
							"CNP: " + txtCNP.getText() + "; Suma: " + txtSuma.getText() + "; Operatie: Extragere\n");
				}
			}
		});
	}

	public void afiseazaInterfata() {
		frame.setVisible(true);
	}
}
