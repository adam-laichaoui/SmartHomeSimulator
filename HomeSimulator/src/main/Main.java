package main;


public class Main {

    public static void main(String[] args) {
    /*
    	Centralina c = new Centralina();
    	
    	SensoreLuce sL = new SensoreLuce(c);
    	SensoreTemperatura sT = new SensoreTemperatura(c);
    	
    	sL.start();
    	sT.start();*/
		// quando crei MyFrame
	MyFrame frame = new MyFrame(); // frame passa se stesso alla centralina
	Centralina centralina = new Centralina(frame);
	//centralina.setObserver(frame); // collega l'osservatore reale



    }
}