package main;


import javax.swing.*;

import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Map;

public class MyFrame extends JFrame implements Observer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	// private boolean freeze = false; // true = blocca generazione automatica
	// private JTextArea display;
    
    public MyFrame() {
        setTitle(Costanti.TITLE);
        setSize(Costanti.X_DIM, Costanti.Y_DIM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon logo = new ImageIcon (".settings/FOTO/logo.jpg");
    	setIconImage(logo.getImage());//cambia l'icona del frame
    	getContentPane().setBackground(new Color(Costanti.WHITE_HEX));

        


       /* MyMenu menuBar = new MyMenu(centralina);
        setJMenuBar(menuBar);*/

        setVisible(true);
    }

	
	@Override
	public void update(String id, Map<String, DatoSensore> datiAggiornati) {
		
		DatoSensore dato = datiAggiornati.get(id);
		
		System.out.println(generaStato(id, dato));
	}
	
	public String generaStato(String id, DatoSensore dato) {
		
	    return String.format("[%s] %s: %s = %.2f", dato.getOraFormattata(), id, dato.getTipo(), dato.getValore());
	}
}
