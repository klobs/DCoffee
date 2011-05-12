package watchdog;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import de.tu.dresden.inf.dud.libmulticastdc.DCMulticastChannel;

public class WatchdogThread implements Runnable {

	private DCMulticastChannel 	assocChannel;
	private JButton				assocCoffeeButton;
//	private long 				lastCoffeeRequest = 0;
//	private int					coffeeLevel = 0;
	
	public WatchdogThread(DCMulticastChannel dcmc, JButton coffeebutton) {
	 assocChannel = dcmc;
	 assocCoffeeButton = coffeebutton;
	}
	
	public void run() {
		while (true){
			assocChannel.read();
			setIconAcordingToCoffeeDemandLevel();
		}
	}
	
	private void setIconAcordingToCoffeeDemandLevel(){
		assocCoffeeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gui/kaffee_20.png")));
	}

}
