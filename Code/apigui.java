package weka.api;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.StringToWordVector;

import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class apigui {

	private JFrame frmTpDataLining;
	private File file;
	private File file2;
	private JRadioButton A1;
	private JRadioButton A2;
	private JRadioButton A3;
	private JRadioButton A4;
	private JRadioButton A5;
	private JRadioButton A6;
	private JRadioButton B1;
	private JRadioButton B2;
	private JRadioButton B3;
	private JTextField input1;
	private JTextField input2;
	private int CrossValidation_Value;
	private float Split_Percentage;
	private BufferedReader bufferedReader;
	private Instances datasetInstances;
	private String DataStes;
	private JTextField input3;
	private JTextField input4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					apigui window = new apigui();
					window.frmTpDataLining.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public apigui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmTpDataLining = new JFrame();
		frmTpDataLining.setTitle("TP04-05 Data Lining");
		frmTpDataLining.setBounds(100, 100, 881, 599);
		frmTpDataLining.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTpDataLining.getContentPane().setLayout(null);

		JLabel label = new JLabel("");
		label.setForeground(SystemColor.activeCaption);
		label.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 132, 309, 44);
		frmTpDataLining.getContentPane().add(label);
		
		JCheckBox vTF = new JCheckBox("TF");
		vTF.setBounds(546, 191, 65, 23);
		frmTpDataLining.getContentPane().add(vTF);
		
		JCheckBox vNON = new JCheckBox("Non just filter");
		vNON.setBounds(546, 263, 150, 23);
		frmTpDataLining.getContentPane().add(vNON);
		
		JCheckBox vIDF = new JCheckBox("IDF");
		vIDF.setBounds(546, 226, 52, 23);
		frmTpDataLining.getContentPane().add(vIDF);
		
		JCheckBox vLS = new JCheckBox("Lovin's Stemmer");
		vLS.setBounds(630, 226, 150, 23);
		frmTpDataLining.getContentPane().add(vLS);
		
		JCheckBox vLC = new JCheckBox("Lower Case");
		vLC.setBounds(630, 191, 150, 23);
		frmTpDataLining.getContentPane().add(vLC);

		JCheckBox ddd1 = new JCheckBox("Discretize");
		ddd1.setBounds(45, 263, 101, 23);
		frmTpDataLining.getContentPane().add(ddd1);

		JCheckBox mmm1 = new JCheckBox("Remove Missing Values");
		mmm1.setBounds(45, 228, 200, 23);
		frmTpDataLining.getContentPane().add(mmm1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 304, 826, 225);
		frmTpDataLining.getContentPane().add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

//		Button for opening the  learning dataset

		JButton btnNewButton = new JButton("Load learn set");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("ARFF File", "arff"));
				int option = fileChooser.showOpenDialog(frmTpDataLining);
				if (option == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					label.setText("File Selected: " + file.getName());
				} else {
					label.setText("Loading ARFF file canceled");
				}

			}
		});

//		Button for opening the  learning testset

		JButton btnLoadTestSet = new JButton("Load test set");
		btnLoadTestSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser2 = new JFileChooser();
				fileChooser2.setFileFilter(new FileNameExtensionFilter("ARFF File", "arff"));
				int option = fileChooser2.showOpenDialog(frmTpDataLining);
				if (option == JFileChooser.APPROVE_OPTION) {
					file2 = fileChooser2.getSelectedFile();
					label.setText("File Selected: " + file2.getName());
				} else {
					label.setText("Loading TEST file canceled");
				}

			}
		});
		btnLoadTestSet.setBounds(177, 20, 144, 25);
		frmTpDataLining.getContentPane().add(btnLoadTestSet);

		btnNewButton.setBounds(12, 20, 144, 25);
		frmTpDataLining.getContentPane().add(btnNewButton);

//		Button for view the  learning learn set

		JButton btnViewArff = new JButton("View learn file");
		btnViewArff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// textArea.setText("");
				try {
					BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					textArea.read(input, "READING FILE :-)");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnViewArff.setBounds(12, 57, 144, 25);
		frmTpDataLining.getContentPane().add(btnViewArff);

		input1 = new JTextField();
		input1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		input1.setHorizontalAlignment(SwingConstants.CENTER);
		input1.setText("10");
		input1.setBounds(741, 43, 52, 31);
		frmTpDataLining.getContentPane().add(input1);
		input1.setColumns(10);

		input2 = new JTextField();
		input2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		input2.setText("66");
		input2.setHorizontalAlignment(SwingConstants.CENTER);
		input2.setColumns(10);
		input2.setBounds(740, 108, 52, 31);
		frmTpDataLining.getContentPane().add(input2);
		
		
		input3 = new JTextField();
		input3.setText("10");
		input3.setHorizontalAlignment(SwingConstants.CENTER);
		input3.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		input3.setColumns(10);
		input3.setBounds(467, 187, 52, 31);
		frmTpDataLining.getContentPane().add(input3);
		
		input4 = new JTextField();
		input4.setText("10");
		input4.setHorizontalAlignment(SwingConstants.CENTER);
		input4.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		input4.setColumns(10);
		input4.setBounds(467, 222, 52, 31);
		frmTpDataLining.getContentPane().add(input4);

		JLabel lblChooseValidationOption = new JLabel("Choose Classifier Algorithm :");
		lblChooseValidationOption.setBounds(339, 16, 216, 15);
		frmTpDataLining.getContentPane().add(lblChooseValidationOption);

		JLabel lblChooseValidationOption_1 = new JLabel("Choose Validation Option :");
		lblChooseValidationOption_1.setBounds(567, 16, 200, 15);
		frmTpDataLining.getContentPane().add(lblChooseValidationOption_1);

		B1 = new JRadioButton("Cross Validation");
		B1.setBounds(577, 39, 150, 23);
		frmTpDataLining.getContentPane().add(B1);

		B2 = new JRadioButton("Use test set file");
		B2.setBounds(577, 76, 150, 23);
		frmTpDataLining.getContentPane().add(B2);

		B3 = new JRadioButton("Percentage split");
		B3.setBounds(577, 116, 150, 23);
		frmTpDataLining.getContentPane().add(B3);

		A1 = new JRadioButton("C.4.5");
		A1.setBounds(342, 43, 200, 23);
		frmTpDataLining.getContentPane().add(A1);

		A2 = new JRadioButton("RandomForset");
		A2.setBounds(342, 80, 200, 23);
		frmTpDataLining.getContentPane().add(A2);

		A4 = new JRadioButton("One Rule");
		A4.setBounds(342, 157, 89, 23);
		frmTpDataLining.getContentPane().add(A4);

		A3 = new JRadioButton("Naive Bayes");
		A3.setBounds(342, 120, 200, 23);
		frmTpDataLining.getContentPane().add(A3);
		
		A5 = new JRadioButton("Bagging NB");
		A5.setBounds(342, 195, 117, 23);
		frmTpDataLining.getContentPane().add(A5);
		
		A6 = new JRadioButton("Boosting NB");
		A6.setBounds(342, 230, 117, 23);
		frmTpDataLining.getContentPane().add(A6);
		

		


		ButtonGroup gpp = new ButtonGroup();
		gpp.add(A1);
		gpp.add(A2);
		gpp.add(A3);
		gpp.add(A4);
		gpp.add(A5);
		gpp.add(A6);

		ButtonGroup gpp2 = new ButtonGroup();
		gpp2.add(B1);
		gpp2.add(B2);
		gpp2.add(B3);

//		Button for clearing screen

		JButton btnClearResults = new JButton("Clear Results");
		btnClearResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		btnClearResults.setBounds(101, 94, 144, 25);
		frmTpDataLining.getContentPane().add(btnClearResults);

//		Button for view the  learning testset

		JButton btnViewTestFile = new JButton("View test file");
		btnViewTestFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// textArea.setText("");
				try {
					BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
					textArea.read(input, "READING FILE :-)");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnViewTestFile.setBounds(177, 57, 144, 25);
		frmTpDataLining.getContentPane().add(btnViewTestFile);

//		most important button

		JButton btnNewButton_2 = new JButton("RUN");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					// Dataset path
					DataStes = file.getPath();

					// Creating bufferedreader to read the dataset
					bufferedReader = new BufferedReader(new FileReader(DataStes));

					// Create dataset instances
					datasetInstances = new Instances(bufferedReader);

					if (datasetInstances.classIndex() == -1) {
						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);
					}
					

				}
				// Catch block to handle the exceptions
				catch (Exception e) {

					// Print message on the console
					System.out.println("Error Occurred!!!! \n" + e.getMessage());
					textArea.setText("Error Occurred!!!! \n" + e.getMessage());
				}
				
				
				// Text Classification
				// Options for StringToWordVector filter
				
				// Just The filter with no options
				
				if (vNON.isSelected()) {
					
					
					
					try {
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(false);
						filter.setTFTransform(false);
						filter.setIDFTransform(false);		
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
				    
				}
				// Catch block to handle the exceptions
				catch (Exception e) {

					// Print message on the console
					System.out.println("Error Occurred!!!! \n" + e.getMessage());
					textArea.setText("Error Occurred!!!! \n" + e.getMessage());
				}
					

					
					
				}
				
				// TF + IDF + LowerCase + Lovin's Stemmer
				else if (vTF.isSelected() && vIDF.isSelected() && vLC.isSelected() && vLS.isSelected()) {
					
					
					
					try {
						
						
					
					StringToWordVector filter = new StringToWordVector();
					filter.setInputFormat(datasetInstances);
					filter.setLowerCaseTokens(true);
					filter.setTFTransform(true);
					filter.setIDFTransform(true);		
				    LovinsStemmer stemmer = new LovinsStemmer();
				    filter.setStemmer(stemmer);
				    datasetInstances = Filter.useFilter(datasetInstances, filter);
				    datasetInstances.setClassIndex(0);
				    
				}
				// Catch block to handle the exceptions
				catch (Exception e) {

					// Print message on the console
					System.out.println("Error Occurred!!!! \n" + e.getMessage());
					textArea.setText("Error Occurred!!!! \n" + e.getMessage());
				}
					
					
			
					
					
				}
				
				
				// TF + IDF + LowerCase
				else if (vTF.isSelected() && vIDF.isSelected() && vLC.isSelected()) {
					
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(true);
						filter.setTFTransform(true);
						filter.setIDFTransform(true);		
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
					
				}
				
				// TF + IDF + Lovin's Stemmer
				else if (vTF.isSelected() && vIDF.isSelected() && vLS.isSelected()) {
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(false);
						filter.setTFTransform(true);
						filter.setIDFTransform(true);		
					    LovinsStemmer stemmer = new LovinsStemmer();
					    filter.setStemmer(stemmer);
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
				}
				
				// TF + LowerCase + Lovin's Stemmer
				else if (vTF.isSelected() && vLC.isSelected() && vLS.isSelected()) {
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(true);
						filter.setTFTransform(true);
						filter.setIDFTransform(false);		
					    LovinsStemmer stemmer = new LovinsStemmer();
					    filter.setStemmer(stemmer);
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
				}
				
				// IDF + LowerCase + Lovin's Stemmer
				else if (vIDF.isSelected() && vLC.isSelected() && vLS.isSelected()) {
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(true);
						filter.setTFTransform(false);
						filter.setIDFTransform(true);		
					    LovinsStemmer stemmer = new LovinsStemmer();
					    filter.setStemmer(stemmer);
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
				}
				
				// LowerCase + Lovin's Stemmer
				else if (vLC.isSelected() && vLS.isSelected()) {
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(true);
						filter.setTFTransform(false);
						filter.setIDFTransform(false);		
					    LovinsStemmer stemmer = new LovinsStemmer();
					    filter.setStemmer(stemmer);
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
				}
				
				// TF + IDF
				else if (vTF.isSelected() && vIDF.isSelected()) {
					
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(false);
						filter.setTFTransform(true);
						filter.setIDFTransform(true);		
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
					
				}
				
				// TF + Lovin's Stemmer
				else if (vTF.isSelected() && vLS.isSelected()) {
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(false);
						filter.setTFTransform(true);
						filter.setIDFTransform(false);		
					    LovinsStemmer stemmer = new LovinsStemmer();
					    filter.setStemmer(stemmer);
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
					
				}
				
				// IDF + LowerCase
				else if (vIDF.isSelected() && vLC.isSelected()) {
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(true);
						filter.setTFTransform(false);
						filter.setIDFTransform(true);		
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
				}
				
				
				// TF
				else if (vTF.isSelected()) {
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(false);
						filter.setTFTransform(true);
						filter.setIDFTransform(false);		
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
					
									
				}
				
				// Lovin's Stemmer
				else if (vLS.isSelected()) {
					
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(false);
						filter.setTFTransform(false);
						filter.setIDFTransform(false);		
					    LovinsStemmer stemmer = new LovinsStemmer();
					    filter.setStemmer(stemmer);
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
					
				}
				
				// IDF
				else if (vIDF.isSelected()) {
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(false);
						filter.setTFTransform(false);
						filter.setIDFTransform(true);		
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
				}
				
				// LowerCase
				else if (vLC.isSelected()) {
					
					
					try {
						
						
						StringToWordVector filter = new StringToWordVector();
						filter.setInputFormat(datasetInstances);
						filter.setLowerCaseTokens(true);
						filter.setTFTransform(false);
						filter.setIDFTransform(false);		
					    datasetInstances = Filter.useFilter(datasetInstances, filter);
					    datasetInstances.setClassIndex(0);
					    
					    
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}
					
					
					
				}
				

				// Missing Values + Discretize

				else if (ddd1.isSelected() && mmm1.isSelected()) {
					try {

						// remove instances with missing class
						datasetInstances.deleteWithMissingClass();

						// replace missing values
						ReplaceMissingValues m_replaceMissing = new ReplaceMissingValues();
						m_replaceMissing.setInputFormat(datasetInstances);
						datasetInstances = Filter.useFilter(datasetInstances, m_replaceMissing);

						// Discretize numaric values
						Discretize m_DiscretizeFilter = new Discretize();
						m_DiscretizeFilter.setInputFormat(datasetInstances);
						datasetInstances = Filter.useFilter(datasetInstances, m_DiscretizeFilter);

					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

					// Discretize

				} else if (ddd1.isSelected()) {

					try {
						// Discretize numaric values
						Discretize m_DiscretizeFilter = new Discretize();
						m_DiscretizeFilter.setInputFormat(datasetInstances);
						datasetInstances = Filter.useFilter(datasetInstances, m_DiscretizeFilter);
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

					// Missing Values

				} else if (mmm1.isSelected()) {

					try {

						// remove instances with missing class
						datasetInstances.deleteWithMissingClass();

						// replace missing values
						ReplaceMissingValues m_replaceMissing = new ReplaceMissingValues();
						m_replaceMissing.setInputFormat(datasetInstances);
						datasetInstances = Filter.useFilter(datasetInstances, m_replaceMissing);

					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

					// No Filters

				} else {

					try {
						// Dataset path
						DataStes = file.getPath();

						// Creating bufferedreader to read the dataset
						bufferedReader = new BufferedReader(new FileReader(DataStes));

						// Create dataset instances
						datasetInstances = new Instances(bufferedReader);
						
						if (datasetInstances.classIndex() == -1) {
							datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);
						}
					}
					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Cross validation + J48
				if (A1.isSelected() && B1.isSelected()) {

					try {
						// getting CrossValidation_Value input

						CrossValidation_Value = Integer.parseInt(input1.getText());

						// Create J48 classifier by
						// creating object of J48 class
						J48 j48Classifier = new J48();

//						// Set Target Class
//						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// Evaluating by creating object of Evaluation
						// class
						Evaluation evaluation = new Evaluation(datasetInstances);

						// Cross Validate Model with user selected folds
						evaluation.crossValidateModel(j48Classifier, datasetInstances, CrossValidation_Value,
								new Random(1));

						System.out.println(evaluation.toSummaryString("\nResults", false));
						textArea.setText(evaluation.toSummaryString("Cross validation + J48 Evaluation Results : \n"
								+ "\nPrecision = " + evaluation.precision(1) + "\nRecall = " + evaluation.recall(1)
								+ "\nfMeasure = " + evaluation.fMeasure(1) + "\nError Rate = " + evaluation.errorRate()
								+ "\n\n", false));

					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}
				// Cross validation + RandomForest
				else if (A2.isSelected() && B1.isSelected()) {

					try {

						// getting CrossValidation_Value input

						CrossValidation_Value = Integer.parseInt(input1.getText());

						// Create RandomForest classifier by
						// creating object of RandomForest class
						RandomForest RandomForestClassifier = new RandomForest();
						RandomForestClassifier.setNumTrees(100);

//						// Set Target Class
//						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// Evaluating by creating object of Evaluation
						// class
						Evaluation evaluation = new Evaluation(datasetInstances);

						// Cross Validate Model with user selected folds
						evaluation.crossValidateModel(RandomForestClassifier, datasetInstances, CrossValidation_Value,
								new Random(1));

						System.out.println(evaluation.toSummaryString("\nResults", false));
						textArea.setText(
								evaluation.toSummaryString("Cross validation + RandomForest Evaluation Results : \n"
										+ "\nPrecision = " + evaluation.precision(1) + "\nRecall = "
										+ evaluation.recall(1) + "\nfMeasure = " + evaluation.fMeasure(1)
										+ "\nError Rate = " + evaluation.errorRate() + "\n\n", false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}
				// Cross validation + NaiveBayes
				else if (A3.isSelected() && B1.isSelected()) {

					try {

						// getting CrossValidation_Value input

						CrossValidation_Value = Integer.parseInt(input1.getText());

						// Create NaiveBayes classifier by
						// creating object of NaiveBayes class
						NaiveBayes NaiveBayesClassifier = new NaiveBayes();

//						// Set Target Class
//						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// Evaluating by creating object of Evaluation
						// class
						Evaluation evaluation = new Evaluation(datasetInstances);

						// Cross Validate Model with user selected folds
						evaluation.crossValidateModel(NaiveBayesClassifier, datasetInstances, CrossValidation_Value,
								new Random(1));

						System.out.println(evaluation.toSummaryString("\nResults", false));
						textArea.setText(
								evaluation.toSummaryString("Cross validation + NaiveBayes Evaluation Results : \n"
										+ "\nPrecision = " + evaluation.precision(1) + "\nRecall = "
										+ evaluation.recall(1) + "\nfMeasure = " + evaluation.fMeasure(1)
										+ "\nError Rate = " + evaluation.errorRate() + "\n\n", false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Cross validation + One Rule
				else if (A4.isSelected() && B1.isSelected()) {

					try {

						// getting CrossValidation_Value input

						CrossValidation_Value = Integer.parseInt(input1.getText());

						// Create One Rule classifier by
						// creating object of One Rule class
						OneR OneRClassifier = new OneR();

//						// Set Target Class
//						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// Evaluating by creating object of Evaluation
						// class
						Evaluation evaluation = new Evaluation(datasetInstances);

						// Cross Validate Model with user selected folds
						evaluation.crossValidateModel(OneRClassifier, datasetInstances, CrossValidation_Value,
								new Random(1));

						System.out.println(evaluation.toSummaryString("\nResults", false));
						textArea.setText(
								evaluation.toSummaryString("Cross validation + One Rule Evaluation Results : \n"
										+ "\nPrecision = " + evaluation.precision(1) + "\nRecall = "
										+ evaluation.recall(1) + "\nfMeasure = " + evaluation.fMeasure(1)
										+ "\nError Rate = " + evaluation.errorRate() + "\n\n", false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Test File + J48
				else if (A1.isSelected() && B2.isSelected()) {

					try {

						// Create J48 classifier by
						// creating object of J48 class
						J48 j48Classifier = new J48();

						// testset path
						String stastes2 = file2.getPath();

						// Creating bufferedreader to read the testset
						BufferedReader bufferedReader2 = new BufferedReader(new FileReader(stastes2));

						// Create test instances
						Instances testsetInstances = new Instances(bufferedReader2);

						// Set Target Class
						testsetInstances.setClassIndex(testsetInstances.numAttributes() - 1);

						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// build model

						j48Classifier.buildClassifier(datasetInstances);

						// use
						Evaluation eval = new Evaluation(testsetInstances);
						eval.evaluateModel(j48Classifier, testsetInstances);

						System.out.println(eval.toSummaryString("\nResults", false));
						textArea.setText(
								eval.toSummaryString("Test Set + J48 Evaluation Results : \n" + "\nPrecision = "
										+ eval.precision(1) + "\nRecall = " + eval.recall(1) + "\nfMeasure = "
										+ eval.fMeasure(1) + "\nError Rate = " + eval.errorRate() + "\n\n", false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Test File + RandomForest
				else if (A2.isSelected() && B2.isSelected()) {

					try {

						// Create RandomForest classifier by
						// creating object of RandomForest class
						RandomForest RandomForestClassifier = new RandomForest();
						RandomForestClassifier.setNumTrees(100);

						// testset path
						String stastes2 = file2.getPath();

						// Creating bufferedreader to read the testset
						BufferedReader bufferedReader2 = new BufferedReader(new FileReader(stastes2));

						// Create test instances
						Instances testsetInstances = new Instances(bufferedReader2);

						// Set Target Class
						testsetInstances.setClassIndex(testsetInstances.numAttributes() - 1);

						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// build model

						RandomForestClassifier.buildClassifier(datasetInstances);

						// use
						Evaluation eval = new Evaluation(testsetInstances);
						eval.evaluateModel(RandomForestClassifier, testsetInstances);

						System.out.println(eval.toSummaryString("\nResults", false));
						textArea.setText(
								eval.toSummaryString(
										"Test Set + RandomForest Evaluation Results : \n" + "\nPrecision = "
												+ eval.precision(1) + "\nRecall = " + eval.recall(1) + "\nfMeasure = "
												+ eval.fMeasure(1) + "\nError Rate = " + eval.errorRate() + "\n\n",
										false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Test File + NaiveBayes
				else if (A3.isSelected() && B2.isSelected()) {

					try {

						// Create NaiveBayes classifier by
						// creating object of NaiveBayes class
						NaiveBayes NaiveBayesClassifier = new NaiveBayes();

						// testset path
						String stastes2 = file2.getPath();

						// Creating bufferedreader to read the testset
						BufferedReader bufferedReader2 = new BufferedReader(new FileReader(stastes2));

						// Create test instances
						Instances testsetInstances = new Instances(bufferedReader2);

						// Set Target Class
						testsetInstances.setClassIndex(testsetInstances.numAttributes() - 1);

						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// build model

						NaiveBayesClassifier.buildClassifier(datasetInstances);

						// use
						Evaluation eval = new Evaluation(testsetInstances);
						eval.evaluateModel(NaiveBayesClassifier, testsetInstances);

						System.out.println(eval.toSummaryString("\nResults", false));
						textArea.setText(
								eval.toSummaryString(
										"Test Set + Naive Bayes Evaluation Results : \n" + "\nPrecision = "
												+ eval.precision(1) + "\nRecall = " + eval.recall(1) + "\nfMeasure = "
												+ eval.fMeasure(1) + "\nError Rate = " + eval.errorRate() + "\n\n",
										false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Test File + One Rule
				else if (A4.isSelected() && B2.isSelected()) {

					try {

						// Create One Rule classifier by
						// creating object of One Rule class
						OneR OneRClassifier = new OneR();

						// testset path
						String stastes2 = file2.getPath();

						// Creating bufferedreader to read the testset
						BufferedReader bufferedReader2 = new BufferedReader(new FileReader(stastes2));

						// Create test instances
						Instances testsetInstances = new Instances(bufferedReader2);

						// Set Target Class
						testsetInstances.setClassIndex(testsetInstances.numAttributes() - 1);

						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// build model

						OneRClassifier.buildClassifier(datasetInstances);

						// use
						Evaluation eval = new Evaluation(testsetInstances);
						eval.evaluateModel(OneRClassifier, testsetInstances);

						System.out.println(eval.toSummaryString("\nResults", false));
						textArea.setText(
								eval.toSummaryString(
										"Test Set + One Rule Evaluation Results : \n" + "\nPrecision = "
												+ eval.precision(1) + "\nRecall = " + eval.recall(1) + "\nfMeasure = "
												+ eval.fMeasure(1) + "\nError Rate = " + eval.errorRate() + "\n\n",
										false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Percentage Split + J48
				else if (A1.isSelected() && B3.isSelected()) {

					try {

						// Get Percentage from input

						Split_Percentage = Integer.parseInt(input2.getText());

//						// Create J48 classifier by
//						// creating object of J48 class
//						J48 j48Classifier = new J48();

//						// Creating bufferedreader to read the dataset
//						BufferedReader bufferedReader = new BufferedReader(new FileReader(DataStes));
//
//
//						// Create dataset instances
//						Instances datasetInstances = new Instances(bufferedReader);

						String[] options = new String[4];
						options[0] = "-t";
						options[1] = DataStes;
						options[2] = "-split-percentage";
						options[3] = String.valueOf(Split_Percentage);

//						// Randomize data
//						 Randomize rand = new Randomize();
//						 rand.setInputFormat(datasetInstances);
//						 rand.setRandomSeed(42);
//						 datasetInstances = Filter.useFilter(datasetInstances, rand);
//						 
//						 // Remove train percentage from data to get the train set
//						 RemovePercentage rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 Instances test = Filter.useFilter(datasetInstances, rp);
//						 
//						 // Remove test percentage from data to get the test set
//						 rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 rp.setInvertSelection(true);
//						 Instances train = Filter.useFilter(datasetInstances, rp);

//
//						
//						    int trainSize = (int) Math.round(datasetInstances.numInstances() * Split_Percentage / 100);
//							int testSize = datasetInstances.numInstances() - trainSize;
//							Instances train = new Instances(datasetInstances, 0, trainSize);
//							Instances test = new Instances(datasetInstances, trainSize, testSize);

//						// Set Target Class
//						test.setClassIndex(test.numAttributes() - 1);
//						
//						train.setClassIndex(train.numAttributes() - 1);
//						
//				        //build model
//				     
//				        j48Classifier.buildClassifier(train);
//
//				        //use

						// System.out.println(Evaluation.evaluateModel(new J48(), options));
						textArea.setText("Percentage Split + J48 Evaluation Results : \n"
								+ Evaluation.evaluateModel(new J48(), options));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Percentage Split + RandomForest
				else if (A2.isSelected() && B3.isSelected()) {

					try {

						// Get Percentage from input

						Split_Percentage = Integer.parseInt(input2.getText());
						
						
						// Create RandomForest classifier by
						// creating object of RandomForest class
						RandomForest RandomForestClassifier = new RandomForest();
						RandomForestClassifier.setNumTrees(100);

//						// Creating bufferedreader to read the dataset
//						BufferedReader bufferedReader = new BufferedReader(new FileReader(DataStes));
//
//
//						// Create dataset instances
//						Instances datasetInstances = new Instances(bufferedReader);

						String[] options = new String[4];
						options[0] = "-t";
						options[1] = DataStes;
						options[2] = "-split-percentage";
						options[3] = String.valueOf(Split_Percentage);

//						// Randomize data
//						 Randomize rand = new Randomize();
//						 rand.setInputFormat(datasetInstances);
//						 rand.setRandomSeed(42);
//						 datasetInstances = Filter.useFilter(datasetInstances, rand);
//						 
//						 // Remove train percentage from data to get the train set
//						 RemovePercentage rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 Instances test = Filter.useFilter(datasetInstances, rp);
//						 
//						 // Remove test percentage from data to get the test set
//						 rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 rp.setInvertSelection(true);
//						 Instances train = Filter.useFilter(datasetInstances, rp);

//
//						
//						    int trainSize = (int) Math.round(datasetInstances.numInstances() * Split_Percentage / 100);
//							int testSize = datasetInstances.numInstances() - trainSize;
//							Instances train = new Instances(datasetInstances, 0, trainSize);
//							Instances test = new Instances(datasetInstances, trainSize, testSize);

//						// Set Target Class
//						test.setClassIndex(test.numAttributes() - 1);
//						
//						train.setClassIndex(train.numAttributes() - 1);
//
//
//						
//				        //build model
//				     
//						RandomForestClassifier.buildClassifier(train);
//
//				        //use
//				        Evaluation eval = new Evaluation(test);
//				        eval.evaluateModel(RandomForestClassifier,test);

//						System.out.println(eval.toSummaryString("\nResults", false));
//						textArea.setText(eval.toSummaryString("Test Set + RandomForest Evaluation Results : \n"+ "\nPrecision = "+eval.precision(1)+"\nRecall = "+eval.recall(1)+"\nfMeasure = "+eval.fMeasure(1)+"\nError Rate = "+eval.errorRate()+"\n\n", false));
						textArea.setText("Percentage Split + Random Forset Evaluation Results : \n"
								+ Evaluation.evaluateModel(RandomForestClassifier, options));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Percentage Split + NaiveBayes
				else if (A3.isSelected() && B3.isSelected()) {

					try {
						
						// Get Percentage from input

						Split_Percentage = Integer.parseInt(input2.getText());
						
						

//						// Create NaiveBayes classifier by
//						// creating object of NaiveBayes class
//						NaiveBayes NaiveBayesClassifier = new NaiveBayes();

//						// Creating bufferedreader to read the dataset
//						BufferedReader bufferedReader = new BufferedReader(new FileReader(DataStes));
//
//
//						// Create dataset instances
//						Instances datasetInstances = new Instances(bufferedReader);

						String[] options = new String[4];
						options[0] = "-t";
						options[1] = DataStes;
						options[2] = "-split-percentage";
						options[3] = String.valueOf(Split_Percentage);

//						// Randomize data
//						 Randomize rand = new Randomize();
//						 rand.setInputFormat(datasetInstances);
//						 rand.setRandomSeed(42);
//						 datasetInstances = Filter.useFilter(datasetInstances, rand);
//						 
//						 // Remove train percentage from data to get the train set
//						 RemovePercentage rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 Instances test = Filter.useFilter(datasetInstances, rp);
//						 
//						 // Remove test percentage from data to get the test set
//						 rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 rp.setInvertSelection(true);
//						 Instances train = Filter.useFilter(datasetInstances, rp);

//
//						
//						    int trainSize = (int) Math.round(datasetInstances.numInstances() * Split_Percentage / 100);
//							int testSize = datasetInstances.numInstances() - trainSize;
//							Instances train = new Instances(datasetInstances, 0, trainSize);
//							Instances test = new Instances(datasetInstances, trainSize, testSize);

//						// Set Target Class
//						test.setClassIndex(test.numAttributes() - 1);
//						
//						train.setClassIndex(train.numAttributes() - 1);
//
//
//						
//				        //build model
//				     
//						NaiveBayesClassifier.buildClassifier(datasetInstances);
//
//				        //use
//				        Evaluation eval = new Evaluation(test);
//				        eval.evaluateModel(NaiveBayesClassifier,test);

//						System.out.println(eval.toSummaryString("\nResults", false));
//						textArea.setText(eval.toSummaryString("Test Set + Naive Bayes Evaluation Results : \n"+ "\nPrecision = "+eval.precision(1)+"\nRecall = "+eval.recall(1)+"\nfMeasure = "+eval.fMeasure(1)+"\nError Rate = "+eval.errorRate()+"\n\n", false));
						textArea.setText("Percentage Split + Naive Bayes Evaluation Results : \n"
								+ Evaluation.evaluateModel(new NaiveBayes(), options));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}

				// Percentage Split + One Rule
				else if (A4.isSelected() && B3.isSelected()) {

					try {
						
						// Get Percentage from input

						Split_Percentage = Integer.parseInt(input2.getText());
						

//						// Create One Rule classifier by
//						// creating object of One Rule class
//						OneR OneRClassifier = new OneR();

//						// Creating bufferedreader to read the dataset
//						BufferedReader bufferedReader = new BufferedReader(new FileReader(DataStes));
//
//
//						// Create dataset instances
//						Instances datasetInstances = new Instances(bufferedReader);

						String[] options = new String[4];
						options[0] = "-t";
						options[1] = DataStes;
						options[2] = "-split-percentage";
						options[3] = String.valueOf(Split_Percentage);

//						// Randomize data
//						 Randomize rand = new Randomize();
//						 rand.setInputFormat(datasetInstances);
//						 rand.setRandomSeed(42);
//						 datasetInstances = Filter.useFilter(datasetInstances, rand);
//						 
//						 // Remove train percentage from data to get the train set
//						 RemovePercentage rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 Instances test = Filter.useFilter(datasetInstances, rp);
//						 
//						 // Remove test percentage from data to get the test set
//						 rp = new RemovePercentage();
//						 rp.setInputFormat(datasetInstances);
//						 rp.setPercentage(Split_Percentage);
//						 rp.setInvertSelection(true);
//						 Instances train = Filter.useFilter(datasetInstances, rp);

//
//						
//						    int trainSize = (int) Math.round(datasetInstances.numInstances() * Split_Percentage / 100);
//							int testSize = datasetInstances.numInstances() - trainSize;
//							Instances train = new Instances(datasetInstances, 0, trainSize);
//							Instances test = new Instances(datasetInstances, trainSize, testSize);

//						// Set Target Class
//						test.setClassIndex(test.numAttributes() - 1);
//						
//						train.setClassIndex(train.numAttributes() - 1);
//
//
//						
//				        //build model
//				     
//						OneRClassifier.buildClassifier(train);
//
//				        //use
//				        Evaluation eval = new Evaluation(test);				        
//				        eval.evaluateModel(OneRClassifier,test);

//						System.out.println(eval.toSummaryString("\nResults", false));
//						textArea.setText(eval.toSummaryString("Test Set + One Rule Evaluation Results : \n"+ "\nPrecision = "+eval.precision(1)+"\nRecall = "+eval.recall(1)+"\nfMeasure = "+eval.fMeasure(1)+"\nError Rate = "+eval.errorRate()+"\n\n", false));
						textArea.setText("Percentage Split + One Rule Evaluation Results : \n"
								+ Evaluation.evaluateModel(new OneR(), options));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}
				
				
				
				
				// Cross validation + Bagging
				else if (A5.isSelected() && B1.isSelected()) {

					try {
						
						
						
						// Set Target Class
						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// getting CrossValidation_Value input

						CrossValidation_Value = Integer.parseInt(input1.getText());

						// Create Bagger classifier by
						// creating object of Bagger class
						Bagging bagger = new Bagging();
						bagger.setClassifier(new NaiveBayes());
						bagger.setNumIterations(Integer.parseInt(input3.getText()));
						bagger.buildClassifier(datasetInstances);	



						// Evaluating by creating object of Evaluation
						// class
						Evaluation evaluation = new Evaluation(datasetInstances);

						// Cross Validate Model with user selected folds
						evaluation.crossValidateModel(bagger, datasetInstances, CrossValidation_Value,
								new Random(1));

						System.out.println(evaluation.toSummaryString("\nResults", false));
						textArea.setText(
								evaluation.toSummaryString("Cross validation + Bagging Evaluation Results : \n"
										+ "\nPrecision = " + evaluation.precision(1) + "\nRecall = "
										+ evaluation.recall(1) + "\nfMeasure = " + evaluation.fMeasure(1)
										+ "\nError Rate = " + evaluation.errorRate() + "\n\n", false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}
				
				

				
				
				
				// Cross validation + Boosting
				else if (A6.isSelected() && B1.isSelected()) {

					try {
						
						
						// Set Target Class
						datasetInstances.setClassIndex(datasetInstances.numAttributes() - 1);

						// getting CrossValidation_Value input

						CrossValidation_Value = Integer.parseInt(input1.getText());

						/* Boosting a weak classifier using the Adaboost M1 method
						 * for boosting a nominal class classifier
						 * Tackles only nominal class problems
						 * Improves performance
						 * Sometimes overfits.
						 */
						//AdaBoost .. 
						
						AdaBoostM1 booster = new AdaBoostM1();
						booster.setClassifier(new NaiveBayes());
						booster.setNumIterations(Integer.parseInt(input4.getText()));
						booster.buildClassifier(datasetInstances);


						// Evaluating by creating object of Evaluation
						// class
						Evaluation evaluation = new Evaluation(datasetInstances);

						// Cross Validate Model with user selected folds
						evaluation.crossValidateModel(booster, datasetInstances, CrossValidation_Value,
								new Random(1));

						System.out.println(evaluation.toSummaryString("\nResults", false));
						textArea.setText(
								evaluation.toSummaryString("Cross validation + Booster Evaluation Results : \n"
										+ "\nPrecision = " + evaluation.precision(1) + "\nRecall = "
										+ evaluation.recall(1) + "\nfMeasure = " + evaluation.fMeasure(1)
										+ "\nError Rate = " + evaluation.errorRate() + "\n\n", false));
					}

					// Catch block to handle the exceptions
					catch (Exception e) {

						// Print message on the console
						System.out.println("Error Occurred!!!! \n" + e.getMessage());
						textArea.setText("Error Occurred!!!! \n" + e.getMessage());
					}

				}
				
				
				
				
				
				
				
			}
		});
		btnNewButton_2.setBounds(731, 262, 117, 25);
		frmTpDataLining.getContentPane().add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("Done by Hadjazi Mohammed Hisham G01 RSSI");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(494, 541, 354, 15);
		frmTpDataLining.getContentPane().add(lblNewLabel);

		JLabel lblFolds = new JLabel("Folds");
		lblFolds.setHorizontalAlignment(SwingConstants.CENTER);
		lblFolds.setBounds(797, 51, 52, 15);
		frmTpDataLining.getContentPane().add(lblFolds);

		JLabel lblFolds_1 = new JLabel("%");
		lblFolds_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblFolds_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFolds_1.setBounds(796, 116, 52, 15);
		frmTpDataLining.getContentPane().add(lblFolds_1);
		
		JLabel lblDataPreprocessingOptions = new JLabel("Data Preprocessing Options : ");
		lblDataPreprocessingOptions.setBounds(29, 205, 216, 15);
		frmTpDataLining.getContentPane().add(lblDataPreprocessingOptions);
		
		JLabel lblOptionsForTexttoword = new JLabel("Options for TextToWordVector filter : ");
		lblOptionsForTexttoword.setBounds(530, 168, 278, 15);
		frmTpDataLining.getContentPane().add(lblOptionsForTexttoword);
		

		


	}
}
