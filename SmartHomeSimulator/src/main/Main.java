package main;

public class Main {

	public static void main(String[] args) {
		
		Sensore s1 = new SensoreTemperatura();
		SensoreLuce s2 = new SensoreLuce();
		Sensore s3 = new SensoreTemperatura();
	
		Thread t1 = new Thread(s1);
		Thread t2 = new Thread(s2);
		Thread t3 = new Thread(s3);
		t3.start();
		t1.start();
		t2.start();
	}

}
