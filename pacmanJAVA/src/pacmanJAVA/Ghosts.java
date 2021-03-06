package pacmanJAVA;

/**
 * Interfejs reprezentujący duchy przeszkadzające Pacmanowi.
 *
 */
public interface Ghosts {
	
	/** 
	 * Metoda generująca ruch dla ducha.
	 * @return Obiekty klasy Moves - możliwy ruch jaki powinien wykonać duch w następnej kolejności.
	 */
	public Moves MoveGenerator();
}
