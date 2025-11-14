package main;

public class Main {

    public static void main(String[] args) {
    
    	Centralina c = new Centralina();
    	
    	SensoreLuce sL = new SensoreLuce(c);
    	SensoreTemperatura sT = new SensoreTemperatura(c);
    	
    	sL.start();
    	sT.start();
    }
}