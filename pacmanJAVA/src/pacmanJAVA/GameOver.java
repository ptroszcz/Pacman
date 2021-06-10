package pacmanJAVA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Klasa odpowiedzialna za wy�wietlane okno po przegranej gracza.
 * Pobiera wpisan� nazw� gracza i dodaje j� do listy najlepszych wynik�w
 *
 */
@SuppressWarnings("serial")
public class GameOver extends JFrame{
	
	
	/**
	 * Pole odpowiadaj�ce oknu wy�wietlanemu po przegranej grze.
	 */
	private JPanel window = new JPanel();
	/**
	 * Obiekt odpowiedzialna za pole, w kt�rym gracz wpisuje nazw� jaka ma zosta� dodana do listy wynik�w.
	 */
	private JTextField Name;
	/**
	 * Obiekt stanowi�cy przycisk zapisu podanej nazwy gracza do listy wynik�w
	 */
	private JButton button;
	
	/**
	 * Pole informuj�ce czy okno jest otwarte. 
	 */
	public boolean isOpen = true;
	
	/**
	 * Kontruktor klasy GameOver odpowiedzialne za okno wy�wietlaj�ce si� graczowi po przegranej.
	 * @param score - wynik z jakim gracz zako�czy� gr�
	 * @param ranking - obiekt klasy Ranking przechowuj�cy informacj� o aktualnej tabeli wynik�w
	 */
	public GameOver(int score, Ranking ranking) {
		super("Game");
		
		setTitle("GAME OVER");
		setSize(100,150);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        JLabel Score = new JLabel("Your score: "+String.valueOf(score));
        Score.setBounds(150,20, 200, 50);
        window.add(Score);
        
        JLabel Player = new JLabel("Player name");
        Player.setBounds(100, 50, 50, 50);
        window.add(Player);
        
        Name = new JTextField("Pacman Master");
        Name.setBounds(150, 50, 100, 50);
        window.add(Name);
        
        button = new JButton();
        button.setText("Save Score");
        button.setBounds(250,50, 50, 50);
        button.addActionListener(new ActionListener(){
        	/**
        	 * Metoda odpowiedzialna za obs�ug� wydarzenia jakim jest naci�ni�cie przycisku.
        	 * Dodaje wpisan� nazw� wraz z punktacj� do listy najlepszych wynik�w.
        	 * @param e - wydarzenie naci�niecia przysiku
        	 */
        	public void actionPerformed(ActionEvent e) {
        		ranking.AddScore(Name.getText(),score);
        		ranking.saveRankingToFile();
        		isOpen=false;
        		dispose();
        	}
        });
        window.add(button);
        
        
        this.getContentPane().add(window);
        this.setResizable(false);
        setVisible(true);
	}
}
