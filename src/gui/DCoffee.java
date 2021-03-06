package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

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

import watchdog.WatchdogChannelThread;
import watchdog.WatchdogCoffeeLevelThread;
import watchdog.WatchdogConnectionThread;

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
public class DCoffee extends javax.swing.JPanel implements Observer {

	private static final long 	serialVersionUID = -2278436951424873713L;
	private AbstractAction 		actionDemandCoffee;
	private AbstractAction		actionReconnect;
	private JButton 			buttonCoffeeIndicator;
	DCMulticastParticipant		participant = null;
	DCMulticastChannel			channel = null;
	private long				random;
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new DCoffee());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public DCoffee() {
		super();
		
		random = System.currentTimeMillis(); 
		
		initGUI();
	}
	
	private void doDCSetup(){
		
		BasicConfigurator.configure();
		
	    Logger.getRootLogger().setLevel(Level.INFO);
		
		
		Participant part = new Participant("DCoffee");
		part.addObserver(this);
		Connection c = part.establishNewConnection("dud73", Connection.DEFAULTPORT);
		
		DCMulticastParticipant dcmc = new DCMulticastParticipant();
		dcmc.setConnection(c);
		dcmc.setParticipant(part);
		
		Thread t = new Thread(new WatchdogConnectionThread(c, buttonCoffeeIndicator, getActionReconnect()), "WatchdogConnection");
		t.start();
		
		participant = dcmc;
		channel = dcmc.listenToMulicastChannel(0xdcaffee);
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
		
		WatchdogCoffeeLevelThread wdclt = new WatchdogCoffeeLevelThread(getActionDemandCoffee());
		
		(new Thread(wdclt,"CoffeeLevelWatchdogThread")).start();
		(new Thread(new WatchdogChannelThread(channel, wdclt),"CoffeeWatchdogThread")).start();
	}
	
	public AbstractAction getActionDemandCoffee() {
		if(actionDemandCoffee == null) {
			actionDemandCoffee = new AbstractAction("Coffee!", new ImageIcon(getClass().getClassLoader().getResource("gui/kaffee_0.png"))) {
				private static final long serialVersionUID = -3360043336423152790L;

				public void actionPerformed(ActionEvent evt) {
				
				channel.write(("kaffepause-" + String.valueOf(random)).getBytes());
					
				}
			};
		}
		return actionDemandCoffee;
	}

	public AbstractAction getActionReconnect() {
		if (actionReconnect == null) {
			actionReconnect = new AbstractAction("Reconnect") {
				private static final long serialVersionUID = -3460043336423152790L;

				public void actionPerformed(ActionEvent evt) {

					Connection c = participant.getAssocParticipant()
							.establishNewConnection("dud73",
									Connection.DEFAULTPORT);
					participant.setConnection(c);

					buttonCoffeeIndicator.setAction(getActionDemandCoffee());
				}
			};
		}
		return actionReconnect;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
//		if (arg0 instanceof Participant){
//			// Connection crashed
//			if (arg1 instanceof IOException){
//				buttonCoffeeIndicator.setAction(getActionReconnect());
//			}
//		}
	}
}
