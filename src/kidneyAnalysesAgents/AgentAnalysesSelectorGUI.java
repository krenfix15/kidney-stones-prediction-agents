package kidneyAnalysesAgents;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AgentAnalysesSelectorGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	// AgentAnalysesSelector
	private AgentAnalysesSelector agentAnalysesSelector;

	// File handle
	private FileAdministrator adminFile;

	private JFrame frmDataSelector;
	private JTextField txtGravityBetween;
	private JTextField txtPhBetween;
	private JTextField txtOsmolarityBetween;
	private JTextField txtConductivityBetween;
	private JTextField txtUreaBetween;
	private JTextField txtCalciumBetween;
	private JTextField txtMinimumGravity;
	private JTextField txtMaximumGravity;
	private JTextField txtMinimumPH;
	private JTextField txtMinimumOsmo;
	private JTextField txtMinimumCond;
	private JTextField txtMinimumUrea;
	private JTextField txtMinimumCalcium;
	private JTextField txtMaximumPH;
	private JTextField txtMaximumOsmo;
	private JTextField txtMaximumCond;
	private JTextField txtMaximumUrea;
	private JTextField txtMaximumCalcium;
	private JCheckBox chckbxPH;
	private JCheckBox chckbxOsmo;
	private JCheckBox chckbxCond;
	private JCheckBox chckbxUrea;
	private JCheckBox chckbxCalcium;
	private JLabel lblNewLabel;

	/**
	 * Create the application.
	 */
	public AgentAnalysesSelectorGUI(AgentAnalysesSelector aAS) {
		super(aAS.getLocalName());
		agentAnalysesSelector = aAS;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDataSelector = new JFrame();
		frmDataSelector.setTitle("Analysis Data Selector");
		frmDataSelector.getContentPane().setBackground(Color.DARK_GRAY);
		frmDataSelector.getContentPane().setLayout(null);
		
		txtGravityBetween = new JTextField();
		txtGravityBetween.setBackground(SystemColor.desktop);
		txtGravityBetween.setForeground(SystemColor.activeCaptionBorder);
		txtGravityBetween.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtGravityBetween.setText("Gravity between");
		txtGravityBetween.setEnabled(false);
		txtGravityBetween.setEditable(false);
		txtGravityBetween.setBounds(10, 65, 141, 22);
		frmDataSelector.getContentPane().add(txtGravityBetween);
		txtGravityBetween.setColumns(10);
		
		txtPhBetween = new JTextField();
		txtPhBetween.setText("pH between");
		txtPhBetween.setForeground(SystemColor.activeCaptionBorder);
		txtPhBetween.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtPhBetween.setEnabled(false);
		txtPhBetween.setEditable(false);
		txtPhBetween.setColumns(10);
		txtPhBetween.setBackground(SystemColor.desktop);
		txtPhBetween.setBounds(10, 97, 141, 22);
		frmDataSelector.getContentPane().add(txtPhBetween);
		
		txtOsmolarityBetween = new JTextField();
		txtOsmolarityBetween.setText("Osmolarity between");
		txtOsmolarityBetween.setForeground(SystemColor.activeCaptionBorder);
		txtOsmolarityBetween.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtOsmolarityBetween.setEnabled(false);
		txtOsmolarityBetween.setEditable(false);
		txtOsmolarityBetween.setColumns(10);
		txtOsmolarityBetween.setBackground(SystemColor.desktop);
		txtOsmolarityBetween.setBounds(10, 129, 141, 22);
		frmDataSelector.getContentPane().add(txtOsmolarityBetween);
		
		txtConductivityBetween = new JTextField();
		txtConductivityBetween.setText("Conductivity between");
		txtConductivityBetween.setForeground(SystemColor.activeCaptionBorder);
		txtConductivityBetween.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtConductivityBetween.setEnabled(false);
		txtConductivityBetween.setEditable(false);
		txtConductivityBetween.setColumns(10);
		txtConductivityBetween.setBackground(SystemColor.desktop);
		txtConductivityBetween.setBounds(10, 161, 141, 22);
		frmDataSelector.getContentPane().add(txtConductivityBetween);
		
		txtUreaBetween = new JTextField();
		txtUreaBetween.setText("Urea between");
		txtUreaBetween.setForeground(SystemColor.activeCaptionBorder);
		txtUreaBetween.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtUreaBetween.setEnabled(false);
		txtUreaBetween.setEditable(false);
		txtUreaBetween.setColumns(10);
		txtUreaBetween.setBackground(SystemColor.desktop);
		txtUreaBetween.setBounds(10, 195, 141, 22);
		frmDataSelector.getContentPane().add(txtUreaBetween);
		
		txtCalciumBetween = new JTextField();
		txtCalciumBetween.setText("Calcium between");
		txtCalciumBetween.setForeground(SystemColor.activeCaptionBorder);
		txtCalciumBetween.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCalciumBetween.setEnabled(false);
		txtCalciumBetween.setEditable(false);
		txtCalciumBetween.setColumns(10);
		txtCalciumBetween.setBackground(SystemColor.desktop);
		txtCalciumBetween.setBounds(10, 227, 141, 22);
		frmDataSelector.getContentPane().add(txtCalciumBetween);
		
		txtMinimumGravity = new JTextField();
		txtMinimumGravity.setEnabled(false);
		txtMinimumGravity.setEditable(false);
		txtMinimumGravity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMinimumGravity.getText().toLowerCase().contains("minimum")) {
					txtMinimumGravity.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMinimumGravity.getText().isEmpty()) {
					txtMinimumGravity.setText("minimum");
				}
			}
		});
		txtMinimumGravity.setText("minimum");
		txtMinimumGravity.setBackground(SystemColor.desktop);
		txtMinimumGravity.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumGravity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumGravity.setBounds(200, 65, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumGravity);
		txtMinimumGravity.setColumns(10);
		
		txtMaximumGravity = new JTextField();
		txtMaximumGravity.setEnabled(false);
		txtMaximumGravity.setEditable(false);
		txtMaximumGravity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMaximumGravity.getText().toLowerCase().contains("maximum")) {
					txtMaximumGravity.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMaximumGravity.getText().isEmpty()) {
					txtMaximumGravity.setText("maximum");
				}
			}
		});
		txtMaximumGravity.setText("maximum");
		txtMaximumGravity.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumGravity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumGravity.setColumns(10);
		txtMaximumGravity.setBackground(SystemColor.desktop);
		txtMaximumGravity.setBounds(316, 65, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumGravity);
		
		JCheckBox chckbxGravity = new JCheckBox("ALL");
		chckbxGravity.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				boolean chckbxSelected = chckbxGravity.isSelected();
				txtMinimumGravity.setEnabled(!chckbxSelected);
				txtMinimumGravity.setEditable(!chckbxSelected);
				txtMaximumGravity.setEnabled(!chckbxSelected);
				txtMaximumGravity.setEditable(!chckbxSelected);
			}
		});
		chckbxGravity.setSelected(true);
		chckbxGravity.setForeground(SystemColor.activeCaptionBorder);
		chckbxGravity.setBackground(SystemColor.desktop);
		chckbxGravity.setBounds(425, 67, 48, 21);
		frmDataSelector.getContentPane().add(chckbxGravity);
		
		txtMinimumPH = new JTextField();
		txtMinimumPH.setEnabled(false);
		txtMinimumPH.setEditable(false);
		txtMinimumPH.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMinimumPH.getText().toLowerCase().contains("minimum")) {
					txtMinimumPH.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMinimumPH.getText().isEmpty()) {
					txtMinimumPH.setText("minimum");
				}
			}
		});
		txtMinimumPH.setText("minimum");
		txtMinimumPH.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumPH.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumPH.setColumns(10);
		txtMinimumPH.setBackground(SystemColor.desktop);
		txtMinimumPH.setBounds(200, 97, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumPH);
		
		txtMinimumOsmo = new JTextField();
		txtMinimumOsmo.setEnabled(false);
		txtMinimumOsmo.setEditable(false);
		txtMinimumOsmo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMinimumOsmo.getText().toLowerCase().contains("minimum")) {
					txtMinimumOsmo.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMinimumOsmo.getText().isEmpty()) {
					txtMinimumOsmo.setText("minimum");
				}
			}
		});
		txtMinimumOsmo.setText("minimum");
		txtMinimumOsmo.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumOsmo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumOsmo.setColumns(10);
		txtMinimumOsmo.setBackground(SystemColor.desktop);
		txtMinimumOsmo.setBounds(200, 129, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumOsmo);
		
		txtMinimumCond = new JTextField();
		txtMinimumCond.setEnabled(false);
		txtMinimumCond.setEditable(false);
		txtMinimumCond.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMinimumCond.getText().toLowerCase().contains("minimum")) {
					txtMinimumCond.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMinimumCond.getText().isEmpty()) {
					txtMinimumCond.setText("minimum");
				}
			}
		});
		txtMinimumCond.setText("minimum");
		txtMinimumCond.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumCond.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumCond.setColumns(10);
		txtMinimumCond.setBackground(SystemColor.desktop);
		txtMinimumCond.setBounds(200, 161, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumCond);
		
		txtMinimumUrea = new JTextField();
		txtMinimumUrea.setEnabled(false);
		txtMinimumUrea.setEditable(false);
		txtMinimumUrea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMinimumUrea.getText().toLowerCase().contains("minimum")) {
					txtMinimumUrea.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMinimumUrea.getText().isEmpty()) {
					txtMinimumUrea.setText("minimum");
				}
			}
		});
		txtMinimumUrea.setText("minimum");
		txtMinimumUrea.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumUrea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumUrea.setColumns(10);
		txtMinimumUrea.setBackground(SystemColor.desktop);
		txtMinimumUrea.setBounds(200, 195, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumUrea);
		
		txtMinimumCalcium = new JTextField();
		txtMinimumCalcium.setEditable(false);
		txtMinimumCalcium.setEnabled(false);
		txtMinimumCalcium.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMinimumCalcium.getText().toLowerCase().contains("minimum")) {
					txtMinimumCalcium.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMinimumCalcium.getText().isEmpty()) {
					txtMinimumCalcium.setText("minimum");
				}
			}
		});
		txtMinimumCalcium.setText("minimum");
		txtMinimumCalcium.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumCalcium.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumCalcium.setColumns(10);
		txtMinimumCalcium.setBackground(SystemColor.desktop);
		txtMinimumCalcium.setBounds(200, 227, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumCalcium);
		
		txtMaximumPH = new JTextField();
		txtMaximumPH.setEnabled(false);
		txtMaximumPH.setEditable(false);
		txtMaximumPH.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMaximumPH.getText().toLowerCase().contains("maximum")) {
					txtMaximumPH.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMaximumPH.getText().isEmpty()) {
					txtMaximumPH.setText("maximum");
				}
			}
		});
		txtMaximumPH.setText("maximum");
		txtMaximumPH.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumPH.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumPH.setColumns(10);
		txtMaximumPH.setBackground(SystemColor.desktop);
		txtMaximumPH.setBounds(316, 97, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumPH);
		
		txtMaximumOsmo = new JTextField();
		txtMaximumOsmo.setEnabled(false);
		txtMaximumOsmo.setEditable(false);
		txtMaximumOsmo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMaximumOsmo.getText().toLowerCase().contains("maximum")) {
					txtMaximumOsmo.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMaximumOsmo.getText().isEmpty()) {
					txtMaximumOsmo.setText("maximum");
				}
			}
		});
		txtMaximumOsmo.setText("maximum");
		txtMaximumOsmo.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumOsmo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumOsmo.setColumns(10);
		txtMaximumOsmo.setBackground(SystemColor.desktop);
		txtMaximumOsmo.setBounds(316, 129, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumOsmo);
		
		txtMaximumCond = new JTextField();
		txtMaximumCond.setEnabled(false);
		txtMaximumCond.setEditable(false);
		txtMaximumCond.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMaximumCond.getText().toLowerCase().contains("maximum")) {
					txtMaximumCond.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMaximumCond.getText().isEmpty()) {
					txtMaximumCond.setText("maximum");
				}
			}
		});
		txtMaximumCond.setText("maximum");
		txtMaximumCond.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumCond.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumCond.setColumns(10);
		txtMaximumCond.setBackground(SystemColor.desktop);
		txtMaximumCond.setBounds(316, 161, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumCond);
		
		txtMaximumUrea = new JTextField();
		txtMaximumUrea.setEnabled(false);
		txtMaximumUrea.setEditable(false);
		txtMaximumUrea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMaximumUrea.getText().toLowerCase().contains("maximum")) {
					txtMaximumUrea.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMaximumUrea.getText().isEmpty()) {
					txtMaximumUrea.setText("maximum");
				}
			}
		});
		txtMaximumUrea.setText("maximum");
		txtMaximumUrea.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumUrea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumUrea.setColumns(10);
		txtMaximumUrea.setBackground(SystemColor.desktop);
		txtMaximumUrea.setBounds(316, 195, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumUrea);
		
		txtMaximumCalcium = new JTextField();
		txtMaximumCalcium.setEnabled(false);
		txtMaximumCalcium.setEditable(false);
		txtMaximumCalcium.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtMaximumCalcium.getText().toLowerCase().contains("maximum")) {
					txtMaximumCalcium.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (txtMaximumCalcium.getText().isEmpty()) {
					txtMaximumCalcium.setText("maximum");
				}
			}
		});
		txtMaximumCalcium.setText("maximum");
		txtMaximumCalcium.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumCalcium.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumCalcium.setColumns(10);
		txtMaximumCalcium.setBackground(SystemColor.desktop);
		txtMaximumCalcium.setBounds(316, 227, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumCalcium);
		
		chckbxPH = new JCheckBox("ALL");
		chckbxPH.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				boolean chckbxSelected = chckbxPH.isSelected();
				txtMinimumPH.setEnabled(!chckbxSelected);
				txtMinimumPH.setEditable(!chckbxSelected);
				txtMaximumPH.setEnabled(!chckbxSelected);
				txtMaximumPH.setEditable(!chckbxSelected);
			}
		});
		chckbxPH.setSelected(true);
		chckbxPH.setForeground(SystemColor.activeCaptionBorder);
		chckbxPH.setBackground(SystemColor.desktop);
		chckbxPH.setBounds(425, 97, 48, 21);
		frmDataSelector.getContentPane().add(chckbxPH);
		
		chckbxOsmo = new JCheckBox("ALL");
		chckbxOsmo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				boolean chckbxSelected = chckbxOsmo.isSelected();
				txtMinimumOsmo.setEnabled(!chckbxSelected);
				txtMinimumOsmo.setEditable(!chckbxSelected);
				txtMaximumOsmo.setEnabled(!chckbxSelected);
				txtMaximumOsmo.setEditable(!chckbxSelected);
			}
		});
		chckbxOsmo.setSelected(true);
		chckbxOsmo.setForeground(SystemColor.activeCaptionBorder);
		chckbxOsmo.setBackground(SystemColor.desktop);
		chckbxOsmo.setBounds(425, 129, 48, 21);
		frmDataSelector.getContentPane().add(chckbxOsmo);
		
		chckbxCond = new JCheckBox("ALL");
		chckbxCond.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				boolean chckbxSelected = chckbxCond.isSelected();
				txtMinimumCond.setEnabled(!chckbxSelected);
				txtMinimumCond.setEditable(!chckbxSelected);
				txtMaximumCond.setEnabled(!chckbxSelected);
				txtMaximumCond.setEditable(!chckbxSelected);
			}
		});
		chckbxCond.setSelected(true);
		chckbxCond.setForeground(SystemColor.activeCaptionBorder);
		chckbxCond.setBackground(SystemColor.desktop);
		chckbxCond.setBounds(425, 161, 48, 21);
		frmDataSelector.getContentPane().add(chckbxCond);
		
		chckbxUrea = new JCheckBox("ALL");
		chckbxUrea.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				boolean chckbxSelected = chckbxUrea.isSelected();
				txtMinimumUrea.setEnabled(!chckbxSelected);
				txtMinimumUrea.setEditable(!chckbxSelected);
				txtMaximumUrea.setEnabled(!chckbxSelected);
				txtMaximumUrea.setEditable(!chckbxSelected);
			}
		});
		chckbxUrea.setSelected(true);
		chckbxUrea.setForeground(SystemColor.activeCaptionBorder);
		chckbxUrea.setBackground(SystemColor.desktop);
		chckbxUrea.setBounds(425, 193, 48, 21);
		frmDataSelector.getContentPane().add(chckbxUrea);
		
		chckbxCalcium = new JCheckBox("ALL");
		chckbxCalcium.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				boolean chckbxSelected = chckbxCalcium.isSelected();
				txtMinimumCalcium.setEnabled(!chckbxSelected);
				txtMinimumCalcium.setEditable(!chckbxSelected);
				txtMaximumCalcium.setEnabled(!chckbxSelected);
				txtMaximumCalcium.setEditable(!chckbxSelected);
			}
		});
		chckbxCalcium.setSelected(true);
		chckbxCalcium.setForeground(SystemColor.activeCaptionBorder);
		chckbxCalcium.setBackground(SystemColor.desktop);
		chckbxCalcium.setBounds(425, 227, 48, 21);
		frmDataSelector.getContentPane().add(chckbxCalcium);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBackground(SystemColor.desktop);
		btnSelect.setForeground(SystemColor.activeCaptionBorder);
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSelect.setBounds(10, 284, 463, 25);
		frmDataSelector.getContentPane().add(btnSelect);
		
		lblNewLabel = new JLabel("Select the urine analyses with these intervals for the parameters: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setBounds(10, 10, 464, 25);
		frmDataSelector.getContentPane().add(lblNewLabel);

		// Close the agent interface
		frmDataSelector.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				agentAnalysesSelector.doDelete();
			}
		});
		frmDataSelector.setBounds(100, 100, 498, 357);
		frmDataSelector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
	}

	public void showInterface() {
		frmDataSelector.setVisible(true);
	}

}
