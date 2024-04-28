package kidneyAnalysesAgents.AgentsGUI;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import kidneyAnalysesAgents.AgentsBehaviour.AgentAnalysesManager;
import kidneyAnalysesAgents.Helpers.Analysis;
import kidneyAnalysesAgents.Helpers.FileAdministrator;

import javax.swing.JTable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JScrollPane;

public class AgentAnalysesManagerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private String columns[] = {"gravity","ph","osmolarity","conductivity","urea","calcium","kidneystonespresence"};
	
	private FileAdministrator fileAdmin;
	
	private AgentAnalysesManager agentAnalysesManager;

	private JFrame frmAnalysesManager;
	private DefaultTableModel tableModel;
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
		frmAnalysesManager.setBounds(100, 100, 827, 427);
		frmAnalysesManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAnalysesManager.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 793, 370);
		frmAnalysesManager.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
					updateAnalysesTable();
				}
		});

		// Close the agent
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
	
	public void updateAnalysesTable() {
		tableModel = new DefaultTableModel(columns, 0);
		
		try {
			fileAdmin = new FileAdministrator("urineAnalyses.csv");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<Analysis> analyses = fileAdmin.GetAllAnalyses();

		for (Analysis a : analyses) {
				String[] params = a.toString().split(a.getSeparator());
				tableModel.addRow(params);
		}
		
		table.setModel(tableModel);
	}
}
