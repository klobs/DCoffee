package watchdog;

import de.tu.dresden.inf.dud.libmulticastdc.DCMulticastChannel;

public class WatchdogChannelThread implements Runnable {

	private DCMulticastChannel 	assocChannel;
	private WatchdogCoffeeLevelThread assocCoffeeLevelThread;
	
	public WatchdogChannelThread(DCMulticastChannel dcmc, WatchdogCoffeeLevelThread coffeeLevelEvaluator) {
	 assocChannel = dcmc;
	 assocCoffeeLevelThread = coffeeLevelEvaluator; 
	 
	}
	
	public void run() {
		while (true){
			assocCoffeeLevelThread.put(new String(assocChannel.read()));
		}
	}

}
