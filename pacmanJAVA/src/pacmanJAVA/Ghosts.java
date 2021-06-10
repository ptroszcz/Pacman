package pacmanJAVA;

/**
 * Interfejs reprezentuj¹cy duchy przeszkadzaj¹ce Pacmanowi.
 *
 */
public interface Ghosts {
	
	/** 
	 * Metoda generuj¹ca ruch dla ducha.
	 * @return Obiekty klasy Moves - mo¿liwy ruch jaki powinien wykonaæ duch w nastêpnej kolejnoœci.
	 */
	public Moves MoveGenerator();
}
