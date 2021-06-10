package pacmanJAVA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Klasa odpowiedzialna za obs�uge okna wy�wietlaj�cego si� po wpisaniu przez gracza swojej nazwy na list� wynik�w
 *
 */
@SuppressWarnings("serial")
public class RestartWindow extends JFrame{
	
	/**
	 * Pole reprezentuj�ce wy�wietlane okno oczekiwania gracza na decyzj� zako�czenia lub nowej gry.
	 */
	private JPanel window = new JPanel();
	/**
	 * Pole odpowiedzialne za przycisk nowej gry.
	 */
	private JButton restart;
	/**
	 * Pole odpowiedzialne za przycisk ko�ca gry.
	 */
	private JButton close;
	
	/**
	 * Pole sprawdzaj�ce czy dane okno jest otwarte.
	 */
	public boolean isOpen = true;
	/**
	 * Pole okre�laj�ce czy gra ma zosta� uruchomiona od nowa.
	 */
	public boolean isNewGame;
	
	/**
	 * Kontruktor bezparametryczny klasy RestartWindow odpowiedzialne za inicjacj� obs�ugi przycisk�w oraz graficzn� cz�c okna.
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
        	 * Metoda odpowiedzialna za obs�ug� wydarzenia jakim jest naci�ni�cie przycisku "New Game".
        	 * Inicjuje proces ca�kowitego restartu ca�ej gry
        	 * @param e - wydarzenie naci�niecia przysiku
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
        	 * Metoda odpowiedzialna za obs�ug� wydarzenia jakim jest naci�ni�cie przycisku "Exit Game".
        	 * Inicjuje proces zako�czenia gry
        	 * @param e - wydarzenie naci�niecia przysiku
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
