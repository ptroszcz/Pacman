package pacmanJAVA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Klasa odpowiedzialna za wyœwietlane okno po przegranej gracza.
 * Pobiera wpisan¹ nazwê gracza i dodaje j¹ do listy najlepszych wyników
 *
 */
@SuppressWarnings("serial")
public class GameOver extends JFrame{
	
	
	/**
	 * Pole odpowiadaj¹ce oknu wyœwietlanemu po przegranej grze.
	 */
	private JPanel window = new JPanel();
	/**
	 * Obiekt odpowiedzialna za pole, w którym gracz wpisuje nazwê jaka ma zostaæ dodana do listy wyników.
	 */
	private JTextField Name;
	/**
	 * Obiekt stanowi¹cy przycisk zapisu podanej nazwy gracza do listy wyników
	 */
	private JButton button;
	
	/**
	 * Pole informuj¹ce czy okno jest otwarte. 
	 */
	public boolean isOpen = true;
	
	/**
	 * Kontruktor klasy GameOver odpowiedzialne za okno wyœwietlaj¹ce siê graczowi po przegranej.
	 * @param score - wynik z jakim gracz zakoñczy³ grê
	 * @param ranking - obiekt klasy Ranking przechowuj¹cy informacjê o aktualnej tabeli wyników
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
        	 * Metoda odpowiedzialna za obs³ugê wydarzenia jakim jest naciœniêcie przycisku.
        	 * Dodaje wpisan¹ nazwê wraz z punktacj¹ do listy najlepszych wyników.
        	 * @param e - wydarzenie naciœniecia przysiku
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
