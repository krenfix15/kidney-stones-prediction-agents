package kidneyAnalysesAgents.AgentsGUI;

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

import kidneyAnalysesAgents.AgentsBehaviour.AgentAnalysesManager;

import javax.swing.JTable;

public class AgentAnalysesManagerGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private AgentAnalysesManager agentAnalysesManager;

	private JFrame frmAnalysesManager;
	private JTable table;

	// Creaza aplicatia agentului de adaugare analize
	public AgentAnalysesManagerGUI(AgentAnalysesManager aAnalysesManager) {
		super(aAnalysesManager.getLocalName());
		agentAnalysesManager = aAnalysesManager;
		initialize();
	}

	// Initializeaza continutul interfetei
	private void initialize() {
		frmAnalysesManager = new JFrame();
		frmAnalysesManager.setTitle("Analyses Manager");
		frmAnalysesManager.getContentPane().setBackground(Color.DARK_GRAY);
		frmAnalysesManager.setBounds(100, 100, 475, 353);
		frmAnalysesManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAnalysesManager.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 10, 441, 296);
		frmAnalysesManager.getContentPane().add(table);

		// Inchide agentul la inchiderea interfetei
		frmAnalysesManager.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				agentAnalysesManager.doDelete();
			}
		});

		setResizable(false);
	}

	public void showInterface() {
		frmAnalysesManager.setVisible(true);
	}
}
