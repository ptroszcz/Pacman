package pacmanJAVA;



/**
 * G³ówna klasa programu, odpowiedzialna za uruchomienie wszystkich 
 * elementów dzia³ania gry
 * @author Pawe³ Troszczyñski 248925
 */
public class Main {
	
	/**
	 * Metoda klasy Main odpowiedzialna za poprawny start programu
	 * @param args argumenty wywo³ania programu
	 */
	public static void main(String[] args) {
		Game game = new Game();
		GameGraphic graphic = new GameGraphic(game);
		game.start();
		graphic.CatchEvent();

	}

}
