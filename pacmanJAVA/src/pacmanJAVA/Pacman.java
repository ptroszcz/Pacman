package pacmanJAVA;

/**
 * Klasa odpowiedzialna za reprezentacj� Pacmana w grze.
 *
 */
public class Pacman extends MovingObjects{
	
	/**
	 * Pole okre�laj�ce aktualny wynik gracza
	 */
	public int points = 0;
	/**
	 * Pole okre�laj�ce pozosta�e �ycia gracza
	 */
	public int lives = 3;
	/**
	 * Pole okre�laj�ce aktualny ruch w jakim porusza si� Pacman
	 */
	protected Moves actualMove = null;

	
	
	/**
	 * Kontruktor klasy Pacman, inicjuj�cy wszystkie pola wed�ug wskazanje gry, kt�rego Pacman dotyczy
	 * @param game - gra, kt�rej dotyczy Pacman
	 */
	public Pacman(Game game) {
		super(game,BoardObjects.Pacman);
	}
	
	
	/**
	 * Metoda odpowiedzialna za ruch Pacmana w stron� jak� gracz wybra� naci�ni�tym przyciskiem.
	 * Dodatkowo metoda odpowiedzialna jest za sprawdzanie kolizji Pacmana z duchami i w razie jej wyst�pienia
	 * wywo�anie odpowiednich dzia�a�.
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
	 * Metoda resetuj�ca na null stron�, w kt�r� porusza si� Pacman.
	 */
	@Override
	protected void defaultValues() {
		actualMove = null;
	}
	
	
	
}
