package pacmanJAVA;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * G��wna klasa odpowiedzialna za grafik� gry.
 *
 */
@SuppressWarnings("serial")
public class GameGraphic extends JFrame implements KeyListener{
	/**
	 * Rysowana gra.
	 */
	private Game game;
	/**
	 * Timer odpowiedzialny za cz�stotliwo�� rysowania grafiki.
	 */
	private Timer timer;
	/**
	 * Pole odpowiedzialne za rysowanie planszy gry oraz wszystkich element�w grafiki
	 */
	private BoardDraw boarddraw;
	
	/**
	 * Op�znienie w�tku odpowiedzialnego za rysowanie.
	 */
	private final int Delay=20;
	/**
	 * Interwa� w�tku odpowiedzialny za rysowanie.
	 */
	private final int Interval=20;
	/**
	 * Pole graficzne wy�wietlaj�ce aktualne punkty gracza.
	 */
	private JLabel points;
	/**
	 * Pole graficzne wy�wietlaj�ce pozosta�e �ycia gracza.
	 */
	private JLabel lives;
	/**
	 * Pole klasy Ranking odpowiedzialne za przechowywanie rankingu rozgrywki.
	 */
	private Ranking ranks = new Ranking();
	/**
	 * Pole graficzne wy�wietlaj�ce 10 najlepszych wynik�w w grze.
	 */
	private JTable ranking;
	/**
	 * Pole graficzne wy�wietlaj�ce sterowanie gry.
	 */
	private JLabel controls;
	
	/**
	 * Konstruktor klasy GameGraphic odpowiedzialny za otwarcie okna i stworzenie podstawowych element�w grafiki dla otrzymanej gry.
	 * @param game - obiekty typu Game dla, kt�rej ma by� rysowana grafika
	 */
	public GameGraphic(Game game) {
		super("Game");
		this.setResizable(false);
		setTitle("Pacman");
		setSize(800,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addKeyListener(this);
		
		this.game=game;
		
		
		boarddraw = new BoardDraw(game.board);
		this.getContentPane().setLocation(50, 50);
		this.getContentPane().add(boarddraw);
		boarddraw.setLayout(null);
		
		points = new JLabel();
		points.setBounds(600, 20, 100, 20);
		boarddraw.add(points);
		
		lives = new JLabel();
		lives.setBounds(600, 20, 100, 60);
		boarddraw.add(lives);
		
		controls = new JLabel();
		controls.setBounds(600, 450, 200, 300);
		controls.setText("<html>Controls: <br/>W - Move Up<br/> S - Move Down<br/> A - Move Left<br/> D - Move Right<br/>Esc - Exit </html>");
		boarddraw.add(controls);
		
		ranking = ranks.RankingList();
		ranking.setBounds(580,200,200,176);
		boarddraw.add(ranking);
		
		JLabel leaderboard = new JLabel();
		leaderboard.setBounds(635,160,100,60);
		leaderboard.setText("Leaderboard");
		boarddraw.add(leaderboard);
		
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new gameLoop(), Delay, Interval);
		setVisible(true);
	}
	
	/**
	 * Metoda rysuj�ca ponownie plansze gry.
	 */
	public void draw() {
		boarddraw.repaint();
	}
	
	/**
	 * Klasa odpowiedzialna za p�tle rysowania grafiki gry.
	 *
	 */
	private class gameLoop extends TimerTask {
     
		/**
		 * Metoda zwi�zana ze startem w�tku, rysuje na bierz�co plansze gry i aktualizuje stan �y� i punkt�w gracza.
		 */
		@Override
		public void run() {
			boarddraw.repaint();
			points.setText("Punkty - "+String.valueOf(game.pacman.points));
			lives.setText("�ycia - "+String.valueOf(game.pacman.lives));
		}

	}
	
	/**
	 * Metoda obs�uguj�ca wydarzenie wpisania mo�liwego do zapisania w unicodzie znaku na klawiaturze.
	 */
	public void keyTyped(KeyEvent e) {
		PacmanMove(e);
	}
	/**
	 * Metoda obs�uguj�ca wydarzenie naci�ni�cia dowolnego przycisku na klawiaturze.
	 */
	public void keyPressed(KeyEvent e) {
		PacmanMove(e);
	}
	/**
	 * Metoda obs�uguj�ca wydarzenie puszczenia dowolnego przycisku na klawiaturze
	 */
	public void keyReleased(KeyEvent e) {
		PacmanMove(e);
	}
	
	
	/**
	 * Metoda wywo�uj�ca odpowiednie reakcje gry na naci�ni�ty przycisk przez gracza.
	 * @param e - wci�ni�ty przycisk na klawiaturze
	 */
	public void PacmanMove(KeyEvent e) {
		int Key=e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_W:
			game.pacman.actualMove=Moves.UP;
			break;
		case KeyEvent.VK_S:
			game.pacman.actualMove=Moves.DOWN;
			break;
		case KeyEvent.VK_A:
			game.pacman.actualMove=Moves.LEFT;
			break;
		case KeyEvent.VK_D:
			game.pacman.actualMove=Moves.RIGHT;
			break;
		case KeyEvent.VK_ESCAPE:
			dispose();
			System.exit(0);
			break;
		}
	}
	
	/**
	 * Metoda odpowiedzialna za wy�wietlenie okna zapisania wyniku, nowej gry oraz aktualizacji tabeli wynik�w, gdy gracz straci wszystkie �ycia.
	 */
	private void EndGame() {
		GameOver end= new GameOver(game.pacman.points,ranks);
		while (end.isOpen) {
			try 
			{
				TimeUnit.MILLISECONDS.sleep(250);
			}
			catch(InterruptedException ex)
			{
				System.out.println("Failed to wait 250 ms in EndGame.");
			}
		}
		boarddraw.remove(ranking);
		ranking = ranks.RankingList();
		ranking.setBounds(580,200,200,176);
		boarddraw.add(ranking);
		RestartWindow newgame = new RestartWindow();
		while(newgame.isOpen) {
			try 
			{
				TimeUnit.MILLISECONDS.sleep(250);
			}
			catch(InterruptedException ex)
			{
				System.out.println("Failed to wait 250 ms in EndGame.");
			}
		}
		
		if (!newgame.isNewGame) {
			dispose();
			System.exit(0);
		}
		
	}
	
	
	/**
	 * Metoda wy�apuj�ca sytuacje, kt�re wymagaj� wi�kszych zmian w grze i w grafice.
	 * Wy�apuje i obs�uguje sytuacje, gdy gracz uko�czy dany poziom(zbierze wszystkie punkty) oraz 
	 * gdy graczowi sko�cz� sie �ycia.
	 */
	protected void CatchEvent() {
		while(true) {
			while(true) {
				try 
				{
					TimeUnit.MILLISECONDS.sleep(250);
				}
				catch(InterruptedException ex)
				{
					System.out.println("Failed to wait 250 ms in CatchEvent.");
				}
				//System.out.println(game.board.maxpoints);
				if (game.board.maxpoints<=0) break;
				if (game.pacman.lives<=0) break;
			}
			if (game.pacman.lives<=0) {
				EndGame();
				try 
				{
					TimeUnit.MILLISECONDS.sleep(250);
				}
				catch(InterruptedException ex)
				{
					System.out.println("Failed to wait 250 ms in CatchEvent.");
				}
				game.gamereset();
			}
			//System.out.println("TUtaj");
			if (game.board.maxpoints<=0) {
				game.nextMap();
			}
			
		}
	}
}





/**
 * Klasa odpowiedzialna za rysowanie planszy gry.
 *
 */
@SuppressWarnings("serial")
class BoardDraw extends JPanel {
	
	/**
	 * Pole okre�laj�ce szeroko�� jednego pola.
	 */
	private static final int field_width = 30;
	/**
	 * Pole okre�laj�ce wysoko�� jednego pola.
	 */
	private static final int field_height = 30;
	/**
	 * Pole okre�laj�ce odleg�o�ci mi�dzy polami.
	 */
	private static final int field_differences = 30;
	
	/**
	 * Pole odpowiedzialne za plansze, kt�ra b�dzie rysowana.
	 */
	Board board;
	
	/**
	 * Kontruktor kalsy BoardDraw ustawiaj�cy wskazan� plansz� jako t� do rysowania
	 * @param board - plansza do narysowania
	 */
	public BoardDraw(Board board) {
		this.board=board;
	}
	
	/**
	 * Metoda odpowiedzialna za rysowanie wszystkich p�l na planszy gry w zale�no�ci od obiekt�w,
	 * kt�re znajduj� si� na danym polu.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i=0; i< board.Width;++i) {
			for (int j=0;j<board.Height;++j) {
				if (board.board[i][j].contains(BoardObjects.Wall)) {
					g.setColor(Color.BLUE);
					g.fillRect(i*field_differences,j*field_differences,field_width,field_height);
				}
				if (board.board[i][j].contains(BoardObjects.Empty)) {
					g.setColor(Color.BLACK);
					g.fillRect(i*field_differences,j*field_differences,field_width,field_height);
				}
				if (board.board[i][j].contains(BoardObjects.Point)) {
					g.setColor(Color.YELLOW);
					g.fillOval(i*field_differences+10,j*field_differences+10,field_width/3,field_height/3);
				}
				if (board.board[i][j].contains(BoardObjects.Pacman)) {
					g.setColor(Color.YELLOW);
					g.fillRect(i*field_differences,j*field_differences,field_width,field_height);
				}
				if (board.board[i][j].contains(BoardObjects.Pinky)) {
					g.setColor(Color.PINK);
					g.fillRect(i*field_differences,j*field_differences,field_width,field_height);
				}
				if (board.board[i][j].contains(BoardObjects.Clyde)) {
					g.setColor(Color.ORANGE);
					g.fillRect(i*field_differences,j*field_differences,field_width,field_height);
				}
				if (board.board[i][j].contains(BoardObjects.Blinky)) {
					g.setColor(Color.RED);
					g.fillRect(i*field_differences,j*field_differences,field_width,field_height);
				}
				
			}
		}
	}
}


