package pacmanJAVA;

import java.util.Random;

/**
 * Klasa odpowiedzialna za reprezentacj� ducha Clyde w grze.
 *
 */
public class Clyde extends MovingObjects implements Ghosts{
	/**
	 * Pole okre�laj�ce liczb� ruch�w po jakiej duch zaczyna porusza� si� zgodnie ze swoim algorytmem SI
	 */
	private int Moves_to_Ai; 
	
	/**
	 * Kontruktor klasy Clyde, inicjuj�cy wszystkie pola wed�ug wskazanje gry, kt�rego Clyde dotyczy
	 * @param game - gra, kt�rej dotyczy Clyde
	 */
	public Clyde(Game game) {
		super(game,BoardObjects.Clyde);
		this.Moves_to_Ai=5;
	}
	
	/**
	 * Metoda poruszaj�ca ducha na pocz�tku w �ci�le okre�lonym kierunku, a po 5 ruchach
	 * generuj�ca dozwolone ruchy losowo.
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
	 * Metoda generuj�ca dozwolony ruch w losow� stron�.
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
	 * Metoda resetuj�ca liczb� ruch�w bez SI ducha.
	 */
	protected void defaultValues() {
		Moves_to_Ai=5;
	}

}
