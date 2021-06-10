package pacmanJAVA;

import java.util.Random;

/**
 * Klasa odpowiedzialna za reprezentacj� ducha Blinky w grze.
 *
 */
public class Blinky extends MovingObjects implements Ghosts {
	/**
	 * Pole okre�laj�ce liczb� ruch�w po jakiej duch zaczyna porusza� si� zgodnie ze swoim algorytmem SI
	 */
	private int Moves_to_Ai; 
	/**
	 * Licznik ruch�w przechowywany w celu u�atwienia("og�upienie" sztucznej inteligencji) sposobu poruszania si� tego ducha
	 */
	private int facilitator;
	

	/**
	 * Kontruktor klasy Blinky, inicjuj�cy wszystkie pola wed�ug wskazanje gry, kt�rego Blinky dotyczy
	 * @param game - gra, kt�rej dotyczy Blinky
	 */
	public Blinky(Game game) {
		super(game,BoardObjects.Blinky);
		this.Moves_to_Ai=6;
		this.facilitator=1;
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
			case 4:
				move=Moves.RIGHT;
				break;
			case 1:
				move=Moves.LEFT;
				break;
			}
			move(move);
			this.Moves_to_Ai--;
		}
		else {
			Moves move = MoveGenerator();
			move(move);
			facilitator++;
		}
	}
	
	/**
	 * Generuje ruch, kt�ry pozwoli si� duchowi przybli�y� do Pacmana. Je�eli niemo�liwe lub jest to ruch
	 * o numerze b�d�cym wielokrotn��ci� 10 generuje losowy dozwolony ruch.
	 */
	public Moves MoveGenerator() {
		Moves move = Moves.UP;
		boolean stupefaction=false;
		if ((this.facilitator%10)==0) {//"og�upienie" SI, co 10 ruch wykona losowy ruch zamiast poprawnego
			stupefaction=true;
		}
		if (!stupefaction && game.pacman.y>this.y && isMoveAllowed(Moves.DOWN) && !((this.x==9)&&(this.y==8))) {
			move=Moves.DOWN;
		}
		else if (!stupefaction && game.pacman.y<this.y && isMoveAllowed(Moves.UP)) {
			move=Moves.UP;
		}
		else if (!stupefaction && game.pacman.x<this.x && isMoveAllowed(Moves.LEFT)) {
			move=Moves.LEFT;
		}
		else if (!stupefaction && game.pacman.x>this.x && isMoveAllowed(Moves.RIGHT))
			move=Moves.RIGHT;
		else { //Nie znaleziono dozwolonego ruchu, wi�c zostanie on wylosowany
			Random chosen = new Random();
			boolean found=false;
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
		facilitator=1;
	}

}
