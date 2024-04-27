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
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JCheckBox;
import javax.swing.Box;
import javax.swing.JPanel;

public class AnalysesSelectorGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	// Agent banca
	private AgentAnalysesSelector agentBanca;

	// Handle pentru operatiile cu fisiere
	private FileAdministrator adminFisier;

	// Regex pentru CNP
	private String regexCNP = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";

	private JFrame frmDataSelector;
	private JTextField txtSelectTheInterval;
	private JTextField txtGravityBetween;
	private JTextField txtPhBetween;
	private JTextField txtOsmolarityBetween;
	private JTextField txtConductivityBetween;
	private JTextField txtUreaBetween;
	private JTextField txtCalciumBetween;
	private JTextField txtMinimumGravity;
	private JTextField txtMaximumGravity;
	private JTextField txtAnd;
	private JTextField txtMinimumPH;
	private JTextField txtMinimumOsmo;
	private JTextField txtMinimumCond;
	private JTextField txtMinimumUrea;
	private JTextField txtMinimumCalcium;
	private JTextField txtAnd2;
	private JTextField txtAnd3;
	private JTextField txtAnd4;
	private JTextField txtAnd5;
	private JTextField txtAnd6;
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

	/**
	 * Create the application.
	 */
	public AnalysesSelectorGUI(AgentAnalysesSelector aB) {
		super(aB.getLocalName());
		agentBanca = aB;
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
		
		txtSelectTheInterval = new JTextField();
		txtSelectTheInterval.setEditable(false);
		txtSelectTheInterval.setEnabled(false);
		txtSelectTheInterval.setBackground(SystemColor.desktop);
		txtSelectTheInterval.setForeground(SystemColor.activeCaptionBorder);
		txtSelectTheInterval.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSelectTheInterval.setText("Select the urine analyses with these intervals for the parameters: ");
		txtSelectTheInterval.setBounds(10, 10, 463, 25);
		frmDataSelector.getContentPane().add(txtSelectTheInterval);
		txtSelectTheInterval.setColumns(10);
		
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
		txtMinimumGravity.setText("minimum");
		txtMinimumGravity.setBackground(SystemColor.desktop);
		txtMinimumGravity.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumGravity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumGravity.setBounds(179, 65, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumGravity);
		txtMinimumGravity.setColumns(10);
		
		txtMaximumGravity = new JTextField();
		txtMaximumGravity.setText("maximum");
		txtMaximumGravity.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumGravity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumGravity.setColumns(10);
		txtMaximumGravity.setBackground(SystemColor.desktop);
		txtMaximumGravity.setBounds(332, 65, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumGravity);
		
		txtAnd = new JTextField();
		txtAnd.setEditable(false);
		txtAnd.setEnabled(false);
		txtAnd.setText("and");
		txtAnd.setForeground(SystemColor.activeCaptionBorder);
		txtAnd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAnd.setColumns(10);
		txtAnd.setBackground(SystemColor.desktop);
		txtAnd.setBounds(274, 65, 31, 22);
		frmDataSelector.getContentPane().add(txtAnd);
		
		JCheckBox chckbxGravity = new JCheckBox("ALL");
		chckbxGravity.setSelected(true);
		chckbxGravity.setForeground(SystemColor.activeCaptionBorder);
		chckbxGravity.setBackground(SystemColor.desktop);
		chckbxGravity.setBounds(425, 67, 48, 21);
		frmDataSelector.getContentPane().add(chckbxGravity);
		
		txtMinimumPH = new JTextField();
		txtMinimumPH.setText("minimum");
		txtMinimumPH.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumPH.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumPH.setColumns(10);
		txtMinimumPH.setBackground(SystemColor.desktop);
		txtMinimumPH.setBounds(179, 97, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumPH);
		
		txtMinimumOsmo = new JTextField();
		txtMinimumOsmo.setText("minimum");
		txtMinimumOsmo.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumOsmo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumOsmo.setColumns(10);
		txtMinimumOsmo.setBackground(SystemColor.desktop);
		txtMinimumOsmo.setBounds(179, 129, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumOsmo);
		
		txtMinimumCond = new JTextField();
		txtMinimumCond.setText("minimum");
		txtMinimumCond.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumCond.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumCond.setColumns(10);
		txtMinimumCond.setBackground(SystemColor.desktop);
		txtMinimumCond.setBounds(179, 161, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumCond);
		
		txtMinimumUrea = new JTextField();
		txtMinimumUrea.setText("minimum");
		txtMinimumUrea.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumUrea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumUrea.setColumns(10);
		txtMinimumUrea.setBackground(SystemColor.desktop);
		txtMinimumUrea.setBounds(179, 195, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumUrea);
		
		txtMinimumCalcium = new JTextField();
		txtMinimumCalcium.setText("minimum");
		txtMinimumCalcium.setForeground(SystemColor.activeCaptionBorder);
		txtMinimumCalcium.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMinimumCalcium.setColumns(10);
		txtMinimumCalcium.setBackground(SystemColor.desktop);
		txtMinimumCalcium.setBounds(179, 227, 67, 22);
		frmDataSelector.getContentPane().add(txtMinimumCalcium);
		
		txtAnd2 = new JTextField();
		txtAnd2.setText("and");
		txtAnd2.setForeground(SystemColor.activeCaptionBorder);
		txtAnd2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAnd2.setEnabled(false);
		txtAnd2.setEditable(false);
		txtAnd2.setColumns(10);
		txtAnd2.setBackground(SystemColor.desktop);
		txtAnd2.setBounds(274, 97, 31, 22);
		frmDataSelector.getContentPane().add(txtAnd2);
		
		txtAnd3 = new JTextField();
		txtAnd3.setText("and");
		txtAnd3.setForeground(SystemColor.activeCaptionBorder);
		txtAnd3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAnd3.setEnabled(false);
		txtAnd3.setEditable(false);
		txtAnd3.setColumns(10);
		txtAnd3.setBackground(SystemColor.desktop);
		txtAnd3.setBounds(274, 129, 31, 22);
		frmDataSelector.getContentPane().add(txtAnd3);
		
		txtAnd4 = new JTextField();
		txtAnd4.setText("and");
		txtAnd4.setForeground(SystemColor.activeCaptionBorder);
		txtAnd4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAnd4.setEnabled(false);
		txtAnd4.setEditable(false);
		txtAnd4.setColumns(10);
		txtAnd4.setBackground(SystemColor.desktop);
		txtAnd4.setBounds(274, 161, 31, 22);
		frmDataSelector.getContentPane().add(txtAnd4);
		
		txtAnd5 = new JTextField();
		txtAnd5.setText("and");
		txtAnd5.setForeground(SystemColor.activeCaptionBorder);
		txtAnd5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAnd5.setEnabled(false);
		txtAnd5.setEditable(false);
		txtAnd5.setColumns(10);
		txtAnd5.setBackground(SystemColor.desktop);
		txtAnd5.setBounds(274, 195, 31, 22);
		frmDataSelector.getContentPane().add(txtAnd5);
		
		txtAnd6 = new JTextField();
		txtAnd6.setText("and");
		txtAnd6.setForeground(SystemColor.activeCaptionBorder);
		txtAnd6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAnd6.setEnabled(false);
		txtAnd6.setEditable(false);
		txtAnd6.setColumns(10);
		txtAnd6.setBackground(SystemColor.desktop);
		txtAnd6.setBounds(274, 227, 31, 22);
		frmDataSelector.getContentPane().add(txtAnd6);
		
		txtMaximumPH = new JTextField();
		txtMaximumPH.setText("maximum");
		txtMaximumPH.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumPH.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumPH.setColumns(10);
		txtMaximumPH.setBackground(SystemColor.desktop);
		txtMaximumPH.setBounds(332, 97, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumPH);
		
		txtMaximumOsmo = new JTextField();
		txtMaximumOsmo.setText("maximum");
		txtMaximumOsmo.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumOsmo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumOsmo.setColumns(10);
		txtMaximumOsmo.setBackground(SystemColor.desktop);
		txtMaximumOsmo.setBounds(332, 129, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumOsmo);
		
		txtMaximumCond = new JTextField();
		txtMaximumCond.setText("maximum");
		txtMaximumCond.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumCond.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumCond.setColumns(10);
		txtMaximumCond.setBackground(SystemColor.desktop);
		txtMaximumCond.setBounds(332, 161, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumCond);
		
		txtMaximumUrea = new JTextField();
		txtMaximumUrea.setText("maximum");
		txtMaximumUrea.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumUrea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumUrea.setColumns(10);
		txtMaximumUrea.setBackground(SystemColor.desktop);
		txtMaximumUrea.setBounds(332, 193, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumUrea);
		
		txtMaximumCalcium = new JTextField();
		txtMaximumCalcium.setText("maximum");
		txtMaximumCalcium.setForeground(SystemColor.activeCaptionBorder);
		txtMaximumCalcium.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMaximumCalcium.setColumns(10);
		txtMaximumCalcium.setBackground(SystemColor.desktop);
		txtMaximumCalcium.setBounds(332, 227, 67, 22);
		frmDataSelector.getContentPane().add(txtMaximumCalcium);
		
		chckbxPH = new JCheckBox("ALL");
		chckbxPH.setSelected(true);
		chckbxPH.setForeground(SystemColor.activeCaptionBorder);
		chckbxPH.setBackground(SystemColor.desktop);
		chckbxPH.setBounds(425, 97, 48, 21);
		frmDataSelector.getContentPane().add(chckbxPH);
		
		chckbxOsmo = new JCheckBox("ALL");
		chckbxOsmo.setSelected(true);
		chckbxOsmo.setForeground(SystemColor.activeCaptionBorder);
		chckbxOsmo.setBackground(SystemColor.desktop);
		chckbxOsmo.setBounds(425, 129, 48, 21);
		frmDataSelector.getContentPane().add(chckbxOsmo);
		
		chckbxCond = new JCheckBox("ALL");
		chckbxCond.setSelected(true);
		chckbxCond.setForeground(SystemColor.activeCaptionBorder);
		chckbxCond.setBackground(SystemColor.desktop);
		chckbxCond.setBounds(425, 161, 48, 21);
		frmDataSelector.getContentPane().add(chckbxCond);
		
		chckbxUrea = new JCheckBox("ALL");
		chckbxUrea.setSelected(true);
		chckbxUrea.setForeground(SystemColor.activeCaptionBorder);
		chckbxUrea.setBackground(SystemColor.desktop);
		chckbxUrea.setBounds(425, 193, 48, 21);
		frmDataSelector.getContentPane().add(chckbxUrea);
		
		chckbxCalcium = new JCheckBox("ALL");
		chckbxCalcium.setSelected(true);
		chckbxCalcium.setForeground(SystemColor.activeCaptionBorder);
		chckbxCalcium.setBackground(SystemColor.desktop);
		chckbxCalcium.setBounds(425, 227, 48, 21);
		frmDataSelector.getContentPane().add(chckbxCalcium);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBackground(SystemColor.desktop);
		btnSelect.setForeground(SystemColor.activeCaptionBorder);
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSelect.setBounds(10, 274, 463, 25);
		frmDataSelector.getContentPane().add(btnSelect);

		// Inchide agentul la inchiderea interfetei
		// din butonul din dreapta sus
		frmDataSelector.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				agentBanca.doDelete();
			}
		});
		frmDataSelector.setBounds(100, 100, 498, 344);
		frmDataSelector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
	}

	public void afiseazaInterfata() {
		frmDataSelector.setVisible(true);
	}
}
