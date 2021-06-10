package pacmanJAVA;

/**
 * Interfejs reprezentuj�cy duchy przeszkadzaj�ce Pacmanowi.
 *
 */
public interface Ghosts {
	
	/** 
	 * Metoda generuj�ca ruch dla ducha.
	 * @return Obiekty klasy Moves - mo�liwy ruch jaki powinien wykona� duch w nast�pnej kolejno�ci.
	 */
	public Moves MoveGenerator();
}
