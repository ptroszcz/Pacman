package pacmanJAVA;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Abstrakcyjna klasa odpowiedzialna za w¹tki, odpowiedzialne za wszystkie ruchome obiekty
 * na planszy tj. Pacmana oraz duchów
 *
 */
public abstract class MovingObjects implements Runnable{
	
	/**
	 * Timer odpowiedzialny za czêstotliwoœæ poruszania siê obiektów na planszy.
	 */
	protected Timer timer;
	/**
	 * Pole odpowiadajace opóznieniu timera.
	 */
	protected final int delay = 200;
	/**
	 * Pole odpowiadajace interwalowi timera.
	 */
	protected final int interval = 250;
	/**
	 * Aktualna wartoœæ wspó³rzêdnej x obiektu, którego ten obiekt dotyczy
	 */
	protected int x=-1;
	/**
	 * Aktualna wartoœæ wspó³rzêdnej y obiektu, którego ten obiekt dotyczy
	 */
	protected int y=-1;
	/**
	 * Gra, której dotycz¹ obiekty ruchome.
	 */
	protected Game game;
	/**
	 * Rodzaj obiektu ruchomego jakiego ten obiekt dotyczy
	 */
	protected BoardObjects type;
	
	
	/**
	 * Kontruktor klasy MovingObjects okreœlaj¹cy konkretny typ danego obiektu oraz gry, której ten obiekt dotyczy.
	 * @param game - gra, w której sk³adzie znajduje siê dany obiekt
	 * @param type - typ tworzonego obiektu
	 */
	protected MovingObjects(Game game, BoardObjects type) {
		this.game=game;
		this.type=type;
		this.set_starting_point(type);
	}
	
	/**
	 * Ustawia pole x oraz y na te okreœlaj¹ce domyœln¹ pozycjê obiektu ruchomego wskazanego w parametrze 
	 * @param type - typ obiektu, którego domyœlne pole ma zostaæ ustawione
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
	 * Metoda odpowiedzialna za start w¹tku.
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
	 * Metoda abstrakcyjna odpowiedzialna za ustawienie wartoœci domyœlnych aktualnego obiektu ruchomego.
	 */
	protected abstract void defaultValues();
	
	/**
	 * Klasa odpowiedzialna za pêtlê dzia³ania w¹tku.
	 *
	 */
	protected class gameLoop extends TimerTask {
		
		/**
		 * Metoda odpowiedzialna za dzia³anie w¹tku.
		 */
		@Override
		public void run() {
			move();
		}
	}
	
	/**
	 * Metoda sprawdzaj¹ca czy wskazany ruch jest dozwolony
	 * @param move - sprawdzany ruch
	 * @return true - je¿eli ruch jest dozwolony, false - je¿eli ruch nie jest dozwolony
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
	 * @param move - ruch jaki ma zostaæ wykonany przez ruchomy obiekt
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
	 * Metoda odpowiedzialna za dzia³anie na w¹tkach ruchomych obiektów  w przypadku wygrania planszy przez gracza
	 */
	public synchronized void win() {
		timer.cancel();
		timer.purge();
		Random map = new Random();
		
		game.board.generateBoard(map.nextInt(3));
		this.set_starting_point(type);
	}
	
	/**
	 * Metoda odpowiedzialna za dzia³anie na w¹tkach ruchomych obiektów  w przypadku utraty wszystkich ¿yæ przez gracza
	 */
	public synchronized void end() {
		timer.cancel();
		timer.purge();
		Random map = new Random();
		game.board.generateBoard(map.nextInt(3));
		this.set_starting_point(type);
	}
	/**
	 * Metoda odpowiedzialna za wznowienie dzia³ania w¹tku ruchomego obiektu
	 */
	public void resume() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new gameLoop(), delay, interval);
	}
	
	/**
	 * Metoda odpowiedzialna za zrestartowanie po³o¿enia obiektu ruchomego i zatrzymanie w¹tku
	 */
	public synchronized void reset() {
		timer.cancel();
		timer.purge();
		game.board.board[x][y].remove(type);
		this.set_starting_point(type);
		game.board.board[x][y].add(type);
	}

}
