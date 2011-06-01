package watchdog;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import de.tu.dresden.dud.dc.Connection;

public class WatchdogConnectionThread implements Runnable {

	private AbstractAction 	assocAction;
	private JButton			assocButton;
	private Connection 		assocConnection;
	
	public WatchdogConnectionThread(Connection connection, JButton button, AbstractAction reconnectAction) {
		assocAction = reconnectAction;
		assocButton = button;
		assocConnection = connection;
	}
	
	public void run() {
		while (true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (assocConnection == null
				|| assocConnection.isStopped()){
				assocButton.setAction(assocAction);
				break;
			}
		}
	}

}
