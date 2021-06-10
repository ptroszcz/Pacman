package pacmanJAVA;



/**
 * G��wna klasa programu, odpowiedzialna za uruchomienie wszystkich 
 * element�w dzia�ania gry
 * @author Pawe� Troszczy�ski 248925
 */
public class Main {
	
	/**
	 * Metoda klasy Main odpowiedzialna za poprawny start programu
	 * @param args argumenty wywo�ania programu
	 */
	public static void main(String[] args) {
		Game game = new Game();
		GameGraphic graphic = new GameGraphic(game);
		game.start();
		graphic.CatchEvent();

	}

}
