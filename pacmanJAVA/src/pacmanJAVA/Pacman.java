package pacmanJAVA;

/**
 * Klasa odpowiedzialna za reprezentacjê Pacmana w grze.
 *
 */
public class Pacman extends MovingObjects{
	
	/**
	 * Pole okreœlaj¹ce aktualny wynik gracza
	 */
	public int points = 0;
	/**
	 * Pole okreœlaj¹ce pozosta³e ¿ycia gracza
	 */
	public int lives = 3;
	/**
	 * Pole okreœlaj¹ce aktualny ruch w jakim porusza siê Pacman
	 */
	protected Moves actualMove = null;

	
	
	/**
	 * Kontruktor klasy Pacman, inicjuj¹cy wszystkie pola wed³ug wskazanje gry, którego Pacman dotyczy
	 * @param game - gra, której dotyczy Pacman
	 */
	public Pacman(Game game) {
		super(game,BoardObjects.Pacman);
	}
	
	
	/**
	 * Metoda odpowiedzialna za ruch Pacmana w stronê jak¹ gracz wybra³ naciœniêtym przyciskiem.
	 * Dodatkowo metoda odpowiedzialna jest za sprawdzanie kolizji Pacmana z duchami i w razie jej wyst¹pienia
	 * wywo³anie odpowiednich dzia³añ.
	 */
	@Override
	public synchronized void move() {
		if (actualMove!=null) {
			if (game.board.board[x][y].contains(BoardObjects.Clyde) ||
				game.board.board[x][y].contains(BoardObjects.Pinky)||
				game.board.board[x][y].contains(BoardObjects.Blinky)) {
				lives--;
				game.reset();
				return;
			}
			if (isMoveAllowed(actualMove)) {
				move(actualMove);
				if (game.board.board[x][y].contains(BoardObjects.Point)) {
					points+=10;
					game.board.board[x][y].remove(BoardObjects.Point);
					game.board.maxpoints--;
				}
			}
			if (game.board.board[x][y].contains(BoardObjects.Clyde) ||
				game.board.board[x][y].contains(BoardObjects.Pinky)||
				game.board.board[x][y].contains(BoardObjects.Blinky)) {
				lives--;
				game.reset();
				return;
			}
		}
	}
	
	/**
	 * Metoda resetuj¹ca na null stronê, w któr¹ porusza siê Pacman.
	 */
	@Override
	protected void defaultValues() {
		actualMove = null;
	}
	
	
	
}
