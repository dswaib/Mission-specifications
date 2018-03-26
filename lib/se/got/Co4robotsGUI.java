/**
 * Copyright (C) 2011-2014 Swinburne University of Technology and University of Gotheborg
 *
 * These file have been developed as a part of the co4robots project.
 * It is a tool
 *
 * These files are based on PSPWizard which was developed at Faculty of Science, Engineering and
 * Technology at Swinburne University of Technology, Australia.
 * The patterns, structured English grammar and mappings are due to
 * Marco Autili, Universita` dell'Aquila
 * Lars Grunske, University of Stuttgart
 * Markus Lumpe, Swinburne University of Technology
 * Patrizio Pelliccione, University of Gothenburg
 * Antony Tang, Swinburne University of Technology
 *
 * Details about the PSP framework can found in
 * "Aligning Qualitative, Real-Time, and Probabilistic
 * Property Specification Patterns Using a Structured
 * English Grammar"
 *
 *
 *
 * PSPWizard is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * PSPWizard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PSPWizard; see the file COPYING.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package se.got;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.naming.spi.DirectoryManager;
import javax.security.auth.callback.TextOutputCallback;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSliderUI.ScrollListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import jsyntaxpane.DefaultSyntaxKit;
import se.got.engine.EventSelectionValidator;
import se.got.engine.EventStorage;
import se.got.ltl.LTLFormula;
import se.got.ltl.visitors.LTLFormulaToStringVisitor;
import se.got.sel.Event;
import se.got.sel.patterns.Pattern;
import se.got.sel.scopes.Scope;

public class Co4robotsGUI extends javax.swing.JFrame  {

	private static final String INIT_POSITION_MESSAGE = "Insert the positions to be considered separated by a comma";

	private static final int FRAME_INIT_HEIGTH = 600;

	private static final int FRAME_INIT_WIDTH = 800;
	
	public static String CurrentNewPatternName = "";
	public static String DirPath = "";

	private JPanel locationPanel;
	private static int FORMULA_COUNTER = 1;

	private static Map<String, LTLFormula> formulae;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String TITLE = "co4robots: High Level Specification Panel";
	private final static String EVENTNAMES = "Show service names";
	private final static String EVENTSPECIFICATION = "Show service specifications";
	private final static String EDITEVENT = "Edit service";
	private final static String ADDEVENT = "Add Service";
	private final static String MOVEMENT_PATTERN = "Movement Specification Pattern";
	private final static String SEND_MISSION = "Send mission";
	private final static String LOAD_MISSION = "Load mission";
	private final static String ADD_MISSION = "Add mission";	
	private final static String LOAD_PROPERTY = "Load property";

	private final static String SELECT_PATTERN_CATEGORY = "Select pattern category";

	private static JList<String> propertyList;

	private JComboBox<String> patternCategorySelector;
	private JComboBox<String> patternBoxSelector;
	private JTextArea ltlFormula;

	private JTextArea intentText;
	private JTextArea variation;
	private JTextArea examples;
	private JTextArea relationships;
	private JTextArea occurences;
	private JComboBox<String> f1;
	private JComboBox<String> f2;
	private final DefaultComboBoxModel<String> patternItems;

	private JTextField locations;
	public final static Color BACKGROUNDCOLOR = Color.WHITE;
	public final static Color ADDMISSIONBACKGROUNDCOLOR = Color.BLUE;
	
	private EventStorage fEvents;

	private void initMappings() {
	}

	public Co4robotsGUI() {

		patternItems = new DefaultComboBoxModel<>();

		this.f1 = new JComboBox<String>();
		this.f2 = new JComboBox<String>();
		this.formulae = new HashMap<>();
		String[] elements = { "" };
		f1 = new JComboBox<>();

		f1.setBorder(javax.swing.BorderFactory.createTitledBorder("Formula f1"));
		f1.setToolTipText("");

		f2.setBorder(javax.swing.BorderFactory.createTitledBorder("Formula f2"));
		f2.setToolTipText("");

		TitledBorder movementPatternTitle = BorderFactory.createTitledBorder("Property  List");
		movementPatternTitle.setTitlePosition(TitledBorder.RIGHT);

		propertyList = new JList<String>(elements);
		propertyList.setBackground(Color.GRAY);

		propertyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		initComponents();
		
		reset();

		initMappings();

		// update initial SEL and mapping
		updateSELandMapping();
	}
		
	// Entry point

	public static void main(String args[]) {
		
		//create a directory
		String homeDir = System.getProperty("user.home");
		String appDir = homeDir + "/Co4robots";
		File appFolder = new File(appDir);
		if(appFolder.isDirectory() && appFolder.exists()){
			//do nothing
		}else{
			try {
				boolean foo  = appFolder.mkdir();//if the folder is not created, we are suppose to create a new folder with the same name
				System.out.println(foo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("New creation failed");
			}
		}
		Co4robotsGUI.DirPath = appDir;
		
//		compileAndRun("Sample", System.getProperty("user.home") + "/Documents/workspace/Sample.java");
		
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				(new Co4robotsGUI()).showEditor();
			}
		});
		
//		java.awt.EventQueue.invokeLater(new Runnable() {
//
//	        @Override
//	        public void run() {
//	            SyntaxTester();
//	        }
//	    });
		
		//the next step is to add more tabs for other things
		//SyntaxTester(); 
		
	}
	
	
	public static void SyntaxTester() {
	    JFrame f = new JFrame("Mission/Pattern Editor");
	    final Container c = f.getContentPane();
	    c.setLayout(new BorderLayout());
	    
	    JTabbedPane tabbedPane = new JTabbedPane();
	    c.add(tabbedPane, BorderLayout.CENTER);
	    
	    //add a panel/page
	    JPanel editorPanel = new JPanel();
	    editorPanel.setLayout(new BorderLayout());
	    
	    //the name of the pattern
	    JPanel configPanel = new JPanel();

	    JPanel nameConfig = new JPanel();
	    nameConfig.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    
	    JLabel patterNameLabel = new JLabel("Pattern Name : ");
	    gbc.weightx = 0.5;
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    nameConfig.add(patterNameLabel, gbc);
	    
	    JTextField patterNameField = new JTextField();
	    patterNameField.setMaximumSize(new Dimension(200, 20));
	    gbc.weightx = 0.5;
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    nameConfig.add(patterNameField, gbc);
	    
	   // editorPanel.add(nameConfig,BorderLayout.NORTH);


	    DefaultSyntaxKit.initKit();

	    final JEditorPane codeEditor = new JEditorPane();
	    JScrollPane scrPane = new JScrollPane(codeEditor);
	    //c.add(scrPane, BorderLayout.CENTER);
	    editorPanel.add(scrPane,BorderLayout.CENTER);
	    
	    JTextArea label = new JTextArea("In the text editor, enter the LTL function that will generate the corresponding LTL formula for each mission generated from this pattern");
	    label.setEditable(false);
	    label.setSize(300, 400);
	    label.setPreferredSize(new Dimension(300, 400));
	    label.setLineWrap(true);
	    //label.setp
	    label.setBorder(BorderFactory.createCompoundBorder(
	    	 label.getBorder(), 
	         BorderFactory.createEmptyBorder(10, 10, 10, 10)
	       )
	    );
	    label.setBackground(new Color(200,200,200));
	    
	    JPanel p = new JPanel();
	    p.setLayout(new GridLayout(0,1));
	    p.add(label);
	    editorPanel.add(p, BorderLayout.EAST);
	    tabbedPane.addTab("LTL FORMULA", editorPanel);
	    //c.doLayout();
	    
	    
	    codeEditor.setContentType("text/java");
	    //codeEditor.setText("public static void main(String[] args) {\n}");
	    
	    
	    
	    //add a save button
	    JButton jb  = new JButton("save");
	    c.add(jb, BorderLayout.SOUTH);
	    jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String code = codeEditor.getText();
				
				//System.out.println(code);
				//save this to  a file
				//String temp = Templates.PatternsTemplate;
				//temp = temp.replace("{{formulaBody}}", code);
				
				//System.out.println(temp);
				
				//save the file
				String pth = Co4robotsGUI.DirPath + "/" + Co4robotsGUI.CurrentNewPatternName ;
				File f = new File(pth);
				if(f.isDirectory() && f.exists()){
					//do nothing
				}else{
					try {
						boolean foo  = f.mkdir();//if the folder is not created, we are suppose to create a new folder with the same name
						System.out.println(foo);
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
						System.out.println("New creation java folder failed");
					}
				}
				String fullFileName = pth + "/" + Co4robotsGUI.CurrentNewPatternName + ".java"; 
				try{
					//save a file
					
					File codeFile = new File(fullFileName);
					codeFile.createNewFile();
					
					
					FileWriter fw = new FileWriter(codeFile);
					fw.write(code);
					fw.flush();
					fw.close();
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					System.out.println("New creation java file failed");
				}
				
				//compile the application
				try {
					runProcess("javac -d "+ pth + "/ " + fullFileName);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	    
	    
	    //load in the template
	    String temp = Templates.PatternsTemplate;
	    //replace the name
	    temp = temp.replace("{{patternName}}", Co4robotsGUI.CurrentNewPatternName);
	    codeEditor.setText(temp);
	    

	    f.setSize(800, 600);
	    f.setVisible(true);
	    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
	
	
	
	
	//internal builder
	//This will print output of the external program
	private static void printLines(String name, InputStream ins) throws Exception {
		String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(name + " " + line);
	    }
	}
	//this runs the external programe
	private static void runProcess(String command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		printLines(command + " stdout:", pro.getInputStream());
		System.out.println("--------------------------------");
		printLines(command + " stderr:", pro.getErrorStream());
		pro.waitFor();
		System.out.println(command + " exitValue() " + pro.exitValue());
	}
	//external compile and runner
	public static void compileAndRun(String className, String fullFileName) {
	    try {
	      //-d option specifies where to put the compiled code
	      //runProcess("javac -d \"G:/ai research/newAge\" \"G:/ai research/NewPattern.java\" ");
	      //runProcess("java -cp \"G:/ai research/newAge\" NewPattern ");
	      String x = System.getProperty("user.home");
	      runProcess("javac -d "+ x + "/Documents/workspace/robot/ " + fullFileName);
	    	
	      String cp = fullFileName.replace("/" + className + ".java", "");
	      //runProcess("javac  " + fullFileName);
		  runProcess("java -cp "+ cp +" " + className);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	

	public void showEditor() {
		setLocationRelativeTo(null); // center?
		setVisible(true);
	}

	public JFrame getHostFrame() {
		return this;
	}

	public void reset() {
		Event.reset();
		fEvents = new EventStorage();

		fSelectedScope = null;
		fSelectedPattern = null;
		EventSelectionValidator.clearSelection();
		fSEs.setEnabled(true);
		fESpec.setSelected(false);
		fEName.setSelected(true);
		Event.EventStringMethod = Event.E_Name;
	}

	// event controller facet

	public Event newEvent(String aName) {
		Event Result = fEvents.newEvent(aName);

		return Result;
	}

	public Event newEvent(String aName, String aSpecification) {
		Event Result = fEvents.newEvent(aName, aSpecification);

		return Result;
	}

	public Iterator<Event> iterator() {
		return fEvents.iterator();
	}

	// Scope events

	private Scope fSelectedScope;

	public boolean isScopeEventSelectionPossible(Event aEvent) {
		return EventSelectionValidator.isScopeEventSelectionPossible(this, aEvent);
	}

	public void updateScope() {

		EventSelectionValidator.updateScopeEvents(fSelectedScope);

		// update SEL and mapping
		updateSELandMapping();
	}

	// Pattern events
	private Pattern fSelectedPattern;


	public boolean isPatternEventSelectionPossible(Event aEvent, Event aAltEvent) {
		return EventSelectionValidator.isPatternEventSelectionPossible(this, aEvent, aAltEvent);
	}

	public void updatePattern() {

		EventSelectionValidator.updatePatternEvents(fSelectedPattern);

		// update SEL and mapping
		updateSELandMapping();
	}

	private void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

	// SEL expansion

	private void updateSELandMapping() {
		// StringBuilder sb = new StringBuilder();

		if (fSelectedScope != null && fSelectedPattern != null) {
			fSELP.setText("");
			appendToPane(fSELP, fSelectedScope.getSpecificationAsSEL(), Color.GRAY);
			appendToPane(fSELP, ", ", Color.RED);
			appendToPane(fSELP, fSelectedPattern.getSpecificationAsSEL(), Color.DARK_GRAY);
			appendToPane(fSELP, ".", Color.RED);

			// fSELP.setText( sb.toString() );

		}
	}
	/*
	public static void myframe() {
        final JFrame frame = new JFrame("JScrollbar Demo");
        final JLabel label = new JLabel( );
 
        JScrollBar hbar=new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 500);
        JScrollBar vbar=new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 500);
 
        class MyAdjustmentListener implements AdjustmentListener {
        	@Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                label.setText("Slider's position is " + e.getValue());
                frame.repaint();
            }
        }
 
        hbar.addAdjustmentListener(new MyAdjustmentListener( ));
        vbar.addAdjustmentListener(new MyAdjustmentListener( ));
 
        frame.setLayout(new BorderLayout( ));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,200);
        frame.getContentPane().add(label);
        
        frame.getContentPane().add(hbar, BorderLayout.SOUTH);
        frame.getContentPane().add(vbar, BorderLayout.EAST);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setVisible(true);
    }*/

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
	//setting initials of layout	
		setLayout(new BorderLayout( ));
		setSize(300,200);
		JScrollBar hbar=new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 500);
        JScrollBar vbar=new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 500);
        class MyAdjustmentListener implements AdjustmentListener {
        	@Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                getContentPane().repaint();
            }
        }
 
        hbar.addAdjustmentListener(new MyAdjustmentListener( ));
        vbar.addAdjustmentListener(new MyAdjustmentListener( ));
	//ending the initials	
		patternsJPanel = new javax.swing.JPanel();
		patternsJPanel.setBackground(BACKGROUNDCOLOR);

		patternJPanel = new javax.swing.JPanel();
		optionJPanel = new javax.swing.JPanel();
		jPanelLogo = new javax.swing.JPanel();
		fEName = new javax.swing.JCheckBox();
		fESpec = new javax.swing.JCheckBox();
		this.sendMission = new javax.swing.JButton();
		this.loadMission = new javax.swing.JButton();
		this.addMission = new javax.swing.JButton(); //editor
		addMissionJPanel = new javax.swing.JPanel(); //editor
		//addMissionJPanel.setBackground(ADDMISSIONBACKGROUNDCOLOR); //editor
		//addMissionJPanel.setBounds(0,0,100,100);
		//addMissionJPanel.setMinimumSize(new Dimension(200, 200));
		
		this.loadProperty = new javax.swing.JButton();
		jPanel5 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		fSELP = new javax.swing.JTextPane();
		propertyPanel = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		fMapping = new javax.swing.JTextArea();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		eventJPanel = new javax.swing.JPanel();
		fNE = new javax.swing.JButton();
		fSEs = new javax.swing.JButton();
		fEE = new javax.swing.JButton();
		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(optionJPanel);

		getContentPane().setBackground(BACKGROUNDCOLOR);
		patternJPanel.setBackground(BACKGROUNDCOLOR);
		optionJPanel.setBackground(BACKGROUNDCOLOR);
		jPanelLogo.setBackground(BACKGROUNDCOLOR);
		fEName.setBackground(BACKGROUNDCOLOR);
		fESpec.setBackground(BACKGROUNDCOLOR);
		jPanel5.setBackground(BACKGROUNDCOLOR);
		jScrollPane1.setBackground(BACKGROUNDCOLOR);
		fSELP.setBackground(BACKGROUNDCOLOR);
		jScrollPane2.setBackground(BACKGROUNDCOLOR);
		fMapping.setBackground(BACKGROUNDCOLOR);
		jLabel1.setBackground(BACKGROUNDCOLOR);
		jLabel2.setBackground(BACKGROUNDCOLOR);
		eventJPanel.setBackground(BACKGROUNDCOLOR);
		fNE.setBackground(BACKGROUNDCOLOR);
		fSEs.setBackground(BACKGROUNDCOLOR);
		fEE.setBackground(BACKGROUNDCOLOR);
		jPanelLogo.setBackground(Color.WHITE);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle(TITLE);
		setResizable(true);

		javax.swing.GroupLayout patternSelectionPanel = new javax.swing.GroupLayout(patternsJPanel);

		patternsJPanel.setLayout(patternSelectionPanel);

		// patternSelectionJPanel.se

		patternsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(MOVEMENT_PATTERN));
		patternsJPanel.setToolTipText("");

		String[] patternCategories = { "Core Movement", "Triggers", "Avoidance", "Composition" };
		DefaultComboBoxModel<String> patternCategoriestItems = new DefaultComboBoxModel<>();

		Arrays.asList(patternCategories).stream().forEach(p -> patternCategoriestItems.addElement(p.toString()));

		patternCategorySelector = new JComboBox<String>(patternCategoriestItems);

		Arrays.asList(CoreMovementPatterns.values()).stream().forEach(p -> patternItems.addElement(p.toString()));

		patternBoxSelector = new JComboBox<String>(patternItems);

		patternBoxSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) patternBoxSelector.getSelectedItem();
				System.out.println(selectedItem);
				if (selectedItem != null) {
					switch (selectedItem) {

					case "OR":
						intentText.setText(Composition.OR.getDescription());
						f1.removeAllItems();
						f2.removeAllItems();

						DefaultComboBoxModel<String> formulaeList1 = new DefaultComboBoxModel<String>();
						DefaultComboBoxModel<String> formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "AND":
						intentText.setText(Composition.AND.getDescription());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);

						break;
						//triggers
					case "Wait":
						intentText.setText(Triggers.WAIT.getDescription());
						variation.setText(Triggers.WAIT.getVariations());
						examples.setText(Triggers.WAIT.getExamples());
						relationships.setText(Triggers.WAIT.getRelationships());
						occurences.setText(Triggers.WAIT.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Instantaneous Reaction":
						intentText.setText(Triggers.INSTANTANEOUS_REACTION.getDescription());
						variation.setText(Triggers.INSTANTANEOUS_REACTION.getVariations());
						examples.setText(Triggers.INSTANTANEOUS_REACTION.getExamples());
						relationships.setText(Triggers.INSTANTANEOUS_REACTION.getRelationships());
						occurences.setText(Triggers.INSTANTANEOUS_REACTION.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);

						break;
					case "Delayed Reaction":
						intentText.setText(Triggers.DELAYED_REACTION.getDescription());
						variation.setText(Triggers.DELAYED_REACTION.getVariations());
						examples.setText(Triggers.DELAYED_REACTION.getExamples());
						relationships.setText(Triggers.DELAYED_REACTION.getRelationships());
						occurences.setText(Triggers.DELAYED_REACTION.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
						//core movements
						//visits
					case "Visit":
						intentText.setText(CoreMovementPatterns.VISIT.getDescription());
						variation.setText(CoreMovementPatterns.VISIT.getVariations());
						examples.setText(CoreMovementPatterns.VISIT.getExamples());
						relationships.setText(CoreMovementPatterns.VISIT.getRelationships());
						occurences.setText(CoreMovementPatterns.VISIT.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Sequenced Visit":
						intentText.setText(CoreMovementPatterns.SEQUENCED_VISIT.getDescription());
						variation.setText(CoreMovementPatterns.SEQUENCED_VISIT.getVariations());
						examples.setText(CoreMovementPatterns.SEQUENCED_VISIT.getExamples());
						relationships.setText(CoreMovementPatterns.SEQUENCED_VISIT.getRelationships());
						occurences.setText(CoreMovementPatterns.SEQUENCED_VISIT.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Ordered Visit":
						intentText.setText(CoreMovementPatterns.ORDERED_VISIT.getDescription());
						variation.setText(CoreMovementPatterns.ORDERED_VISIT.getVariations());
						examples.setText(CoreMovementPatterns.ORDERED_VISIT.getExamples());
						relationships.setText(CoreMovementPatterns.ORDERED_VISIT.getRelationships());
						occurences.setText(CoreMovementPatterns.ORDERED_VISIT.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Strict Ordered Visit":
						intentText.setText(CoreMovementPatterns.STRICT_ORDERED_VISIT.getDescription());
						variation.setText(CoreMovementPatterns.STRICT_ORDERED_VISIT.getVariations());
						examples.setText(CoreMovementPatterns.STRICT_ORDERED_VISIT.getExamples());
						relationships.setText(CoreMovementPatterns.STRICT_ORDERED_VISIT.getRelationships());
						occurences.setText(CoreMovementPatterns.STRICT_ORDERED_VISIT.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Fair Visit":
						intentText.setText(CoreMovementPatterns.FAIR_VISIT.getDescription());
						variation.setText(CoreMovementPatterns.FAIR_VISIT.getVariations());
						examples.setText(CoreMovementPatterns.FAIR_VISIT.getExamples());
						relationships.setText(CoreMovementPatterns.FAIR_VISIT.getRelationships());
						occurences.setText(CoreMovementPatterns.FAIR_VISIT.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
						//patrolling
					case "Patrolling":
						intentText.setText(CoreMovementPatterns.PATROLLING.getDescription());
						variation.setText(CoreMovementPatterns.PATROLLING.getVariations());
						examples.setText(CoreMovementPatterns.PATROLLING.getExamples());
						relationships.setText(CoreMovementPatterns.PATROLLING.getRelationships());
						occurences.setText(CoreMovementPatterns.PATROLLING.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Sequenced Patrolling":
						intentText.setText(CoreMovementPatterns.SEQUENCED_PATROLLING.getDescription());
						variation.setText(CoreMovementPatterns.SEQUENCED_PATROLLING.getVariations());
						examples.setText(CoreMovementPatterns.SEQUENCED_PATROLLING.getExamples());
						relationships.setText(CoreMovementPatterns.SEQUENCED_PATROLLING.getRelationships());
						occurences.setText(CoreMovementPatterns.SEQUENCED_PATROLLING.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Ordered Patrolling":
						intentText.setText(CoreMovementPatterns.ORDERED_PATROLLING.getDescription());
						variation.setText(CoreMovementPatterns.ORDERED_PATROLLING.getVariations());
						examples.setText(CoreMovementPatterns.ORDERED_PATROLLING.getExamples());
						relationships.setText(CoreMovementPatterns.ORDERED_PATROLLING.getRelationships());
						occurences.setText(CoreMovementPatterns.ORDERED_PATROLLING.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Strict Ordered Patrolling":
						intentText.setText(CoreMovementPatterns.STRICT_ORDERED_PATROLLING.getDescription());
						variation.setText(CoreMovementPatterns.STRICT_ORDERED_PATROLLING.getVariations());
						examples.setText(CoreMovementPatterns.STRICT_ORDERED_PATROLLING.getExamples());
						relationships.setText(CoreMovementPatterns.STRICT_ORDERED_PATROLLING.getRelationships());
						occurences.setText(CoreMovementPatterns.STRICT_ORDERED_PATROLLING.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Fair Patrolling":
						intentText.setText(CoreMovementPatterns.FAIR_PATROLLING.getDescription());
						variation.setText(CoreMovementPatterns.FAIR_PATROLLING.getVariations());
						examples.setText(CoreMovementPatterns.FAIR_PATROLLING.getExamples());
						relationships.setText(CoreMovementPatterns.FAIR_PATROLLING.getRelationships());
						occurences.setText(CoreMovementPatterns.FAIR_PATROLLING.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
						//avoidance
						//past avoidance
					case "Past Avoidance":
						intentText.setText(Avoidance.PAST_AVOIDANCE.getDescription());
						variation.setText(Avoidance.PAST_AVOIDANCE.getVariations());
						examples.setText(Avoidance.PAST_AVOIDANCE.getExamples());
						relationships.setText(Avoidance.PAST_AVOIDANCE.getRelationships());
						occurences.setText(Avoidance.PAST_AVOIDANCE.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Future Avoidance":
						intentText.setText(Avoidance.FUTURE_AVOIDANCE.getDescription());
						variation.setText(Avoidance.FUTURE_AVOIDANCE.getVariations());
						examples.setText(Avoidance.FUTURE_AVOIDANCE.getExamples());
						relationships.setText(Avoidance.FUTURE_AVOIDANCE.getRelationships());
						occurences.setText(Avoidance.FUTURE_AVOIDANCE.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
						break;
					case "Global Avoidance":
						intentText.setText(Avoidance.GLOBAL_AVOIDANCE.getDescription());
						variation.setText(Avoidance.GLOBAL_AVOIDANCE.getVariations());
						examples.setText(Avoidance.GLOBAL_AVOIDANCE.getExamples());
						relationships.setText(Avoidance.GLOBAL_AVOIDANCE.getRelationships());
						occurences.setText(Avoidance.GLOBAL_AVOIDANCE.getOccurrences());
						f1.removeAllItems();
						f2.removeAllItems();

						formulaeList1 = new DefaultComboBoxModel<String>();
						formulaeList2 = new DefaultComboBoxModel<String>();

						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));

						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
					default:
						break;
					}
				}
				
			}
	});
		
		patternCategorySelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) patternCategorySelector.getSelectedItem();
				cleanPanels();
				switch (selectedItem) {
				case "Core Movement":
					TitledBorder movementPatternTitle = BorderFactory.createTitledBorder("Core Movement");
					movementPatternTitle.setTitlePosition(TitledBorder.RIGHT);

					patternBoxSelector.setBorder(movementPatternTitle);
					patternItems.removeAllElements();

					Arrays.asList(CoreMovementPatterns.values()).stream()
							.forEach(p -> patternItems.addElement(p.toString()));

					patternBoxSelector.setModel(patternItems);
					locationPanel.setVisible(true);
					ltlFormula.setVisible(true);
					intentText.setVisible(true);
					variation.setVisible(true);
					examples.setVisible(true);
					relationships.setVisible(true);
					occurences.setVisible(true);

					break;
				case "Triggers":
					movementPatternTitle = BorderFactory.createTitledBorder("Triggers");
					movementPatternTitle.setTitlePosition(TitledBorder.RIGHT);

					patternItems.removeAllElements();
					Arrays.asList(Triggers.values()).stream().forEach(p -> patternItems.addElement(p.toString()));

					patternBoxSelector.setBorder(movementPatternTitle);
					patternBoxSelector.setModel(patternItems);
					locationPanel.setVisible(false);
					intentText.setVisible(true);
					ltlFormula.setVisible(true);
					variation.setVisible(true);
					examples.setVisible(true);
					relationships.setVisible(true);
					occurences.setVisible(true);

					break;
				case "Avoidance":
					movementPatternTitle = BorderFactory.createTitledBorder("Avoidance");
					movementPatternTitle.setTitlePosition(TitledBorder.RIGHT);

					patternItems.removeAllElements();
					Arrays.asList(Avoidance.values()).stream().forEach(p -> patternItems.addElement(p.toString()));

					patternBoxSelector.setBorder(movementPatternTitle);
					patternBoxSelector.setModel(patternItems);
					locationPanel.setVisible(true);
					intentText.setVisible(true);
					ltlFormula.setVisible(true);
					variation.setVisible(true);
					examples.setVisible(true);
					relationships.setVisible(true);
					occurences.setVisible(true);

					break;
				case "Composition":
					movementPatternTitle = BorderFactory.createTitledBorder("Composition");
					movementPatternTitle.setTitlePosition(TitledBorder.RIGHT);

					patternItems.removeAllElements();
					Arrays.asList(Composition.values()).stream().forEach(p -> patternItems.addElement(p.toString()));

					patternBoxSelector.setBorder(movementPatternTitle);
					patternBoxSelector.setModel(patternItems);
					intentText.setText(Composition.AND.getDescription());
					locationPanel.setVisible(false);
					intentText.setVisible(true);
					ltlFormula.setVisible(true);//edited
					variation.setVisible(false);
					examples.setVisible(false);
					relationships.setVisible(false);
					occurences.setVisible(false);

					break;
				default:
					break;

				}

				patternBoxSelector.validate();
				patternBoxSelector.updateUI();
				patternBoxSelector.repaint();
				patternBoxSelector.doLayout();
				

			}
		});
		patternSelectionPanel
				.setHorizontalGroup(patternSelectionPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(patternSelectionPanel.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		patternSelectionPanel
				.setVerticalGroup(patternSelectionPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(patternSelectionPanel.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		optionJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

		fEName.setSelected(true);
		fEName.setText(EVENTNAMES);
		fEName.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fENameActionPerformed(evt);
			}
		});

		fESpec.setText(EVENTSPECIFICATION);
		fESpec.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fESpecActionPerformed(evt);
			}
		});

		this.sendMission.setText(SEND_MISSION);
		this.loadMission.setText(LOAD_MISSION);
		this.addMission.setText(ADD_MISSION); //editor
		this.loadProperty.setText(LOAD_PROPERTY);
		
		this.sendMission.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println();
				if (locations.getText().equals(INIT_POSITION_MESSAGE)) {
					JOptionPane.showMessageDialog(null, "Insert the set of locations to be considered");
				} else {
					MissionSender sender = new MissionSender();
					try {
						sender.send(loadMission().accept(new LTLFormulaToStringVisitor()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		this.loadMission.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				System.out.println();
				if (locations.getText().equals(INIT_POSITION_MESSAGE)) {
					JOptionPane.showMessageDialog(null, "Insert the set of locations to be considered");
				} else {

					try {
						loadMission();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		
		this.addMission.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//test
				System.out.println("Add mission clicked");
				
				//to first ask for the name 
				Co4robotsGUI.CurrentNewPatternName = "";
				JOptionPane jp = new JOptionPane();
				jp.createDialog("Co4robots");
				String x = jp.showInputDialog("Whats the name of the pattern ?");
				System.out.println("Name of pettern: " + x);
				Co4robotsGUI.CurrentNewPatternName  = x;
				SyntaxTester(); 
			}

		});

		this.loadProperty.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (locations.getText().equals(INIT_POSITION_MESSAGE)) {
					JOptionPane.showMessageDialog(null, "Insert the set of locations to be considered");
				} else {

					LTLFormula property;
					try {
						property = loadMission();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		});

		optionJPanel.setLayout(jPanel3Layout);

		jPanelLogo.setLayout(new BorderLayout());

		BufferedImage myPicture;
		try {
			System.out.println(Co4robotsGUI.class.getClassLoader().getResourceAsStream("images/co4robotsLogo.png"));
			myPicture = ImageIO
					.read(Co4robotsGUI.class.getClassLoader().getResourceAsStream("images/co4robotsLogo.png"));

			ImageIcon icon = new ImageIcon(myPicture);
			JLabel picLabel = new JLabel(icon);
			jPanelLogo.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
//capturing the main panel
		JPanel mainPanel = new JPanel();
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(layout);
		getContentPane().add(mainPanel);
		

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(mainPanel);
	//end capturing		

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		TitledBorder patternCategoryTile = BorderFactory.createTitledBorder(SELECT_PATTERN_CATEGORY);
		patternCategoryTile.setTitlePosition(TitledBorder.RIGHT);

		patternCategorySelector.setBorder(patternCategoryTile);

		TitledBorder movementPatternTitle = BorderFactory.createTitledBorder("Movement Pattern");
		movementPatternTitle.setTitlePosition(TitledBorder.RIGHT);

		patternBoxSelector.setBorder(movementPatternTitle);

		locationPanel = new JPanel();
		locations = new JTextField(40);
		locations.setText(INIT_POSITION_MESSAGE);

		locationPanel.add(locations);
		TitledBorder locationsTitle = BorderFactory.createTitledBorder("Locations");
		locationsTitle.setTitlePosition(TitledBorder.RIGHT);
		locationPanel.setBorder(locationsTitle);

		ltlFormula = new JTextArea();
		TitledBorder ltlFormulaTitle = BorderFactory.createTitledBorder("LTL formula associated with the pattern");
		ltlFormulaTitle.setTitlePosition(TitledBorder.RIGHT);
		ltlFormula.setBorder(ltlFormulaTitle);
		
		intentText = new JTextArea();
		intentText.setLineWrap(true);

		TitledBorder intentTitle = BorderFactory.createTitledBorder("Intent");
		intentTitle.setTitlePosition(TitledBorder.RIGHT);
		intentText.setBorder(intentTitle);

		variation = new JTextArea();
		variation.setLineWrap(true);
		TitledBorder variationTitle = BorderFactory.createTitledBorder("Variations");
		variationTitle.setTitlePosition(TitledBorder.RIGHT);
		variation.setBorder(variationTitle);

		examples = new JTextArea();
		examples.setLineWrap(true);
		TitledBorder examplesTitle = BorderFactory.createTitledBorder("Examples and Known Uses");
		examplesTitle.setTitlePosition(TitledBorder.RIGHT);
		examples.setBorder(examplesTitle);
		

		relationships = new JTextArea();
		relationships.setLineWrap(true);
		TitledBorder relationshipsTitle = BorderFactory.createTitledBorder("Relationships");
		relationshipsTitle.setTitlePosition(TitledBorder.RIGHT);
		relationships.setBorder(relationshipsTitle);
		
		occurences = new JTextArea();
		occurences.setLineWrap(true);
		TitledBorder occuttencesTitle = BorderFactory.createTitledBorder("Occurences");
		occuttencesTitle.setTitlePosition(TitledBorder.RIGHT);
		occurences.setBorder(occuttencesTitle);
		

		TitledBorder propertiesTitle = BorderFactory.createTitledBorder("Property Library");
		propertiesTitle.setTitlePosition(TitledBorder.RIGHT);

		JScrollPane p = new JScrollPane(this.propertyList);
		p.setBorder(propertiesTitle);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(jPanelLogo)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(addMissionJPanel)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(patternCategorySelector)

						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(patternBoxSelector)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(locationPanel)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(ltlFormula)

						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(intentText)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(variation)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(examples)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(relationships)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(occurences)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(f1)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(f2)

				).addGroup(layout.createParallelGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))

						.addComponent(this.loadProperty)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(this.loadMission)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(this.addMission)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(this.sendMission)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(p)))

		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
				.addComponent(jPanelLogo)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
				.addComponent(addMissionJPanel)
				.addGroup(layout.createParallelGroup().addGroup(layout.createSequentialGroup()

						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(patternCategorySelector)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(patternBoxSelector)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(locationPanel)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(ltlFormula)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(intentText)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(variation)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(examples)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(relationships)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(occurences)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(f1)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(f2))

						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
								.addComponent(this.addMission)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
								.addComponent(this.loadMission)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
								.addComponent(this.loadProperty)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
								.addComponent(this.sendMission)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(p))));

		
		
		setBounds(0, 0, FRAME_INIT_WIDTH, FRAME_INIT_HEIGTH);
		setVisible(false);
		this.setResizable(true);
	}

	private void fENameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fENameActionPerformed
		// TODO add your handling code here:

		if (!fEName.isSelected()) {
			fESpec.setSelected(true);
			Event.EventStringMethod = Event.E_Spec;
		} else {
			if (fESpec.isSelected())
				Event.EventStringMethod = Event.E_NameAndSpec;
			else
				Event.EventStringMethod = Event.E_Name;
		}
		updateSELandMapping();
		repaint();
	}// GEN-LAST:event_fENameActionPerformed

	private void fESpecActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fESpecActionPerformed
		// TODO add your handling code here:

		if (!fESpec.isSelected()) {
			fEName.setSelected(true);
			Event.EventStringMethod = Event.E_Name;
		} else {
			if (fEName.isSelected())
				Event.EventStringMethod = Event.E_NameAndSpec;
			else
				Event.EventStringMethod = Event.E_Spec;
		}
		updateSELandMapping();
		repaint();
	}// GEN-LAST:event_fESpecActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton sendMission;
	private javax.swing.JButton loadMission;
	private javax.swing.JButton addMission; //editor
	private javax.swing.JPanel addMissionJPanel; //editor
	
	private javax.swing.JButton loadProperty;
	private javax.swing.JButton fEE;
	private javax.swing.JCheckBox fEName;
	private javax.swing.JCheckBox fESpec;
	private javax.swing.JTextArea fMapping;
	private javax.swing.JButton fNE;
	private javax.swing.JTextPane fSELP;
	private javax.swing.JButton fSEs;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel patternsJPanel;
	private javax.swing.JPanel patternJPanel;
	private javax.swing.JPanel optionJPanel;
	private javax.swing.JPanel jPanelLogo;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel propertyPanel;
	private javax.swing.JPanel eventJPanel;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	// End of variables declaration//GEN-END:variables

	private LTLFormula loadMission() throws Exception {

		String selectedIdem = (String) patternBoxSelector.getSelectedItem();
		String patternCategory = (String) patternCategorySelector.getSelectedItem();

		LTLFormula computedltlformula = LTLFormula.TRUE;

		String locationsText = locations.getText().replaceAll(" ", "");
		String[] selectedLocations = locationsText.split(",");

		switch (patternCategory) {
		case "Triggers":
			Triggers p2 = Triggers.valueOf(selectedIdem.toUpperCase().replaceAll(" ", "_"));
			// computedltlformula = p2.getMission(selectedLocations);
			
			intentText.setText(p2.getDescription());
			variation.setText(p2.getVariations());
			examples.setText(p2.getExamples());
			relationships.setText(p2.getRelationships());
			occurences.setText(p2.getOccurrences());

			computedltlformula = p2.getMission(formulae.get((String) f1.getSelectedItem()),
					formulae.get((String) f2.getSelectedItem()));
			
			ltlFormula.setText(computedltlformula.accept(new LTLFormulaToStringVisitor()));

			formulae.put(FORMULA_COUNTER + " - " + (String) patternBoxSelector.getSelectedItem() + "(" + f1.getSelectedItem() +", "
					+f2.getSelectedItem()+")", computedltlformula);

			List<String> array1 = new ArrayList<String>(formulae.keySet());
			String[]t = new String[array1.size()];
			array1.toArray(t);
			propertyList.setListData(t);
			
			FORMULA_COUNTER = FORMULA_COUNTER + 1;
			break;
		case "Avoidance":
			Avoidance p = Avoidance.valueOf(selectedIdem.toUpperCase().replaceAll(" ", "_"));
			computedltlformula = p.getMission(selectedLocations);
			intentText.setText(p.getDescription());

			variation.setText(p.getVariations());

			examples.setText(p.getExamples());

			relationships.setText(p.getRelationships());

			occurences.setText(p.getOccurrences());
			ltlFormula.setText(computedltlformula.accept(new LTLFormulaToStringVisitor()));

			formulae.put(FORMULA_COUNTER + " - " + (String) patternBoxSelector.getSelectedItem() + "(" + locations.getText()
					+ ")", computedltlformula);

			List<String> array = new ArrayList<String>(formulae.keySet());
			String[] d = new String[array.size()];
			array.toArray(d);
			propertyList.setListData(d);
			FORMULA_COUNTER = FORMULA_COUNTER + 1;
			
			break;
		case "Core Movement":
			CoreMovementPatterns p1 = CoreMovementPatterns.valueOf(selectedIdem.toUpperCase().replaceAll(" ", "_"));
			computedltlformula = p1.getMission(selectedLocations);
			intentText.setText(p1.getDescription());
			variation.setText(p1.getVariations());
			examples.setText(p1.getExamples());
			relationships.setText(p1.getRelationships());
			occurences.setText(p1.getOccurrences());

			ltlFormula.setText(computedltlformula.accept(new LTLFormulaToStringVisitor()));

			formulae.put(FORMULA_COUNTER + " - " + (String) patternBoxSelector.getSelectedItem() + "(" + locations.getText()
					+ ")", computedltlformula);
			array = new ArrayList<String>(formulae.keySet());
			d = new String[array.size()];
			array.toArray(d);
			propertyList.setListData(d);
			FORMULA_COUNTER = FORMULA_COUNTER + 1;
			
			break;
		case "Composition":
			Composition c = Composition.valueOf(selectedIdem.toUpperCase().replaceAll(" ", "_"));
			intentText.setText(c.getDescription());

			computedltlformula = c.getMission(formulae.get((String) f1.getSelectedItem()),
					formulae.get((String) f2.getSelectedItem()));
			
			ltlFormula.setText(computedltlformula.accept(new LTLFormulaToStringVisitor()));

			formulae.put(FORMULA_COUNTER + " - " + (String) patternBoxSelector.getSelectedItem() + "(" + f1.getSelectedItem() +", "
					+f2.getSelectedItem()+")", computedltlformula);

			array = new ArrayList<String>(formulae.keySet());
			d = new String[array.size()];
			array.toArray(d);
			propertyList.setListData(d);
			
			FORMULA_COUNTER = FORMULA_COUNTER + 1;
		default:
			break;
		}
		return computedltlformula;
	}
	private void cleanPanels(){
;
		intentText.setText("");
		ltlFormula.setText("");
		variation.setText("");
		examples.setText("");
		relationships.setText("");
		occurences.setText("");
	}
}
