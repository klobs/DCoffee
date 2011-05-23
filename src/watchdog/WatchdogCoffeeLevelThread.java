package watchdog;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class WatchdogCoffeeLevelThread implements Runnable {

	
	private JButton						assocCoffeeButton;
	private LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
	private String[]					coffeeLevel = new String[7];
	private int							currentCoffeeLevel = 0;

	
	public WatchdogCoffeeLevelThread(JButton coffeebutton) {
		assocCoffeeButton = coffeebutton;
		
		coffeeLevel[0] = "gui/kaffee_0.png";
		coffeeLevel[1] = "gui/kaffee_20.png";
		coffeeLevel[2] = "gui/kaffee_35.png";
		coffeeLevel[3] = "gui/kaffee_50.png";
		coffeeLevel[4] = "gui/kaffee_90.png";
		coffeeLevel[5] = "gui/kaffee_100.png";
		coffeeLevel[6] = "gui/kaffee_110.png";
		
	}
	
	public void decreaseCoffeeLevel(){
		if (currentCoffeeLevel > 6)
			currentCoffeeLevel = 6;
		else if (currentCoffeeLevel > 0)
			currentCoffeeLevel--;
		else 
			currentCoffeeLevel = 0;
		assocCoffeeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource(coffeeLevel[currentCoffeeLevel])));
	}
	
	public void increaseCoffeeLevel(){
		if (currentCoffeeLevel < 0)
			currentCoffeeLevel = 0;
		else if (currentCoffeeLevel < 6)
			currentCoffeeLevel++;
		else 
			currentCoffeeLevel = 6;
		assocCoffeeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource(coffeeLevel[currentCoffeeLevel])));
	}
	
	public void put(String m){
		try {
			messageQueue.put(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true){
			try {
				setIconAcordingToCoffeeDemandLevel(messageQueue.poll(30, TimeUnit.SECONDS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setIconAcordingToCoffeeDemandLevel(String m){
		// Interrupt hat zugeschlagen --> es gab 5 Minuten niemanden, der kaffe wollte hat.
		if(m == null){
			decreaseCoffeeLevel();
		} 
		// jemand hat eine kaffeepausennachricht gesendet
		else if(m.startsWith("kaffepause-")){
			increaseCoffeeLevel();
		}
	}
}
