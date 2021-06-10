package pacmanJAVA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Klasa odpowiedzialna za obs³uge okna wyœwietlaj¹cego siê po wpisaniu przez gracza swojej nazwy na listê wyników
 *
 */
@SuppressWarnings("serial")
public class RestartWindow extends JFrame{
	
	/**
	 * Pole reprezentuj¹ce wyœwietlane okno oczekiwania gracza na decyzjê zakoñczenia lub nowej gry.
	 */
	private JPanel window = new JPanel();
	/**
	 * Pole odpowiedzialne za przycisk nowej gry.
	 */
	private JButton restart;
	/**
	 * Pole odpowiedzialne za przycisk koñca gry.
	 */
	private JButton close;
	
	/**
	 * Pole sprawdzaj¹ce czy dane okno jest otwarte.
	 */
	public boolean isOpen = true;
	/**
	 * Pole okreœlaj¹ce czy gra ma zostaæ uruchomiona od nowa.
	 */
	public boolean isNewGame;
	
	/**
	 * Kontruktor bezparametryczny klasy RestartWindow odpowiedzialne za inicjacjê obs³ugi przycisków oraz graficzn¹ czêœc okna.
	 */
	public RestartWindow() {
		super("Game");
		
		setTitle("New Game");
		setSize(250,150);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        JLabel Score = new JLabel("Do you want to start New Game?");
        Score.setBounds(150,20, 200, 50);
        window.add(Score);
        
        
        restart = new JButton();
        restart.setText("New Game");
        restart.setBounds(250,50, 50, 50);
        restart.addActionListener(new ActionListener(){
        	/**
        	 * Metoda odpowiedzialna za obs³ugê wydarzenia jakim jest naciœniêcie przycisku "New Game".
        	 * Inicjuje proces ca³kowitego restartu ca³ej gry
        	 * @param e - wydarzenie naciœniecia przysiku
        	 */
        	public void actionPerformed(ActionEvent e) {
        		isOpen=false;
        		isNewGame = true;
        		dispose();
        	}
        });
        window.add(restart);
        
        close = new JButton();
        close.setText("Exit Game");
        close.setBounds(250,50, 50, 50);
        close.addActionListener(new ActionListener(){
        	/**
        	 * Metoda odpowiedzialna za obs³ugê wydarzenia jakim jest naciœniêcie przycisku "Exit Game".
        	 * Inicjuje proces zakoñczenia gry
        	 * @param e - wydarzenie naciœniecia przysiku
        	 */
        	public void actionPerformed(ActionEvent e) {
        		isOpen=false;
        		isNewGame = false;
        		dispose();
        	}
        });
        window.add(close);
        
        this.getContentPane().add(window);
        this.setResizable(false);
        setVisible(true);
	}

}
