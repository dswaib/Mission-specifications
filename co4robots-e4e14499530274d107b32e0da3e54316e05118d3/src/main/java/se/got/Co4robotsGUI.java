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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.*;

import javax.imageio.ImageIO;
import javax.naming.spi.DirectoryManager;
import javax.security.auth.callback.TextOutputCallback;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
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
import javax.swing.JRadioButton;
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
import javax.swing.event.ListDataListener;
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

public class Co4robotsGUI extends javax.swing.JFrame implements java.awt.event.WindowFocusListener,  java.awt.event.WindowStateListener {
	
	public static Co4robotsGUI thisGui = null;
	public static javax.swing.JFrame editorGui = null;

	private static final String INIT_POSITION_MESSAGE = "Insert the positions to be considered separated by a comma";

	private static final int FRAME_INIT_HEIGTH = 600;

	private static final int FRAME_INIT_WIDTH = 800;
	
	public static String CurrentNewPatternName = "";
	public static String DirPath = "";
	public static String CurrentSelectedNewCategory = "";
	public static String CurrentSelectedNewCategoryType = "l";

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
		
	
		
		
		//create a directory for categories added at run time
		String homeDir = System.getProperty("user.home");
		String appDir = homeDir + "/Co4robots";//add lib folder after running the application for the first time
		File appFolder = new File(appDir);
		if(appFolder.isDirectory() && appFolder.exists()){
			//do nothing
		}else{
			try {
				boolean foo  = appFolder.mkdir();
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
				Co4robotsGUI gui = new Co4robotsGUI();
			    gui.showEditor();
			    Co4robotsGUI.thisGui = gui;
			}
		});
		
		
	}
	
	//This was testing loading of compiled classes at runtime
	/*public void TestX(String patternName){
		String path = Co4robotsGUI.DirPath + "/" + patternName  ;
	    
	    try {
	    	URL[] classes = {new File(path).toURI().toURL()};
		    URLClassLoader child = new URLClassLoader (classes, this.getClass().getClassLoader());
		    
	        Class classToLoad = Class.forName(patternName, true, child);
	        Method method = classToLoad.getDeclaredMethod ("getMission", String[].class);
	        Object instance = classToLoad.newInstance();
	        
	        Object result = method.invoke(instance, (Object)(new String[]{"x","y"}));
	        LTLFormula fmx = (LTLFormula)result;
	        System.out.println(result.toString());
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SecurityException e) {
	        e.printStackTrace();
	    } catch (NoSuchMethodException e) {
	        e.printStackTrace();
	    } catch (InstantiationException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	    } catch (InvocationTargetException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}*/
	
	
	public static void SyntaxTester() {
	    JFrame f = new JFrame("Mission/Pattern Editor");
	    
	    final Container c = f.getContentPane();
	    c.setLayout(new BorderLayout());
	    
	    JTabbedPane tabbedPane = new JTabbedPane();
	    c.add(tabbedPane, BorderLayout.CENTER);
	    
	    //add a select category tab
	    JPanel selectCategoryPanel = new JPanel();
	    selectCategoryPanel.setLayout(new BorderLayout());
	    
	    JPanel formContainerPanel = new JPanel();
	    formContainerPanel.setLayout(null);
	    JPanel formPanel = new JPanel();
	    formPanel.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    formPanel.setBounds(0, 0, 400, 250);
	    //gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.anchor = GridBagConstraints.CENTER;
	    
	    JLabel headingLabel = new JLabel();
	    headingLabel.setText("Add a new category");
	    gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
	    formPanel.add(headingLabel,gbc); //0,1
        
	    gbc.anchor = GridBagConstraints.LINE_END;
	    JLabel nameLabel = new JLabel();
	    nameLabel.setText("Category Name :");
	    gbc.gridx = 0;
        gbc.gridy = 1;
	    formPanel.add(nameLabel,gbc); //0,1
	    
	    JLabel typeLabel = new JLabel();
	    typeLabel.setText("Category type :");
	    gbc.gridx = 0;
        gbc.gridy = 2;
	    formPanel.add(typeLabel,gbc); //0,1
	    
	    JLabel typeLabel2 = new JLabel();
	    typeLabel2.setText(" ");
	    gbc.gridx = 0;
        gbc.gridy = 3;
	    formPanel.add(typeLabel2,gbc); //0,1
	    
	    JLabel emptyLabel1 = new JLabel();
	    emptyLabel1.setText(" ");
	    gbc.gridx = 0;
        gbc.gridy = 4;
	    formPanel.add(emptyLabel1,gbc); //0,1
	    
	    JLabel emptyLabel2 = new JLabel();
	    emptyLabel2.setText(" ");
	    gbc.gridx = 0;
        gbc.gridy = 5;
	    formPanel.add(emptyLabel2,gbc); //0,1
	    
	    JLabel emptyLabel3 = new JLabel();
	    emptyLabel3.setText(" ");
	    gbc.gridx = 0;
        gbc.gridy = 6;
	    formPanel.add(emptyLabel3,gbc); //0,1
	    
	    JLabel selectCatLabel = new JLabel();
	    selectCatLabel.setText("Select Category");
	    gbc.gridx = 0;
        gbc.gridy = 7;
	    formPanel.add(selectCatLabel,gbc); //0,1
	    
	    gbc.anchor = GridBagConstraints.LINE_START;
	    JTextField nameTextField = new JTextField(15);
	    gbc.gridx = 1;
        gbc.gridy = 1;
	    formPanel.add(nameTextField,gbc); //1,1
	    
	    JRadioButton typeButton1 = new JRadioButton("Location Cordinates");
	    typeButton1.setSelected(true);
	    Co4robotsGUI.CurrentSelectedNewCategoryType = "l";
	    typeButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Co4robotsGUI.CurrentSelectedNewCategoryType = "l";
			}
		});
	    JRadioButton typeButton2 = new JRadioButton("LTL Formula");
	    typeButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Co4robotsGUI.CurrentSelectedNewCategoryType = "f";
			}
		});

	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(typeButton1);
	    group.add(typeButton2);
	    gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(typeButton1,gbc); //1,1
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(typeButton2,gbc); //1,1
        
        JComboBox<ComboItem> catsSelector = new JComboBox<ComboItem>();
        JButton addCategoryButton = new JButton("Add Category");
	    gbc.gridx = 1;
        gbc.gridy = 4;
        addCategoryButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String catName = Co4robotsGUI.CurrentSelectedNewCategoryType + nameTextField.getText();
				String catDir = Co4robotsGUI.DirPath + "/" + catName;
				File catFolder = new File(catDir);
				if(catFolder.isDirectory() && catFolder.exists()){
					//do nothing
				}else{
					try {
						boolean foo  = catFolder.mkdir();
						System.out.println(foo);
					} catch (Exception exe) {
						// TODO Auto-generated catch block
						exe.printStackTrace();
						System.out.println("New creation of category failed");
					}
				}
				
				//reaload categories list in drop down
				catsSelector.removeAllItems();
				int indexToSelect = 0;
				int indexes = 0;
			    File directory = new File(Co4robotsGUI.DirPath);		
			    FileFilter directoryFileFilter = new FileFilter() {
			        public boolean accept(File file) {
			            return file.isDirectory() && !file.getName().equals("lib");
			        }
			    };
			    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
			    for (File directoryAsFile : directoryListAsFile) {
			       String catNameTemp = directoryAsFile.getName();
			       catsSelector.addItem(new ComboItem(catNameTemp.substring(1), catNameTemp));
			       if(catNameTemp.equals(catName)){
			    	   indexToSelect =  indexes;
			       }
			       indexes = indexes + 1;
			    }			    
				//clear fields
				nameTextField.setText("");
				//select this pattern
				catsSelector.setSelectedIndex(indexToSelect);
				Co4robotsGUI.CurrentSelectedNewCategory = catName;
				Co4robotsGUI.thisGui.patternCategorySelector.addItem("~"+catName);
			}
		});
	    formPanel.add(addCategoryButton,gbc); //1,1
	    
	    
	    File directory = new File(Co4robotsGUI.DirPath);		
	    FileFilter directoryFileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isDirectory() && !file.getName().equals("lib");
	        }
	    };
	    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
	    for (File directoryAsFile : directoryListAsFile) {
	       String catNameTemp = directoryAsFile.getName();
	       catsSelector.addItem(new ComboItem(catNameTemp.substring(1), catNameTemp));
	       //((Co4robotsGUI)Co4robotsGUI.thisGui).patternCategorySelector.addItem("~"+catNameTemp);
	    }
	    if(catsSelector.getItemCount() > 0){
	    	catsSelector.setSelectedIndex(0);
	    }
	    catsSelector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object item = catsSelector.getSelectedItem();
				if(item != null){
					String value = ((ComboItem)item).getValue();
					Co4robotsGUI.CurrentSelectedNewCategory = value;
				}
			}
		});
	    gbc.gridx = 1;
        gbc.gridy = 7;
	    formPanel.add(catsSelector,gbc); //1,1
	    JButton nextCategoryButton = new JButton("Next >>>");
	    gbc.gridx = 1;
        gbc.gridy = 8;
        nextCategoryButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object item = catsSelector.getSelectedItem();
				String value = ((ComboItem)item).getValue();
				Co4robotsGUI.CurrentSelectedNewCategory = value;
				//go to the next tab
				tabbedPane.setSelectedIndex(1);
			}
		});
	    formPanel.add(nextCategoryButton,gbc); //1,1
	    
	    formPanel.setBackground(typeButton1.getBackground());
	    formContainerPanel.setBackground(typeButton1.getBackground());
	    formContainerPanel.add(formPanel);
	    selectCategoryPanel.add(formContainerPanel,BorderLayout.CENTER);
	    
	    JPanel pExplanation = new JPanel();
	    pExplanation.setLayout(new GridLayout(0,1));
	    JTextArea labelText = new JTextArea("In the text editor, enter the LTL function that will generate the corresponding LTL formula for each mission generated from this pattern");
	    labelText.setEditable(false);
	    labelText.setSize(300, 400);
	    labelText.setPreferredSize(new Dimension(300, 400));
	    labelText.setLineWrap(true);
	    //label.setp
	    labelText.setBorder(BorderFactory.createCompoundBorder(
	    		labelText.getBorder(), 
	         BorderFactory.createEmptyBorder(10, 10, 10, 10)
	       )
	    );
	    labelText.setBackground(new Color(200,200,200));
	    pExplanation.add(labelText);
	    selectCategoryPanel.add(pExplanation, BorderLayout.EAST);
	    tabbedPane.addTab("ADD CATEGORY", selectCategoryPanel);
	    
	    //add an editor tab/page
	    JPanel editorPanel = new JPanel();
	    editorPanel.setLayout(new BorderLayout());
	    
	    JPanel configPanel = new JPanel();

	    
	    DefaultSyntaxKit.initKit();//initializing the jsyntax pane
        //creating the editor
	    final JEditorPane codeEditor = new JEditorPane();
	    JScrollPane scrPane = new JScrollPane(codeEditor);
	    editorPanel.add(scrPane,BorderLayout.CENTER);
	    
	    JTextArea label = new JTextArea("In the text editor, enter the LTL function that will generate the corresponding LTL formula for each mission generated from this pattern");
	    label.setEditable(false);
	    label.setSize(300, 400);
	    label.setPreferredSize(new Dimension(300, 400));
	    label.setLineWrap(true);
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
	    
	    
	    codeEditor.setContentType("text/java");
	   
	    
	    //add a save button
	    JButton jb  = new JButton("save");
	    c.add(jb, BorderLayout.SOUTH);
	    jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String code = codeEditor.getText();
				
				
				//save the file
				String pth = Co4robotsGUI.DirPath + "/" + Co4robotsGUI.CurrentSelectedNewCategory +  "/" + Co4robotsGUI.CurrentNewPatternName ;
				File f = new File(pth);
				if(f.isDirectory() && f.exists()){
					//do nothing
				}else{
					try {
						boolean foo  = f.mkdir();//pattern directory inside category directory
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
					codeFile.createNewFile();//create java file 
					
					
					FileWriter fw = new FileWriter(codeFile);
					fw.write(code);//write code to java file
					fw.flush();
					fw.close();
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					System.out.println("New creation java file failed");
				}
				
				//compile the application
				try {
					String cmd = "javac -cp "+ Co4robotsGUI.DirPath + "/lib/:"+ Co4robotsGUI.DirPath + "/lib/guava-16.0.1.jar  -d "+ pth + "/ " + fullFileName;
					
					System.out.println("running compile cmd: " + cmd);//print for debugging purpose
					runProcess(cmd);
					
					
					Co4robotsGUI.thisGui.show(true);
					Co4robotsGUI.editorGui.dispose();
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
	    Co4robotsGUI.editorGui = f;
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
	private static String getOutput(String name, InputStream ins) throws Exception {
		String line = null;
		String outPut = "";
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(name + " " + line);
	        outPut = outPut + line;
	    }
	    return outPut;
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
	//this runs the external programe
	private static String getProcessResults(String command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		String val = getOutput(command + " stdout:", pro.getInputStream());
		System.out.println("-----****ERROR****----");
		printLines(command + " stderr:", pro.getErrorStream());
		pro.waitFor();
		System.out.println(command + " exitValue() " + pro.exitValue());
		return val;
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

	// event controller facetcommand

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
		
		//nca
		//patternCategoriestItems.removeAllElements();
	    File directory = new File(Co4robotsGUI.DirPath);		
	    FileFilter directoryFileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isDirectory() && !file.getName().equals("lib");
	        }
	    };
	    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
	    for (File directoryAsFile : directoryListAsFile) {
	       String catNameTemp = directoryAsFile.getName();
	       //catsSelector.addItem(new ComboItem(catNameTemp.substring(1), catNameTemp));
	       patternCategorySelector.addItem("~"+catNameTemp);
	    }
	    //end nca
		

		Arrays.asList(CoreMovementPatterns.values()).stream().forEach(p -> patternItems.addElement(p.toString()));

		patternBoxSelector = new JComboBox<String>(patternItems);

		patternBoxSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) patternBoxSelector.getSelectedItem();
				System.out.println(selectedItem);
				//nca
				if (selectedItem != null && selectedItem.startsWith("~")) {
					String patternName = selectedItem.replace("~", "");
					//we have to run the compiled code
					String runClassPath = Co4robotsGUI.DirPath + "/" + Co4robotsGUI.CurrentSelectedNewCategory + "/" + patternName ;
				    String runCmd = "java -cp "+ runClassPath +":"+ Co4robotsGUI.DirPath + "/lib/guava-16.0.1.jar " + patternName;
				    System.out.println("RUNNING THE COMPILED CODE OF " + patternName);
				    System.out.println(runCmd);
				    try {
				    	
				    	String desc = getProcessResults(runCmd + " description");
				    	String vari = getProcessResults(runCmd + " variations");
				    	String exam = getProcessResults(runCmd + " examples");
				    	String rela = getProcessResults(runCmd + " relationships");
				    	String occu = getProcessResults(runCmd + " occurrences");
				    	
				    	intentText.setText(desc);
						variation.setText(vari);
						examples.setText(exam);
						relationships.setText(rela);
						occurences.setText(occu);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				    String catName = (String)patternCategorySelector.getSelectedItem();
					if(catName.startsWith("~f")){
					    f1.removeAllItems();
						f2.removeAllItems();
	
						DefaultComboBoxModel<String> formulaeList1 = new DefaultComboBoxModel<String>();
						DefaultComboBoxModel<String> formulaeList2 = new DefaultComboBoxModel<String>();
	
						formulae.keySet().stream().forEach(p -> formulaeList1.addElement(p));
						formulae.keySet().stream().forEach(p -> formulaeList2.addElement(p));
	
						f1.setModel(formulaeList1);
						f2.setModel(formulaeList2);
					}
				}
				//nca
				//added piece is && !selectedItem.startsWith("~")
				if (selectedItem != null && !selectedItem.startsWith("~")) {
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
				//nca
				//System.out.println("selectedItem>>" + selectedItem);
				if(selectedItem.startsWith("~")){
					System.out.println("cool");
					patternBoxSelector.removeAllItems();
					patternBoxSelector.removeAll();
					String catName = selectedItem.substring(1);
					//System.out.println("selectedItem<<" + catName);
					Co4robotsGUI.CurrentSelectedNewCategory = catName;
					//load the directories under this cat
					File directory = new File(Co4robotsGUI.DirPath + "/" + catName);		
				    FileFilter directoryFileFilter = new FileFilter() {
				        public boolean accept(File file) {
				            return file.isDirectory();
				        }
				    };
				    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
				    for (File directoryAsFile : directoryListAsFile) {
				       String patternNameTemp = directoryAsFile.getName();
				       //System.out.println("putting<<" + patternNameTemp);
				       //catsSelector.addItem(new ComboItem(catNameTemp.substring(1), catNameTemp));
				       patternBoxSelector.addItem("~"+patternNameTemp);
				    }
				}else{
				//end nca
					switch (selectedItem) {
					case "Core Movement":
						TitledBorder movementPatternTitle = BorderFactory.createTitledBorder("Core Movement");
						movementPatternTitle.setTitlePosition(TitledBorder.RIGHT);
	
						patternBoxSelector.setBorder(movementPatternTitle);
						patternItems.removeAllElements();
	
						Arrays.asList(CoreMovementPatterns.values()).stream()
								.forEach(p -> patternItems.addElement(p.toString()));
	
						patternBoxSelector.setModel(patternItems);
						
						
						//nca
						//cbh
						//add things here
						ArrayList<String> xx = Templates.findFoldersInDirectory(Co4robotsGUI.DirPath);
						patternItems.removeAllElements();
						xx.forEach( p -> patternItems.addElement(p.toString()));
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
				//nca
				}//end of else block
				//end nca
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
				Co4robotsGUI.thisGui.setVisible(false);
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
	
	//nca
	public LTLFormula getFormulaFromPath(String patternName, String path, String []args){
		try {  
			URL[] classes = {new File(path).toURI().toURL()}; 
			URLClassLoader child = new URLClassLoader (classes, this.getClass().getClassLoader());  
			
			Class classToLoad = Class.forName(patternName, true, child); 
			Method method = classToLoad.getDeclaredMethod(
				"getMission", String[].class
			);
			Object instance = classToLoad.newInstance(); 	        
			Object result = method.invoke(instance, (Object)(args)); 
			LTLFormula fmx = (LTLFormula)result;  
			return fmx; 
		}catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
		}catch (SecurityException e) { 
			e.printStackTrace(); 
		}catch (NoSuchMethodException e) { 
			e.printStackTrace(); 
		}catch (InstantiationException e) {
			e.printStackTrace(); 
		}catch (IllegalAccessException e) { 
			e.printStackTrace(); 
		}catch (IllegalArgumentException e) { 
			e.printStackTrace(); 
		}catch (InvocationTargetException e) { 
			e.printStackTrace(); 
		}catch (Exception e) { 
			e.printStackTrace(); 
		}
		return LTLFormula.TRUE;
	}
	
	public LTLFormula getFormulaFromPath(String patternName, String path, LTLFormula f1, LTLFormula f2){
		try {  
			URL[] classes = {new File(path).toURI().toURL()}; 
			URLClassLoader child = new URLClassLoader (classes, this.getClass().getClassLoader());  
			
			Class classToLoad = Class.forName(patternName, true, child); 
			Method method = classToLoad.getDeclaredMethod(
				"getMission", LTLFormula.class, LTLFormula.class
			);
			Object instance = classToLoad.newInstance(); 	        
			Object result = method.invoke(instance, (Object)(f1), (Object)(f2)); 
			LTLFormula fmx = (LTLFormula)result;  
			return fmx; 
		}catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
		}catch (SecurityException e) { 
			e.printStackTrace(); 
		}catch (NoSuchMethodException e) { 
			e.printStackTrace(); 
		}catch (InstantiationException e) {
			e.printStackTrace(); 
		}catch (IllegalAccessException e) { 
			e.printStackTrace(); 
		}catch (IllegalArgumentException e) { 
			e.printStackTrace(); 
		}catch (InvocationTargetException e) { 
			e.printStackTrace(); 
		}catch (Exception e) { 
			e.printStackTrace(); 
		}
		return LTLFormula.TRUE;
	}
	//end nca

	private LTLFormula loadMission() throws Exception {

		String selectedIdem = (String) patternBoxSelector.getSelectedItem();
		String patternCategory = (String) patternCategorySelector.getSelectedItem();

		LTLFormula computedltlformula = LTLFormula.TRUE;

		String locationsText = locations.getText().replaceAll(" ", "");
		String[] selectedLocations = locationsText.split(",");

		//nca
		if(selectedIdem.startsWith("~")){
			//+ "/" + Co4robotsGUI.CurrentSelectedNewCategory + 
			String catName = patternCategory.substring(1);
			String patternName = selectedIdem.replace("~", "");
			//we have to run the compiled code
			String runClassPath = Co4robotsGUI.DirPath + "/" + catName +  "/" + patternName ;
		    String runCmd = "java -cp "+ runClassPath +":"+ Co4robotsGUI.DirPath + "/lib/guava-16.0.1.jar " + patternName;
		    System.out.println("RUNNING THE COMPILED CODE OF " + patternName);
		    System.out.println(runCmd);
		    try {
		    	
		    	String desc = getProcessResults(runCmd + " description");
		    	String vari = getProcessResults(runCmd + " variations");
		    	String exam = getProcessResults(runCmd + " examples");
		    	String rela = getProcessResults(runCmd + " relationships");
		    	String occu = getProcessResults(runCmd + " occurrences");
		    	
		    	intentText.setText(desc);
				variation.setText(vari);
				examples.setText(exam);
				relationships.setText(rela);
				occurences.setText(occu);
				
				String fml = "";
				String params = "";
				//the computed formula
				LTLFormula formula =  LTLFormula.TRUE;
				if(catName.startsWith("l")){
					String positions = locationsText.replace(","," ");
					fml = getProcessResults(runCmd + " mission " + positions);
					params = locations.getText();
					
					String []posArgs = params.split(",");
					formula = getFormulaFromPath(patternName, runClassPath, posArgs);
					fml = formula.accept(new LTLFormulaToStringVisitor()); 
				}else if(catName.startsWith("f")){
					String m1 = (String)f1.getSelectedItem();
					m1 = m1.replace(" ", "");
					int flagIndex = m1.indexOf('~');
					m1 = m1.substring(flagIndex);
					String m2 = (String)f2.getSelectedItem();
					m2 = m2.replace(" ", "");
					flagIndex = m2.indexOf('~');
					m2 = m2.substring(flagIndex);
					String cmdToRun = runCmd + " mission " + m1 + " " + m2;
					System.out.println("cmdToRun: " + cmdToRun);
					fml = getProcessResults(cmdToRun);
					params = m1 + ", " + m2;
					
					String x = (String)f1.getSelectedItem();
					String y = (String)f2.getSelectedItem();
					LTLFormula fm1 = (LTLFormula)formulae.get(x);
					LTLFormula fm2 = (LTLFormula)formulae.get(y);
					formula = getFormulaFromPath(patternName, runClassPath, fm1, fm2);
					fml = formula.accept(new LTLFormulaToStringVisitor()); 
				}
				
				
				ltlFormula.setText(fml);
				
				formulae.put(FORMULA_COUNTER + " - " + (String) patternBoxSelector.getSelectedItem() + "(" + params + ")", formula);
				List<String> array = new ArrayList<String>(formulae.keySet());
				String[] d = new String[array.size()];
				array.toArray(d);
				propertyList.setListData(d);
				FORMULA_COUNTER = FORMULA_COUNTER + 1;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
		//end nca
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
				if(selectedIdem.startsWith("~")){
					String patternName = selectedIdem.replace("~", "");
					//we have to run the compiled code
					String runClassPath = Co4robotsGUI.DirPath + "/" + patternName ;
				    String runCmd = "java -cp "+ runClassPath +":"+ Co4robotsGUI.DirPath + "/lib/guava-16.0.1.jar " + patternName;
				    System.out.println("RUNNING THE COMPILED CODE OF " + patternName);
				    System.out.println(runCmd);
				    try {
				    	
				    	String desc = getProcessResults(runCmd + " description");
				    	String vari = getProcessResults(runCmd + " variations");
				    	String exam = getProcessResults(runCmd + " examples");
				    	String rela = getProcessResults(runCmd + " relationships");
				    	String occu = getProcessResults(runCmd + " occurrences");
				    	
				    	intentText.setText(desc);
						variation.setText(vari);
						examples.setText(exam);
						relationships.setText(rela);
						occurences.setText(occu);
						
						//the computed formula
						String positions = locationsText.replace(","," ");
						//String fml = getProcessResults(runCmd + " mission " + positions);
						
						String fml = getProcessResults(runCmd + " mission ~VISIT(x,y) ~VISIT(x,y)");
						
						
						
						ltlFormula.setText(fml);
						
						formulae.put(FORMULA_COUNTER + " - " + (String) patternBoxSelector.getSelectedItem() + "(" + locations.getText()
								+ ")", LTLFormula.TRUE);
						array = new ArrayList<String>(formulae.keySet());
						d = new String[array.size()];
						array.toArray(d);
						propertyList.setListData(d);
						FORMULA_COUNTER = FORMULA_COUNTER + 1;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
				
					CoreMovementPatterns p1 = CoreMovementPatterns.valueOf(selectedIdem.toUpperCase().replaceAll(" ", "_"));
					computedltlformula = p1.getMission(selectedLocations);
					intentText.setText(p1.getDescription());
					variation.setText(p1.getVariations());
					examples.setText(p1.getExamples());
					relationships.setText(p1.getRelationships());
					occurences.setText(p1.getOccurrences());
		
					String foo = computedltlformula.accept(new LTLFormulaToStringVisitor());
					System.out.println("formula is : " + foo);
					ltlFormula.setText(foo);
		
					formulae.put(FORMULA_COUNTER + " - " + (String) patternBoxSelector.getSelectedItem() + "(" + locations.getText()
							+ ")", computedltlformula);
					array = new ArrayList<String>(formulae.keySet());
					d = new String[array.size()];
					array.toArray(d);
					propertyList.setListData(d);
					FORMULA_COUNTER = FORMULA_COUNTER + 1;
				}
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
		//nca
		} //end of else block for ~
		//end nca
		return computedltlformula;
	}
	private void cleanPanels(){
		intentText.setText("");
		ltlFormula.setText("");
		variation.setText("");
		examples.setText("");
		relationships.setText("");
		occurences.setText("");
	}

	

	@Override
	public void windowGainedFocus(WindowEvent e) {
		System.out.println("focus has been gained");
	}
	
	public void reloadCats(){
		System.out.println("reloading cats");
		// TODO Auto-generated method stub				
		patternCategorySelector.removeAll();
		// TODO Auto-generated method stub
		String[] patternCategories = { "Core Movement", "Triggers", "Avoidance", "Composition" };
		DefaultComboBoxModel<String> patternCategoriestItems = new DefaultComboBoxModel<>();

		Arrays.asList(patternCategories).stream().forEach(p -> patternCategoriestItems.addElement(p.toString()));

		patternCategorySelector = new JComboBox<String>(patternCategoriestItems);
		
		//nca
		//patternCategoriestItems.removeAllElements();
	    File directory = new File(Co4robotsGUI.DirPath);		
	    FileFilter directoryFileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isDirectory() && !file.getName().equals("lib");
	        }
	    };
	    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
	    for (File directoryAsFile : directoryListAsFile) {
	       String catNameTemp = directoryAsFile.getName();
	       //catsSelector.addItem(new ComboItem(catNameTemp.substring(1), catNameTemp));
	       patternCategorySelector.addItem("~"+catNameTemp);
	    }
	    //end nca
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("focus has been lost");
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("focus has been lost xx");
	}
}
