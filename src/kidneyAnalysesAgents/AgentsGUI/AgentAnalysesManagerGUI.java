package kidneyAnalysesAgents.AgentsGUI;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import kidneyAnalysesAgents.AgentsBehaviour.AgentAnalysesManager;
import kidneyAnalysesAgents.Helpers.Analysis;
import kidneyAnalysesAgents.Helpers.FileAdministrator;

public class AgentAnalysesManagerGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private String columns[] = { "index", "gravity", "ph", "osmolarity", "conductivity", "urea", "calcium",
			"kidneystonespresence" };

	private FileAdministrator fileAdmin;

	private AgentAnalysesManager agentAnalysesManager;

	private JFrame frmAnalysesManager;
	private DefaultTableModel tableModel;
	private JTable table;
	private JToggleButton tglBtnAllSelectedAnalyses;

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
		frmAnalysesManager.setBounds(100, 100, 909, 427);
		frmAnalysesManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAnalysesManager.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 873, 334);
		frmAnalysesManager.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		tglBtnAllSelectedAnalyses = new JToggleButton("Switch between all analyses and selected analyses");
		tglBtnAllSelectedAnalyses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tglBtnAllSelectedAnalyses.isSelected()) {
					updateAllAnalysesTable();
					table.requestFocus();
				} else {
					updateSelectedAnalysesTable();
					table.requestFocus();
				}
			}
		});
		tglBtnAllSelectedAnalyses.setBounds(10, 354, 873, 23);
		frmAnalysesManager.getContentPane().add(tglBtnAllSelectedAnalyses);
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tglBtnAllSelectedAnalyses.isSelected()) {
					updateAllAnalysesTable();
				} else {
					updateSelectedAnalysesTable();
				}
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

	public void updateAllAnalysesTable() {
		tableModel = new DefaultTableModel(columns, 0);

		try {
			fileAdmin = new FileAdministrator("urineAnalyses.csv");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<Analysis> analyses = fileAdmin.GetAllAnalyses();

		int idx = 1; // Start index from 1
		for (Analysis a : analyses) {
			String[] params = a.toString().split(a.getSeparator());
			// Insert index as the first column
			String[] rowWithIndex = new String[params.length + 1];
			rowWithIndex[0] = Integer.toString(idx++); // Increment index after each row
			System.arraycopy(params, 0, rowWithIndex, 1, params.length);
			tableModel.addRow(rowWithIndex);
		}

		table.setModel(tableModel);
	}

	public void updateSelectedAnalysesTable() {
		tableModel = new DefaultTableModel(columns, 0);

		try {
			fileAdmin = new FileAdministrator("selectedUrineAnalyses.csv");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<Analysis> analyses = fileAdmin.GetSelectedAnalyses();

		int idx = 1; // Start index from 1
		for (Analysis a : analyses) {
			String[] params = a.toString().split(a.getSeparator());
			// Insert index as the first column
			String[] rowWithIndex = new String[params.length + 1];
			rowWithIndex[0] = Integer.toString(idx++); // Increment index after each row
			System.arraycopy(params, 0, rowWithIndex, 1, params.length);
			tableModel.addRow(rowWithIndex);
		}

		table.setModel(tableModel);
	}
}
