package pacmanJAVA;

import java.util.Random;

/**
 * Klasa odpowiedzialna za reprezentacjê ducha Clyde w grze.
 *
 */
public class Clyde extends MovingObjects implements Ghosts{
	/**
	 * Pole okreœlaj¹ce liczbê ruchów po jakiej duch zaczyna poruszaæ siê zgodnie ze swoim algorytmem SI
	 */
	private int Moves_to_Ai; 
	
	/**
	 * Kontruktor klasy Clyde, inicjuj¹cy wszystkie pola wed³ug wskazanje gry, którego Clyde dotyczy
	 * @param game - gra, której dotyczy Clyde
	 */
	public Clyde(Game game) {
		super(game,BoardObjects.Clyde);
		this.Moves_to_Ai=5;
	}
	
	/**
	 * Metoda poruszaj¹ca ducha na pocz¹tku w œciœle okreœlonym kierunku, a po 5 ruchach
	 * generuj¹ca dozwolone ruchy losowo.
	 */
	public synchronized void move() {
		if (game.pacman.actualMove==null)
		{}
		else if (this.Moves_to_Ai>0) {
			Moves move = Moves.UP;
			switch(this.Moves_to_Ai) {
			case 3:
				move=Moves.LEFT;
				break;
			}
			move(move);
			this.Moves_to_Ai--;
		}
		else {
			Moves move = MoveGenerator();
			move(move);
		}
	}
	
	/**
	 * Metoda generuj¹ca dozwolony ruch w losow¹ stronê.
	 */
	public Moves MoveGenerator() {
		Random chosen = new Random();
		boolean found=false;
		Moves move=Moves.DOWN;
		while(!found) {
			int chose = chosen.nextInt(4);
			switch (chose) {
			case 0:
				move= Moves.UP;
				break;
			case 1:
				move= Moves.DOWN;
				break;
			case 2:
				move= Moves.RIGHT;
				break;
			case 3:
				move= Moves.LEFT;
				break;
			}
			if (isMoveAllowed(move))
				found=true;
			if (move==Moves.DOWN && ((this.x==9)&&(this.y==8))) // nie wracanie do bazy przez duchy
				found=false;
		}
		return move;
	}
	/**
	 * Metoda resetuj¹ca liczbê ruchów bez SI ducha.
	 */
	protected void defaultValues() {
		Moves_to_Ai=5;
	}

}
