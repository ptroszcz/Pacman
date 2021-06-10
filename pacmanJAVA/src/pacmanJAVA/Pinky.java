package pacmanJAVA;

import java.util.Random;

/**
 * Klasa odpowiedzialna za reprezentacjê ducha Pinky w grze.
 *
 */
public class Pinky extends MovingObjects implements Ghosts{
	/**
	 * Pole okreœlaj¹ce liczbê ruchów po jakiej duch zaczyna poruszaæ siê zgodnie ze swoim algorytmem SI
	 */
	private int Moves_to_Ai; 
	
	/**
	 * Pole okreœlaj¹ce liczbê wykonanych ruchów w celu wyboru sposobu poruszania siê ducha
	 */
	private int Moves_to_change;
	
	
	/**
	 * Kontruktor klasy Pinky, inicjuj¹cy wszystkie pola wed³ug wskazanje gry, którego Pinky dotyczy
	 * @param game - gra, której dotyczy Pinky
	 */
	public Pinky(Game game) {
		super(game,BoardObjects.Pinky);
		this.Moves_to_Ai=6;
		this.Moves_to_change=0;
		
	}
	
	/**
	 * Metoda poruszaj¹ca ducha na pocz¹tku w œciœle okreœlonym kierunku, a po 6 ruchach
	 * generuj¹ca dozwolone ruchy zgodnie z jego algorytmem SI.
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
	 * Generuje kolejno co 10 ruchów ruch zgodnie z algorytmem poruszania siê ducha Blinky oraz ducha Clyde.
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
	 * Metoda resetuj¹ca liczbê ruchów bez SI ducha.
	 */
	protected void defaultValues() {
		Moves_to_Ai=6;
		Moves_to_change=0;
	}

}