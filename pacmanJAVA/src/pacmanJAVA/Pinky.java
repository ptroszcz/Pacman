package pacmanJAVA;

import java.util.Random;

/**
 * Klasa odpowiedzialna za reprezentacj� ducha Pinky w grze.
 *
 */
public class Pinky extends MovingObjects implements Ghosts{
	/**
	 * Pole okre�laj�ce liczb� ruch�w po jakiej duch zaczyna porusza� si� zgodnie ze swoim algorytmem SI
	 */
	private int Moves_to_Ai; 
	
	/**
	 * Pole okre�laj�ce liczb� wykonanych ruch�w w celu wyboru sposobu poruszania si� ducha
	 */
	private int Moves_to_change;
	
	
	/**
	 * Kontruktor klasy Pinky, inicjuj�cy wszystkie pola wed�ug wskazanje gry, kt�rego Pinky dotyczy
	 * @param game - gra, kt�rej dotyczy Pinky
	 */
	public Pinky(Game game) {
		super(game,BoardObjects.Pinky);
		this.Moves_to_Ai=6;
		this.Moves_to_change=0;
		
	}
	
	/**
	 * Metoda poruszaj�ca ducha na pocz�tku w �ci�le okre�lonym kierunku, a po 6 ruchach
	 * generuj�ca dozwolone ruchy zgodnie z jego algorytmem SI.
	 */
	public synchronized void move() {
		if (game.pacman.actualMove==null)
		{}
		else if (this.Moves_to_Ai>0) {
			Moves move = Moves.UP;
			switch(this.Moves_to_Ai) {
			case 5:
				move=Moves.LEFT;
				break;
			case 2:
				move=Moves.RIGHT;
				break;
			}
			move(move);
			this.Moves_to_Ai--;
		}
		else {
			Moves move = MoveGenerator();
			move(move);
			this.Moves_to_change++;
		}
	}
	
	/**
	 * Generuje kolejno co 10 ruch�w ruch zgodnie z algorytmem poruszania si� ducha Blinky oraz ducha Clyde.
	 */
	public Moves MoveGenerator() {
		Moves move = Moves.UP;
		boolean found = false;
		if (((this.Moves_to_change/10)%2)==0) {
			if (game.pacman.y>this.y && isMoveAllowed(Moves.DOWN) && !((this.x==9)&&(this.y==8))) {
				move=Moves.DOWN;
				found=true;
			}
			else if (game.pacman.y<this.y && isMoveAllowed(Moves.UP)) {
				move=Moves.UP;
				found=true;
			}
			else if (game.pacman.x<this.x && isMoveAllowed(Moves.LEFT)) {
				move=Moves.LEFT;
				found=true;
			}
			else if (game.pacman.x>this.x && isMoveAllowed(Moves.RIGHT))
				move=Moves.RIGHT;
			found=true;
		}
		if (!found) {
			Random chosen = new Random();
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
		}
		return move;
	}
	
	/**
	 * Metoda resetuj�ca liczb� ruch�w bez SI ducha.
	 */
	protected void defaultValues() {
		Moves_to_Ai=6;
		Moves_to_change=0;
	}

}