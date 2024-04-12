package kidneyAnalysesAgents;

import java.awt.Color;
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

public class BazaDeDateAnalizeGui extends JFrame {
	private static final long serialVersionUID = 1L;

	// Agent banca
	private AgentBanca agentBanca;

	// Handle pentru operatiile cu fisiere
	private AdministrareFisier adminFisier;

	// Regex pentru CNP
	private String regexCNP = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";

	private JFrame frmFilial;

	private JTextField txtCnp;
	private JTextField txtNume;
	private JTextField txtPrenume;
	private JTextField txtSold;

	/**
	 * Create the application.
	 */
	public BazaDeDateAnalizeGui(AgentBanca aB) {
		super(aB.getLocalName());
		agentBanca = aB;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFilial = new JFrame();
		frmFilial.setTitle("Filial\u0103");
		frmFilial.getContentPane().setBackground(Color.DARK_GRAY);
		frmFilial.getContentPane().setLayout(null);

		// Inchide agentul la inchiderea interfetei
		// din butonul din dreapta sus
		frmFilial.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				agentBanca.doDelete();
			}
		});

		txtCnp = new JTextField();
		txtCnp.setText("CNP");
		txtCnp.setForeground(Color.LIGHT_GRAY);
		txtCnp.setColumns(10);
		txtCnp.setBackground(Color.BLACK);
		txtCnp.setBounds(10, 11, 273, 20);
		frmFilial.getContentPane().add(txtCnp);

		txtCnp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtCnp.getText().equals("CNP")) {
					txtCnp.setText("");
					txtCnp.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtCnp.getText().equals("")) {
					txtCnp.setText("CNP");
					txtCnp.setForeground(Color.lightGray);
				}
			}
		});

		txtNume = new JTextField();
		txtNume.setEnabled(false);
		txtNume.setText("Nume");
		txtNume.setForeground(Color.LIGHT_GRAY);
		txtNume.setColumns(10);
		txtNume.setBackground(Color.BLACK);
		txtNume.setBounds(10, 75, 273, 20);
		frmFilial.getContentPane().add(txtNume);

		txtPrenume = new JTextField();
		txtPrenume.setEnabled(false);
		txtPrenume.setText("Prenume");
		txtPrenume.setForeground(Color.LIGHT_GRAY);
		txtPrenume.setColumns(10);
		txtPrenume.setBackground(Color.BLACK);
		txtPrenume.setBounds(10, 106, 273, 20);
		frmFilial.getContentPane().add(txtPrenume);

		txtSold = new JTextField();
		txtSold.setEnabled(false);
		txtSold.setText("SOLD [LEI]");
		txtSold.setForeground(Color.LIGHT_GRAY);
		txtSold.setColumns(10);
		txtSold.setBackground(Color.BLACK);
		txtSold.setBounds(10, 137, 273, 20);
		frmFilial.getContentPane().add(txtSold);

		JButton btnCauta = new JButton("Caut\u0103");

		btnCauta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtCnp.getText().matches(regexCNP)) {
					String cnpCautat = txtCnp.getText();

					try {
						adminFisier = new AdministrareFisier("clienti.txt");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ArrayList<Analize> clienti = adminFisier.GetClienti();

					for (Analize c : clienti) {
						if (c.getCNP().equals(cnpCautat)) {
							txtNume.setText(c.getNume());
							txtPrenume.setText(c.getPrenume());
							txtSold.setText(c.getSold());
						}
					}
				}
			}
		});

		btnCauta.setForeground(Color.WHITE);
		btnCauta.setBackground(Color.BLACK);
		btnCauta.setBounds(10, 29, 273, 23);
		frmFilial.getContentPane().add(btnCauta);
		frmFilial.setBounds(100, 100, 310, 205);
		frmFilial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
	}

	public void afiseazaInterfata() {
		frmFilial.setVisible(true);
	}
}
