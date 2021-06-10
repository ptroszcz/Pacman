package pacmanJAVA;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Abstrakcyjna klasa odpowiedzialna za w�tki, odpowiedzialne za wszystkie ruchome obiekty
 * na planszy tj. Pacmana oraz duch�w
 *
 */
public abstract class MovingObjects implements Runnable{
	
	/**
	 * Timer odpowiedzialny za cz�stotliwo�� poruszania si� obiekt�w na planszy.
	 */
	protected Timer timer;
	/**
	 * Pole odpowiadajace op�znieniu timera.
	 */
	protected final int delay = 200;
	/**
	 * Pole odpowiadajace interwalowi timera.
	 */
	protected final int interval = 250;
	/**
	 * Aktualna warto�� wsp�rz�dnej x obiektu, kt�rego ten obiekt dotyczy
	 */
	protected int x=-1;
	/**
	 * Aktualna warto�� wsp�rz�dnej y obiektu, kt�rego ten obiekt dotyczy
	 */
	protected int y=-1;
	/**
	 * Gra, kt�rej dotycz� obiekty ruchome.
	 */
	protected Game game;
	/**
	 * Rodzaj obiektu ruchomego jakiego ten obiekt dotyczy
	 */
	protected BoardObjects type;
	
	
	/**
	 * Kontruktor klasy MovingObjects okre�laj�cy konkretny typ danego obiektu oraz gry, kt�rej ten obiekt dotyczy.
	 * @param game - gra, w kt�rej sk�adzie znajduje si� dany obiekt
	 * @param type - typ tworzonego obiektu
	 */
	protected MovingObjects(Game game, BoardObjects type) {
		this.game=game;
		this.type=type;
		this.set_starting_point(type);
	}
	
	/**
	 * Ustawia pole x oraz y na te okre�laj�ce domy�ln� pozycj� obiektu ruchomego wskazanego w parametrze 
	 * @param type - typ obiektu, kt�rego domy�lne pole ma zosta� ustawione
	 */
	public void set_starting_point(BoardObjects type) {
		switch (type){
		case Pacman:
			this.x=Board.DefaultPacman[0];
			this.y=Board.DefaultPacman[1];
			break;
		case Pinky:
			this.x=Board.DefaultPinky[0];
			this.y=Board.DefaultPinky[1];
			break;
		case Blinky:
			this.x=Board.DefaultBlinky[0];
			this.y=Board.DefaultBlinky[1];
			break;
		case Clyde:
			this.x=Board.DefaultClyde[0];
			this.y=Board.DefaultClyde[1];
			break;
		default:
			throw new IllegalArgumentException("The chosen object can't be a MovingObject");
		}
	}
	
	/**
	 * Metoda odpowiedzialna za start w�tku.
	 */
	public void run() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new gameLoop(), delay, interval);
	}
	
	/**
	 * Metoda abstrakcyjna odpowiedzialna za ruch obiektu.
	 */
	protected abstract void move();
	
	/**
	 * Metoda abstrakcyjna odpowiedzialna za ustawienie warto�ci domy�lnych aktualnego obiektu ruchomego.
	 */
	protected abstract void defaultValues();
	
	/**
	 * Klasa odpowiedzialna za p�tl� dzia�ania w�tku.
	 *
	 */
	protected class gameLoop extends TimerTask {
		
		/**
		 * Metoda odpowiedzialna za dzia�anie w�tku.
		 */
		@Override
		public void run() {
			move();
		}
	}
	
	/**
	 * Metoda sprawdzaj�ca czy wskazany ruch jest dozwolony
	 * @param move - sprawdzany ruch
	 * @return true - je�eli ruch jest dozwolony, false - je�eli ruch nie jest dozwolony
	 */
	protected synchronized boolean isMoveAllowed(Moves move) {
		switch (move) {
		case UP:
			if (y-1>0 && !(game.board.board[x][y-1].contains(BoardObjects.Wall)))
				return true;
			break;
		case DOWN:
			if (y+1>0 && !(game.board.board[x][y+1].contains(BoardObjects.Wall)))
				return true;
			break;
		case RIGHT:
			if (x+1>0 && !(game.board.board[x+1][y].contains(BoardObjects.Wall)))
				return true;
			break;
		case LEFT:
			if (x-1>0 && !(game.board.board[x-1][y].contains(BoardObjects.Wall)))
				return true;
			break;
		}
		return false;
	}
	
	
	/**
	 * Metoda odpowiedzialna za ruch obiektu we wskazanym kierunku.
	 * @param move - ruch jaki ma zosta� wykonany przez ruchomy obiekt
	 */
	protected synchronized void move(Moves move) {
		if (isMoveAllowed(move)) {
			switch (move) {
			case UP:
				game.board.board[x][y].remove(this.type);
				this.y=this.y-1;
				game.board.board[x][y].add(this.type);
				break;
			case DOWN:
				game.board.board[x][y].remove(this.type);
				this.y=this.y+1;
				game.board.board[x][y].add(this.type);
				break;
			case RIGHT:
				game.board.board[x][y].remove(this.type);
				this.x=this.x+1;
				game.board.board[x][y].add(this.type);
				break;
			case LEFT:
				game.board.board[x][y].remove(this.type);
				this.x=this.x-1;
				game.board.board[x][y].add(this.type);
				break;
			}
		}
	}
	
	
	/**
	 * Metoda odpowiedzialna za dzia�anie na w�tkach ruchomych obiekt�w  w przypadku wygrania planszy przez gracza
	 */
	public synchronized void win() {
		timer.cancel();
		timer.purge();
		Random map = new Random();
		
		game.board.generateBoard(map.nextInt(3));
		this.set_starting_point(type);
	}
	
	/**
	 * Metoda odpowiedzialna za dzia�anie na w�tkach ruchomych obiekt�w  w przypadku utraty wszystkich �y� przez gracza
	 */
	public synchronized void end() {
		timer.cancel();
		timer.purge();
		Random map = new Random();
		game.board.generateBoard(map.nextInt(3));
		this.set_starting_point(type);
	}
	/**
	 * Metoda odpowiedzialna za wznowienie dzia�ania w�tku ruchomego obiektu
	 */
	public void resume() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new gameLoop(), delay, interval);
	}
	
	/**
	 * Metoda odpowiedzialna za zrestartowanie po�o�enia obiektu ruchomego i zatrzymanie w�tku
	 */
	public synchronized void reset() {
		timer.cancel();
		timer.purge();
		game.board.board[x][y].remove(type);
		this.set_starting_point(type);
		game.board.board[x][y].add(type);
	}

}
