package audioPac;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import audioPac.WaveFile;

public class AudioGui {
	private static String freqNum = "";
	private static String freqText = "none";
	private static Object currentSound; 
    private static Clip clip;
	private static AudioFormat format;
	private static JLabel label;
	private static JLabel freLabel;
	private static WaveFile wavF = null;
	public static void main(String[] args) {
		JFrame gui = new JFrame();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(600,200);
		gui.setVisible(true);
		gui.setTitle("Audio Wave Frequency");
	
 
		JPanel panel = new JPanel();						
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		label = new JLabel("Audio Wave Frequency");
		panel.add(label);
		c.gridx = 1;
		c.gridy =4;
		c.insets = new Insets(5,5,5,5);
		panel.add(label, c);
		
		
		JTextField textfield = new JTextField(15);
		panel.add(textfield);
		c.gridx = 2;
		c.gridy =4;
		c.gridwidth = 3;
		c.insets = new Insets(5,5,5,5);
		panel.add(textfield, c);
 
       gui.setLocationRelativeTo(null);
       gui.pack();
       gui.setVisible(true);
	JButton FcButton = new JButton("Choose a file");
	panel.add(FcButton);
	c.gridx = 1;
	c.gridy =1;
	
	c.insets = new Insets(5,5,5,5);
	panel.add(FcButton, c);
	
	freLabel = new JLabel("your frequency is:");
	panel.add(freLabel);
	c.gridx = 1;
	c.gridy =6;
	
	c.insets = new Insets(5,5,5,5);
	panel.add(freLabel, c);
	
	JButton displayAmp = new JButton ("Display Amplitude");
	panel.add(displayAmp);
	c.gridx = 1;
	c.gridy =3;
	
	c.insets = new Insets(5,5,5,5);
	panel.add(displayAmp, c);
	displayAmp.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			 {
		    	  JFreeChart chart = ChartFactory.createScatterPlot(	//Calls thing to create chart
				          "Amplitude graph", // chart title
				          "Bytes", // x axis label
				          "Frequency Domain", // y axis label
				          createDatasetAmp(), // data
				          PlotOrientation.VERTICAL,
				          false, // include legend
				          true, // tooltips
				          false // urls
			          );

				      ChartFrame frame = new ChartFrame("Frequency Chart", chart);	// Displays chart
				      frame.pack();
				      frame.setVisible(true);
		    	  }
		}});
	JButton displayFre = new JButton ("Display frequency");
	panel.add(displayFre);
	c.gridx = 3;
	c.gridy =3;
	
	c.insets = new Insets(5,5,5,5);
	panel.add(displayFre, c);
	displayFre.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			 {
		    	  JFreeChart chart = ChartFactory.createScatterPlot(	//Calls thing to create chart
				          "Frequency graph", // chart title
				          "Bytes", // x axis label
				          "Frequency Domain", // y axis label
				          createDatasetAmp(), // data
				          PlotOrientation.VERTICAL,
				          false, // include legend
				          true, // tooltips
				          false // urls
			          );

				      ChartFrame frame = new ChartFrame("Frequency Chart", chart);	// Displays chart
				      frame.pack();
				      frame.setVisible(true);
		    	  }
		}});
	
		FcButton.addActionListener(new ActionListener(){
			
			public void actionPerformed (ActionEvent e) {
				File workingDir = new File(System.getProperty("user.dir"));
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Select an audio file");
				chooser.setCurrentDirectory(workingDir);
				FileNameExtensionFilter filt = new FileNameExtensionFilter ("wave" , "wav");
				chooser.setFileFilter(filt);
				int status = chooser.showOpenDialog(null);
				if(status == JFileChooser.APPROVE_OPTION) {
					System.out.println("File Opened: " + 
				chooser.getSelectedFile().getName());
				}
				File sound = chooser.getSelectedFile();
				
					if(status !=JFileChooser.APPROVE_OPTION) {
						String Error = "!File not accepted!";
						JOptionPane.showMessageDialog(null, Error);;
					}
					else {
						try {
							currentSound = AudioSystem.getAudioInputStream(sound);
						} catch (UnsupportedAudioFileException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						File file = chooser.getSelectedFile();
						
						displayAmp.setEnabled(true);
						displayFre.setEnabled(true);
						try {
							Scanner scan = new Scanner(file);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						StringBuilder info = new StringBuilder();
					}
					try {
						wavF = new WaveFile(sound);
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
			}
				
				
			
		});
		
		JButton FreButton = new JButton("play frequency");
		panel.add(FreButton);
		c.gridx = 3;
		c.gridy =1;
		
		c.insets = new Insets(5,5,5,5);
		panel.add(FreButton, c);
		FreButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				 if (currentSound != null)							//Play file frequency
		    	  {
			    	  try											//Try to play file
				      	  {
				    	  AudioInputStream stream = (AudioInputStream) currentSound;
				    	  format = stream.getFormat();
				    	  DataLine.Info info = new DataLine.Info(Clip.class,
				    	  stream.getFormat(),
				    	  ((int) stream.getFrameLength() * format.getFrameSize()));
				    	  clip = (Clip) AudioSystem.getLine(info);
				    	  clip.open(stream);
				    	  currentSound = clip;
				    	  clip.start();
				    	  }
				      catch (Exception ex)
			    	  {
				    	  ex.printStackTrace();
				    	  currentSound = null;
				    	  JOptionPane.showMessageDialog(gui, "Unable to play file.");
			    	  }
		    	  }
		    	  else												//Play text entered frequency (don't touch it)
		    	  {
		    		float floatFreq = new Float(freqNum).floatValue();
		    		byte[] buf = new byte[ 1 ];;
		    		AudioFormat af = new AudioFormat( floatFreq, 8, 1, true, false );
		    		SourceDataLine sdl = null;
					try {
						sdl = AudioSystem.getSourceDataLine( af );
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
		    		  try {
						sdl.open();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
		    		  sdl.start();
		    		  for( int i = 0; i < 1000 * floatFreq / 1000; i++ ) {
		    		      double angle = i / ( floatFreq / 440 ) * 2.0 * Math.PI;
		    		      buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
		    		      sdl.write( buf, 0, 1 );
		    		  }
		    		  sdl.drain();
		    		  sdl.stop();
		    	  }
			}
		});
		JButton entButton = new JButton("Enter Frequency");
		panel.add(entButton);
		c.gridx = 5;
		c.gridy =4;
		
		c.insets = new Insets(5,5,5,5);
		panel.add(entButton, c);
		entButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		
			      
			    	  freqNum = textfield.getText();				//Reads entered frequency
			    	  boolean allowed = false;						//Checks if valid input
			    	  try
			    	  {
			    		  int foo = Integer.parseInt(freqNum);
			    		  allowed = true;
			    	  }
			    	  catch (NumberFormatException r)
			    	  {
			    		  displayAmp.setEnabled(false);
			    		  displayFre.setEnabled(false);
			    		  JOptionPane.showMessageDialog(gui, "Please enter a numeric value.");
			    	  }
			    	  if (allowed)
			    	  {
			    		  displayAmp.setEnabled(true);										//Enables buttons
			    		  displayFre.setEnabled(true);
			    		  freLabel.setText("Your frequency is: " + freqNum);		//Sets label
			    	  }
			      }
			    });
			
			
			
		
		
		displayAmp.setEnabled(false);
		displayFre.setEnabled(false);
		gui.getContentPane().add(panel);
		gui.setVisible(true);
		
}
	
	private static XYDataset createDatasetAmp() {				//Puts Amplitude values into array
	    XYSeriesCollection result = new XYSeriesCollection();
	    XYSeries series = new XYSeries("Amplitude");
	    for (int i = 0; i < wavF.getFramesCount(); i++) {
	  	    int amplitude = wavF.getSampleInt(i);
	  	    System.out.println(amplitude);
	  	    double x = i;
	        double y = amplitude;
	        series.add(x, y);
	  	    }
	    
	    result.addSeries(series);
	    return result;
	}
	

}
