package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.WindowConstants;
import javax.swing.JFrame;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.tu.dresden.dud.dc.Connection;
import de.tu.dresden.dud.dc.Participant;
import de.tu.dresden.inf.dud.libmulticastdc.DCMulticastChannel;
import de.tu.dresden.inf.dud.libmulticastdc.DCMulticastParticipant;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainWindow extends javax.swing.JPanel {

	private static final long 	serialVersionUID = -2278436951424873713L;
	private AbstractAction 		actionDemandCoffee;
	private JButton 			buttonCoffeeIndicator;
	DCMulticastChannel			channel = null;
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new MainWindow());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public MainWindow() {
		super();
		initGUI();
	}
	
	private void doDCSetup(){
		
		BasicConfigurator.configure();
		
	    Logger.getRootLogger().setLevel(Level.DEBUG);
		
		
		Participant part = new Participant("DCoffee");
		Connection c = part.doAllTheThingsToBecomeActive("dud73", Connection.DEFAULTPORT);
		
		DCMulticastParticipant dcmc = new DCMulticastParticipant();
		dcmc.setConnection(c);
		dcmc.setParticipant(part);
		
		channel = dcmc.listenToMulicastChannel(0xdeadbeef);
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				buttonCoffeeIndicator = new JButton();
				buttonCoffeeIndicator.setText("Coffee!");
				buttonCoffeeIndicator.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gui/kaffee_0.png")));
				buttonCoffeeIndicator.setAction(getActionDemandCoffee());
			}
				thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(buttonCoffeeIndicator, 0, 277, Short.MAX_VALUE)
					.addContainerGap());
				thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(buttonCoffeeIndicator, 0, 377, Short.MAX_VALUE)
					.addContainerGap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doDCSetup();
	}
	
	public AbstractAction getActionDemandCoffee() {
		if(actionDemandCoffee == null) {
			actionDemandCoffee = new AbstractAction("Coffee!", new ImageIcon(getClass().getClassLoader().getResource("gui/kaffee_0.png"))) {
				private static final long serialVersionUID = -3360043336423152790L;

				public void actionPerformed(ActionEvent evt) {
//				buttonCoffeeIndicator.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gui/kaffee_20.png")));
				long time = System.currentTimeMillis();
				channel.write("Hallo".getBytes());
					
				}
			};
		}
		return actionDemandCoffee;
	}

}
